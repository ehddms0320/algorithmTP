package server.models;

import java.util.Arrays;

public class Landmark {
    private String name;
    private String country;
    private String[] tags;
    private double latitude;
    private double longitude;

    public Landmark(String name, String country, String[] tags, double latitude, double longitude) {
        this.name = name;
        this.country = country;
        this.tags = tags;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String[] getTags() {
        return tags;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String toJson() {
        return String.format(
            "{\"name\":\"%s\", \"country\":\"%s\", \"tags\":%s, \"latitude\":%f, \"longitude\":%f}",
            name, country, Arrays.toString(tags), latitude, longitude
        );
    }
}
