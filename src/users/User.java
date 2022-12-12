package src.users;

import src.db.client.DBClient;
import src.db.repository.ClinicRepository;
import src.db.repository.ScheduleRepository;
import src.db.repository.UserRepository;
import src.db.tables.ScheduleTable;
import src.db.tables.UsersTable;

import java.sql.SQLException;
import java.util.Objects;

public abstract class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private String city;
    private int phoneNumber;
    private Permissions permissions;
    private final DBClient dbClientAutoCommit;

    //<editor-fold desc="Getters">
    public int getId() {
        return id;
    }
    public String getCity() {
        return city;
    }
    public Permissions getPermissions() {
        return permissions;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getAddress() {
        return address;
    }
    public int getPhoneNumber() {
        return phoneNumber;
    }
    //</editor-fold>

    //<editor-fold desc="Setters">
    public void setId(int id) {
        this.id = id;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    //</editor-fold>

    //<editor-fold desc="Constructors">
    public User(String firstName, String lastName, String email, String password, String address, String city, int phoneNumber, Permissions permissions) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.permissions = permissions;
        try {
            dbClientAutoCommit = new DBClient(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User(int id, String firstName, String lastName, String email, String password, String address, String city, int phoneNumber, Permissions permissions) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.permissions = permissions;
        try {
            dbClientAutoCommit = new DBClient(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //</editor-fold>

    //<editor-fold desc="Equals & HashCode">
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                email.equals(user.email) &&
                password.equals(user.password) &&
                address.equals(user.address) &&
                city.equals(user.city) &&
                permissions.equals(user.permissions) &&
                phoneNumber == user.phoneNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, address, city, phoneNumber, permissions);
    }
    //</editor-fold>

    //<editor-fold desc="Database Handling">
    public void insertToDB() {
        UsersTable user = new UsersTable(0,firstName, lastName,address, city, phoneNumber, email, password, permissions);
        UserRepository userRepository = new UserRepository(dbClientAutoCommit);
        this.id = userRepository.insertUser(user);
        user.setUserId(id);
    }

    public void removeFromDB() {
        UserRepository userRepository = new UserRepository(dbClientAutoCommit);
        userRepository.deleteUserById(id);
    }

    public void updateDB() {
        UserRepository userRepository = new UserRepository(dbClientAutoCommit);
        userRepository.updateUser(new UsersTable(id,firstName, lastName,address, city, phoneNumber, email, password, permissions));
    }
    //</editor-fold>

    //<editor-fold desc="ToString">
    @Override
    public String toString() {
        return "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", permission='" + permissions + '\'';
    }
    //</editor-fold>
}

