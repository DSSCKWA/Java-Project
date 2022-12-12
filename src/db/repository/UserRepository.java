package src.db.repository;

import src.db.client.DBClient;
import src.db.tables.UsersTable;
import src.users.Permissions;
import src.users.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Locale;

public class UserRepository extends Repository {
    public UserRepository(DBClient client) {
        super(client);
    }

    public User toUser(UsersTable usersTables) {
        return new User(
                usersTables.getUserId(),
                usersTables.getName(),
                usersTables.getSurname(),
                usersTables.getEmail(),
                usersTables.getPassword(),
                usersTables.getAddress(),
                usersTables.getCity(),
                usersTables.getPhoneNumber(),
                usersTables.getPermissions()
        );
    }

    public ArrayList<User> toUserList(ArrayList<UsersTable> usersTables) {
        ArrayList<User> users = new ArrayList<>();
        for (UsersTable user : usersTables) {
            users.add(toUser(user));
        }
        return users;
    }

    public ArrayList<UsersTable> getAllUsers() {
        String query = "SELECT * FROM users";
        ArrayList<UsersTable> users = new ArrayList<>();
        try (Statement stmt = client.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                users.add(new UsersTable(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("address"),
                        rs.getString("city"),
                        rs.getInt("phone_number"),
                        rs.getString("email"),
                        rs.getString("password"),
                        Permissions.valueOf(rs.getString("permissions").toUpperCase(Locale.ROOT))
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public ArrayList<UsersTable> getUsersByPermissions(Permissions permissions) {
        String query = "SELECT * FROM users WHERE permissions = ?";
        ArrayList<UsersTable> users = new ArrayList<>();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setString(1, permissions.toString().toLowerCase(Locale.ROOT));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(new UsersTable(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("address"),
                        rs.getString("city"),
                        rs.getInt("phone_number"),
                        rs.getString("email"),
                        rs.getString("password"),
                        Permissions.valueOf(rs.getString("permissions").toUpperCase(Locale.ROOT))
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public UsersTable getUserById(int userId) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        UsersTable user = new UsersTable();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setAddress(rs.getString("address"));
                user.setCity(rs.getString("city"));
                user.setPhoneNumber(rs.getInt("phone_number"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                Permissions.valueOf(rs.getString("permissions").toUpperCase(Locale.ROOT));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public UsersTable getUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        UsersTable user = new UsersTable();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setAddress(rs.getString("address"));
                user.setCity(rs.getString("city"));
                user.setPhoneNumber(rs.getInt("phone_number"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                Permissions.valueOf(rs.getString("permissions").toUpperCase(Locale.ROOT));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public int insertUser(UsersTable user) {
        String query = "INSERT INTO users(name,surname,address,city,phone_number,email,password,permissions) VALUES (?,?,?,?,?,?,?,?)";
        int userId = 0;
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getSurname());
            stmt.setString(3, user.getAddress());
            stmt.setString(4, user.getCity());
            stmt.setInt(5, user.getPhoneNumber());
            stmt.setString(6, user.getEmail());
            stmt.setString(7, user.getPassword());
            stmt.setString(8, user.getPermissions().toString().toLowerCase(Locale.ROOT));
            int rowAffected = stmt.executeUpdate();
            if (rowAffected == 1) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    userId = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userId;
    }

    public void updateUser(UsersTable user) {
        String query = "UPDATE users SET name = ?, surname = ?, address = ?, city = ?, phone_number = ?, email = ?, password = ?, permissions = ? WHERE user_id = ?";
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getSurname());
            stmt.setString(3, user.getAddress());
            stmt.setString(4, user.getCity());
            stmt.setInt(5, user.getPhoneNumber());
            stmt.setString(6, user.getEmail());
            stmt.setString(7, user.getPassword());
            stmt.setString(8, user.getPermissions().toString().toLowerCase(Locale.ROOT));
            stmt.setInt(9, user.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUserById(int userId) {
        String query = "DELETE FROM users where user_id = ?";
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
