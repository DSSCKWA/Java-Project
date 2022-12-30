package src.users;

import src.clinic.Clinic;
import src.db.client.DBClient;
import src.db.entities.ClinicEntity;
import src.db.entities.DoctorEntity;
import src.db.entities.ExpertiseEntity;
import src.db.repository.ClinicRepository;
import src.db.repository.DoctorsRepository;
import src.db.repository.ExpertiseRepository;
import src.db.repository.UserRepository;
import src.expertise.Expertise;

import java.sql.SQLException;
import java.util.ArrayList;

public class ModeratorTest {
    public static void main(String[] args) throws SQLException {
        Moderator moderator = new Moderator("Jan", "Kowalski", "jkowalsky@gmail.com", "1234", "ul. Akacjowa 4", "Warszawa", 123456789);
        Clinic clinic = new Clinic("Klinika 123", "ul. Medyczna 13", "Kraków");
        moderator.addClinic(clinic);
        DBClient dbClient = new DBClient(true);
        ClinicRepository clinicRepository = new ClinicRepository(dbClient);
        ArrayList<ClinicEntity> clinics = clinicRepository.getAllClinics();
        System.out.println(clinics);

        moderator.changeClinicName(clinic, "Klinika nowa");
        moderator.changeClinicAddress(clinic, "ul. Nowa 21", "Olkusz");
        Clinic clinic2 = new Clinic("Klinika nr 2", "ul. Wojskowa 3", "Warszawa");
        moderator.addClinic(clinic2);
        clinics = clinicRepository.getAllClinics();
        System.out.println(clinics);

        Doctor doctor = new Doctor("Tadeusz", "Kwiatkowski", "tadek@gmail.com", "haslo", "ul. Parkowa 4", "Wieliczka", 111333555);
        moderator.addDoctorToClinic(doctor, clinic);

        DoctorsRepository doctorsRepository = new DoctorsRepository(dbClient);
        ArrayList<DoctorEntity> doctors = doctorsRepository.getAllDoctors();
        System.out.println(doctors);

        Expertise expertise = new Expertise(doctor.getId(), "pediatra");
        moderator.addDoctorExpertise(doctor, expertise);
        Expertise expertise2 = new Expertise(doctor.getId(), "stomatolog");
        moderator.addDoctorExpertise(doctor, expertise2);

        ExpertiseRepository expertiseRepository = new ExpertiseRepository(dbClient);
        ArrayList<ExpertiseEntity> expertises = expertiseRepository.getAllExpertises();
        System.out.println(expertises);

        moderator.removeDoctorExpertise(doctor, expertise);
        expertises = expertiseRepository.getAllExpertises();
        System.out.println("Expertises after deleting one of them:");
        System.out.println(expertises);
        moderator.removeDoctorExpertise(doctor, expertise2);

        moderator.delDoctorFromClinic(doctor, clinic);
        doctorsRepository = new DoctorsRepository(dbClient);
        doctors = doctorsRepository.getAllDoctors();
        System.out.println(doctors);

        moderator.deleteDoctorFromAllClinics(doctor);
        moderator.delClinic(clinic);
        moderator.delClinic(clinic2);
        clinics = clinicRepository.getAllClinics();
        System.out.println(clinics);

        UserRepository userRepository = new UserRepository(dbClient);
        userRepository.deleteUserById(doctor.getId());

    }
}
