package com.recycleapp.backend.generalapi;

import com.recycleapp.backend.databaserefresher.DatabaseRefresher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping(path="api/v1")
public class GeneralApiController {

    private final GeneralApiService generalApiService;
    private final DatabaseRefresher databaseRefresher;

    @Autowired
    public GeneralApiController(GeneralApiService generalApiService, DatabaseRefresher databaseRefresher) {
        this.generalApiService = generalApiService;
        this.databaseRefresher = databaseRefresher;
    }

    @GetMapping("/general")
    public String getRecyclingLocations() throws URISyntaxException, IOException {
        return generalApiService.getRecyclingLocations();
    }

    @GetMapping("/refresh")
    public String refreshDatabases() throws URISyntaxException, IOException {
        String refreshInfo = databaseRefresher.refreshDatabases();
        return "Databases refreshed!\n " + refreshInfo;
    }
}
