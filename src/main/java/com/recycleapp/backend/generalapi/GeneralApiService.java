package com.recycleapp.backend.generalapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.recycleapp.backend.recyclingcenter.RecyclingCenter;
import com.recycleapp.backend.recyclingcenter.RecyclingCenterService;
import com.recycleapp.backend.recyclingcontainer.RecyclingContainer;
import com.recycleapp.backend.recyclingcontainer.RecyclingContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class GeneralApiService {

    private final RecyclingCenterService recyclingCenterService;
    private final RecyclingContainerService recyclingContainerService;

    @Autowired
    public GeneralApiService(RecyclingCenterService recyclingCenterService,
                             RecyclingContainerService recyclingContainerService) {
        this.recyclingCenterService = recyclingCenterService;
        this.recyclingContainerService = recyclingContainerService;
    }

    public String getRecyclingLocations() throws URISyntaxException, IOException {
        List<RecyclingCenter> recyclingCenters = recyclingCenterService.getRecyclingCenters();
        List<RecyclingContainer> recyclingContainers = recyclingContainerService.getRecyclingContainers();

        String recyclingCenterInfo = "Found " + recyclingCenters.size() + " recycling centers!\n";
        String recyclingContainerInfo = "Found " + recyclingContainers.size() + " recycling containers!\n";
        System.out.println(recyclingCenterInfo + recyclingContainerInfo);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        ArrayNode containerNode = objectMapper.valueToTree(recyclingContainers);
        ArrayNode wasteCenterNode = objectMapper.valueToTree(recyclingCenters);

        ObjectNode outJson = objectMapper.createObjectNode();

        outJson.set("WasteContainers", containerNode);
        outJson.set("WasteCenters", wasteCenterNode);

        return objectMapper.writeValueAsString(outJson);
    }

}
