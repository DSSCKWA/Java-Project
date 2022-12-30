package src.db.repository;

import src.db.client.DBClient;
import src.db.entities.*;
import src.users.Doctor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DoctorsRepository extends Repository {
    public DoctorsRepository(DBClient client) {
        super(client);
    }

    public Doctor toDoctor(int doctorId) {
        UserRepository userRepository = new UserRepository(client);
        DoctorsRepository doctorsRepository = new DoctorsRepository(client);
        ClinicRepository clinicRepository = new ClinicRepository(client);
        ExpertiseRepository expertiseRepository = new ExpertiseRepository(client);
        ScheduleRepository scheduleRepository = new ScheduleRepository(client);

        UserEntity userEntity = userRepository.getUserById(doctorId);
        ArrayList<DoctorEntity> doctorEntities = doctorsRepository.getDoctorById(doctorId);
        ArrayList<ClinicEntity> clinicEntities = new ArrayList<>();

        for (DoctorEntity doctorEntity : doctorEntities) {
            clinicEntities.add(clinicRepository.getClinicById(doctorEntity.getClinicId()));
        }
        ArrayList<ScheduleEntity> scheduleEntities = scheduleRepository.getSchedulesByDoctorId(doctorId);
        ArrayList<ExpertiseEntity> expertiseEntities = expertiseRepository.getExpertiseByDoctorId(doctorId);

        return new Doctor(
                userEntity.getUserId(),
                userEntity.getName(),
                userEntity.getSurname(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getAddress(),
                userEntity.getCity(),
                userEntity.getPhoneNumber(),
                clinicRepository.toClinicList(clinicEntities),
                scheduleRepository.toScheduleList(scheduleEntities),
                expertiseRepository.toExpertiseList(expertiseEntities)
        );
    }

    public ArrayList<DoctorEntity> getAllDoctors() {
        String query = "SELECT * FROM doctors";
        ArrayList<DoctorEntity> doctor = new ArrayList<>();
        try (Statement stmt = client.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                doctor.add(new DoctorEntity(
                        rs.getInt("doctor_id"),
                        rs.getInt("clinic_id")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return doctor;
    }

    public ArrayList<DoctorEntity> getDoctorById(int doctorId) {
        String query = "SELECT * FROM doctors WHERE doctor_id = ?";
        ArrayList<DoctorEntity> expertises = new ArrayList<>();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                expertises.add(new DoctorEntity(
                        rs.getInt("doctor_id"),
                        rs.getInt("clinic_id")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return expertises;
    }

    public ArrayList<DoctorEntity> getDoctorByClinic(int clinicId) {
        String query = "SELECT * FROM doctors WHERE clinic_id = ?";
        ArrayList<DoctorEntity> doctors = new ArrayList<>();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, clinicId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                doctors.add(new DoctorEntity(
                        rs.getInt("doctor_id"),
                        rs.getInt("clinic_id")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return doctors;
    }

    public void insertDoctor(DoctorEntity doctor) {
        String query = "INSERT INTO doctors(doctor_id, clinic_id) VALUES (?,?)";
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, doctor.getDoctorId());
            stmt.setInt(2, doctor.getClinicId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteDoctorFromClinics(int doctorId) {
        String query = "DELETE FROM doctors where doctor_id = ?";
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteDoctorFromClinic(int doctorId, int clinicId) {
        String query = "DELETE FROM doctors where doctor_id = ? and clinic_id = ?";
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            stmt.setInt(2, clinicId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
