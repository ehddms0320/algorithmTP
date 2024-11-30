package server.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import server.models.Landmark;
import server.models.Airport;
import server.models.Route;
import server.utils.RouteOptimizer;
import server.data.TravelData;
import server.services.LandmarkService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class RouteController implements HttpHandler {
    private final Gson gson;
    private final RouteOptimizer routeOptimizer;
    private final TravelData travelData;
    private final LandmarkService landmarkService;

    public RouteController() {
        this.gson = new Gson();
        this.routeOptimizer = new RouteOptimizer();
        this.travelData = new TravelData();
        this.landmarkService = new LandmarkService();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if ("POST".equals(exchange.getRequestMethod())) {
                handlePostRequest(exchange);
            } else if ("OPTIONS".equals(exchange.getRequestMethod())) {
                handleOptionsRequest(exchange);
            } else if ("GET".equals(exchange.getRequestMethod())) {
                handleGetCountries(exchange);
            } else {
                sendResponse(exchange, 405, "Method not allowed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    private void handlePostRequest(HttpExchange exchange) throws IOException {
        // Read request body
        InputStream requestBody = exchange.getRequestBody();
        String requestString = new String(requestBody.readAllBytes(), StandardCharsets.UTF_8);
        
        // Parse request
        RouteRequest request = gson.fromJson(requestString, RouteRequest.class);
        
        // Validate request
        if (!isValidRequest(request)) {
            sendResponse(exchange, 400, "Invalid request parameters");
            return;
        }

        // Get landmarks based on country
        List<Landmark> availableLandmarks = getLandmarksByCountry(request.country);
        if (availableLandmarks.isEmpty()) {
            sendResponse(exchange, 404, "No landmarks found for the specified country");
            return;
        }

        // Get starting airport for the country
        List<Airport> countryAirports = TravelData.getAirports(request.country);
        if (countryAirports == null || countryAirports.isEmpty()) {
            sendResponse(exchange, 404, "No airport found for the specified country");
            return;
        }
        Airport startAirport = countryAirports.get(0);  // 첫 번째 공항을 시작점으로 사용

        // Calculate optimal route with tags
        Route optimizedRoute = routeOptimizer.findOptimalRoute(
            availableLandmarks,
            startAirport,
            request.tags,
            request.maxLandmarks
        );

        if (optimizedRoute.getLandmarks().isEmpty()) {
            sendResponse(exchange, 404, "No route found matching the specified criteria");
            return;
        }

        // Create response with both airport and route
        Map<String, Object> response = new HashMap<>();
        response.put("startAirport", startAirport);
        response.put("route", optimizedRoute.getLandmarks());  // 랜드마크 리스트만 반환
        response.put("totalDistance", optimizedRoute.getTotalDistance());

        // Send response
        String responseBody = gson.toJson(response);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        sendResponse(exchange, 200, responseBody);
    }

    private void handleOptionsRequest(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS, GET");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
        sendResponse(exchange, 204, "");
    }

    private void handleGetCountries(HttpExchange exchange) throws IOException {
        List<String> countries = Arrays.asList("Korea", "Japan", "Vietnam");
        String response = new Gson().toJson(countries);
        sendJsonResponse(exchange, response);
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        // Add CORS headers
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
        
        byte[] responseBytes = response.getBytes("UTF-8");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }

    private void sendJsonResponse(HttpExchange exchange, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        sendResponse(exchange, 200, response);
    }

    private boolean isValidRequest(RouteRequest request) {
        if (request == null || request.country == null || request.country.trim().isEmpty()) {
            return false;
        }
        
        if (request.tags == null || request.tags.isEmpty() || request.tags.size() < 2 || request.tags.size() > 5) {
            return false;
        }
        
        return request.maxLandmarks > 0 && request.maxLandmarks <= 20;
    }

    private List<Landmark> getLandmarksByCountry(String country) {
        return landmarkService.getLandmarksByCountry(country);
    }

    public static class RouteRequest {
        public String country;
        public List<String> tags;
        public int maxLandmarks;
    }
}
