package com.recycleapp.backend.recyclingcontainer;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping(path="rec-container")
public class RecyclingContainerController {

    private final RecyclingContainerService recyclingContainerService;

    @Autowired
    public RecyclingContainerController(RecyclingContainerService recyclingContainerService) {
        this.recyclingContainerService = recyclingContainerService;
    }

    @GetMapping("/update")
    public String updateRecyclingContainers() throws URISyntaxException, IOException {
        return recyclingContainerService.updateRecyclingContainers();
    }

}
