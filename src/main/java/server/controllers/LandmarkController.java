package server.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.data.TravelData;
import server.models.Landmark;
import server.models.Airport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.nio.charset.StandardCharsets;

public class LandmarkController implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // CORS 설정 추가
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        // OPTIONS 요청 처리
        if ("OPTIONS".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(200, -1);
            return;
        }

        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        try {
            switch (path) {
                case "/api/countries":
                    if ("GET".equals(method)) {
                        handleGetCountries(exchange);
                    }
                    break;
                    
                case "/api/landmarks":
                    if ("GET".equals(method)) {
                        handleGetLandmarks(exchange);
                    }
                    break;
                    
                case "/api/tags":
                    if ("GET".equals(method)) {
                        handleGetTags(exchange);
                    }
                    break;
                    
                case "/api/search":
                    if ("POST".equals(method)) {
                        handleSearchLandmarks(exchange);
                    }
                    break;
                    
                default:
                    exchange.sendResponseHeaders(404, -1); // Not Found
            }
        } catch (Exception e) {
            String response = "{\"error\": \"" + e.getMessage() + "\"}";
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(500, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
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
        String country = exchange.getRequestURI().getQuery();
        if (country == null || country.isEmpty()) {
            throw new IOException("Country parameter is required");
        }
        
        String countryName = country.split("=")[1];
        List<Landmark> landmarks = TravelData.getLandmarks(countryName);
        
        if (landmarks == null) {
            throw new IOException("Country not found: " + countryName);
        }
        
        StringBuilder json = new StringBuilder("[");
        for (Landmark landmark : landmarks) {
            json.append(landmark.toJson()).append(",");
        }
        if (!landmarks.isEmpty()) {
            json.deleteCharAt(json.length() - 1);
        }
        json.append("]");
        
        sendJsonResponse(exchange, json.toString());
    }

    private void handleSearchLandmarks(HttpExchange exchange) throws IOException {
        // 요청 본문에서 검색 매개변수 읽기
        String requestBody = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))
                .lines().collect(Collectors.joining("\n"));
        
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
}
