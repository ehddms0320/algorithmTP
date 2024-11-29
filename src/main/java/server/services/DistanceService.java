package server.services;

import server.utils.ApiClient;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonObject;
import javax.json.JsonArray;

public class DistanceService {

    private static final String API_KEY = "AIzaSyAQ62izot9EIhYfDd-3kkKD5_OH-kEuzzc";

    /**
     * Gets the distance between two locations using Google Distance Matrix API.
     *
     * @param origin      The starting location in "latitude,longitude" format.
     * @param destination The destination location in "latitude,longitude" format.
     * @return The distance in meters.
     * @throws Exception If the API request fails or the response is invalid.
     */
    public double getDistance(String origin, String destination) throws Exception {
        // Construct the API URL
        String url = String.format(
            "https://maps.googleapis.com/maps/api/distancematrix/json?origins=%s&destinations=%s&key=%s",
            origin, destination, API_KEY
        );

        // Send GET request to the API
        String response = ApiClient.sendGetRequest(url);

        // JSON 파싱
        try (JsonReader jsonReader = Json.createReader(new StringReader(response))) {
            JsonObject jsonResponse = jsonReader.readObject();
            String status = jsonResponse.getString("status");

            if (!status.equals("OK")) {
                throw new Exception("Google Distance Matrix API returned status: " + status);
            }

            // Extract the distance in meters
            JsonArray rows = jsonResponse.getJsonArray("rows");
            JsonObject elements = rows.getJsonObject(0).getJsonArray("elements").getJsonObject(0);

            String elementStatus = elements.getString("status");
            if (!elementStatus.equals("OK")) {
                throw new Exception("Element status is not OK: " + elementStatus);
            }

            double distanceInMeters = elements.getJsonObject("distance").getJsonNumber("value").doubleValue();

            return distanceInMeters;
        }
    }
}
