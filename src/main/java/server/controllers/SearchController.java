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
import java.util.Arrays;
import java.util.stream.Collectors;
import java.nio.charset.StandardCharsets;

public class SearchController implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!"POST".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            return;
        }

        try {
            // 요청 본문 읽기
            String requestBody;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))) {
                requestBody = reader.lines().collect(Collectors.joining("\n"));
            }

            // TODO: JSON 파싱 로직 구현
            String country = ""; // requestBody에서 추출
            List<String> selectedTags = new ArrayList<>(); // requestBody에서 추출

            // 선택된 국가의 랜드마크 가져오기
            List<Landmark> allLandmarks = TravelData.getLandmarks(country);
            
            // 선택된 태그에 맞는 랜드마크 필터링
            List<Landmark> filteredLandmarks = allLandmarks.stream()
                    .filter(landmark -> {
                        List<String> landmarkTags = Arrays.asList(landmark.getTags());
                        return selectedTags.stream()
                                .anyMatch(landmarkTags::contains);
                    })
                    .collect(Collectors.toList());

            // 응답 생성
            StringBuilder json = new StringBuilder("[");
            for (Landmark landmark : filteredLandmarks) {
                json.append(landmark.toJson()).append(",");
            }
            if (!filteredLandmarks.isEmpty()) {
                json.deleteCharAt(json.length() - 1);
            }
            json.append("]");

            // 응답 전송
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            byte[] responseBytes = json.toString().getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, responseBytes.length);
            
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseBytes);
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
}
