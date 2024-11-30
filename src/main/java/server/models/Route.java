package server.models;

import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Route {
    @JsonProperty("landmarks")
    private List<Landmark> landmarks;

    @JsonProperty("totalDistance")
    private double totalDistance;  // in kilometers

    public Route() {
        this.landmarks = new ArrayList<>();
        this.totalDistance = 0.0;
    }

    public Route(List<Landmark> landmarks) {
        this.landmarks = new ArrayList<>(landmarks);  // defensive copy
        this.calculateTotalDistance();
    }

    public Route(List<Landmark> landmarks, double totalDistance) {
        this.landmarks = new ArrayList<>(landmarks);  // defensive copy
        this.totalDistance = totalDistance;
    }

    public List<Landmark> getLandmarks() {
        return new ArrayList<>(landmarks);  // defensive copy
    }

    public void setLandmarks(List<Landmark> landmarks) {
        this.landmarks = new ArrayList<>(landmarks);  // defensive copy
        this.calculateTotalDistance();
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    private void calculateTotalDistance() {
        totalDistance = 0.0;
        for (int i = 0; i < landmarks.size() - 1; i++) {
            totalDistance += landmarks.get(i).calculateDistance(landmarks.get(i + 1));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Route{");
        sb.append("landmarks=").append(landmarks.size()).append(", ");
        sb.append("totalDistance=").append(String.format("%.2f", totalDistance)).append("km}");
        return sb.toString();
    }
}
