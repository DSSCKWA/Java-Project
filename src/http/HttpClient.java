package src.http;

import com.google.gson.Gson;
import src.clinic.Clinic;
import src.equipment.Equipment;
import src.users.User;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;

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

    public boolean updateClinic(Clinic clinic) throws IOException, InterruptedException {
        Gson g = new Gson();
        String json = g.toJson(clinic);
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/clinics/" + clinic.getClinicId()))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .PUT(java.net.http.HttpRequest.BodyPublishers.ofString(json))
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == 200;
    }

    public Clinic getClinic(int id) throws IOException, InterruptedException {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/clinics/" + id))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());

        Gson g = new Gson();
        String res = response.body();
        Clinic clinic = g.fromJson(res, Clinic.class);
        return clinic;
    }

    public boolean addClinic(Clinic clinic) throws IOException, InterruptedException {
        Gson g = new Gson();
        String json = g.toJson(clinic);
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/clinics"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .POST(java.net.http.HttpRequest.BodyPublishers.ofString(json))
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == 201;
    }

    public boolean deleteClinic(int id) throws IOException, InterruptedException {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/clinics/" + id))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == 200;
    }

    public boolean deleteUser(int id) throws IOException, InterruptedException {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/users/" + id))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == 200;
    }

    public boolean updateUser(User user) throws IOException, InterruptedException {
        Gson g = new Gson();
        String json = g.toJson(user);
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/users/" + user.getId()))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .PUT(java.net.http.HttpRequest.BodyPublishers.ofString(json))
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == 200;
    }

    public boolean addUser(User user) throws IOException, InterruptedException {
        Gson g = new Gson();
        String json = g.toJson(user);
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/users"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .POST(java.net.http.HttpRequest.BodyPublishers.ofString(json))
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == 201;
    }

    public User getUserById(int id) throws IOException, InterruptedException {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/users/" + id))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        return getUser(request);
    }

    public User getUserByEmail(String email) throws IOException, InterruptedException {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/users?email=" + URLEncoder.encode(email, StandardCharsets.UTF_8)))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        return getUser(request);
    }

    private User getUser(HttpRequest request) throws IOException, InterruptedException {
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());

        Gson g = new Gson();
        String res = response.body();
        System.out.println(res);
        User user = g.fromJson(res, User.class);
        return user;
    }

    public ArrayList<Equipment> getEquipmentByClinicId(int id) throws IOException, InterruptedException {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/equipment?clinicId=" + id))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());

        Gson g = new Gson();
        String res = response.body();
        System.out.println(res);
        Equipment[] equipment = g.fromJson(res, Equipment[].class);
        return new ArrayList<>(Arrays.asList(equipment));
    }

    public Equipment getEquipmentById(int id) throws IOException, InterruptedException {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/equipment/" + id))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());

        Gson g = new Gson();
        String res = response.body();
        System.out.println(res);
        Equipment equipment = g.fromJson(res, Equipment.class);
        return equipment;
    }

    public boolean addEquipment(Equipment equipment) throws IOException, InterruptedException {
        Gson g = new Gson();
        String json = g.toJson(equipment);
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/equipment"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .POST(java.net.http.HttpRequest.BodyPublishers.ofString(json))
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == 201;
    }

    public boolean updateEquipment(Equipment equipment) throws IOException, InterruptedException {
        Gson g = new Gson();
        String json = g.toJson(equipment);
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/equipment/" + equipment.getEquipmentId()))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .PUT(java.net.http.HttpRequest.BodyPublishers.ofString(json))
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == 200;
    }

    public boolean deleteEquipment(int id) throws IOException, InterruptedException {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/equipment/" + id))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == 200;
    }

}
