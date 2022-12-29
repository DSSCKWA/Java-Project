package src.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import src.http.service.DoctorService;

import java.util.logging.Logger;

public class DoctorRestHandler implements RestHandler {
    private static final Logger LOGGER = Logger.getLogger(DoctorRestHandler.class.getName());

    private final DoctorService doctorService;

    public DoctorRestHandler(DoctorService doctorService) {
        this.doctorService = doctorService;
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
