package src.http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import src.equipment.Equipment;
import src.gson.GsonConverter;
import src.http.constants.Headers;
import src.http.constants.HttpStatus;
import src.http.service.ClinicService;
import src.http.service.EquipmentService;
import src.http.util.HttpException;
import src.http.util.HttpHandlerUtil;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class EquipmentRestHandler implements RestHandler {
    private static final Logger LOGGER = Logger.getLogger(EquipmentRestHandler.class.getName());
    private static final Gson gson = GsonConverter.newGsonWriterConverter();

    private final ClinicService clinicService;
    private final EquipmentService equipmentService;

    public EquipmentRestHandler(EquipmentService equipmentService, ClinicService clinicService) {
        this.equipmentService = equipmentService;
        this.clinicService = clinicService;
    }

    @Override
    public void handleGet(HttpExchange exchange) {
        LOGGER.info("HTTP Equipment Handler [GET]");
        String[] paths = exchange.getRequestURI().getPath().substring(1).split("/");
        byte[] equipmentBytes;
        switch (paths.length) {
            case 1 -> {
                String query = exchange.getRequestURI().getQuery();
                if (query != null) {
                    Map<String, String> queryMap = HttpHandlerUtil.getQueryParams(query);
                    if (queryMap.get("clinicId") != null) {
                        equipmentBytes = gson.toJson(equipmentService.getAllEquipmentByClinic(Integer.parseInt(queryMap.get("clinicId")))).getBytes();
                    } else {
                        equipmentBytes = gson.toJson(equipmentService.getAllEquipment()).getBytes();
                    }
                } else {
                    equipmentBytes = gson.toJson(equipmentService.getAllEquipment()).getBytes();
                }
            }
            case 2 -> {
                try {
                    int equipmentId = Integer.parseInt(paths[1]);
                    Equipment equipment = equipmentService.getEquipmentById(equipmentId);
                    equipmentBytes = gson.toJson(Objects.requireNonNullElse(equipment, "{}")).getBytes();
                } catch (NumberFormatException e) {
                    throw new HttpException(HttpStatus.BAD_REQUEST, "Invalid payload");
                }
            }
            default -> throw new HttpException(HttpStatus.BAD_REQUEST, "Invalid payload");
        }
        try {
            exchange.getResponseHeaders().set(Headers.contentType, Headers.appJson);
            exchange.sendResponseHeaders(HttpStatus.OK.getStatus(), equipmentBytes.length);
            exchange.getResponseBody().write(equipmentBytes);
            exchange.close();
        } catch (HttpException httpException) {
            throw httpException;
        } catch (Exception exception) {
            throw new HttpException(HttpStatus.INTERNAL_ERROR, "Unexpected error " + exception.getMessage());
        }
    }

    @Override
    public void handlePost(HttpExchange exchange) {
        LOGGER.info("HTTP Equipment Handler [POST]");
        try {
            final String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Map<String, String> equipmentData = HttpHandlerUtil.validateRequestBody(body);
            int clinicId = Integer.parseInt(equipmentData.get("clinicId"));
            if (clinicService.getClinicById(clinicId) == null) {
                throw new HttpException(HttpStatus.NOT_FOUND, "Clinic does not exist");
            }
            Equipment equipment = equipmentService.addEquipment(equipmentData);
            byte[] equipmentBytes = gson.toJson(equipment).getBytes();
            exchange.getResponseHeaders().set(Headers.contentType, Headers.appJson);
            exchange.sendResponseHeaders(HttpStatus.CREATED.getStatus(), equipmentBytes.length);
            exchange.getResponseBody().write(equipmentBytes);
            exchange.close();
        } catch (HttpException httpException) {
            throw httpException;
        } catch (Exception exception) {
            throw new HttpException(HttpStatus.INTERNAL_ERROR, "Unexpected error " + exception.getMessage());
        }
    }

    @Override
    public void handlePut(HttpExchange exchange) {
        LOGGER.info("HTTP Equipment Handler [PUT]");
        try {
            final String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Map<String, String> equipmentData = HttpHandlerUtil.validateRequestBody(body);
            String[] paths = exchange.getRequestURI().getPath().substring(1).split("/");
            if (paths.length != 2) {
                throw new HttpException(HttpStatus.BAD_REQUEST, "Bad Request");
            }

            int equipmentId = Integer.parseInt(paths[1]);
            Equipment equipment = equipmentService.getEquipmentById(equipmentId);
            if (equipment == null) {
                throw new HttpException(HttpStatus.NOT_FOUND, "Equipment does not exist");
            }

            int clinicId = Integer.parseInt(equipmentData.get("clinicId"));
            if (clinicService.getClinicById(clinicId) == null) {
                throw new HttpException(HttpStatus.NOT_FOUND, "Clinic does not exist");
            }

            equipment = equipmentService.updateEquipment(equipmentId, equipmentData);
            byte[] equipmentBytes = gson.toJson(equipment).getBytes();
            exchange.getResponseHeaders().set(Headers.contentType, Headers.appJson);
            exchange.sendResponseHeaders(HttpStatus.OK.getStatus(), equipmentBytes.length);
            exchange.getResponseBody().write(equipmentBytes);
            exchange.close();
        } catch (HttpException httpException) {
            throw httpException;
        } catch (Exception exception) {
            throw new HttpException(HttpStatus.INTERNAL_ERROR, "Unexpected error " + exception.getMessage());
        }
    }

    @Override
    public void handleDelete(HttpExchange exchange) {
        LOGGER.info("HTTP Equipment Handler [DELETE]");
        try {
            String[] paths = exchange.getRequestURI().getPath().substring(1).split("/");
            if (paths.length != 2) {
                throw new HttpException(HttpStatus.BAD_REQUEST, "Bad Request");
            }
            int equipmentId = Integer.parseInt(paths[1]);
            Equipment equipment = equipmentService.getEquipmentById(equipmentId);
            if (equipment == null) {
                throw new HttpException(HttpStatus.NOT_FOUND, "Equipment does not exist");
            }
            equipmentService.deleteEquipment(equipmentId);
            exchange.sendResponseHeaders(HttpStatus.OK.getStatus(), 0);
            exchange.close();
        } catch (HttpException httpException) {
            throw httpException;
        } catch (Exception exception) {
            throw new HttpException(HttpStatus.INTERNAL_ERROR, "Unexpected error " + exception.getMessage());
        }
    }
}
