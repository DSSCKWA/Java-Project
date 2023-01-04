package src.http.web;

import com.sun.net.httpserver.HttpServer;
import src.http.handlers.*;
import src.http.service.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WebServerFactory {

    public static HttpServer createServer() {
        try {
            final HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8081), 0);
            Executor executor = Executors.newFixedThreadPool(25);
            server.setExecutor(executor);
            UserService userService = new UserService();
            ClinicService clinicService = new ClinicService();
            EquipmentService equipmentService = new EquipmentService();
            VisitService visitService = new VisitService();
            DoctorService doctorService = new DoctorService();
            ExpertiseService expertiseService = new ExpertiseService();
            ScheduleService scheduleService = new ScheduleService();

            server.createContext("/users", new GlobalHttpHandler(new UserRestHandler(userService)));
            server.createContext("/clinics", new GlobalHttpHandler(new ClinicRestHandler(clinicService)));
            server.createContext("/equipment", new GlobalHttpHandler(new EquipmentRestHandler(equipmentService, clinicService)));
            server.createContext("/visits", new GlobalHttpHandler(new VisitRestHandler(visitService, userService)));
            server.createContext("/doctors", new GlobalHttpHandler(new DoctorRestHandler(doctorService, userService, clinicService)));
            server.createContext("/expertise", new GlobalHttpHandler(new ExpertiseRestHandler(expertiseService, userService)));
            server.createContext("/schedule", new GlobalHttpHandler(new ScheduleRestHandler(scheduleService, userService, clinicService)));

            return server;
        } catch (IOException e) {
            throw new RuntimeException("Unable to start server", e);
        }
    }
}
