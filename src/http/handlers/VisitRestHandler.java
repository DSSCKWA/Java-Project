package src.http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import src.gson.GsonConverter;
import src.http.constants.Headers;
import src.http.constants.HttpStatus;
import src.http.service.VisitService;
import src.http.util.HttpException;
import src.http.util.HttpHandlerUtil;
import src.users.User;
import src.visit.Visit;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class VisitRestHandler implements RestHandler {
    private static final Logger LOGGER = Logger.getLogger(VisitRestHandler.class.getName());
    private static final Gson gson = GsonConverter.newGsonWriterConverter();

    private final VisitService visitService;

    public VisitRestHandler(VisitService visitService) {
        this.visitService = visitService;
    }

    @Override
    public void handleGet(HttpExchange exchange) {
        LOGGER.info("HTTP Visit Handler [GET]");
        String[] paths = exchange.getRequestURI().getPath().substring(1).split("/");
        byte[] visitBytes;
        switch (paths.length) {
            case 1 -> {
                String query = exchange.getRequestURI().getQuery();
                if (query != null) {
                    Map<String, String> queryMap = HttpHandlerUtil.getQueryParams(query);
                    if (queryMap.get("doctorId") != null) {
                        visitBytes = gson.toJson(visitService.getVisitsByDoctorId(Integer.parseInt(queryMap.get("doctorId")))).getBytes();
                    } else if (queryMap.get("clientId") != null) {
                        visitBytes = gson.toJson(visitService.getVisitsByClientId(Integer.parseInt(queryMap.get("clientId")))).getBytes();
                    } else {
                        visitBytes = gson.toJson(visitService.getAllVisits()).getBytes();
                    }
                } else {
                    visitBytes = gson.toJson(visitService.getAllVisits()).getBytes();
                }
            }
            case 2 -> {
                try {
                    int visitId = Integer.parseInt(paths[1]);
                    Visit visit = visitService.getVisitById(visitId);
                    if (visit == null) {
                        throw new HttpException(HttpStatus.NOT_FOUND, "Visit does not exist");
                    } else {
                        visitBytes = gson.toJson(visit).getBytes();
                    }
                } catch (NumberFormatException e) {
                    throw new HttpException(HttpStatus.BAD_REQUEST, "Invalid payload");
                }
            }
            default -> throw new HttpException(HttpStatus.BAD_REQUEST, "Invalid payload");
        }
        try {
            exchange.getResponseHeaders().set(Headers.contentType, Headers.appJson);
            exchange.sendResponseHeaders(HttpStatus.OK.getStatus(), visitBytes.length);
            exchange.getResponseBody().write(visitBytes);
            exchange.close();
        } catch (HttpException httpException) {
            throw httpException;
        } catch (Exception exception) {
            throw new HttpException(HttpStatus.INTERNAL_ERROR, "Unexpected error " + exception.getMessage());
        }
    }

    @Override
    public void handlePost(HttpExchange exchange) {
        LOGGER.info("HTTP Visit Handler [POST]");
        try {
            final String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Map<String, String> visitData = HttpHandlerUtil.validateRequestBody(body);
            Visit visit = visitService.getVisitByDateTime(
                    Integer.parseInt(visitData.get("doctorId")),
                    Integer.parseInt(visitData.get("clientId")),
                    LocalDate.parse(visitData.get("date")),
                    LocalTime.parse(visitData.get("time"))
            );
            if (visit != null) {
                throw new HttpException(HttpStatus.CONFLICT, "Visit already exist");
            }
            visit = visitService.addVisit(visitData);
            byte[] visitBytes = gson.toJson(visit).getBytes();
            exchange.getResponseHeaders().set(Headers.contentType, Headers.appJson);
            exchange.sendResponseHeaders(HttpStatus.CREATED.getStatus(), visitBytes.length);
            exchange.getResponseBody().write(visitBytes);
            exchange.close();
        } catch (HttpException httpException) {
            throw httpException;
        } catch (Exception exception) {
            throw new HttpException(HttpStatus.INTERNAL_ERROR, "Unexpected error " + exception.getMessage());
        }
    }

    @Override
    public void handlePut(HttpExchange exchange) {
        LOGGER.info("HTTP Visit Handler [PUT]");
        try {
            final String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Map<String, String> visitData = HttpHandlerUtil.validateRequestBody(body);
            String[] paths = exchange.getRequestURI().getPath().substring(1).split("/");
            if (paths.length != 2) {
                throw new HttpException(HttpStatus.BAD_REQUEST, "Bad Request");
            }
            int visitId = Integer.parseInt(paths[1]);
            Visit visit = visitService.getVisitById(visitId);
            if (visit == null) {
                throw new HttpException(HttpStatus.NOT_FOUND, "Visit does not exist");
            }
            visit = visitService.updateVisit(visitId, visitData);
            byte[] visitBytes = gson.toJson(visit).getBytes();
            exchange.getResponseHeaders().set(Headers.contentType, Headers.appJson);
            exchange.sendResponseHeaders(HttpStatus.OK.getStatus(), visitBytes.length);
            exchange.getResponseBody().write(visitBytes);
            exchange.close();
        } catch (HttpException httpException) {
            throw httpException;
        } catch (Exception exception) {
            throw new HttpException(HttpStatus.INTERNAL_ERROR, "Unexpected error " + exception.getMessage());
        }
    }

    @Override
    public void handleDelete(HttpExchange exchange) {
        LOGGER.info("HTTP Visit Handler [DELETE]");
        throw new HttpException(HttpStatus.METHOD_NOT_ALLOWED, "Visit cannot be deleted");
    }
}
