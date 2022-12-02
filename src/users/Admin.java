package src.users;

<<<<<<< Updated upstream
public class Admin {
=======
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class Admin extends User{

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private String city;
    private int phoneNumber;
    private Permissions permissions;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Permissions getPermissions() {
        return this.permissions;
    }

    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }

    public void changePermissions(Object o, Permissions permissions) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method m;
        try {
            m = o.getClass().getMethod("setPermissions", (Class[])null);
            m.invoke(o, permissions);
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Admin)) {
            return false;
        }
        Admin admin = (Admin) o;
        return id == admin.id &&
                firstName.equals(admin.firstName) &&
                lastName.equals(admin.lastName) &&
                email.equals(admin.email) &&
                password.equals(admin.password) &&
                address.equals(admin.address) &&
                city.equals(admin.city) &&
                permissions.equals(admin.permissions) &&
                phoneNumber == admin.phoneNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, address, city, phoneNumber, permissions);
    }

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

>>>>>>> Stashed changes
}
