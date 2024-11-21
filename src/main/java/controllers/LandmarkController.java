package com.travelrecommendation.controllers;

import com.travelrecommendation.models.Landmark;
import com.travelrecommendation.services.RouteService;
import com.travelrecommendation.exceptions.CustomException;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
public class LandmarkController {

    private RouteService routeService = new RouteService();

    /**
     * Endpoint to get landmarks based on selected country and tags.
     *
     * @param country The selected country.
     * @param tags    The list of selected tags.
     * @return List of landmarks matching the criteria.
     */
    @PostMapping("/getLandmarks")
    public ResponseEntity<?> getLandmarks(@RequestParam String country, @RequestBody List<String> tags) {
        try {
            // Validate inputs
            if (country == null || country.isEmpty()) {
                throw new CustomException("Country must be specified.");
            }
            if (tags == null || tags.size() < 2 || tags.size() > 5) {
                throw new CustomException("Please select between 2 and 5 tags.");
            }

            // TODO: Fetch landmarks from data source based on country and tags
            List<Landmark> landmarks = fetchLandmarks(country, tags);

            return ResponseEntity.ok(landmarks);

        } catch (CustomException e) {
            // Return error message with BAD_REQUEST status
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Log error and return generic message
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred.");
        }
    }

    /**
     * Endpoint to get the optimized route.
     *
     * @param landmarks List of landmarks selected.
     * @return Ordered list of landmarks representing the optimal route.
     */
    @PostMapping("/getOptimizedRoute")
    public ResponseEntity<?> getOptimizedRoute(@RequestBody List<Landmark> landmarks) {
        try {
            List<Landmark> optimizedRoute = routeService.calculateOptimalRoute(landmarks);
            return ResponseEntity.ok(optimizedRoute);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred.");
        }
    }

    /**
     * Mock method to fetch landmarks based on country and tags.
     * Replace this with actual data retrieval logic.
     *
     * @param country The selected country.
     * @param tags    The list of selected tags.
     * @return List of landmarks.
     */
    private List<Landmark> fetchLandmarks(String country, List<String> tags) {
        // TODO: Implement data fetching logic
        return null;
    }
}
