package src.http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import src.clinic.Clinic;
import src.http.service.ClinicService;
import src.http.service.UserService;
import src.schedule.Schedule;
import src.gson.GsonConverter;
import src.http.constants.Headers;
import src.http.constants.HttpStatus;
import src.http.service.ScheduleService;
import src.http.util.HttpException;
import src.http.util.HttpHandlerUtil;
import src.users.Permissions;
import src.users.User;

import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

public class ScheduleRestHandler implements RestHandler {
    private static final Logger LOGGER = Logger.getLogger(ScheduleRestHandler.class.getName());
    private static final Gson gson = GsonConverter.newGsonWriterConverter();

    private final ScheduleService scheduleService;
    private final UserService userService;
    private final ClinicService clinicService;

    public ScheduleRestHandler(ScheduleService scheduleService, UserService userService, ClinicService clinicService) {
        this.scheduleService = scheduleService;
        this.userService = userService;
        this.clinicService = clinicService;
    }

    @Override
    public void handleGet(HttpExchange exchange) {
        LOGGER.info("HTTP Schedule Handler [GET]");
        String[] paths = exchange.getRequestURI().getPath().substring(1).split("/");
        byte[] scheduleBytes;
        if (paths.length == 1) {
            String query = exchange.getRequestURI().getQuery();
            if (query != null) {
                Map<String, String> queryMap = HttpHandlerUtil.getQueryParams(query);
                if (queryMap.get("doctorId") != null && queryMap.get("clinicId") == null && queryMap.get("day") == null) {
                    scheduleBytes = gson.toJson(scheduleService.getSchedules(
                            Integer.parseInt(queryMap.get("doctorId"))
                    )).getBytes();
                } else if (queryMap.get("doctorId") != null && queryMap.get("clinicId") != null && queryMap.get("day") == null) {
                    scheduleBytes = gson.toJson(scheduleService.getSchedules(
                            Integer.parseInt(queryMap.get("doctorId")),
                            Integer.parseInt(queryMap.get("clinicId"))
                    )).getBytes();
                } else if (queryMap.get("doctorId") != null && queryMap.get("clinicId") != null && queryMap.get("day") != null) {
                    Schedule schedule = scheduleService.getSchedule(
                            Integer.parseInt(queryMap.get("doctorId")),
                            Integer.parseInt(queryMap.get("clinicId")),
                            DayOfWeek.valueOf(queryMap.get("day").toUpperCase(Locale.ROOT)));
                    if (schedule == null) {
                        throw new HttpException(HttpStatus.NOT_FOUND, "Schedule does not exist");
                    }
                    scheduleBytes = gson.toJson(schedule).getBytes();
                } else {
                    throw new HttpException(HttpStatus.BAD_REQUEST, "Unsupported query");
                }
            } else {
                scheduleBytes = gson.toJson(scheduleService.getAllSchedules()).getBytes();
            }
        } else {
            throw new HttpException(HttpStatus.BAD_REQUEST, "Invalid payload");
        }
        try {
            exchange.getResponseHeaders().set(Headers.contentType, Headers.appJson);
            exchange.sendResponseHeaders(HttpStatus.OK.getStatus(), scheduleBytes.length);
            exchange.getResponseBody().write(scheduleBytes);
            exchange.close();
        } catch (HttpException httpException) {
            throw httpException;
        } catch (Exception exception) {
            throw new HttpException(HttpStatus.INTERNAL_ERROR, "Unexpected error " + exception.getMessage());
        }
    }

    @Override
    public void handlePost(HttpExchange exchange) {
        LOGGER.info("HTTP Schedule Handler [POST]");
        try {
            final String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Map<String, String> scheduleData = HttpHandlerUtil.validateRequestBody(body);
            Schedule schedule = scheduleService.getSchedule(
                    Integer.parseInt(scheduleData.get("doctorId")),
                    Integer.parseInt(scheduleData.get("clinicId")),
                    DayOfWeek.valueOf(scheduleData.get("day").toUpperCase(Locale.ROOT)));
            if (schedule != null) {
                throw new HttpException(HttpStatus.CONFLICT, "Schedule already exists");
            }
            User user = userService.getUser(Integer.parseInt(scheduleData.get("doctorId")));
            if (user == null) {
                throw new HttpException(HttpStatus.NOT_FOUND, "User does not exist");
            }
            if (!user.getPermissions().equals(Permissions.DOCTOR)) {
                throw new HttpException(HttpStatus.BAD_REQUEST, "User is not a doctor");
            }
            Clinic clinic = clinicService.getClinic(Integer.parseInt(scheduleData.get("clinicId")));
            if (clinic == null) {
                throw new HttpException(HttpStatus.NOT_FOUND, "Clinic does not exist");
            }
            schedule = scheduleService.addSchedule(scheduleData);
            byte[] scheduleBytes = gson.toJson(schedule).getBytes();
            exchange.getResponseHeaders().set(Headers.contentType, Headers.appJson);
            exchange.sendResponseHeaders(HttpStatus.CREATED.getStatus(), scheduleBytes.length);
            exchange.getResponseBody().write(scheduleBytes);
            exchange.close();
        } catch (HttpException httpException) {
            throw httpException;
        } catch (Exception exception) {
            throw new HttpException(HttpStatus.INTERNAL_ERROR, "Unexpected error " + exception.getMessage());
        }
    }

    @Override
    public void handlePut(HttpExchange exchange) {
        LOGGER.info("HTTP Schedule Handler [PUT]");
        try {
            final String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Map<String, String> scheduleData = HttpHandlerUtil.validateRequestBody(body);
            Schedule schedule = scheduleService.getSchedule(
                    Integer.parseInt(scheduleData.get("doctorId")),
                    Integer.parseInt(scheduleData.get("clinicId")),
                    DayOfWeek.valueOf(scheduleData.get("day").toUpperCase(Locale.ROOT)));
            if (schedule == null) {
                throw new HttpException(HttpStatus.NOT_FOUND, "Schedule does not exist");
            }
            User user = userService.getUser(Integer.parseInt(scheduleData.get("doctorId")));
            if (user == null) {
                throw new HttpException(HttpStatus.NOT_FOUND, "User does not exist");
            }
            if (!user.getPermissions().equals(Permissions.DOCTOR)) {
                throw new HttpException(HttpStatus.BAD_REQUEST, "User is not a doctor");
            }
            Clinic clinic = clinicService.getClinic(Integer.parseInt(scheduleData.get("clinicId")));
            if (clinic == null) {
                throw new HttpException(HttpStatus.NOT_FOUND, "Clinic does not exist");
            }
            schedule = scheduleService.updateSchedule(scheduleData);
            byte[] scheduleBytes = gson.toJson(schedule).getBytes();
            exchange.getResponseHeaders().set(Headers.contentType, Headers.appJson);
            exchange.sendResponseHeaders(HttpStatus.OK.getStatus(), scheduleBytes.length);
            exchange.getResponseBody().write(scheduleBytes);
            exchange.close();
        } catch (HttpException httpException) {
            throw httpException;
        } catch (Exception exception) {
            throw new HttpException(HttpStatus.INTERNAL_ERROR, "Unexpected error " + exception.getMessage());
        }
    }

    @Override
    public void handleDelete(HttpExchange exchange) {
        LOGGER.info("HTTP Schedule Handler [DELETE]");
        try {
            String[] paths = exchange.getRequestURI().getPath().substring(1).split("/");
            if (paths.length == 1) {
                String query = exchange.getRequestURI().getQuery();
                if (query != null) {
                    Map<String, String> queryMap = HttpHandlerUtil.getQueryParams(query);
                    if (queryMap.get("doctorId") != null && queryMap.get("clinicId") == null && queryMap.get("day") == null) {
                        scheduleService.deleteSchedule(Integer.parseInt(queryMap.get("doctorId")));
                    } else if (queryMap.get("doctorId") != null && queryMap.get("clinicId") != null && queryMap.get("day") != null) {
                        Schedule schedule = scheduleService.getSchedule(
                                Integer.parseInt(queryMap.get("doctorId")),
                                Integer.parseInt(queryMap.get("clinicId")),
                                DayOfWeek.valueOf(queryMap.get("day").toUpperCase(Locale.ROOT)));
                        if (schedule == null) {
                            throw new HttpException(HttpStatus.NOT_FOUND, "Schedule does not exist");
                        }
                        scheduleService.deleteSchedule(
                                Integer.parseInt(queryMap.get("doctorId")),
                                Integer.parseInt(queryMap.get("clinicId")),
                                DayOfWeek.valueOf(queryMap.get("day").toUpperCase(Locale.ROOT))
                        );
                    } else {
                        throw new HttpException(HttpStatus.BAD_REQUEST, "Unsupported query");
                    }
                } else {
                    throw new HttpException(HttpStatus.BAD_REQUEST, "Invalid path");
                }
            } else {
                throw new HttpException(HttpStatus.BAD_REQUEST, "Invalid path");
            }
            exchange.sendResponseHeaders(HttpStatus.OK.getStatus(), 0);
            exchange.close();
        } catch (HttpException httpException) {
            throw httpException;
        } catch (Exception exception) {
            throw new HttpException(HttpStatus.INTERNAL_ERROR, "Unexpected error " + exception.getMessage());
        }
    }
}
