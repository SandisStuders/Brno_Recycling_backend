package com.recycleapp.backend.recyclingcontainer;

import com.recycleapp.backend.enums.WASTE_COLLECTION_FREQUENCY;
import com.recycleapp.backend.enums.WASTE_LOCATIONS;
import com.recycleapp.backend.enums.WEEKDAYS;
import com.recycleapp.backend.recyclinglocation.RecyclingLocation;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.ArrayList;

@Entity
@Table
public class RecyclingContainer extends RecyclingLocation {

    private WASTE_COLLECTION_FREQUENCY wasteCollectionFrequency;
    private ArrayList<WEEKDAYS> wasteCollectionDays;

    public RecyclingContainer() {
        super();
    }

    public RecyclingContainer(String name, WASTE_LOCATIONS locationType, double xLoc, double yLoc) {
        this.setName(name);
        this.setLocationType(locationType);
        this.setXLoc(xLoc);
        this.setYLoc(yLoc);
    }

    public WASTE_COLLECTION_FREQUENCY getWasteCollectionFrequency() {
        return wasteCollectionFrequency;
    }

    public void setWasteCollectionFrequency(WASTE_COLLECTION_FREQUENCY wasteCollectionFrequency) {
        this.wasteCollectionFrequency = wasteCollectionFrequency;
    }

    public ArrayList<WEEKDAYS> getWasteCollectionDays() {return wasteCollectionDays;}

    public void setWasteCollectionDays(ArrayList<WEEKDAYS> wasteCollectionDays) {this.wasteCollectionDays = wasteCollectionDays;}

    @Override
    public void printRecyclingLocation() {
        super.printRecyclingLocation();
        System.out.println("Waste Collection Frequency: " + wasteCollectionFrequency);
        System.out.println("Waste collection days: " + wasteCollectionDays);
    }

}

