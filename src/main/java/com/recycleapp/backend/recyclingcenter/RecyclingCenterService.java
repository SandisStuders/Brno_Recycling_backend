package com.recycleapp.backend.recyclingcenter;

import com.recycleapp.backend.enums.API_SOURCES;
import com.recycleapp.backend.externalcontentparsers.ApiContentParser;
import com.recycleapp.backend.recyclinglocation.RecyclingLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecyclingCenterService {

    private final RecyclingCenterRepository recyclingCenterRepository;

    @Autowired
    public RecyclingCenterService(RecyclingCenterRepository recyclingCenterRepository) {
        this.recyclingCenterRepository = recyclingCenterRepository;
    }

    public String updateRecyclingCenters() throws URISyntaxException, IOException {
        recyclingCenterRepository.deleteAll();

        String apiResponseWasteCenters = ApiContentParser.getApiResponse(API_SOURCES.API_WASTE_CENTERS);
        ArrayList<RecyclingLocation> wasteCenterLocationsPre = ApiContentParser.parseApiResponse(apiResponseWasteCenters, API_SOURCES.API_WASTE_CENTERS);
        ArrayList<RecyclingCenter> wasteCenterLocations = new ArrayList<>();
        for (RecyclingLocation recyclingLocation : wasteCenterLocationsPre) {
            RecyclingCenter recyclingCenter = (RecyclingCenter) recyclingLocation;
            wasteCenterLocations.add(recyclingCenter);
        }

       recyclingCenterRepository.saveAll(wasteCenterLocations);

       return "Added " + wasteCenterLocations.size() + " recycling centers to the database!";
    }

    public List<RecyclingCenter> getRecyclingCenters() {
        return recyclingCenterRepository.findAll();
    }

}
