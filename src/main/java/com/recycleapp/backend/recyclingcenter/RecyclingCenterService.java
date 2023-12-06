package com.recycleapp.backend.recyclingcenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecyclingCenterService {

    private final RecyclingCenterRepository recyclingCenterRepository;

    @Autowired
    public RecyclingCenterService(RecyclingCenterRepository recyclingCenterRepository) {
        this.recyclingCenterRepository = recyclingCenterRepository;
    }

    public List<RecyclingCenter> getRecyclingCenters() {
        return recyclingCenterRepository.findAll();
    }

    public void testConnection() {
        System.out.println("Connection works!");

//        RecyclingCenter recyclingCenter = new RecyclingCenter();
//        recyclingCenter.setLocId("testId2");
//        recyclingCenter.setName("testCenter2");
//        recyclingCenter.setPhone("123");
//        recyclingCenter.setOpeningHours("All day");
//        recyclingCenter.setOwner("dad");
//        recyclingCenter.setXLoc(3.55);
//        recyclingCenter.setYLoc(4.55);
//        recyclingCenterRepository.save(recyclingCenter);


    }

}
