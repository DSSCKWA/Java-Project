package src.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import src.http.service.DoctorService;
import src.http.service.VisitService;

import java.io.IOException;
import java.util.logging.Logger;

public class VisitRestHanlder implements RestHandler {
    private static final Logger LOGGER = Logger.getLogger(VisitRestHanlder.class.getName());

    private final VisitService visitService;

    public VisitRestHanlder(VisitService visitService) {
        this.visitService = visitService;
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
