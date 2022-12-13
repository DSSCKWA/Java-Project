package src.users;

public class Admin extends User{
    public Admin(String firstName, String lastName, String email, String password, String address, String city, int phoneNumber) {
        super(firstName, lastName, email, password, address, city, phoneNumber, Permissions.ADMIN);
    }
    public void changePermission(Permissions permission, User user)
    {
        user.setPermissions(permission);
        user.updateDB();
    }
    public Admin(String firstName, String lastName, String email, String password, String address, String city, int phoneNumber, Permissions permissions) {
        super(firstName, lastName, email, password, address, city, phoneNumber, permissions);
    }

    public Admin(int id, String firstName, String lastName, String email, String password, String address, String city, int phoneNumber, Permissions permissions) {
        super(id, firstName, lastName, email, password, address, city, phoneNumber, permissions);
    }
}
