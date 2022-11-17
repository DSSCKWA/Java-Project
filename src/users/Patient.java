package src.users;

public class Patient extends User {
    protected String name;
    protected String surname;
    protected String password;
    protected int pesel;

    protected Patient(String name, String surname, String password, int pesel) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.pesel = pesel;
    }

    @Override
    public void logIn() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void changePassword() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void createAccount() {
        // TODO Auto-generated method stub
        
    }
}