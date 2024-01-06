package com.recycleapp.backend.databaserefresher;

import com.recycleapp.backend.recyclingcenter.RecyclingCenterService;
import com.recycleapp.backend.recyclingcontainer.RecyclingContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
public class DatabaseRefresher {

    private final RecyclingCenterService recyclingCenterService;
    private final RecyclingContainerService recyclingContainerService;

    @Autowired
    public DatabaseRefresher(RecyclingCenterService recyclingCenterService,
                             RecyclingContainerService recyclingContainerService) {
        this.recyclingCenterService = recyclingCenterService;
        this.recyclingContainerService = recyclingContainerService;
    }

    @Scheduled(cron = "0 0 0 * * SUN")
    public String refreshDatabases() throws URISyntaxException, IOException {
        System.out.println("Database refresher engaged!");

        String recyclingCenterInfo = recyclingCenterService.updateRecyclingCenters();
        System.out.println(recyclingCenterInfo);

        String recyclingContainerInfo = recyclingContainerService.updateRecyclingContainers();
        System.out.println(recyclingContainerInfo);

        return recyclingCenterInfo + recyclingContainerInfo;
    }
}
