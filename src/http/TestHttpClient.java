package src.http;

import com.google.gson.Gson;

import java.io.IOException;

public class TestHttpClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient httpClient = new HttpClient("http://localhost:8080");

        System.out.println(httpClient.getClinics());
        System.out.println(httpClient.getUsers());
        System.out.println(httpClient.getEquipment());
    }
}
