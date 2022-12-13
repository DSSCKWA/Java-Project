package src.http.service;

import src.clinic.Clinic;
import src.visit.Visit;

import java.util.Map;
import java.util.logging.Logger;

public class VisitService {
    private static final Logger LOGGER = Logger.getLogger(VisitService.class.getName());

    //private UserRepo repo ??

    public VisitService() {
    }

    public String addVisit(Map<String, String> visitData) {
        //TODO insert user
        return null;
    }

    public Visit getVisit(String visitId) {
        //TODO get user from db and convert to User class
        return null;
    }

    public Visit updateVisit(int visitId, Visit visit) {
        //TODO update user in db
        return null;
    }

    public void deleteVisit(int visitId) {
        //TODO delete user
    }
}
