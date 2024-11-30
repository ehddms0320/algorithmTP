package server.models;

import java.util.List;
import java.util.stream.Collectors;

public class Country {
    private String name;
    private List<Landmark> landmarks;

    public Country(String name, List<Landmark> landmarks) {
        this.name = name;
        this.landmarks = landmarks;
    }

    public String getName() {
        return name;
    }

    public List<Landmark> getLandmarks() {
        return landmarks;
    }

    public void logLandmarkNames() {
        System.out.println("Landmarks in " + name + ": " + 
            landmarks.stream().map(Landmark::getName).collect(Collectors.joining(", ")));
    }
}
