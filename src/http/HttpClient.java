package src.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import src.clinic.Clinic;
import src.equipment.Equipment;
import src.expertise.Expertise;
import src.gson.GsonConverter;
import src.http.constants.HttpStatus;
import src.http.service.DoctorService;
import src.schedule.Schedule;
import src.users.Doctor;
import src.users.User;
import src.visit.Visit;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpClient {
    private final String serverUrl;
    private final java.net.http.HttpClient httpClient;
    private final Gson g = GsonConverter.newGsonRWConverter();

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

        String res = response.body();
        Type type = new TypeToken<ArrayList<Clinic>>() {
        }.getType();
        ArrayList<Clinic> clinics = g.fromJson(res, type);
        return clinics;
    }

    public ArrayList<User> getUsers() throws IOException, InterruptedException {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/users"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());

        String res = response.body();
        Type type = new TypeToken<ArrayList<User>>() {
        }.getType();
        ArrayList<User> users = g.fromJson(res, type);
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

        String res = response.body();
        Type type = new TypeToken<ArrayList<Equipment>>() {
        }.getType();
        ArrayList<Equipment> equipment = g.fromJson(res, type);
        return equipment;
    }

    public boolean updateClinic(Clinic clinic) throws IOException, InterruptedException {

        String json = g.toJson(clinic);
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/clinics/" + clinic.getClinicId()))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .PUT(java.net.http.HttpRequest.BodyPublishers.ofString(json))
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == HttpStatus.OK.getStatus();
    }

    public Clinic getClinic(int id) throws IOException, InterruptedException {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/clinics/" + id))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());

        String res = response.body();
        Clinic clinic = g.fromJson(res, Clinic.class);
        return clinic;
    }

    public boolean addClinic(Clinic clinic) throws IOException, InterruptedException {

        String json = g.toJson(clinic);
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/clinics"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .POST(java.net.http.HttpRequest.BodyPublishers.ofString(json))
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == HttpStatus.CREATED.getStatus();
    }

    public boolean deleteClinic(int id) throws IOException, InterruptedException {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/clinics/" + id))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == HttpStatus.OK.getStatus();
    }

    public boolean deleteUser(int id) throws IOException, InterruptedException {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/users/" + id))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == HttpStatus.OK.getStatus();
    }

    public boolean updateUser(User user) throws IOException, InterruptedException {

        String json = g.toJson(user);
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/users/" + user.getId()))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .PUT(java.net.http.HttpRequest.BodyPublishers.ofString(json))
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == HttpStatus.OK.getStatus();
    }

    public boolean addUser(User user) throws IOException, InterruptedException {

        String json = g.toJson(user);
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/users"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .POST(java.net.http.HttpRequest.BodyPublishers.ofString(json))
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == HttpStatus.CREATED.getStatus();
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
        HttpResponse<String> response = this.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        String res = response.body();
        if (response.statusCode() == HttpStatus.NOT_FOUND.getStatus()) {
            return null;
        }
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

        String res = response.body();
        System.out.println(res);
        Type type = new TypeToken<ArrayList<Equipment>>() {
        }.getType();
        ArrayList<Equipment> equipment = g.fromJson(res, type);
        return equipment;
    }

    public Equipment getEquipmentById(int id) throws IOException, InterruptedException {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/equipment/" + id))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == HttpStatus.NOT_FOUND.getStatus()) {
            return null;
        }

        String res = response.body();
        System.out.println(res);
        Equipment equipment = g.fromJson(res, Equipment.class);
        return equipment;
    }

    public boolean addEquipment(Equipment equipment) throws IOException, InterruptedException {

        String json = g.toJson(equipment);
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/equipment"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .POST(java.net.http.HttpRequest.BodyPublishers.ofString(json))
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == HttpStatus.CREATED.getStatus();
    }

    public boolean updateEquipment(Equipment equipment) throws IOException, InterruptedException {

        String json = g.toJson(equipment);
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/equipment/" + equipment.getEquipmentId()))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .PUT(java.net.http.HttpRequest.BodyPublishers.ofString(json))
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == HttpStatus.OK.getStatus();
    }

    public boolean deleteEquipment(int id) throws IOException, InterruptedException {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/equipment/" + id))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == HttpStatus.OK.getStatus();
    }

    public ArrayList<Visit> getVisits() throws IOException, InterruptedException {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/visits"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        return getVisits(request);
    }

    public boolean addVisit(Visit visit) throws IOException, InterruptedException {

        HashMap<String, String> newVisit = new HashMap<>();
        newVisit.put("date", visit.getDate().toString());
        newVisit.put("time", visit.getTime().toString());
        newVisit.put("duration", Integer.toString(visit.getDuration()));
        newVisit.put("doctorId", Integer.toString(visit.getDoctor().getId()));
        newVisit.put("clientId", Integer.toString(visit.getPatient().getId()));
        newVisit.put("rating", Integer.toString(visit.getRating()));
        newVisit.put("visitStatus", visit.getVisitStatus().toString());

        String json = g.toJson(newVisit);

        System.out.println(json);

        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/visits"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .POST(java.net.http.HttpRequest.BodyPublishers.ofString(json))
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == HttpStatus.CREATED.getStatus();
    }

    public boolean updateVisit(Visit visit) throws IOException, InterruptedException {

        HashMap<String, String> updatedVisit = new HashMap<>();
        updatedVisit.put("date", visit.getDate().toString());
        updatedVisit.put("time", visit.getTime().toString());
        updatedVisit.put("duration", Integer.toString(visit.getDuration()));
        updatedVisit.put("doctorId", Integer.toString(visit.getDoctor().getId()));
        updatedVisit.put("clientId", Integer.toString(visit.getPatient().getId()));
        updatedVisit.put("rating", Integer.toString(visit.getRating()));
        updatedVisit.put("visitStatus", visit.getVisitStatus().toString());

        String json = g.toJson(updatedVisit);
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/visits/" + visit.getVisitId()))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .PUT(java.net.http.HttpRequest.BodyPublishers.ofString(json))
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == HttpStatus.OK.getStatus();
    }

    public boolean deleteVisit(int id) throws IOException, InterruptedException {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/visits/" + id))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == HttpStatus.OK.getStatus();
    }

    public ArrayList<Visit> getVisitsByDoctorId(int doctorId) throws IOException, InterruptedException {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/visits?doctor=" + doctorId))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        return getVisits(request);
    }

    public ArrayList<Visit> getVisitsByClientId(int clientId) throws IOException, InterruptedException {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/visits?clientId=" + clientId))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        return getVisits(request);
    }

    public ArrayList<Visit> getVisits(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = this.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        String res = response.body();
        System.out.println(res);
        Type type = new TypeToken<ArrayList<Visit>>() {
        }.getType();
        ArrayList<Visit> visits = g.fromJson(res, type);
        return visits;
    }

    public ArrayList<Doctor> getDoctors() throws IOException, InterruptedException {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/doctors"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());

        String res = response.body();

        Type type = new TypeToken<ArrayList<Doctor>>() {
        }.getType();
        ArrayList<Doctor> doctors = g.fromJson(res, type);
        return doctors;
    }

    public Doctor getDoctorById(int id) throws IOException, InterruptedException {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/doctors/" + id))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        return getDoctor(request);
    }

    private Doctor getDoctor(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = this.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        String res = response.body();
        if (response.statusCode() == HttpStatus.NOT_FOUND.getStatus() || response.statusCode() == HttpStatus.BAD_REQUEST.getStatus()) {
            return null;
        }

        System.out.println(res);
        Doctor doctor = g.fromJson(res, Doctor.class);
        return doctor;
    }

    public boolean addDoctorToClinic(int doctorId, int clinicId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/doctors/"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"doctorId\":" + doctorId + ",\"clinicId\":" + clinicId + "}"))
                .build();
        HttpResponse<String> response = this.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == HttpStatus.CREATED.getStatus();
    }

    public boolean removeDoctorFromClinics(int doctorId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/doctors/" + doctorId))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        HttpResponse<String> response = this.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == HttpStatus.OK.getStatus();
    }

    public boolean removeDoctorFromClinic(int doctorId, int clinicId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/doctors/" + doctorId + "?clinicId=" + clinicId))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        HttpResponse<String> response = this.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == HttpStatus.OK.getStatus() || response.statusCode() == HttpStatus.NOT_FOUND.getStatus();
    }

    public ArrayList<Expertise> getExpertiseByArea(String areaOfExpertise) throws IOException, InterruptedException {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/expertises?areaOfExpertise=" + areaOfExpertise))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        String res = response.body();
        Type type = new TypeToken<ArrayList<Expertise>>() {
        }.getType();
        ArrayList<Expertise> expertises = g.fromJson(res, type);
        return expertises;
    }

    public Expertise getExpertise(int doctorId, String areaOfExpertise) throws IOException, InterruptedException {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/expertises?doctorId=" + doctorId + "&areaOfExpertise=" + areaOfExpertise))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        String res = response.body();
        if (response.statusCode() == HttpStatus.NOT_FOUND.getStatus() || response.statusCode() == HttpStatus.BAD_REQUEST.getStatus()) {
            return null;
        }
        Expertise expertise = g.fromJson(res, Expertise.class);
        return expertise;
    }

    public ArrayList<Expertise> getExpertiseByDoctorId(int doctorId) throws IOException, InterruptedException {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/expertises?doctorId=" + doctorId))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        String res = response.body();
        Type type = new TypeToken<ArrayList<Expertise>>() {
        }.getType();
        ArrayList<Expertise> expertises = g.fromJson(res, type);
        return expertises;
    }

    public boolean addExpertise(int doctorId, String areaOfExpertise) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/expertises/"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"doctorId\":" + doctorId + ",\"areaOfExpertise\":\"" + areaOfExpertise + "\"}"))
                .build();
        HttpResponse<String> response = this.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == HttpStatus.CREATED.getStatus();
    }

    public boolean removeExpertise(int doctorId, String areaOfExpertise) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/expertises/" + doctorId + "?areaOfExpertise=" + areaOfExpertise))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        HttpResponse<String> response = this.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == HttpStatus.OK.getStatus() || response.statusCode() == HttpStatus.NOT_FOUND.getStatus();
    }

    public boolean removeExpertiseByDoctorId(int doctorId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/expertises/?doctorId=" + doctorId))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        HttpResponse<String> response = this.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == HttpStatus.OK.getStatus();
    }

    public boolean removeExpertiseByAreaOfExpertise(String areaOfExpertise) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/expertises/?areaOfExpertise=" + areaOfExpertise))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        HttpResponse<String> response = this.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == HttpStatus.OK.getStatus();
    }

    public boolean removeExpertiseByDoctorIdAndAreaOfExpertise(int doctorId, String areaOfExpertise) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/expertises/?doctorId=" + URLEncoder.encode(String.valueOf(doctorId), StandardCharsets.UTF_8) + "&areaOfExpertise=" + areaOfExpertise))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        HttpResponse<String> response = this.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == HttpStatus.OK.getStatus();
    }

    public ArrayList<Schedule> getDoctorSchedule(int doctorId) throws IOException, InterruptedException {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(serverUrl + "/schedules?doctorId=" + doctorId))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        java.net.http.HttpResponse<String> response = this.getHttpClient().send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        String res = response.body();
        Type type = new TypeToken<ArrayList<Schedule>>() {
        }.getType();
        ArrayList<Schedule> schedules = g.fromJson(res, type);
        return schedules;
    }

    public ArrayList<Schedule> getDoctorScheduleInClinic(int doctorId, int clinicId) {
        ArrayList<Schedule> schedules = new ArrayList<>();
        try {
            java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                    .uri(java.net.URI.create(serverUrl + "/schedules?doctorId=" + doctorId + "&clinicId=" + clinicId))
                    .timeout(Duration.ofMinutes(1))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            schedules = getSchedules(schedules, request);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(DoctorService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return schedules;
    }

    private ArrayList<Schedule> getSchedules(ArrayList<Schedule> schedules, HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = this.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        String res = response.body();
        Type type = new TypeToken<ArrayList<Schedule>>() {
        }.getType();
        schedules = g.fromJson(res, type);
        return schedules;
    }

//    public ArrayList<Schedule> getDoctorScheduleByDay(int doctorId, int clinicId, DayOfWeek day) {
//        ArrayList<Schedule> schedules = new ArrayList<>();
//        try {
//            java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
//                    .uri(java.net.URI.create(serverUrl + "/schedules?doctorId=" + doctorId + "&clinicId=" + clinicId + "&day=" + day))
//                    .timeout(Duration.ofMinutes(1))
//                    .header("Content-Type", "application/json")
//                    .GET()
//                    .build();
//
//            schedules = getSchedules(schedules, request);
//        } catch (IOException | InterruptedException ex) {
//            Logger.getLogger(DoctorService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return schedules;
//    }

    public Schedule getDoctorScheduleByDay(int doctorId, int clinicId, DayOfWeek day) {
        try {
            java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                    .uri(java.net.URI.create(serverUrl + "/schedules?doctorId=" + doctorId + "&clinicId=" + clinicId + "&day=" + day))
                    .timeout(Duration.ofMinutes(1))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = this.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            String res = response.body();
            Type type = new TypeToken<Schedule>() {
            }.getType();
            Schedule schedule = g.fromJson(res, type);
            return schedule;
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(DoctorService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean addSchedule(Schedule schedule) throws IOException, InterruptedException {
        String json = g.toJson(schedule);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/schedules"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = this.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        return response.statusCode() == HttpStatus.CREATED.getStatus();
    }

    public boolean updateSchedule(Schedule schedule) throws IOException, InterruptedException {
        String json = g.toJson(schedule);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/schedule"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .method("PUT", HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = this.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == HttpStatus.OK.getStatus();
    }

    public boolean removeDoctorSchedules(int doctorId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/schedule?doctorId=" + doctorId))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        HttpResponse<String> response = this.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == HttpStatus.OK.getStatus();
    }

    public boolean removeSchedule(int doctorId, int clinicId, DayOfWeek day) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/schedule?doctorId=" + doctorId + "&clinicId=" + clinicId + "&day=" + day))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        HttpResponse<String> response = this.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == HttpStatus.OK.getStatus();
    }

}
