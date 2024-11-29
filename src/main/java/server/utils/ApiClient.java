package server.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

public class ApiClient {
    
    public static String sendGetRequest(String urlStr) throws IOException, URISyntaxException {
        URL url = new URI(urlStr).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        try {
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            if (conn.getResponseCode() != 200) {
                throw new IOException("HTTP GET Request Failed with Error code : " 
                        + conn.getResponseCode());
            }
            
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String line;
                
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                
                return response.toString();
            }
        } finally {
            conn.disconnect();
        }
    }
    
    public static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return value;
        }
    }
}
