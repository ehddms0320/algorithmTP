package server.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.data.TravelData;
import server.models.Landmark;
import server.constants.TravelTags;
import server.services.LandmarkService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.nio.charset.StandardCharsets;

public class SearchController implements HttpHandler {
    private final Gson gson;
    private final LandmarkService landmarkService;

    public SearchController() {
        this.gson = new Gson();
        this.landmarkService = new LandmarkService();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            handleSearchRequest(exchange);
        } else if ("OPTIONS".equals(exchange.getRequestMethod())) {
            handleOptionsRequest(exchange);
        } else {
            sendResponse(exchange, 405, "Method not allowed");
        }
    }

    private void handleSearchRequest(HttpExchange exchange) throws IOException {
        try {
            // 요청 본문 읽기
            String requestBody = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))
                .lines()
                .collect(Collectors.joining("\n"));

            // JSON 파싱
            JsonObject jsonRequest = gson.fromJson(requestBody, JsonObject.class);
            
            // country 추출
            String country = jsonRequest.has("country") ? jsonRequest.get("country").getAsString() : "";
            
            // tags 추출
            List<TravelTags> selectedTags = new ArrayList<>();
            if (jsonRequest.has("tags") && !jsonRequest.get("tags").isJsonNull()) {
                JsonArray tagsArray = jsonRequest.getAsJsonArray("tags");
                tagsArray.forEach(tagElement -> {
                    try {
                        selectedTags.add(TravelTags.valueOf(tagElement.getAsString()));
                    } catch (IllegalArgumentException e) {
                        // 잘못된 태그는 무시
                    }
                });
            }

            // 검색 실행
            List<Landmark> results = searchLandmarks(country, selectedTags);

            // 응답 생성
            String response = gson.toJson(results);
            
            // 응답 전송
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            sendResponse(exchange, 200, response);

        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 400, "{\"error\": \"Invalid request format\"}");
        }
    }

    private List<Landmark> searchLandmarks(String country, List<TravelTags> tags) {
        return landmarkService.searchLandmarks(country, tags);
    }

    private void handleOptionsRequest(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
        sendResponse(exchange, 204, "");
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}
