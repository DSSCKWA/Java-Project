package src.http;

import src.clinic.Clinic;

import java.io.IOException;

public class TestHttpClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient httpClient = new HttpClient("http://localhost:8080");

        System.out.println(httpClient.getClinics());
        System.out.println(httpClient.getUsers());
        System.out.println(httpClient.getEquipment());

        Clinic clinic = new Clinic(1, "Super Hiper Fajna Klinika", "ul. Testowa 12", "Bydgoszcz");
        httpClient.updateClinic(clinic);
        System.out.println(httpClient.getClinics());
        System.out.println(httpClient.getClinic(1));

        Clinic clinic2 = new Clinic("Fajna Klinika", "ul. Testowa 1", "Wawa");
        System.out.println(httpClient.addClinic(clinic2));
        System.out.println(httpClient.getClinics());

        System.out.println(httpClient.deleteClinic(5));
        System.out.println(httpClient.getClinics());
    }
}
