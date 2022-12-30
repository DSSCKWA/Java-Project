package src.tests;

import src.clinic.Clinic;
import src.users.Doctor;

import java.time.DayOfWeek;
import java.time.LocalTime;


public class DoctorScheduleTest {
    public static void main(String[] args) {

        Doctor edek = new Doctor("Edek", "Kaminski", "EdekIsCool69@gmail.com", "edek123", "Warszawska 1", "Kraków", 654123456);
        Clinic klinika1 = new Clinic("Klinika Zdrowia", "Wawelska 1", "Kraków");
        klinika1.insertToDB();
        edek.addClinic(klinika1);
        edek.insertToDB(klinika1);
        edek.addToSchedule(klinika1.getClinicId(), DayOfWeek.MONDAY, LocalTime.of(12, 0), LocalTime.of(16, 0));
    }
}
