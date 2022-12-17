package src.http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import src.gson.GsonConverter;
import src.http.constants.Headers;
import src.http.constants.HttpStatus;
import src.http.service.UserService;
import src.http.util.HttpException;
import src.http.util.HttpHandlerUtil;
import src.users.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class UserRestHandler implements RestHandler {
    private static final Logger LOGGER = Logger.getLogger(UserRestHandler.class.getName());
    private static final Gson gson = GsonConverter.newGsonWriterConverter();

    private final UserService userService;

    public UserRestHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handleGet(HttpExchange exchange) {
        LOGGER.info("HTTP User Handler [GET]");
        String[] paths = exchange.getRequestURI().getPath().substring(1).split("/");
        byte[] userBytes;
        switch (paths.length) {
            case 1 -> userBytes = gson.toJson(userService.getAllUsers()).getBytes();
            case 2 -> {
                try {
                    int userId = Integer.parseInt(paths[1]);
                    User user = userService.getUserById(userId);
                    userBytes = gson.toJson(Objects.requireNonNullElse(user, "{}")).getBytes();
                } catch (NumberFormatException e) {
                    throw new HttpException(HttpStatus.BAD_REQUEST, "Invalid payload");
                }
            }
            default -> throw new HttpException(HttpStatus.BAD_REQUEST, "Invalid payload");
        }
        try {
            exchange.getResponseHeaders().set(Headers.contentType, Headers.appJson);
            exchange.sendResponseHeaders(HttpStatus.OK.getStatus(), userBytes.length);
            exchange.getResponseBody().write(userBytes);
            exchange.close();
        } catch (HttpException httpException) {
            throw httpException;
        } catch (Exception exception) {
            throw new HttpException(HttpStatus.INTERNAL_ERROR, "Unexpected error " + exception.getMessage());
        }
    }

    @Override
    public void handlePost(HttpExchange exchange) {
        LOGGER.info("HTTP User Handler [POST]");
        try {
            final String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Map<String, String> userData = HttpHandlerUtil.validateRequestBody(body);
            User user = userService.getUserByEmail(userData.get("email"));
            if (user != null) {
                throw new HttpException(HttpStatus.CONFLICT, "User already exists");
            }
            user = userService.addUser(userData);
            byte[] userBytes = gson.toJson(user).getBytes();
            exchange.getResponseHeaders().set(Headers.contentType, Headers.appJson);
            exchange.sendResponseHeaders(HttpStatus.CREATED.getStatus(), userBytes.length);
            exchange.getResponseBody().write(userBytes);
            exchange.close();
        } catch (HttpException httpException) {
            throw httpException;
        } catch (Exception exception) {
            throw new HttpException(HttpStatus.INTERNAL_ERROR, "Unexpected error " + exception.getMessage());
        }
    }

    @Override
    public void handlePut(HttpExchange exchange) {
        LOGGER.info("HTTP User Handler [PUT]");
        try {
            final String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Map<String, String> userData = HttpHandlerUtil.validateRequestBody(body);
            String[] paths = exchange.getRequestURI().getPath().substring(1).split("/");
            if (paths.length != 2) {
                throw new HttpException(HttpStatus.BAD_REQUEST, "Bad Request");
            }
            int userId = Integer.parseInt(paths[1]);
            User user = userService.getUserById(userId);
            if (user == null) {
                throw new HttpException(HttpStatus.NOT_FOUND, "User does not exist");
            }
            user = userService.updateUser(userId, userData);
            byte[] userBytes = gson.toJson(user).getBytes();
            exchange.getResponseHeaders().set(Headers.contentType, Headers.appJson);
            exchange.sendResponseHeaders(HttpStatus.OK.getStatus(), userBytes.length);
            exchange.getResponseBody().write(userBytes);
            exchange.close();
        } catch (HttpException httpException) {
            throw httpException;
        } catch (Exception exception) {
            throw new HttpException(HttpStatus.INTERNAL_ERROR, "Unexpected error " + exception.getMessage());
        }
    }

    @Override
    public void handleDelete(HttpExchange exchange) {
        LOGGER.info("HTTP User Handler [DELETE]");
        try {
            String[] paths = exchange.getRequestURI().getPath().substring(1).split("/");
            if (paths.length != 2) {
                throw new HttpException(HttpStatus.BAD_REQUEST, "Bad Request");
            }
            int userId = Integer.parseInt(paths[1]);
            User user = userService.getUserById(userId);
            if (user == null) {
                throw new HttpException(HttpStatus.NOT_FOUND, "User does not exist");
            }
            userService.deleteUser(userId);
            exchange.sendResponseHeaders(HttpStatus.OK.getStatus(), 0);
            exchange.close();
        } catch (HttpException httpException) {
            throw httpException;
        } catch (Exception exception) {
            throw new HttpException(HttpStatus.INTERNAL_ERROR, "Unexpected error " + exception.getMessage());
        }
    }
}
