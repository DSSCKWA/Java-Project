package src.http.service;

import src.db.client.DBClient;
import src.db.repository.UserRepository;
import src.db.tables.UsersTable;
import src.http.constants.HttpStatus;
import src.http.util.HttpException;
import src.users.Permissions;
import src.users.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

public class UserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());
    private static final DBClient dbClient;

    static {
        try {
            dbClient = new DBClient(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final UserRepository userRepository = new UserRepository(dbClient);

    public ArrayList<User> getAllUsers() {
        return userRepository.toUserList(userRepository.getAllUsers());
    }

    public User getUserById(int userId) {
        UsersTable user = userRepository.getUserById(userId);
        if (user.equals(new UsersTable())) {
            return null;
        }
        return userRepository.toUser(user);
    }

    public User getUserByEmail(String email) {
        UsersTable user = userRepository.getUserByEmail(email);
        if (user.equals(new UsersTable())) {
            return null;
        }
        return userRepository.toUser(user);
    }

    public User addUser(Map<String, String> userData) {
        UsersTable user = toUsersTable(userData);
        int userId = userRepository.insertUser(user);
        user.setUserId(userId);
        return userRepository.toUser(user);
    }

    public User updateUser(int userId, Map<String, String> userData) {
        //TODO validate data
        UsersTable usersTable = toUsersTable(userData);
        usersTable.setUserId(userId);
        userRepository.updateUser(usersTable);
        return userRepository.toUser(usersTable);
    }

    public void deleteUser(int userId) {
        userRepository.deleteUserById(userId);
    }

    private UsersTable toUsersTable(Map<String, String> userData) {
        try {
            UsersTable user = new UsersTable(
                    userData.get("name"),
                    userData.get("surname"),
                    userData.get("address"),
                    userData.get("city"),
                    Integer.parseInt(userData.get("phoneNumber")),
                    userData.get("email"),
                    userData.get("password"),
                    Permissions.valueOf(userData.get("permissions").toUpperCase(Locale.ROOT)));
            if (userData.get("userId") != null) {
                user.setUserId(Integer.parseInt(userData.get("userId")));
            }
            return user;
        } catch (NumberFormatException e) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "Invalid payload");
        }
    }
}
