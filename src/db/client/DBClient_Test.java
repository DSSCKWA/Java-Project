package src.db.client;

import src.db.entities.ClinicEntity;
import src.db.entities.DoctorEntity;
import src.db.entities.ExpertiseEntity;
import src.db.entities.UserEntity;
import src.db.repository.ClinicRepository;
import src.db.repository.DoctorRepository;
import src.db.repository.ExpertiseRepository;
import src.db.repository.UserRepository;
import src.users.Permissions;

import java.sql.SQLException;

public class DBClient_Test {
    public static void main(String[] args) {
        int clinicId = 0;
        ClinicEntity clinic = new ClinicEntity("name", "street name", "city name");
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
            DoctorRepository doctorRepository = new DoctorRepository(dbClientNoAutoCommit);
            ExpertiseRepository expertiseRepository = new ExpertiseRepository(dbClientNoAutoCommit);
            clinic.setAddress("updated address");
            clinicRepository.updateClinic(clinic);
            dbClientNoAutoCommit.getConnection().commit();
            UserEntity user = new UserEntity("name", "surname", "address", "city", 1234567, "mail", "password", Permissions.DOCTOR);
            int userId = userRepository.insertUser(user);
            user.setUserId(userId);
            doctorRepository.insertDoctor(new DoctorEntity(userId, clinicId));
            expertiseRepository.insertExpertise(new ExpertiseEntity(userId, "nothing"));
            dbClientNoAutoCommit.getConnection().commit();
            System.out.println(doctorRepository.getDoctorById(userId));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
