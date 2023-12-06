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
        System.out.println("ALL centers: " + recyclingCenterRepository.findAll());
//        System.out.println("All centers query: " + recyclingCenterRepository.findAllRecyclingCenters());
    }

}
