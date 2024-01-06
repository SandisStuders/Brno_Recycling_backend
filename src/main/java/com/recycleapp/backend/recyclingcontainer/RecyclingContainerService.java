package com.recycleapp.backend.recyclingcontainer;

import com.recycleapp.backend.enums.API_SOURCES;
import com.recycleapp.backend.externalcontentparsers.ApiContentParser;
import com.recycleapp.backend.externalcontentparsers.HtmlContentParser;
import com.recycleapp.backend.recyclinglocation.RecyclingLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecyclingContainerService {

    private final RecyclingContainerRepository recyclingContainerRepository;

    @Autowired
    public RecyclingContainerService(RecyclingContainerRepository recyclingContainerRepository) {
        this.recyclingContainerRepository = recyclingContainerRepository;
    }

    public String updateRecyclingContainers() throws URISyntaxException, IOException {
        recyclingContainerRepository.deleteAll();

        String apiResponseContainers = ApiContentParser.getApiResponse(API_SOURCES.API_WASTE_CONTAINERS);
        ArrayList<RecyclingLocation> containerLocationsPre = ApiContentParser.parseApiResponse(apiResponseContainers, API_SOURCES.API_WASTE_CONTAINERS);
        ArrayList<RecyclingContainer> containerLocations = new ArrayList<>();
        for (RecyclingLocation recyclingLocation : containerLocationsPre) {
            RecyclingContainer recyclingContainer = (RecyclingContainer) recyclingLocation;
            containerLocations.add(recyclingContainer);
        }

        ClassLoader classLoader = HtmlContentParser.class.getClassLoader();
        URL resourceUrl = classLoader.getResource("static/cervena_data.csv");
        String cervenaFilePath = URLDecoder.decode(resourceUrl.getPath(), StandardCharsets.UTF_8);
        ArrayList<String[]> lines = HtmlContentParser.getCsvContent(cervenaFilePath);
        ArrayList<RecyclingLocation> cervenaContainerLocations = HtmlContentParser.getRecyclingLocationsFromCsvContent(lines);
        for (RecyclingLocation recyclingLocation : cervenaContainerLocations) {
            RecyclingContainer recyclingContainer = (RecyclingContainer) recyclingLocation;
            containerLocations.add(recyclingContainer);
        }

        recyclingContainerRepository.saveAll(containerLocations);

        return "Added " + containerLocations.size() + " recycling centers to the database!";
    }

    public List<RecyclingContainer> getRecyclingContainers() throws URISyntaxException, IOException {
        return recyclingContainerRepository.findAll();
    }
}
