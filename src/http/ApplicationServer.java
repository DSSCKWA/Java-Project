package src.http;

import com.sun.net.httpserver.HttpServer;
import src.http.web.WebServerFactory;
import src.mail.PeriodicMail;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ApplicationServer {
    private static final Logger LOGGER = Logger.getLogger(ApplicationServer.class.getName());
    private static final int PERIOD = 5;

    public static void main(String[] args) {
        ApplicationServer applicationServer = new ApplicationServer();
        HttpServer server = applicationServer.createServer();
        server.start();
        LOGGER.info("Medical service server started on " + server.getAddress());

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(new PeriodicMail(PERIOD), 0, PERIOD, TimeUnit.MINUTES);
    }

    public HttpServer createServer() {
        return WebServerFactory.createServer();
    }
}
