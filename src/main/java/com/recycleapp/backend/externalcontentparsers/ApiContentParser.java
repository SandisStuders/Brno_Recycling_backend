package com.recycleapp.backend.externalcontentparsers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.recycleapp.backend.enums.*;
import com.recycleapp.backend.recyclingcenter.RecyclingCenter;
import com.recycleapp.backend.recyclingcontainer.RecyclingContainer;
import com.recycleapp.backend.recyclinglocation.RecyclingLocation;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class ApiContentParser {

        public static String getApiResponse(API_SOURCES apiSource) throws URISyntaxException, IOException {

            String url = getApiUrl(apiSource);

            URI uri = new URI(url);
            URL urlObj = uri.toURL();

            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {

                // Error handling

            } else {

                StringBuilder apiData = new StringBuilder();
                Scanner scanner = new Scanner(urlObj.openStream());

                while (scanner.hasNext()) {
                    apiData.append(scanner.nextLine());
                }
                scanner.close();

                return apiData.toString();
            }

            return null;
        }

        public static ArrayList<RecyclingLocation> parseApiResponse(String apiResponse, API_SOURCES apiSource) throws JsonProcessingException {

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(apiResponse);

            ArrayList<RecyclingLocation> recyclingLocations = new ArrayList<>();
            switch (apiSource) {
                case API_WASTE_CONTAINERS -> {
                    ArrayNode featureNode = (ArrayNode) root.path("features");

                    for (JsonNode elementNode : featureNode) {
                        String name = elementNode.path("attributes").path("nazev").asText();
                        double x = elementNode.path("geometry").path("x").asDouble();
                        double y = elementNode.path("geometry").path("y").asDouble();
                        RecyclingContainer recyclingLocation = new RecyclingContainer(name, WASTE_LOCATIONS.LOCATION_WASTE_CONTAINER, x, y);

                        String wasteType = elementNode.path("attributes").path("komodita_odpad_separovany").asText();
                        switch (wasteType) {
                            case "Biologický odpad" -> {
                                recyclingLocation.addWasteCategory(WASTE_CATEGORIES.WASTE_BIOLOGICAL);
                            }
                            case "Papír" -> {
                                recyclingLocation.addWasteCategory(WASTE_CATEGORIES.WASTE_PAPER);
                            }
                            case "Plasty, nápojové kartony a hliníkové plechovky od nápojů" -> {
                                recyclingLocation.addWasteCategory(WASTE_CATEGORIES.WASTE_PLASTIC);
                                recyclingLocation.addWasteCategory(WASTE_CATEGORIES.WASTE_METAL_FOOD_PACKAGING);
                            }
                            case "Sklo barevné" -> {
                                recyclingLocation.addWasteCategory(WASTE_CATEGORIES.WASTE_COLORED_GLASS);
                            }
                            case "Sklo bílé" -> {
                                recyclingLocation.addWasteCategory(WASTE_CATEGORIES.WASTE_WHITE_GLASS);
                            }
                            case "Textil" -> {
                                recyclingLocation.addWasteCategory(WASTE_CATEGORIES.WASTE_TEXTILE);
                                recyclingLocation.addWasteCategory(WASTE_CATEGORIES.WASTE_TOYS);
                            }
                        }

                        String wasteCollectionFrequency = elementNode.path("attributes").path("cyklus_vyvozu").asText();
                        switch (wasteCollectionFrequency) {
                            case "1x14 sudý" -> {
                                recyclingLocation.setWasteCollectionFrequency(WASTE_COLLECTION_FREQUENCY.BIWEEKLY_EVEN);
                            }
                            case "1x14 lichý" -> {
                                recyclingLocation.setWasteCollectionFrequency(WASTE_COLLECTION_FREQUENCY.BIWEEKLY_ODD);
                            }
                            case "1x měsíčně" -> {
                                recyclingLocation.setWasteCollectionFrequency(WASTE_COLLECTION_FREQUENCY.MONTHLY);
                            }
                            case "Na výzvu" -> {
                                recyclingLocation.setWasteCollectionFrequency(WASTE_COLLECTION_FREQUENCY.WHEN_CALLED);
                            }
                            case "1x týdně", "2x týdně", "3x týdně", "4x týdně", "5x týdně", "6x týdně", "7x týdně" -> {
                                recyclingLocation.setWasteCollectionFrequency(WASTE_COLLECTION_FREQUENCY.WEEKLY_OR_MORE);
                            }
                        }

                        WEEKDAYS[] weekdays = new WEEKDAYS[] {WEEKDAYS.MONDAY, WEEKDAYS.TUESDAY, WEEKDAYS.WEDNESDAY,
                                WEEKDAYS.THURSDAY, WEEKDAYS.FRIDAY, WEEKDAYS.SATURDAY, WEEKDAYS.SUNDAY};

                        ArrayList<WEEKDAYS> wasteCollectionDays = new ArrayList<>();
                        for (int i = 0; i < 7; i++) {
                            String day = elementNode.path("attributes").path("vyvoz_" + (i+1)).asText();
                            if (Objects.equals(day, "A")) {
                                wasteCollectionDays.add(weekdays[i]);
                            }
                        }
                        recyclingLocation.setWasteCollectionDays(wasteCollectionDays);

                        String locId = elementNode.path("attributes").path("GlobalID").asText();
                        recyclingLocation.setLocId(locId);
                        String owner = elementNode.path("attributes").path("majitel").asText();
                        recyclingLocation.setOwner(owner);

                        recyclingLocations.add(recyclingLocation);
                    }
                }
                case API_WASTE_CENTERS -> {
                    ArrayNode featureNode = (ArrayNode) root.path("features");

                    for (JsonNode elementNode : featureNode) {
                        String name = elementNode.path("attributes").path("nazev_sso").asText();
                        double x = elementNode.path("geometry").path("x").asDouble();
                        double y = elementNode.path("geometry").path("y").asDouble();
                        RecyclingCenter recyclingLocation = new RecyclingCenter(name, WASTE_LOCATIONS.LOCATION_WASTE_CENTER, x, y);

                        String locId = elementNode.path("attributes").path("GlobalID").asText();
                        recyclingLocation.setLocId(locId);

                        // PLACEHOLDER VALUES
                        recyclingLocation.addWasteCategory(WASTE_CATEGORIES.WASTE_PLASTIC);
                        recyclingLocation.setOwner("SAKO");
                        recyclingLocation.setOpeningHours("24/7");
                        recyclingLocation.setPhone("123-456-789");
                        // PLACEHOLDER VALUES END

                        recyclingLocations.add(recyclingLocation);
                    }
                }
                case API_LITTER_BINS -> {
                    ArrayNode featureNode = (ArrayNode) root.path("features");

                    for (JsonNode elementNode : featureNode) {
                        String district = elementNode.path("attributes").path("adresa_cast_obce").asText();
                        String street = elementNode.path("attributes").path("adresa_ulice").asText();;
                        String streetNumber = elementNode.path("attributes").path("adresa_cislo_orientacni").asText();;
                        String name = district + ", " + street + ", " + streetNumber;
                        double x = elementNode.path("geometry").path("x").asDouble();
                        double y = elementNode.path("geometry").path("y").asDouble();
                        RecyclingLocation recyclingLocation = new RecyclingLocation(name, WASTE_LOCATIONS.LOCATION_LITTER_BIN, x, y);

                        recyclingLocations.add(recyclingLocation);
                    }
                }
            }

            return recyclingLocations;
        }

        private static String getApiUrl(API_SOURCES apiSource) {
            String baseUrl = "https://services6.arcgis.com/fUWVlHWZNxUvTUh8/arcgis/rest/services/";
            switch (apiSource) {
                case API_WASTE_CONTAINERS -> {
                    return baseUrl + "kontejnery_separovany/FeatureServer/0/query?where=1%3D1&outFields=*&outSR=4326&f=json";
                }
                case API_WASTE_CENTERS -> {
                    return baseUrl + "sberna_strediska_odpadu/FeatureServer/0/query?where=1%3D1&outFields=*&outSR=4326&f=json";
                }
                case API_LITTER_BINS -> {
                    return baseUrl + "odpadkove_kose/FeatureServer/0/query?where=1%3D1&outFields=*&outSR=4326&f=json";
                }
            }
            return "";
        }

        public static ArrayList<RecyclingLocation> getPojoListFromJson(String jsonString) throws JsonProcessingException {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonString);
            ArrayList<RecyclingLocation> recyclingLocations = new ArrayList<>();
            for (JsonNode elementNode : root) {
                RecyclingLocation recyclingLocation = objectMapper.treeToValue(elementNode, RecyclingLocation.class);
                recyclingLocations.add(recyclingLocation);
            }
            return recyclingLocations;
        }
}