package com.travelrecommendation.models;

import java.util.List;

public class Landmark {
    private String name;
    private List<String> tags;
    private double latitude;
    private double longitude;
    private int visitOrder;

    public Landmark(String name, List<String> tags, double latitude, double longitude) {
        this.name = name;
        this.tags = tags;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public List<String> getTags() {
        return tags;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getVisitOrder() {
        return visitOrder;
    }

    public void setVisitOrder(int visitOrder) {
        this.visitOrder = visitOrder;
    }
}
