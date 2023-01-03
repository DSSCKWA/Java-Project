package src.http;

import src.users.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        HttpClient client = new HttpClient("http://localhost:8080");
        User user = client.getUserById(1);
        for (int i = 1; i <= 25; i++) {
            threadPool.execute(new HttpTest(client, user));
        }

        threadPool.shutdown();
    }

    private static class HttpTest implements Runnable {
        private final HttpClient client;
        private final User user;

        public HttpTest(HttpClient client, User user) {
            this.user = user;
            this.client = client;
        }

        @Override
        public void run() {
            try {
                User u = new User(
                        user.getId(),
                        user.getName(),
                        user.getSurname(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getAddress(),
                        user.getCity(),
                        user.getPhoneNumber(),
                        user.getPermissions()
                );
                u.setPhoneNumber(user.getPhoneNumber() + 1);
                client.updateUser(u);
                u.setPhoneNumber(user.getPhoneNumber() - 1);
                client.updateUser(u);
                u.setPhoneNumber(user.getPhoneNumber());
                client.updateUser(u);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
