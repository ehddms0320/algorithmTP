package com.travelrecommendation.services;

import com.travelrecommendation.models.Landmark;
import com.travelrecommendation.utils.ApiClient;
import com.travelrecommendation.exceptions.CustomException;
import org.json.JSONObject;

public class DistanceService {

    private static final String API_KEY = "YOUR_GOOGLE_API_KEY";

    /**
     * Fetches the distance between two landmarks using Google Distance Matrix API.
     *
     * @param origin      The starting landmark.
     * @param destination The destination landmark.
     * @return The distance in meters.
     * @throws CustomException if the API call fails or returns invalid data.
     */
    public double getDistance(Landmark origin, Landmark destination) throws CustomException {
        if (origin == null || destination == null) {
            throw new IllegalArgumentException("Origin and destination landmarks must not be null.");
        }

        String url = buildApiUrl(origin, destination);

        try {
            String response = ApiClient.get(url);
            JSONObject jsonResponse = new JSONObject(response);

            // Check if the response status is OK
            if (!jsonResponse.getString("status").equals("OK")) {
                throw new CustomException("Invalid response from Google Distance Matrix API.");
            }

            // Extract the distance value
            double distance = jsonResponse.getJSONArray("rows")
                    .getJSONObject(0)
                    .getJSONArray("elements")
                    .getJSONObject(0)
                    .getJSONObject("distance")
                    .getDouble("value");

            return distance;

        } catch (Exception e) {
            // Log detailed error message
            System.err.println("Error fetching distance between " + origin.getName() + " and " + destination.getName());
            e.printStackTrace();
            // Provide user-friendly message
            throw new CustomException("Unable to calculate distance at this time. Please try again later.");
        }
    }

    /**
     * Builds the API URL for the Google Distance Matrix API call.
     *
     * @param origin      The starting landmark.
     * @param destination The destination landmark.
     * @return The complete API URL.
     */
    private String buildApiUrl(Landmark origin, Landmark destination) {
        String originParam = origin.getLatitude() + "," + origin.getLongitude();
        String destinationParam = destination.getLatitude() + "," + destination.getLongitude();

        return "https://maps.googleapis.com/maps/api/distancematrix/json"
                + "?origins=" + originParam
                + "&destinations=" + destinationParam
                + "&key=" + API_KEY;
    }
}
