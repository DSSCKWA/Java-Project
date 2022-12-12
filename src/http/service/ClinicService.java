package src.http.service;

import src.clinic.Clinic;
import src.users.User;

import java.util.Map;
import java.util.logging.Logger;

public class ClinicService {
    private static final Logger LOGGER = Logger.getLogger(ClinicService.class.getName());

    //private UserRepo repo ??

    public ClinicService() {
    }

    public String addClinic(Map<String, String> clinicData) {
        //TODO insert user
        return null;
    }

    public Clinic getClinic(String clinicId) {
        //TODO get user from db and convert to User class
        return null;
    }

    public Clinic updateClinic(int clinicId, Clinic clinic) {
        //TODO update user in db
        return null;
    }

    public void deleteClinic(int clinicId) {
        //TODO delete user
    }
}
