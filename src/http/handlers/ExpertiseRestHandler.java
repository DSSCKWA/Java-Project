package src.http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import src.expertise.Expertise;
import src.gson.GsonConverter;
import src.http.constants.Headers;
import src.http.constants.HttpStatus;
import src.http.service.ExpertiseService;
import src.http.service.UserService;
import src.http.util.HttpException;
import src.http.util.HttpHandlerUtil;
import src.users.Permissions;
import src.users.User;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;

public class ExpertiseRestHandler implements RestHandler {
    private static final Logger LOGGER = Logger.getLogger(ExpertiseRestHandler.class.getName());
    private static final Gson gson = GsonConverter.newGsonWriterConverter();

    private final ExpertiseService expertiseService;
    private final UserService userService;

    public ExpertiseRestHandler(ExpertiseService expertiseService, UserService userService) {
        this.expertiseService = expertiseService;
        this.userService = userService;
    }

    @Override
    public void handleGet(HttpExchange exchange) {
        LOGGER.info("HTTP Expertise Handler [GET]");
        String[] paths = exchange.getRequestURI().getPath().substring(1).split("/");
        byte[] expertiseBytes;
        if (paths.length == 1) {
            String query = exchange.getRequestURI().getQuery();
            if (query != null) {
                Map<String, String> queryMap = HttpHandlerUtil.getQueryParams(query);
                if (queryMap.get("doctorId") != null && queryMap.get("areaOfExpertise") == null) {
                    expertiseBytes = gson.toJson(expertiseService.getExpertise(Integer.parseInt(queryMap.get("doctorId")))).getBytes();
                } else if (queryMap.get("doctorId") == null && queryMap.get("areaOfExpertise") != null) {
                    expertiseBytes = gson.toJson(expertiseService.getExpertise(queryMap.get("areaOfExpertise"))).getBytes();
                } else if (queryMap.get("doctorId") != null && queryMap.get("areaOfExpertise") != null) {
                    Expertise expertise = expertiseService.getExpertise(Integer.parseInt(queryMap.get("doctorId")), queryMap.get("areaOfExpertise"));
                    if (expertise == null) {
                        throw new HttpException(HttpStatus.NOT_FOUND, "Expertise does not exist for this doctor");
                    }
                    expertiseBytes = gson.toJson(expertise).getBytes();
                } else {
                    throw new HttpException(HttpStatus.BAD_REQUEST, "Unsupported query");
                }
            } else {
                expertiseBytes = gson.toJson(expertiseService.getAllExpertises()).getBytes();
            }
        } else {
            throw new HttpException(HttpStatus.BAD_REQUEST, "Invalid payload");
        }
        try {
            exchange.getResponseHeaders().set(Headers.contentType, Headers.appJson);
            exchange.sendResponseHeaders(HttpStatus.OK.getStatus(), expertiseBytes.length);
            exchange.getResponseBody().write(expertiseBytes);
            exchange.close();
        } catch (HttpException httpException) {
            throw httpException;
        } catch (Exception exception) {
            throw new HttpException(HttpStatus.INTERNAL_ERROR, "Unexpected error " + exception.getMessage());
        }
    }

    @Override
    public void handlePost(HttpExchange exchange) {
        LOGGER.info("HTTP Expertise Handler [POST]");
        try {
            final String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Map<String, String> expertiseData = HttpHandlerUtil.validateRequestBody(body);
            Expertise expertise = expertiseService.getExpertise(Integer.parseInt(expertiseData.get("doctorId")), expertiseData.get("areaOfExpertise"));
            if (expertise != null) {
                throw new HttpException(HttpStatus.CONFLICT, "Expertise already exist for this doctor");
            }
            User user = userService.getUser(Integer.parseInt(expertiseData.get("doctorId")));
            if (user == null) {
                throw new HttpException(HttpStatus.NOT_FOUND, "User does not exist");
            }
            if (!user.getPermissions().equals(Permissions.DOCTOR)) {
                throw new HttpException(HttpStatus.BAD_REQUEST, "User is not a doctor");
            }
            expertise = expertiseService.addExpertise(expertiseData);
            byte[] expertiseBytes = gson.toJson(expertise).getBytes();
            exchange.getResponseHeaders().set(Headers.contentType, Headers.appJson);
            exchange.sendResponseHeaders(HttpStatus.CREATED.getStatus(), expertiseBytes.length);
            exchange.getResponseBody().write(expertiseBytes);
            exchange.close();
        } catch (HttpException httpException) {
            throw httpException;
        } catch (Exception exception) {
            throw new HttpException(HttpStatus.INTERNAL_ERROR, "Unexpected error " + exception.getMessage());
        }
    }

    @Override
    public void handlePut(HttpExchange exchange) {
        LOGGER.info("HTTP Expertise Handler [PUT]");
        throw new HttpException(HttpStatus.METHOD_NOT_ALLOWED, "Expertise cannot be updated");
    }

    @Override
    public void handleDelete(HttpExchange exchange) {
        LOGGER.info("HTTP Expertise Handler [DELETE]");
        try {
            String[] paths = exchange.getRequestURI().getPath().substring(1).split("/");
            if (paths.length == 1) {
                String query = exchange.getRequestURI().getQuery();
                if (query != null) {
                    Map<String, String> queryMap = HttpHandlerUtil.getQueryParams(query);
                    if (queryMap.get("doctorId") != null && queryMap.get("areaOfExpertise") == null) {
                        ArrayList<Expertise> expertises = expertiseService.getExpertise(Integer.parseInt(queryMap.get("doctorId")));
                        if (expertises.size() == 0) {
                            throw new HttpException(HttpStatus.NOT_FOUND, "Expertises does not exist for this doctor");
                        }
                        expertiseService.deleteExpertise(Integer.parseInt(queryMap.get("doctorId")));
                    } else if (queryMap.get("doctorId") == null && queryMap.get("areaOfExpertise") != null) {
                        ArrayList<Expertise> expertises = expertiseService.getExpertise(queryMap.get("areaOfExpertise"));
                        if (expertises.size() == 0) {
                            throw new HttpException(HttpStatus.NOT_FOUND, "Expertises does not exist");
                        }
                        expertiseService.deleteExpertise(queryMap.get("areaOfExpertise"));
                    } else if (queryMap.get("doctorId") != null && queryMap.get("areaOfExpertise") != null) {
                        Expertise expertise = expertiseService.getExpertise(Integer.parseInt(queryMap.get("doctorId")), queryMap.get("areaOfExpertise"));
                        if (expertise == null) {
                            throw new HttpException(HttpStatus.NOT_FOUND, "Expertise does not exist for this doctor");
                        }
                        expertiseService.deleteExpertise(Integer.parseInt(queryMap.get("doctorId")), queryMap.get("areaOfExpertise"));
                    } else {
                        throw new HttpException(HttpStatus.BAD_REQUEST, "Invalid request");
                    }
                } else {
                    throw new HttpException(HttpStatus.BAD_REQUEST, "Invalid request");
                }
            } else {
                throw new HttpException(HttpStatus.BAD_REQUEST, "Invalid request");
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
