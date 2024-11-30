package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class StaticFileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        
        // 기본 페이지 처리
        if (path.equals("/")) {
            path = "/index.html";
        }

        // 클라이언트 디렉토리의 절대 경로
        String projectRoot = System.getProperty("user.dir");
        String clientDir = projectRoot + File.separator + "client";
        File file = new File(clientDir + path);

        if (file.exists() && file.isFile()) {
            String contentType = getContentType(path);
            exchange.getResponseHeaders().add("Content-Type", contentType);
            exchange.sendResponseHeaders(200, file.length());
            
            try (OutputStream os = exchange.getResponseBody()) {
                Files.copy(file.toPath(), os);
            }
        } else {
            String response = "404 (Not Found)\n";
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
