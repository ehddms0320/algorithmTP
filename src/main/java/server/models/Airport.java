package server.models;

public class Airport {
    private String name;
    private String country;
    private double latitude;
    private double longitude;
    private String code;  // IATA 공항 코드

    public Airport(String name, String country, double latitude, double longitude, String code) {
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCode() {
        return code;
    }

    public String toJson() {
        return String.format(
            "{\"name\":\"%s\", \"country\":\"%s\", \"code\":\"%s\", \"latitude\":%f, \"longitude\":%f}",
            name, country, code, latitude, longitude
        );
    }
}
