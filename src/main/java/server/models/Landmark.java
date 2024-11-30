package server.models;

import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Landmark {
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("country")
    private String country;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("tags")
    private List<String> tags;
    
    @JsonProperty("latitude")
    private double latitude;
    
    @JsonProperty("longitude")
    private double longitude;

    // 기본 생성자
    public Landmark() {
        this.tags = new ArrayList<>();
    }

    // 전체 필드 생성자
    public Landmark(Long id, String name, String country, String description, List<String> tags, 
                   double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.description = description;
        this.tags = new ArrayList<>(tags); // defensive copy
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCountry() { return country; }
    public String getDescription() { return description; }
    public List<String> getTags() { return new ArrayList<>(tags); } // defensive copy
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCountry(String country) { this.country = country; }
    public void setDescription(String description) { this.description = description; }
    public void setTags(List<String> tags) { this.tags = new ArrayList<>(tags); } // defensive copy
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    // 태그 관리 메서드
    public void addTag(String tag) {
        if (tag != null && !tag.trim().isEmpty() && !tags.contains(tag)) {
            tags.add(tag);
        }
    }

    public void removeTag(String tag) {
        tags.remove(tag);
    }

    // 두 랜드마크 간의 거리를 계산하는 메소드 (Haversine formula)
    public double calculateDistance(Landmark other) {
        final int R = 6371; // 지구의 반지름 (km)
        
        double latDistance = Math.toRadians(other.latitude - this.latitude);
        double lonDistance = Math.toRadians(other.longitude - this.longitude);
        
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(other.latitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return R * c;
    }

    @Override
    public String toString() {
        return String.format("{\"id\":%d, \"name\":\"%s\", \"country\":\"%s\", \"description\":\"%s\", \"latitude\":%f, \"longitude\":%f}",
                id, name, country, description, latitude, longitude);
    }

    public String toJson() {
        return toString();
    }
}
