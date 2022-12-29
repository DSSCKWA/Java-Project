package src.http;

import com.google.gson.Gson;
import src.clinic.Clinic;
import src.equipment.Equipment;
import src.users.User;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

public class HttpClient {
    private final String serverUrl;
    private final java.net.http.HttpClient httpClient;

    public HttpClient(String serverUrl) {
        this.serverUrl = serverUrl;
        this.httpClient = java.net.http.HttpClient.newHttpClient();
    }

    public java.net.http.HttpClient getHttpClient() {
        return httpClient;
    }

    public ArrayList<Clinic> getClinics() throws IOException, InterruptedException {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/clinics"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());

        Gson g = new Gson();
        String res = response.body();
        ArrayList<Clinic> clinics = g.fromJson(res, ArrayList.class);
        return clinics;
    }

    public  ArrayList<User> getUsers() throws IOException, InterruptedException {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/users"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());

        Gson g = new Gson();
        String res = response.body();
        ArrayList<User> users = g.fromJson(res, ArrayList.class);
        return users;
    }

    public ArrayList<Equipment> getEquipment() throws IOException, InterruptedException {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/equipment"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());

        Gson g = new Gson();
        String res = response.body();
        ArrayList<Equipment> equipment = g.fromJson(res, ArrayList.class);
        return equipment;
    }

}
