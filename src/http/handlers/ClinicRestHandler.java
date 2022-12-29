package src.http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import src.clinic.Clinic;
import src.gson.GsonConverter;
import src.http.constants.Headers;
import src.http.constants.HttpStatus;
import src.http.service.ClinicService;
import src.http.util.HttpException;
import src.http.util.HttpHandlerUtil;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class ClinicRestHandler implements RestHandler {
    private static final Logger LOGGER = Logger.getLogger(ClinicRestHandler.class.getName());
    private static final Gson gson = GsonConverter.newGsonWriterConverter();

    private final ClinicService clinicService;

    public ClinicRestHandler(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @Override
    public void handleGet(HttpExchange exchange) {
        LOGGER.info("HTTP Clinic Handler [GET]");
        String[] paths = exchange.getRequestURI().getPath().substring(1).split("/");
        byte[] clinicBytes;
        switch (paths.length) {
            case 1 -> clinicBytes = gson.toJson(clinicService.getAllClinics()).getBytes();
            case 2 -> {
                try {
                    int clinicId = Integer.parseInt(paths[1]);
                    Clinic clinic = clinicService.getClinicById(clinicId);
                    clinicBytes = gson.toJson(Objects.requireNonNullElse(clinic, "{}")).getBytes();
                } catch (NumberFormatException e) {
                    throw new HttpException(HttpStatus.BAD_REQUEST, "Invalid payload");
                }
            }
            default -> throw new HttpException(HttpStatus.BAD_REQUEST, "Invalid payload");
        }
        try {
            exchange.getResponseHeaders().set(Headers.contentType, Headers.appJson);
            exchange.sendResponseHeaders(HttpStatus.OK.getStatus(), clinicBytes.length);
            exchange.getResponseBody().write(clinicBytes);
            exchange.close();
        } catch (HttpException httpException) {
            throw httpException;
        } catch (Exception exception) {
            throw new HttpException(HttpStatus.INTERNAL_ERROR, "Unexpected error " + exception.getMessage());
        }
    }

    @Override
    public void handlePost(HttpExchange exchange) {
        LOGGER.info("HTTP Clinic Handler [POST]");
        try {
            final String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Map<String, String> clinicData = HttpHandlerUtil.validateRequestBody(body);
            Clinic clinic = clinicService.addClinic(clinicData);
            byte[] clinicBytes = gson.toJson(clinic).getBytes();
            exchange.getResponseHeaders().set(Headers.contentType, Headers.appJson);
            exchange.sendResponseHeaders(HttpStatus.CREATED.getStatus(), clinicBytes.length);
            exchange.getResponseBody().write(clinicBytes);
            exchange.close();
        } catch (HttpException httpException) {
            throw httpException;
        } catch (Exception exception) {
            throw new HttpException(HttpStatus.INTERNAL_ERROR, "Unexpected error " + exception.getMessage());
        }
    }

    @Override
    public void handlePut(HttpExchange exchange) {
        LOGGER.info("HTTP Clinic Handler [PUT]");
        try {
            final String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Map<String, String> clinicData = HttpHandlerUtil.validateRequestBody(body);
            String[] paths = exchange.getRequestURI().getPath().substring(1).split("/");
            if (paths.length != 2) {
                throw new HttpException(HttpStatus.BAD_REQUEST, "Bad Request");
            }
            int clinicId = Integer.parseInt(paths[1]);
            Clinic clinic = clinicService.getClinicById(clinicId);
            if (clinic == null) {
                throw new HttpException(HttpStatus.NOT_FOUND, "Clinic does not exist");
            }
            clinic = clinicService.updateClinic(clinicId, clinicData);
            byte[] clinicBytes = gson.toJson(clinic).getBytes();
            exchange.getResponseHeaders().set(Headers.contentType, Headers.appJson);
            exchange.sendResponseHeaders(HttpStatus.OK.getStatus(), clinicBytes.length);
            exchange.getResponseBody().write(clinicBytes);
            exchange.close();
        } catch (HttpException httpException) {
            throw httpException;
        } catch (Exception exception) {
            throw new HttpException(HttpStatus.INTERNAL_ERROR, "Unexpected error " + exception.getMessage());
        }
    }

    @Override
    public void handleDelete(HttpExchange exchange) {
        LOGGER.info("HTTP Clinic Handler [DELETE]");
        try {
            String[] paths = exchange.getRequestURI().getPath().substring(1).split("/");
            if (paths.length != 2) {
                throw new HttpException(HttpStatus.BAD_REQUEST, "Bad Request");
            }
            int clinicId = Integer.parseInt(paths[1]);
            Clinic clinic = clinicService.getClinicById(clinicId);
            if (clinic == null) {
                throw new HttpException(HttpStatus.NOT_FOUND, "Clinic does not exist");
            }
            clinicService.deleteClinic(clinicId);
            exchange.sendResponseHeaders(HttpStatus.OK.getStatus(), 0);
            exchange.close();
        } catch (HttpException httpException) {
            throw httpException;
        } catch (Exception exception) {
            throw new HttpException(HttpStatus.INTERNAL_ERROR, "Unexpected error " + exception.getMessage());
        }
    }
}
