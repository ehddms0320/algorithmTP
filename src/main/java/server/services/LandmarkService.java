package server.services;

import server.data.TravelData;
import server.models.Landmark;
import server.constants.TravelTags;

import java.util.List;
import java.util.stream.Collectors;

public class LandmarkService {
    public List<Landmark> getLandmarksByCountry(String country) {
        return TravelData.getLandmarks(country);
    }

    public List<Landmark> searchLandmarks(String country, List<TravelTags> tags) {
        List<Landmark> landmarks = getLandmarksByCountry(country);
        
        if (tags == null || tags.isEmpty()) {
            System.out.println("[DEBUG] Tags are null or empty.");
            throw new IllegalArgumentException("Tags cannot be null or empty.");
        }

        // 검색된 랜드마크 로그 출력
        System.out.println("Searched Landmarks: " + 
            landmarks.stream().map(Landmark::getName).collect(Collectors.joining(", ")));

        return landmarks.stream()
            .filter(landmark -> {
                List<String> landmarkTags = landmark.getTags();
                return tags.stream()
                    .anyMatch(tag -> landmarkTags.contains(tag.name()));
            })
            .collect(Collectors.toList());
    }
}
