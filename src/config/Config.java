package src.config;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Config {
    private String email;
    private String password;
    private String sender;

    public static Config getConfig() {
        Gson gson = new Gson();
        String jsonString;
        try {
            jsonString = readFile("src/config/config.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return gson.fromJson(jsonString, Config.class);
    }

    private static String readFile(String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSender() {
        return sender;
    }
}
