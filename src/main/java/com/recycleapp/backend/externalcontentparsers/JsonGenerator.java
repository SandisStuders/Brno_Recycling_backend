package com.recycleapp.backend.externalcontentparsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.recycleapp.backend.enums.API_SOURCES;
import com.recycleapp.backend.recyclingcenter.RecyclingCenter;
import com.recycleapp.backend.recyclingcontainer.RecyclingContainer;
import com.recycleapp.backend.recyclinglocation.RecyclingLocation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class JsonGenerator {

    public static void getJsonFileFromApi(String filePath, String cervenaFilePath) throws URISyntaxException, IOException {
        String apiResponseContainers = ApiContentParser.getApiResponse(API_SOURCES.API_WASTE_CONTAINERS);
        ArrayList<RecyclingLocation> containerLocationsPre = ApiContentParser.parseApiResponse(apiResponseContainers, API_SOURCES.API_WASTE_CONTAINERS);
        ArrayList<RecyclingContainer> containerLocations = new ArrayList<>();
        for (RecyclingLocation recyclingLocation : containerLocationsPre) {
            RecyclingContainer recyclingContainer = (RecyclingContainer) recyclingLocation;
            containerLocations.add(recyclingContainer);
        }

        ArrayList<String[]> lines = HtmlContentParser.getCsvContent(cervenaFilePath);
        ArrayList<RecyclingLocation> cervenaContainerLocations = HtmlContentParser.getRecyclingLocationsFromCsvContent(lines);
        for (RecyclingLocation recyclingLocation : cervenaContainerLocations) {
            RecyclingContainer recyclingContainer = (RecyclingContainer) recyclingLocation;
            containerLocations.add(recyclingContainer);
        }

        String apiResponseWasteCenters = ApiContentParser.getApiResponse(API_SOURCES.API_WASTE_CENTERS);
        ArrayList<RecyclingLocation> wasteCenterLocationsPre = ApiContentParser.parseApiResponse(apiResponseWasteCenters, API_SOURCES.API_WASTE_CENTERS);
        ArrayList<RecyclingCenter> wasteCenterLocations = new ArrayList<>();
        for (RecyclingLocation recyclingLocation : wasteCenterLocationsPre) {
            RecyclingCenter recyclingCenter = (RecyclingCenter) recyclingLocation;
            wasteCenterLocations.add(recyclingCenter);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        ArrayNode containerNode = objectMapper.valueToTree(containerLocations);
        ArrayNode wasteCenterNode = objectMapper.valueToTree(wasteCenterLocations);

        ObjectNode outJson = objectMapper.createObjectNode();

        outJson.set("WasteContainers", containerNode);
        outJson.set("WasteCenters", wasteCenterNode);

        String jsonString = objectMapper.writeValueAsString(outJson);
        System.out.println(jsonString);

        Files.write(Paths.get(filePath), jsonString.getBytes());

    }

}

