package src.http;

import com.sun.net.httpserver.HttpServer;
import src.http.web.WebServerFactory;

import java.util.logging.Logger;

public class ApplicationServer {
    private static final Logger LOGGER = Logger.getLogger(ApplicationServer.class.getName());

    public static void main(String[] args) {
        ApplicationServer applicationServer = new ApplicationServer();
        HttpServer server = applicationServer.createServer();
        server.start();

        LOGGER.info("Medical service server started on " + server.getAddress());
    }

    public HttpServer createServer() {
        return WebServerFactory.createServer();
    }
}
