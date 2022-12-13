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
}
