package src.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import src.http.constants.HttpStatus;
import src.http.service.ClinicService;
import src.http.util.HttpException;
import src.http.util.HttpHandlerUtil;
import src.users.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Logger;

public class ClinicRestHanlder implements RestHandler {
    private static final Logger LOGGER = Logger.getLogger(ClinicRestHanlder.class.getName());

    private final ClinicService clinicService;

    public ClinicRestHanlder(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @Override
    public void handleGet(HttpExchange exchange) {
        LOGGER.info("HTTP User Handler [GET]");
        //TODO implement getAll and get by id
    }

    @Override
    public void handlePost(HttpExchange exchange) {
        LOGGER.info("HTTP User Handler [POST]");
    }

    @Override
    public void handlePut(HttpExchange exchange) {
        LOGGER.info("HTTP User Handler [PUT]");
    }

    @Override
    public void handleDelete(HttpExchange exchange) {
        LOGGER.info("HTTP User Handler [DELETE]");
    }
}
