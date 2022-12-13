package src.db.client;

import src.db.repository.ClinicRepository;
import src.db.repository.DoctorsRepository;
import src.db.repository.ExpertiseRepository;
import src.db.repository.UserRepository;
import src.db.tables.ClinicsTable;
import src.db.tables.DoctorsTable;
import src.db.tables.ExpertiseTable;
import src.db.tables.UsersTable;
import src.users.Permissions;

import java.sql.SQLException;

public class DBClient_Test {
    public static void main(String[] args) {
        int clinicId = 0;
        ClinicsTable clinic = new ClinicsTable("street name", "city name");
        try {
            DBClient dbClientAutoCommit = new DBClient(true);
            ClinicRepository clinicRepository = new ClinicRepository(dbClientAutoCommit);
            clinicId = clinicRepository.insertClinic(clinic);
            clinic.setClinicId(clinicId);
            System.out.println(clinicRepository.getAllClinics());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            DBClient dbClientNoAutoCommit = new DBClient(false);
            UserRepository userRepository = new UserRepository(dbClientNoAutoCommit);
            ClinicRepository clinicRepository = new ClinicRepository(dbClientNoAutoCommit);
            DoctorsRepository doctorsRepository = new DoctorsRepository(dbClientNoAutoCommit);
            ExpertiseRepository expertiseRepository = new ExpertiseRepository(dbClientNoAutoCommit);
            clinic.setAddress("updated address");
            clinicRepository.updateClinic(clinic);
            dbClientNoAutoCommit.getConnection().commit();
            UsersTable user = new UsersTable("name", "surname", "address", "city", 1234567, "mail", "password", Permissions.DOCTOR);
            int userId = userRepository.insertUser(user);
            user.setUserId(userId);
            doctorsRepository.insertDoctor(new DoctorsTable(userId, clinicId));
            expertiseRepository.insertExpertise(new ExpertiseTable(userId, "nothing"));
            dbClientNoAutoCommit.getConnection().commit();
            System.out.println(doctorsRepository.getDoctorById(userId));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
