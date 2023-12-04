package com.recycleapp.backend.generalapi;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="api/v1")
public class GeneralApiController {

    private final GeneralApiService generalApiService;

    @Autowired
    public GeneralApiController(GeneralApiService generalApiService) {
        this.generalApiService = generalApiService;
    }

    @GetMapping("/general")
    public ObjectNode getRecyclingLocations() {

        return generalApiService.getRecyclingLocations();
    }

}
