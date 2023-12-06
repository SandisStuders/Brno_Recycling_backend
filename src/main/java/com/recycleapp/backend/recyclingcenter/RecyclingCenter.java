package com.recycleapp.backend.recyclingcenter;

import com.recycleapp.backend.enums.WASTE_LOCATIONS;
import com.recycleapp.backend.recyclinglocation.RecyclingLocation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "RecyclingCenters")
public class RecyclingCenter extends RecyclingLocation {

    @Column(name = "openingHours")
    private String openingHours;
    private String phone;

    public RecyclingCenter() {
        super();
    }

    public RecyclingCenter(String name, WASTE_LOCATIONS locationType, double xLoc, double yLoc) {
        this.setName(name);
        this.setLocationType(locationType);
        this.setXLoc(xLoc);
        this.setYLoc(yLoc);
    }

    public String getOpeningHours() {return openingHours;}

    public void setOpeningHours(String openingHours) {this.openingHours = openingHours;}

    public String getPhone() {return phone;}

    public void setPhone(String phone) {this.phone = phone;}

    @Override
    public void printRecyclingLocation() {
        super.printRecyclingLocation();
        System.out.println("Opening hours: " + openingHours);
        System.out.println("Phone: " + phone);
    }

}

