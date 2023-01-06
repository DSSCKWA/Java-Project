package src.http;

import src.clinic.Clinic;
import src.equipment.Equipment;
import src.equipment.EquipmentStatus;
import src.schedule.Schedule;
import src.users.Doctor;
import src.users.Permissions;
import src.users.User;
import src.visit.Visit;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

public class TestHttpClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient httpClient = new HttpClient("http://localhost:8080");

//        System.out.println(httpClient.getClinics());
//        System.out.println(httpClient.getUsers());
//        System.out.println(httpClient.getEquipment());
//
//        Clinic clinic = new Clinic(1, "Super Hiper Fajna Klinika", "ul. Testowa 12", "Bydgoszcz");
//        httpClient.addClinic(clinic);
//        httpClient.updateClinic(clinic);
//        System.out.println(httpClient.getClinics());
//        System.out.println(httpClient.getClinic(1));
//
//        Clinic clinic2 = new Clinic("Fajna Klinika", "ul. Testowa 1", "Wawa");
//        System.out.println(httpClient.addClinic(clinic2));
//        System.out.println(httpClient.getClinics());
//
//        System.out.println(httpClient.deleteClinic(5));
//        System.out.println(httpClient.getClinics());

        //System.out.println(httpClient.getUserById(3));
//        System.out.println(httpClient.getUserByEmail("tkwiatek@gmail.com"));
//        System.out.println(httpClient.getUserByEmail("tkwiatek@gmail.c"));
        //System.out.println(user.isPresent());
//
//        Permissions permissions = Permissions.PATIENT;
//        User user = new User(3, "Tomasz", "Kwiatek", "tkwiatek@gmail.com", "pass", "ul. Bajeczna 4", "Default City", 444, permissions);
//        httpClient.addUser(user);
        //        httpClient.updateUser(user);
//        System.out.println(httpClient.getUserById(3));
//        httpClient.deleteUser(3);
//
//        User user1 = new User("Wojciech", "Kwiatek", "tkwiatek@gmail.com", "pass", "ul. Testowa 44", "DC", 444, permissions);
//        httpClient.addUser(user1);
//
//        System.out.println(httpClient.getUsers());
//
//        Equipment equipment = new Equipment(2, "Testowy sprzet", EquipmentStatus.IN_USE, 7);
//        System.out.println(httpClient.getEquipment());
//        System.out.println(httpClient.addEquipment(equipment));
//        System.out.println(httpClient.getEquipmentByClinicId(7));
//        equipment = new Equipment(1, "Testowy sprzet 2", EquipmentStatus.IN_USE, 7);
//        httpClient.updateEquipment(equipment);
//        System.out.println(httpClient.getEquipment());
//        httpClient.deleteEquipment(2);
//        httpClient.deleteEquipment(22);
//        System.out.println(httpClient.getEquipment());
//        System.out.println(httpClient.getEquipmentById(5));

//        System.out.println(httpClient.getVisits());


//        int doctorId = 3;
//        int clinicId = 1;
//        //System.out.println(httpClient.addDoctorToClinic(doctorId, clinicId));
//        System.out.println(httpClient.removeDoctorFromClinic(3, 1));

//        System.out.println(httpClient.getExpertise(1, "abc"));
//        System.out.println(httpClient.getExpertiseByArea("abc"));
//        System.out.println(httpClient.getExpertiseByDoctorId(1));
//
//        System.out.println(httpClient.addExpertise(1, "ddd"));
//        System.out.println(httpClient.removeExpertiseByDoctorIdAndAreaOfExpertise(1, "abc"));
//        System.out.println(httpClient.getExpertiseByDoctorId(1));

//        Doctor doctor = new Doctor("Tomasz", "Kwiatek", "test@t.pl", "pass", "ul. Testowa 44", "DC", 444, Permissions.DOCTOR);
//        httpClient.addUser(doctor);
//        httpClient.addDoctorToClinic(3, 1);
//        httpClient.addDoctorToClinic(3, 2);
//        httpClient.addExpertise(3, "abc");
//        Schedule schedule = new Schedule(3, 2, DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(16, 0));
//        Schedule schedule2 = new Schedule(3, 1, DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(15, 0));
//        Schedule schedule3 = new Schedule(3, 1, DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0));
//        Schedule schedule4 = new Schedule(3, 2, DayOfWeek.THURSDAY, LocalTime.of(8, 0), LocalTime.of(16, 0));
//        Schedule schedule5 = new Schedule(3, 2, DayOfWeek.FRIDAY, LocalTime.of(12, 0), LocalTime.of(18, 0));
//        System.out.println(httpClient.addSchedule(schedule));
//        httpClient.addSchedule(schedule);
//        httpClient.addSchedule(schedule2);
//        httpClient.addSchedule(schedule3);
//        httpClient.addSchedule(schedule4);
//        httpClient.addSchedule(schedule5);

//        System.out.println(httpClient.getDoctorSchedule(3));
//        ArrayList<Schedule> schedules = httpClient.getDoctorSchedule(3);
//        System.out.println(schedules.get(0).getDay());
//        System.out.println(httpClient.getDoctorScheduleInClinic(3, 2));
//        Schedule schedule = httpClient.getDoctorScheduleByDay(3, 2, DayOfWeek.MONDAY);
//        System.out.println(schedule);
//        System.out.println(schedule.getStartTime());

        //httpClient.removeDoctorSchedules(3);
//        httpClient.removeSchedule(3, 2, DayOfWeek.MONDAY);
//
//        Schedule schedule = new Schedule(3, 1, DayOfWeek.FRIDAY, LocalTime.of(8, 0), LocalTime.of(16, 0));
//        Schedule schedule2 = new Schedule(3, 1, DayOfWeek.WEDNESDAY, LocalTime.of(8, 0), LocalTime.of(16, 0));
//        httpClient.updateSchedule(schedule);
//        httpClient.addSchedule(schedule2);
    }
}
