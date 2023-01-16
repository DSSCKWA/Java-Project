package src.http.service;

import src.expertise.Expertise;
import src.db.client.DBClient;
import src.db.entities.ExpertiseEntity;
import src.db.repository.ExpertiseRepository;
import src.http.constants.HttpStatus;
import src.http.util.HttpException;
import src.validator.Validator;

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;

public class ExpertiseService {
    private static final Logger LOGGER = Logger.getLogger(ExpertiseService.class.getName());
    private static final DBClient dbClient;

    static {
        dbClient = new DBClient(true);
    }

    private static final ExpertiseRepository expertiseRepository = new ExpertiseRepository(dbClient);

    public ArrayList<Expertise> getAllExpertises() {
        return expertiseRepository.toExpertiseList(expertiseRepository.getAllExpertises());
    }

    public ArrayList<Expertise> getExpertise(int doctorId) {
        ArrayList<ExpertiseEntity> expertise = expertiseRepository.getExpertise(doctorId);
        return expertiseRepository.toExpertiseList(expertise);
    }

    public ArrayList<Expertise> getExpertise(String areaOfExpertise) {
        ArrayList<ExpertiseEntity> expertise = expertiseRepository.getExpertise(areaOfExpertise);
        return expertiseRepository.toExpertiseList(expertise);
    }

    public Expertise getExpertise(int doctorId, String areaOfExpertise) {
        ExpertiseEntity expertise = expertiseRepository.getExpertise(doctorId, areaOfExpertise);
        if (expertise.equals(new ExpertiseEntity())) {
            return null;
        }
        return expertiseRepository.toExpertise(expertise);
    }

    public Expertise addExpertise(Map<String, String> expertiseData) {
        ExpertiseEntity expertise = toExpertiseEntity(expertiseData);
        expertiseRepository.insertExpertise(expertise);
        return expertiseRepository.toExpertise(expertise);
    }

    public void deleteExpertise(int doctorId) {
        expertiseRepository.deleteExpertise(doctorId);
    }

    public void deleteExpertise(String areaOfExpertise) {
        expertiseRepository.deleteExpertise(areaOfExpertise);
    }

    public void deleteExpertise(int doctorId, String areaOfExpertise) {
        expertiseRepository.deleteExpertise(doctorId, areaOfExpertise);
    }

    private ExpertiseEntity toExpertiseEntity(Map<String, String> expertiseData) {
        try {
            if (!Validator.isValidStringWithDashAndSpace(expertiseData.get("areaOfExpertise"))) {
                throw new HttpException(HttpStatus.BAD_REQUEST, "Data did not pass validation");
            }
            return new ExpertiseEntity(
                    Integer.parseInt(expertiseData.get("doctorId")),
                    expertiseData.get("areaOfExpertise")
            );
        } catch (NumberFormatException e) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "Invalid payload");
        }
    }
}
