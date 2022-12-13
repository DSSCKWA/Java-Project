package src.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import src.http.constants.HttpStatus;
import src.http.util.HttpException;

import java.io.IOException;

public interface RestHandler {
    void handleGet(HttpExchange exchange);

    void handlePost(HttpExchange exchange);

    void handlePut(HttpExchange exchange);

    void handleDelete(HttpExchange exchange);

    default void unhandledMethod(HttpExchange exchange) {
        throw new HttpException(HttpStatus.METHOD_NOT_ALLOWED, "Unrecognized HTTP method");
    }
}
