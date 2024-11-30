package server.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.data.TravelData;
import server.models.Landmark;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.nio.charset.StandardCharsets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class LandmarkController implements HttpHandler {

    private Gson gson = new GsonBuilder().create();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            // CORS 헤더 설정
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
            
            if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }
            
            if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                exchange.sendResponseHeaders(405, 0);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write("Method not allowed".getBytes());
                }
                return;
            }
            
            String query = exchange.getRequestURI().getQuery();
            if (query == null || !query.startsWith("country=")) {
                exchange.sendResponseHeaders(400, 0);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write("Missing country parameter".getBytes());
                }
                return;
            }
            
            String countryName = java.net.URLDecoder.decode(query.substring(8), StandardCharsets.UTF_8);
            String tagsJson = query.contains("&tags=") 
                ? java.net.URLDecoder.decode(query.substring(query.indexOf("&tags=") + 6), StandardCharsets.UTF_8)
                : null;
            
            final List<String> searchTags = new ArrayList<>();
            if (tagsJson != null && !tagsJson.isEmpty()) {
                try {
                    List<String> parsedTags = new Gson().fromJson(tagsJson, new TypeToken<List<String>>(){}.getType());
                    searchTags.addAll(parsedTags);
                } catch (JsonSyntaxException e) {
                    exchange.sendResponseHeaders(400, 0);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write("Invalid tags format".getBytes());
                    }
                    return;
                }
            }
            
            if (searchTags.size() < 2 || searchTags.size() > 5) {
                exchange.sendResponseHeaders(400, 0);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write("Please select 2-5 tags".getBytes());
                }
                return;
            }
            
            List<Landmark> landmarks = TravelData.getLandmarks(countryName);
            
            // 선택된 태그 중 하나라도 포함하는 랜드마크 필터링
            if (!searchTags.isEmpty()) {
                landmarks = landmarks.stream()
                    .filter(landmark -> {
                        List<String> landmarkTags = landmark.getTags();
                        return searchTags.stream().anyMatch(landmarkTags::contains);
                    })
                    .collect(Collectors.toList());
            }
            
            // JSON 응답 생성
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonResponse = gson.toJson(landmarks);
            
            // Content-Type 헤더 설정
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            
            // 응답 전송
            byte[] responseBytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, responseBytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseBytes);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(500, 0);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write("Internal server error".getBytes());
            }
        }
    }

    private void handleGetCountries(HttpExchange exchange) throws IOException {
        String[] countries = {"한국", "일본", "베트남"};
        String response = "{\"countries\": [" + 
            String.join(",", java.util.Arrays.stream(countries)
                .map(country -> "\"" + country + "\"")
                .collect(Collectors.joining(","))) + 
            "]}";
        
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private void handleGetLandmarks(HttpExchange exchange) throws IOException {
        Map<String, String> queryParams = parseQueryString(exchange.getRequestURI().getQuery());
        String country = queryParams.get("country");
        
        List<Landmark> landmarks;
        if (country != null && !country.isEmpty()) {
            landmarks = TravelData.getLandmarks(country);
        } else {
            landmarks = new ArrayList<>();
            for (String c : java.util.Arrays.asList("Korea", "Japan", "Vietnam")) {
                landmarks.addAll(TravelData.getLandmarks(c));
            }
        }
        
        String response = new Gson().toJson(landmarks);
        sendJsonResponse(exchange, response);
    }

    private Map<String, String> parseQueryString(String query) {
        Map<String, String> result = new HashMap<>();
        if (query != null) {
            for (String param : query.split("&")) {
                String[] entry = param.split("=");
                if (entry.length > 1) {
                    result.put(entry[0], entry[1]);
                } else {
                    result.put(entry[0], "");
                }
            }
        }
        return result;
    }

    private void handleSearchLandmarks(HttpExchange exchange) throws IOException {
        // TODO: JSON 파싱하여 country와 tags 추출
        // 임시 응답
        String response = "{\"message\": \"Search functionality will be implemented\"}";
        sendJsonResponse(exchange, response);
    }

    private void handleGetTags(HttpExchange exchange) throws IOException {
        // 임시 태그 데이터
        String[] tags = {"역사", "문화", "자연", "음식", "쇼핑", "예술", "건축", "종교", "해변", "산"};
        String response = "{\"tags\": [" + 
            String.join(",", java.util.Arrays.stream(tags)
                .map(tag -> "\"" + tag + "\"")
                .collect(Collectors.joining(","))) + 
            "]}";
        
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private void sendJsonResponse(HttpExchange exchange, String response) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }

    private void sendErrorResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
        String response = String.format("{\"error\": \"%s\"}", message);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}
