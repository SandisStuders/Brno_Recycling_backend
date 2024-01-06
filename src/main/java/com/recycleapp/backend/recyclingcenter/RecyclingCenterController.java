package com.recycleapp.backend.recyclingcenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping(path="rec-center")
public class RecyclingCenterController {

    private final RecyclingCenterService recyclingCenterService;

    @Autowired
    public RecyclingCenterController(RecyclingCenterService recyclingCenterService) {
        this.recyclingCenterService = recyclingCenterService;
    }

    @GetMapping("/update")
    public String updateRecyclingCenterDatabase() throws URISyntaxException, IOException {
        return recyclingCenterService.updateRecyclingCenters();
    }
}
