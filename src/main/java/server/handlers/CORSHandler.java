package server.handlers;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.Filter;
import java.util.List;
import java.io.IOException;

public class CORSHandler {
    public static void setupCORS(HttpServer server) {
        server.createContext("/").getFilters().add(new Filter() {
            @Override
            public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
                exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
                
                if ("OPTIONS".equals(exchange.getRequestMethod())) {
                    exchange.sendResponseHeaders(204, -1);
                    exchange.close();
                    return;
                }
                
                chain.doFilter(exchange);
            }

            @Override
            public String description() {
                return "CORS Filter";
            }
        });
    }
}
