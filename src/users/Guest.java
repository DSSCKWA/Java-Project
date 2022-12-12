package src.users;

public class Guest extends User{
    public Guest(String firstName, String lastName, String email, String password, String address, String city, int phoneNumber, Permissions permissions) {
        super(firstName, lastName, email, password, address, city, phoneNumber, permissions);
    }

    public Guest(int id, String firstName, String lastName, String email, String password, String address, String city, int phoneNumber, Permissions permissions) {
        super(id, firstName, lastName, email, password, address, city, phoneNumber, permissions);
    }
}
