package src.users;

import src.clinic.Clinic;
import src.expertise.Expertise;

import java.sql.SQLException;

public class Moderator extends User {

    public Moderator(String firstName, String lastName, String email, String password, String address, String city, int phoneNumber) {
        super(firstName, lastName, email, password, address, city, phoneNumber, Permissions.MODERATOR);
    }

    public void addClinic(Clinic clinic) {
        clinic.insertToDB();
    }

    public void delClinic(Clinic clinic) {
        clinic.removeFromDB();
    }

    public void changeClinicName(Clinic clinic, String name) {
        clinic.changeName(name);
    }

    public void changeClinicAddress(Clinic clinic, String address, String city) {
        clinic.changeAddress(city, address);
    }

    public void addDoctorToClinic(Doctor doctor, Clinic clinic) {
        doctor.addClinic(clinic);
    }

    public void delDoctorFromClinic(Doctor doctor, Clinic clinic) {
        doctor.removeClinic(clinic);
    }

    public void addDoctorExpertise(Doctor doctor, Expertise expertise) throws SQLException {
        doctor.addExpertise(expertise);
    }

    public void removeDoctorExpertise(Doctor doctor, Expertise expertise) throws SQLException {
        doctor.removeExpertise(expertise);
    }

    public void deleteDoctorFromAllClinics(Doctor doctor) {
        doctor.removeClinics();
    }
}