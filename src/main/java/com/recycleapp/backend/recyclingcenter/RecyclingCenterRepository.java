package com.recycleapp.backend.recyclingcenter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecyclingCenterRepository extends JpaRepository<RecyclingCenter, String> {

//    @Query(value = "SELECT * FROM \"RecyclingCenters\"", nativeQuery = true)
//    List<RecyclingCenter> findAllRecyclingCenters();

}
