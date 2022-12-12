package src.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import src.http.util.HttpException;

import java.io.IOException;
import java.util.logging.Logger;

public class GlobalHttpHandler implements HttpHandler {
    private static final Logger LOGGER = Logger.getLogger(UserRestHandler.class.getName());

    private final RestHandler handler;

    public GlobalHttpHandler(RestHandler handler) {
        this.handler = handler;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            switch (exchange.getRequestMethod()) {
                case "PUT" -> handler.handlePut(exchange);
                case "POST" -> handler.handlePost(exchange);
                case "GET" -> handler.handleGet(exchange);
                case "DELETE" -> handler.handleDelete(exchange);
                default -> handler.unhandledMethod(exchange);
            }
        } catch (HttpException e) {
            LOGGER.info(e.getMessage());
            exchange.sendResponseHeaders(e.getHttpStatus().getStatus(), 0);
            exchange.close();
        }
        exchange.close();
    }
}
