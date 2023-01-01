package src.http.service;

import src.db.client.DBClient;
import src.db.entities.DoctorEntity;
import src.db.entities.UserEntity;
import src.db.repository.DoctorRepository;
import src.http.constants.HttpStatus;
import src.http.util.HttpException;
import src.users.Doctor;
import src.users.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;

public class DoctorService {
    private static final Logger LOGGER = Logger.getLogger(DoctorService.class.getName());
    private static final DBClient dbClient;

    static {
        try {
            dbClient = new DBClient(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final DoctorRepository doctorRepository = new DoctorRepository(dbClient);

    public ArrayList<Doctor> getDoctorsById(int doctorId) {
        return doctorRepository.toDoctorList(doctorRepository.getDoctorById(doctorId));
    }

    public Doctor getDoctorInClinic(int doctorId, int clinicId) {
        DoctorEntity doctor = doctorRepository.getDoctorInClinic(doctorId, clinicId);
        if (doctor.equals(new DoctorEntity())) {
            return null;
        }
        return doctorRepository.toDoctor(doctor.getDoctorId());
    }

    public Doctor userToDoctors(User user) {
        return doctorRepository.toDoctor(user.getId());
    }

    public ArrayList<Doctor> usersToDoctors(ArrayList<User> users) {
        ArrayList<Doctor> doctors = new ArrayList<>();
        for (User user : users) {
            doctors.add(doctorRepository.toDoctor(user.getId()));
        }
        return doctors;
    }

    public Doctor addDoctor(Map<String, String> doctorData) {
        DoctorEntity doctor = toDoctorEntity(doctorData);
        doctorRepository.insertDoctor(doctor);
        return doctorRepository.toDoctor(doctor.getDoctorId());
    }

    public void deleteDoctor(int doctorId, int clinicId) {
        doctorRepository.deleteDoctorFromClinic(doctorId, clinicId);
    }

    public void deleteAllDoctorEntries(int doctorId) {
        doctorRepository.deleteDoctorFromClinics(doctorId);
    }

    private DoctorEntity toDoctorEntity(Map<String, String> doctorData) {
        try {
            return new DoctorEntity(
                    Integer.parseInt(doctorData.get("doctorId")),
                    Integer.parseInt(doctorData.get("clinicId"))
            );
        } catch (NumberFormatException e) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "Invalid payload");
        }
    }
}
