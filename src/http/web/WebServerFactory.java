package src.http.web;

import com.sun.net.httpserver.HttpServer;
import src.http.handlers.ClinicRestHanlder;
import src.http.handlers.EquipmentRestHandler;
import src.http.handlers.GlobalHttpHandler;
import src.http.handlers.UserRestHandler;
import src.http.service.ClinicService;
import src.http.service.EquipmentService;
import src.http.service.UserService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WebServerFactory {

    public static HttpServer createServer() {
        try {
            final HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
            Executor executor = Executors.newFixedThreadPool(25);
            server.setExecutor(executor);

            UserService userService = new UserService();
            ClinicService clinicService = new ClinicService();
            EquipmentService equipmentService = new EquipmentService();

            server.createContext("/users", new GlobalHttpHandler(new UserRestHandler(userService)));
            server.createContext("/clinics", new GlobalHttpHandler(new ClinicRestHanlder(clinicService)));
            server.createContext("/equipment", new GlobalHttpHandler(new EquipmentRestHandler(equipmentService, clinicService)));

            return server;
        } catch (IOException e) {
            throw new RuntimeException("Unable to start server", e);
        }
    }
}
