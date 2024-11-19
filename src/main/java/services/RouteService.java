package com.travelrecommendation.services;

import com.travelrecommendation.models.Landmark;
import com.travelrecommendation.exceptions.CustomException;

import java.util.List;

public class RouteService {

    /**
     * Calculates the optimal route using Dijkstra's algorithm.
     * This method is a placeholder and should be implemented with the actual algorithm.
     *
     * @param landmarks List of landmarks to visit.
     * @return Ordered list of landmarks representing the optimal route.
     */
    public List<Landmark> calculateOptimalRoute(List<Landmark> landmarks) throws CustomException {
        if (landmarks == null || landmarks.isEmpty()) {
            throw new CustomException("Landmarks list cannot be null or empty.");
        }

        // TODO: Implement Dijkstra's algorithm here
        // For now, return the list as-is
        return landmarks;
    }
}
