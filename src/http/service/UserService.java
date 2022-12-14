package src.http.service;

import src.db.client.DBClient;
import src.db.entities.UserEntity;
import src.db.repository.UserRepository;
import src.http.constants.HttpStatus;
import src.http.util.HttpException;
import src.users.Permissions;
import src.users.User;
import src.validator.Validator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

public class UserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());
    private static final DBClient dbClient;

    static {
        dbClient = new DBClient(true);
    }

    private static final UserRepository userRepository = new UserRepository(dbClient);

    public ArrayList<User> getAllUsers() {
        return userRepository.toUserList(userRepository.getAllUsers());
    }

    public ArrayList<User> getUsersByPermission(Permissions permissions) {
        return userRepository.toUserList(userRepository.getUsersByPermissions(permissions));
    }

    public User getUser(int userId) {
        UserEntity user = userRepository.getUser(userId);
        if (user.equals(new UserEntity())) {
            return null;
        }
        return userRepository.toUser(user);
    }

    public User getUser(String email) {
        UserEntity user = userRepository.getUser(email);
        if (user.equals(new UserEntity())) {
            return null;
        }
        return userRepository.toUser(user);
    }

    public User addUser(Map<String, String> userData) {
        UserEntity user = toUserEntity(userData);
        int userId = userRepository.insertUser(user);
        user.setUserId(userId);
        return userRepository.toUser(user);
    }

    public User updateUser(int userId, Map<String, String> userData) {
        //TODO validate data
        UserEntity userEntity = toUserEntity(userData);
        userEntity.setUserId(userId);
        userRepository.updateUser(userEntity);
        return userRepository.toUser(userEntity);
    }

    public void deleteUser(int userId) {
        userRepository.deleteUserById(userId);
    }

    private UserEntity toUserEntity(Map<String, String> userData) {
        try {
            if (!Validator.isValidMail(userData.get("email")) || !Validator.isValidPhone(userData.get("phoneNumber"))) {
                throw new HttpException(HttpStatus.BAD_REQUEST, "Data did not pass validation");
            }
            UserEntity user = new UserEntity(
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
