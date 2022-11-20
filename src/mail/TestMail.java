package src.mail;

public class TestMail {
    public static void main(String[] args) {
        Mail mail = new Mail("dssckwabot@gmail.com", "Delta Szwadron Super Cool Komando Wilków Alfa Bot", "");
        mail.sendEmail("dawidgorski0000@gmail.com", "Test subject", "Test email message");
    }
}
