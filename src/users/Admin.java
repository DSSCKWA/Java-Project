package src.users;

public class Admin extends Moderator {
    
    protected Admin(String name, String surname, String password, int pesel) {
        super(name, surname, password, pesel);
    }
}
