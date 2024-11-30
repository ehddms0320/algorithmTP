package server;

import com.sun.net.httpserver.HttpServer;
import server.controllers.RouteController;
import server.data.TravelData;
import server.handlers.StaticFileHandler;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        // Initialize TravelData implicitly by calling getLandmarks
        TravelData.getLandmarks("South Korea");
        
        // Create and start server
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        
        // Add route endpoint
        server.createContext("/api/route", new RouteController());
        
        // Add static file handler for serving client files
        server.createContext("/", new StaticFileHandler());
        
        server.setExecutor(null);
        System.out.println("Server running at http://localhost:8081");
        server.start();
    }
}
