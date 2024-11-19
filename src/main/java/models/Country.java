package com.travelrecommendation.models;

import java.util.List;

public class Country {
    private String name;
    private List<Landmark> landmarks;
    private Landmark mainAirport;

    public Country(String name, List<Landmark> landmarks, Landmark mainAirport) {
        this.name = name;
        this.landmarks = landmarks;
        this.mainAirport = mainAirport;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public List<Landmark> getLandmarks() {
        return landmarks;
    }

    public Landmark getMainAirport() {
        return mainAirport;
    }
}
