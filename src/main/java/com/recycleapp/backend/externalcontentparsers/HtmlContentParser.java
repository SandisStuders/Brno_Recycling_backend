package com.recycleapp.backend.externalcontentparsers;

import com.recycleapp.backend.enums.WASTE_CATEGORIES;
import com.recycleapp.backend.enums.WASTE_LOCATIONS;
import com.recycleapp.backend.recyclingcontainer.RecyclingContainer;
import com.recycleapp.backend.recyclinglocation.RecyclingLocation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class HtmlContentParser {

    public static ArrayList<String[]> getCsvContent(String filePath) {
        ArrayList<String[]> lines = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); // Assumes the CSV is comma-delimited
                lines.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public static ArrayList<RecyclingLocation> getRecyclingLocationsFromCsvContent(ArrayList<String[]> csvContent) {
        ArrayList<RecyclingLocation> recyclingLocations = new ArrayList<>();

        int currentId = 1;
        for (String[] line : csvContent) {
            RecyclingContainer recyclingContainer = new RecyclingContainer();
            recyclingContainer.setLocId("Cervena" + currentId);
            currentId++;

            recyclingContainer.setLocationType(WASTE_LOCATIONS.LOCATION_WASTE_CONTAINER);
            recyclingContainer.addWasteCategory(WASTE_CATEGORIES.WASTE_ELECTRONICS);

            String name = line[0];
            name = name.replace(" ;", ",");
            name = name.replace(";", ",");

            recyclingContainer.setName(name);
            recyclingContainer.setOwner(line[2]);

            double[] decimalCoordinates = transformCoordinates(line[3]);
            recyclingContainer.setYLoc(decimalCoordinates[0]);
            recyclingContainer.setXLoc(decimalCoordinates[1]);

            recyclingLocations.add(recyclingContainer);
        }

        return recyclingLocations;
    }

    public static double[] transformCoordinates(String dmsCoordinates) {
        double[] decimalCoordinates = new double[2];

        String[] splitDmsCoordinates =  dmsCoordinates.split(" ");
        splitDmsCoordinates[0] = splitDmsCoordinates[0].replace("N", "");
        splitDmsCoordinates[1] = splitDmsCoordinates[1].replace("E", "");

        double y = coordinatesDmsToDecimal(splitDmsCoordinates[0]);
        double x = coordinatesDmsToDecimal(splitDmsCoordinates[1]);

        decimalCoordinates[0] = y;
        decimalCoordinates[1] = x;
        return decimalCoordinates;
    }

    private static double coordinatesDmsToDecimal(String dmsCoordinate) {
        String[] splitDmsCoordinates = dmsCoordinate.split("°");
        String yDegrees = splitDmsCoordinates[0];
        splitDmsCoordinates = splitDmsCoordinates[1].split("'");
        String yMinutes = splitDmsCoordinates[0];
        String ySeconds = splitDmsCoordinates[1].replace("N", "");

        return Double.parseDouble(yDegrees) + Double.parseDouble(yMinutes) / 60 + Double.parseDouble(ySeconds) / 3600;
    }

}
