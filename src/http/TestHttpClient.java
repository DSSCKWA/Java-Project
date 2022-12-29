package src.http;

import src.clinic.Clinic;
import src.users.Permissions;
import src.users.User;

import java.io.IOException;

public class TestHttpClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient httpClient = new HttpClient("http://localhost:8080");

//        System.out.println(httpClient.getClinics());
//        System.out.println(httpClient.getUsers());
//        System.out.println(httpClient.getEquipment());
//
//        Clinic clinic = new Clinic(1, "Super Hiper Fajna Klinika", "ul. Testowa 12", "Bydgoszcz");
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

        System.out.println(httpClient.getUserById(2));
        System.out.println(httpClient.getUserByEmail("tkwiatek@gmail.com"));
        System.out.println(httpClient.getUserByEmail("tkwiatek@gmail.co")); // null

        Permissions permissions = Permissions.PATIENT;
        User user = new User(2, "Tomasz", "Kwiatek", "tkwiatek@gmail.com", "pass", "ul. Bajeczna 4", "Default City", 444, permissions);
        httpClient.updateUser(user);
        System.out.println(httpClient.getUserById(2));
        httpClient.deleteUser(2);

        User user1 = new User("Wojciech", "Kwiatek", "tkwiatek@gmail.com", "pass", "ul. Testowa 44", "DC", 444, permissions);
        httpClient.addUser(user1);

        System.out.println(httpClient.getUsers());
    }
}
