package com.recycleapp.backend.recyclinglocation;

import com.recycleapp.backend.enums.WASTE_CATEGORIES;
import com.recycleapp.backend.enums.WASTE_LOCATIONS;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.ArrayList;

@MappedSuperclass
public class RecyclingLocation {

    @Id
    private String locId = "";
    private String name;
    private ArrayList<WASTE_CATEGORIES> wasteCategories = new ArrayList<WASTE_CATEGORIES>();
    private WASTE_LOCATIONS locationType;
    private double xLoc;
    private double yLoc;
    private String owner;

    public RecyclingLocation() {
        // This is needed for Jackson
    }

    RecyclingLocation(String name, WASTE_LOCATIONS locationType, double xLoc, double yLoc) {
        this.name = name;
        this.locationType = locationType;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }

    public String getLocId() {return locId;}

    public void setLocId(String locId) {this.locId = locId;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<WASTE_CATEGORIES> getWasteCategories() {
        return wasteCategories;
    }

    public void addWasteCategory(WASTE_CATEGORIES wasteCategory) {
        wasteCategories.add(wasteCategory);
    }

    public WASTE_LOCATIONS getLocationType() {
        return locationType;
    }

    public void setLocationType(WASTE_LOCATIONS locationType) {
        this.locationType = locationType;
    }

    public double getXLoc() {
        return xLoc;
    }

    public void setXLoc(double xLoc) {this.xLoc = xLoc;}

    public double getYLoc() {
        return yLoc;
    }

    public void setYLoc(double yLoc) {
        this.yLoc = yLoc;
    }

    public String getOwner() {return owner;}

    public void setOwner(String owner) {this.owner = owner;}

    public void printRecyclingLocation() {
        System.out.println("Location id: " + locId);
        System.out.println("Location name: " + name);
        System.out.println("Location type: " + locationType);
        System.out.println("Waste categories: " + wasteCategories.toString());
        System.out.println("Location coordinates: x(" + xLoc + "), y(" + yLoc + ")");
        System.out.println("Owner: " + owner);
    }
}

