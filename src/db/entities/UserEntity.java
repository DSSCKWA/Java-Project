package src.db.entities;

import src.db.client.DBClient;
import src.db.repository.UserRepository;
import src.users.Permissions;

import java.sql.SQLException;
import java.util.Objects;

public class UserEntity {
    private int userId;
    private String name;
    private String surname;
    private String address;
    private String city;
    private int phoneNumber;
    private String email;
    private String password;
    private Permissions permissions;

    public UserEntity(int userId, String name, String surname, String address, String city, int phoneNumber, String email, String password, Permissions permissions) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.permissions = permissions;
    }

    public UserEntity(String name, String surname, String address, String city, int phoneNumber, String email, String password, Permissions permissions) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.permissions = permissions;
    }

    public UserEntity getUserEntityByUserId(int userId) {
        UserRepository db;
        this.userId = userId;
        try {
            db = new UserRepository(new DBClient(false));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return db.getUserById(userId);
    }

    public UserEntity() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Permissions getPermissions() {
        return permissions;
    }

    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UsersEntity{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", permissions=" + permissions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return userId == that.userId && phoneNumber == that.phoneNumber && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(address, that.address) && Objects.equals(city, that.city) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && permissions == that.permissions;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, surname, address, city, phoneNumber, email, password, permissions);
    }
}
