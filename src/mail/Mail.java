package src.mail;

import src.clinic.Clinic;
import src.users.Doctor;
import src.users.Patient;
import src.users.User;
import src.visit.Visit;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class Mail {
    private final String senderEmail;
    private final String senderName;
    private final Session session;

    public Mail(String sender, String senderName, String password) {
        this.senderEmail = sender;
        this.senderName = senderName;

        Properties props = new Properties();
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, password);
            }
        };
        this.session = Session.getInstance(props, auth);
    }

    public boolean sendEmail(String recipient, String subject, String body) {
        try {
            MimeMessage msg = new MimeMessage(this.session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(this.senderEmail, this.senderName));

            msg.setSubject(subject, "UTF-8");
            msg.setText(body, "UTF-8");
            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, false));

            Transport.send(msg);

            System.out.println("Email Sent Successfully!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void visitReminder(Visit visit) {
        String subject = "Upcoming medical appointment on " + visit.getDate();
        String message = "Hello " + visit.getPatient().getFirstName() + " " + visit.getPatient().getLastName() + "! \n\nWe would like to remind you about the upcoming visit to " + visit.getDoctor().getFirstName() + " " + visit.getDoctor().getLastName() + " doctor. The visit will take place on " + visit.getDate() + " at " + visit.getTime() + ".\nRemember to change the date of your visit if the current one does not suit you.";
        this.sendEmail(visit.getPatient().getEmail(), subject, message);
    }

    public void infoAboutPermissionChange(User user) {
        String subject = "Permission changed for user " + user.getFirstName() + " " + user.getLastName();
        String message = "Hello " + user.getFirstName() + " " + user.getLastName() + "! \n\nWe would like to notify you about an important change regarding your account. Your user permissions have changed to " + user.getPermissions().toString().toLowerCase() + ".";
        this.sendEmail(user.getEmail(), subject, message);
    }

    // succesful registration for a visit
    // changes made to a visit - delete / date changes
    // remider to rate a visit

}
