package src.users;

import src.db.client.DBClient;
import src.db.entities.VisitEntity;
import src.db.repository.VisitRepository;
import src.visit.Visit;
import src.visit.VisitStatus;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Patient extends User {
    public Patient(User user) {
        super(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPassword(),
                user.getAddress(),
                user.getCity(),
                user.getPhoneNumber(),
                Permissions.PATIENT
        );
    }

    public Patient(String firstName, String lastName, String email, String password, String address, String city, int phoneNumber, Permissions permissions) {
        super(firstName, lastName, email, password, address, city, phoneNumber, permissions);
    }

    public void registerNewVisit(LocalDate visitDate, LocalTime visitTime, Doctor doctor) {
        Visit visit;

        visit = new Visit(visitDate, visitTime, 20, doctor, this, -1, VisitStatus.PENDING);

        VisitEntity vTable = new VisitEntity(
                visit.getStatus(),
                visit.getDate(),
                visit.getTime(),
                visit.getDuration(),
                visit.getPatient().getId(),
                visit.getDoctor().getId(),
                visit.getRating()
        );

        VisitRepository db;
        db = new VisitRepository(new DBClient(true));

        db.insertVisit(vTable);
    }

    public void cancelVisit(Visit visit) {
        visit.setStatus(VisitStatus.CANCELED);

        VisitEntity vTable = new VisitEntity(
                visit.getStatus(),
                visit.getDate(),
                visit.getTime(),
                visit.getDuration(),
                visit.getPatient().getId(),
                visit.getDoctor().getId(),
                visit.getRating()
        );

        VisitRepository db;
        db = new VisitRepository(new DBClient(true));

        db.updateVisit(vTable);

    }

    public void modifyDate(Visit visit, LocalTime time, LocalDate date) {

        visit.setTime(time);
        visit.setDate(date);

        VisitEntity vTable = new VisitEntity(
                visit.getStatus(),
                visit.getDate(),
                visit.getTime(),
                visit.getDuration(),
                visit.getPatient().getId(),
                visit.getDoctor().getId(),
                visit.getRating()
        );

        VisitRepository db;
        db = new VisitRepository(new DBClient(true));

        db.updateVisit(vTable);

    }

    public void rateVisit(Visit visit, int rating) {

        //zakładam sprawdzenie poprawności wartości ratingu po stronie UI
        visit.setRating(rating);

        VisitEntity vTable = new VisitEntity(
                visit.getStatus(),
                visit.getDate(),
                visit.getTime(),
                visit.getDuration(),
                visit.getPatient().getId(),
                visit.getDoctor().getId(),
                visit.getRating()
        );

        VisitRepository db;
        db = new VisitRepository(new DBClient(true));

        db.updateVisit(vTable);
    }

}
