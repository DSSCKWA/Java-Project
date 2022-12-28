package src.mail;

import src.users.Doctor;
import src.users.Patient;
import src.users.Permissions;
import src.visit.Status;
import src.visit.Visit;

import java.time.LocalDate;
import java.time.LocalTime;

public class TestMail {
    public static void main(String[] args) {
        Mail mail = new Mail("dssckwabot@gmail.com", "Delta Szwadron Super Cool Komando Wilków Alfa Bot", "");
//        mail.sendEmail("dawidgorski0000@gmail.com", "Test subject", "Test email message");
        LocalDate date = LocalDate.of(2022, 12, 14);
        LocalTime time = LocalTime.of(14, 00);
        Doctor doctor = new Doctor("Tadeusz", "Kwiatkowski", "tadek@gmail.com", "haslo", "ul. Parkowa 4", "Wieliczka", 111333555);
        Patient patient = new Patient("Dawid", "Gorski", "dawidgorski0000@gmail.com", "123", "ul. Kwiatowa 1", "Warszawa", 123456789, Permissions.PATIENT);
        Visit visit = new Visit(date, time, 20, doctor, patient, 0, Status.PENDING);
        // mail.visitReminder(visit);
       mail.infoAboutPermissionChange(patient);
    }
}
