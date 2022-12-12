package src.users;

import src.db.client.DBClient;
import src.db.repository.VisitRepository;
import src.db.tables.VisitsTable;
import src.visit.Status;
import src.visit.Visit;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Patient extends User {

    public Patient(String firstName, String lastName, String email, String password, String address, String city, int phoneNumber, Permissions permissions) {
        super(firstName, lastName, email, password, address, city, phoneNumber, permissions);
    }

    public void registerNewVisit(LocalDate visitDate, LocalTime visitTime, int doctorId) {
        Visit visit;

        visit = new Visit();
        visit.setDate(visitDate);
        visit.setTime(visitTime);
        visit.setPatient(this);
        //visit.setDoctor(new Doctor().readDoctorFromDBById(doctorId));
        visit.setPatient(this);
        visit.setRating(-1); //initialize rating, -1 means no rating
        visit.setStatus(Status.PENDING);

        VisitsTable vTable = new VisitsTable(
                visit.getStatus(),
                visit.getDate(),
                visit.getTime(),
                visit.getDuration(),
                visit.getPatient().getId(),
                visit.getDoctor().getId(),
                visit.getRating()
        );

        VisitRepository db;
        try {
            db = new VisitRepository(new DBClient(false));
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        db.insertVisit(vTable);
    }

    public void cancelVisit(Visit visit){
        visit.setStatus(Status.CANCELED);

        VisitsTable vTable = new VisitsTable(
                visit.getStatus(),
                visit.getDate(),
                visit.getTime(),
                visit.getDuration(),
                visit.getPatient().getId(),
                visit.getDoctor().getId(),
                visit.getRating()
        );

        VisitRepository db;
        try {
            db = new VisitRepository(new DBClient(false));
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        db.updateVisit(vTable);

    }

    public void modifyDate(Visit visit, LocalTime time, LocalDate date){

        visit.setTime(time);
        visit.setDate(date);

        VisitsTable vTable = new VisitsTable(
                visit.getStatus(),
                visit.getDate(),
                visit.getTime(),
                visit.getDuration(),
                visit.getPatient().getId(),
                visit.getDoctor().getId(),
                visit.getRating()
        );

        VisitRepository db;
        try {
            db = new VisitRepository(new DBClient(false));
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        db.updateVisit(vTable);

    }

    public void rateVisit(Visit visit, int rating){

        //zakładam sprawdzenie poprawności wartości ratingu po stronie UI
        visit.setRating(rating);

        VisitsTable vTable = new VisitsTable(
                visit.getStatus(),
                visit.getDate(),
                visit.getTime(),
                visit.getDuration(),
                visit.getPatient().getId(),
                visit.getDoctor().getId(),
                visit.getRating()
        );

        VisitRepository db;
        try {
            db = new VisitRepository(new DBClient(false));
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        db.updateVisit(vTable);
    }

}
