package src.ui;

import src.http.HttpClient;
import src.users.User;

public class Session {
    private static Session instance;
    private static HttpClient httpsClient = new HttpClient("http://localhost:8081");
    private static User user;

    // Make the constructor private to prevent direct instantiation
    private Session(User user) {
        Session.user = user;
    }


    // Create a static method that returns the single instance of the class
    public static HttpClient getClient() {
        if (instance == null) {
            // If the instance does not exist, create it
            instance = new Session(null);
        }
        return httpsClient;
    }

    public static User getUser() {
        if (instance == null) {
            // If the instance does not exist, create it
            instance = new Session(null);
        }
        return user;
    }

    public static Session getInstance(User user) {
        if (instance == null) {
            // If the instance does not exist, create it
            instance = new Session(user);
        }
        return instance;
    }

    public static void newUser(User user) {
        Session.user = user;
    }
}
