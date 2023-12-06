package com.recycleapp.backend.recyclingcenter;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="test")
public class RecyclingCenterController {

    private final RecyclingCenterService recyclingCenterService;

    @Autowired
    public RecyclingCenterController(RecyclingCenterService recyclingCenterService) {
        this.recyclingCenterService = recyclingCenterService;
    }


    @GetMapping("/v1")
    public String testController() {
        recyclingCenterService.testConnection();
        return "Heyo!";
    }

}
