package src.http.service;

import src.db.client.DBClient;
import src.db.entities.VisitEntity;
import src.db.repository.VisitRepository;
import src.http.constants.HttpStatus;
import src.http.util.HttpException;
import src.validator.Validator;
import src.visit.Visit;
import src.visit.VisitStatus;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

public class VisitService {
    private static final Logger LOGGER = Logger.getLogger(VisitService.class.getName());
    private static final DBClient dbClient;

    static {
        dbClient = new DBClient(true);
    }

    private static final VisitRepository visitRepository = new VisitRepository(dbClient);

    public ArrayList<Visit> getAllVisits() {
        return visitRepository.toVisitList(visitRepository.getAllVisits());
    }

    public Visit getVisitById(int visitId) {
        VisitEntity visit = visitRepository.getVisitByVisitId(visitId);
        if (visit.equals(new VisitEntity())) {
            return null;
        }
        return visitRepository.toVisit(visit);
    }

    public Visit getVisit(int doctorId, int clientId, LocalDate date, LocalTime time) {
        VisitEntity visit = visitRepository.getVisit(doctorId, clientId, date, time);
        if (visit.equals(new VisitEntity())) {
            return null;
        }
        return visitRepository.toVisit(visit);
    }

    public ArrayList<Visit> getVisitsByDoctorId(int doctorId) {
        return visitRepository.toVisitList(visitRepository.getVisitsByDoctorId(doctorId));
    }

    public ArrayList<Visit> getVisitsByClientId(int clientId) {
        return visitRepository.toVisitList(visitRepository.getVisitsByClientId(clientId));
    }

    public Visit addVisit(Map<String, String> visitData) {
        VisitEntity visit = toVisitEntity(visitData);
        int visitId = visitRepository.insertVisit(visit);
        visit.setVisitId(visitId);
        return visitRepository.toVisit(visit);
    }

    public Visit updateVisit(int visitId, Map<String, String> visitData) {
        VisitEntity visitEntity = toVisitEntity(visitData);
        visitEntity.setVisitId(visitId);
        visitRepository.updateVisit(visitEntity);
        return visitRepository.toVisit(visitEntity);
    }

    private VisitEntity toVisitEntity(Map<String, String> visitData) {
        try {
            VisitEntity visit = new VisitEntity(
                    VisitStatus.valueOf(visitData.get("visitStatus").toUpperCase(Locale.ROOT)),
                    LocalDate.parse(visitData.get("date")),
                    LocalTime.parse(visitData.get("time")),
                    Integer.parseInt(visitData.get("duration")),
                    Integer.parseInt(visitData.get("clientId")),
                    Integer.parseInt(visitData.get("doctorId")),
                    Integer.parseInt(visitData.get("rating"))
            );
            if (visitData.get("visitId") != null) {
                visit.setVisitId(Integer.parseInt(visitData.get("visitId")));
            }
            return visit;
        } catch (NumberFormatException | DateTimeParseException e) {
            System.out.println(e.getMessage());
            throw new HttpException(HttpStatus.BAD_REQUEST, "Invalid payload");
        }
    }
}
