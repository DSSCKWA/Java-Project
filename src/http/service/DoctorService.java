package src.http.service;

import src.users.Doctor;

import java.util.Map;
import java.util.logging.Logger;

public class DoctorService {
    private static final Logger LOGGER = Logger.getLogger(DoctorService.class.getName());

    //private UserRepo repo ??

    public DoctorService() {
    }

    public String addDoctor(Map<String, String> doctorData) {
        //TODO insert user
        return null;
    }

    public Doctor getDoctor(String doctorId) {
        //TODO get user from db and convert to User class
        return null;
    }

    public Doctor updateDoctor(int doctorId, Doctor doctor) {
        //TODO update user in db
        return null;
    }

    public void deleteDoctor(int doctorId) {
        //TODO delete user
    }
}
