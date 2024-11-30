package server.models;

public class Airport {
    private String code;
    private String name;
    private String country;
    private double latitude;
    private double longitude;

    public Airport(String code, String name, String country, double latitude, double longitude) {
        this.code = code;
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getCountry() { return country; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    public double calculateDistance(Landmark landmark) {
        return haversineDistance(this.latitude, this.longitude, landmark.getLatitude(), landmark.getLongitude());
    }

    private double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // 지구의 반지름 (km)
        
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return R * c;
    }

    @Override
    public String toString() {
        return String.format(
            "{\"code\":\"%s\",\"name\":\"%s\",\"country\":\"%s\",\"latitude\":%f,\"longitude\":%f}",
            code, name, country, latitude, longitude
        );
    }
}
