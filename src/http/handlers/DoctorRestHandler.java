package src.http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import src.clinic.Clinic;
import src.gson.GsonConverter;
import src.http.constants.Headers;
import src.http.constants.HttpStatus;
import src.http.service.ClinicService;
import src.http.service.DoctorService;
import src.http.service.UserService;
import src.http.util.HttpException;
import src.http.util.HttpHandlerUtil;
import src.users.Doctor;
import src.users.Permissions;
import src.users.User;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;

public class DoctorRestHandler implements RestHandler {
    private static final Logger LOGGER = Logger.getLogger(DoctorRestHandler.class.getName());
    private static final Gson gson = GsonConverter.newGsonWriterConverter();

    private final DoctorService doctorService;
    private final UserService userService;
    private final ClinicService clinicService;

    public DoctorRestHandler(DoctorService doctorService, UserService userService, ClinicService clinicService) {
        this.doctorService = doctorService;
        this.userService = userService;
        this.clinicService = clinicService;
    }

    @Override
    public void handleGet(HttpExchange exchange) {
        LOGGER.info("HTTP Doctor Handler [GET]");
        String[] paths = exchange.getRequestURI().getPath().substring(1).split("/");
        byte[] doctorBytes;
        switch (paths.length) {
            case 1 -> {
                ArrayList<User> users = userService.getUsersByPermission(Permissions.DOCTOR);
                doctorBytes = gson.toJson(doctorService.usersToDoctors(users)).getBytes();
            }
            case 2 -> {
                try {
                    int doctorId = Integer.parseInt(paths[1]);
                    User user = userService.getUser(doctorId);
                    if (user == null) {
                        throw new HttpException(HttpStatus.NOT_FOUND, "Doctor does not exist");
                    } else if (!user.getPermissions().equals(Permissions.DOCTOR)) {
                        throw new HttpException(HttpStatus.BAD_REQUEST, "This user is not a doctor");
                    } else {
                        doctorBytes = gson.toJson(doctorService.userToDoctors(user)).getBytes();
                    }
                } catch (NumberFormatException e) {
                    throw new HttpException(HttpStatus.BAD_REQUEST, "Invalid payload");
                }
            }
            default -> throw new HttpException(HttpStatus.BAD_REQUEST, "Invalid payload");
        }
        try {
            exchange.getResponseHeaders().set(Headers.contentType, Headers.appJson);
            exchange.sendResponseHeaders(HttpStatus.OK.getStatus(), doctorBytes.length);
            exchange.getResponseBody().write(doctorBytes);
            exchange.close();
        } catch (HttpException httpException) {
            throw httpException;
        } catch (Exception exception) {
            throw new HttpException(HttpStatus.INTERNAL_ERROR, "Unexpected error " + exception.getMessage());
        }
    }

    @Override
    public void handlePost(HttpExchange exchange) {
        LOGGER.info("HTTP Doctor Handler [POST]");
        try {
            final String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Map<String, String> doctorData = HttpHandlerUtil.validateRequestBody(body);
            int doctorId = Integer.parseInt(doctorData.get("doctorId"));
            int clinicId = Integer.parseInt(doctorData.get("clinicId"));
            User user = userService.getUser(doctorId);
            if (user == null) {
                throw new HttpException(HttpStatus.NOT_FOUND, "Doctor does not exist");
            }
            if (!user.getPermissions().equals(Permissions.DOCTOR)) {
                throw new HttpException(HttpStatus.BAD_REQUEST, "This user is not a doctor");
            }
            Clinic clinic = clinicService.getClinic(clinicId);
            if (clinic == null) {
                throw new HttpException(HttpStatus.NOT_FOUND, "Clinic does not exist");
            }
            Doctor doctor = doctorService.getDoctor(doctorId, clinicId);
            if (doctor != null) {
                throw new HttpException(HttpStatus.CONFLICT, "Doctor already exists in this clinic");
            }
            doctor = doctorService.addDoctor(doctorData);
            byte[] doctorBytes = gson.toJson(doctor).getBytes();
            exchange.getResponseHeaders().set(Headers.contentType, Headers.appJson);
            exchange.sendResponseHeaders(HttpStatus.CREATED.getStatus(), doctorBytes.length);
            exchange.getResponseBody().write(doctorBytes);
            exchange.close();
        } catch (HttpException httpException) {
            throw httpException;
        } catch (Exception exception) {
            throw new HttpException(HttpStatus.INTERNAL_ERROR, "Unexpected error " + exception.getMessage());
        }
    }

    @Override
    public void handlePut(HttpExchange exchange) {
        LOGGER.info("HTTP Doctor Handler [PUT]");
        throw new HttpException(HttpStatus.METHOD_NOT_ALLOWED, "Doctor cannot be updated");
    }

    @Override
    public void handleDelete(HttpExchange exchange) {
        LOGGER.info("HTTP Doctor Handler [DELETE]");
        try {
            String[] paths = exchange.getRequestURI().getPath().substring(1).split("/");
            if (paths.length != 2) {
                throw new HttpException(HttpStatus.BAD_REQUEST, "Bad Request");
            }
            int doctorId = Integer.parseInt(paths[1]);
            doctorService.getDoctors(doctorId);
            String query = exchange.getRequestURI().getQuery();
            if (query != null) {
                Map<String, String> queryMap = HttpHandlerUtil.getQueryParams(query);
                if (queryMap.get("clinicId") != null) {
                    int clinicId = Integer.parseInt(queryMap.get("clinicId"));
                    Doctor doctor = doctorService.getDoctor(doctorId, clinicId);
                    if (doctor == null) {
                        throw new HttpException(HttpStatus.NOT_FOUND, "Doctor does not exist in this clinic");
                    } else {
                        doctorService.deleteDoctor(doctorId, clinicId);
                    }
                } else {
                    throw new HttpException(HttpStatus.BAD_REQUEST, "Unsupported query");
                }
            } else {
                ArrayList<Doctor> doctors = doctorService.getDoctors(doctorId);
                if (doctors.size() == 0) {
                    throw new HttpException(HttpStatus.NOT_FOUND, "Doctor does not exist");
                } else {
                    doctorService.deleteAllDoctorEntries(doctorId);
                }
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
