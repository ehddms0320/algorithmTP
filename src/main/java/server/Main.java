package server;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import server.controllers.LandmarkController;
import server.controllers.SearchController;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);

        // Register endpoints
        LandmarkController landmarkController = new LandmarkController();
        server.createContext("/api", landmarkController);
        server.createContext("/api/search", new SearchController());
        
        // 정적 파일 서빙을 위한 핸들러 추가
        server.createContext("/", new StaticFileHandler());

        server.setExecutor(null); // Default executor
        System.out.println("Server running at http://localhost:8081");
        server.start();
    }

    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            
            // 기본 페이지 처리
            if (path.equals("/")) {
                path = "/index.html";
            }

            // 클라이언트 디렉토리의 절대 경로
            String clientDir = new File("client").getAbsolutePath();
            File file = new File(clientDir + path);

            if (file.exists() && file.isFile()) {
                String contentType = getContentType(path);
                exchange.getResponseHeaders().add("Content-Type", contentType);
                exchange.sendResponseHeaders(200, file.length());
                
                try (OutputStream os = exchange.getResponseBody()) {
                    Files.copy(file.toPath(), os);
                }
            } else {
                String response = "404 (Not Found)\\n";
                exchange.sendResponseHeaders(404, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        }

        private String getContentType(String path) {
            if (path.endsWith(".html")) return "text/html";
            if (path.endsWith(".css")) return "text/css";
            if (path.endsWith(".js")) return "application/javascript";
            if (path.endsWith(".png")) return "image/png";
            if (path.endsWith(".jpg") || path.endsWith(".jpeg")) return "image/jpeg";
            return "application/octet-stream";
        }
    }
}
