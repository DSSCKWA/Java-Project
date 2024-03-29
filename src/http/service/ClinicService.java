package src.http.service;

import src.clinic.Clinic;
import src.db.client.DBClient;
import src.db.entities.ClinicEntity;
import src.db.repository.ClinicRepository;
import src.http.constants.HttpStatus;
import src.http.util.HttpException;
import src.validator.Validator;

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;

public class ClinicService {
    private static final Logger LOGGER = Logger.getLogger(ClinicService.class.getName());
    private static final DBClient dbClient;

    static {
        dbClient = new DBClient(true);
    }

    private static final ClinicRepository clinicRepository = new ClinicRepository(dbClient);

    public ArrayList<Clinic> getAllClinics() {
        return clinicRepository.toClinicList(clinicRepository.getAllClinics());
    }

    public synchronized Clinic getClinic(int clinicId) {
        ClinicEntity clinic = clinicRepository.getClinic(clinicId);
        if (clinic.equals(new ClinicEntity())) {
            return null;
        }
        return clinicRepository.toClinic(clinic);
    }

    public synchronized Clinic addClinic(Map<String, String> clinicData) {
        ClinicEntity clinic = toClinicEntity(clinicData);
        int clinicId = clinicRepository.insertClinic(clinic);
        clinic.setClinicId(clinicId);
        return clinicRepository.toClinic(clinic);
    }

    public synchronized Clinic updateClinic(int clinicId, Map<String, String> clinicData) {
        ClinicEntity clinicEntity = toClinicEntity(clinicData);
        clinicEntity.setClinicId(clinicId);
        clinicRepository.updateClinic(clinicEntity);
        return clinicRepository.toClinic(clinicEntity);
    }

    public synchronized void deleteClinic(int clinicId) {
        clinicRepository.deleteClinicById(clinicId);
    }

    private ClinicEntity toClinicEntity(Map<String, String> clinicData) {
        try {
            if (!Validator.isValidStringWithSpace(clinicData.get("name")) ||
                    !Validator.isValidAddress(clinicData.get("address")) ||
                    !Validator.isValidStringWithDashAndSpace(clinicData.get("city"))
            ) {
                throw new HttpException(HttpStatus.BAD_REQUEST, "Data did not pass validation");
            }
            ClinicEntity clinic = new ClinicEntity(
                    clinicData.get("name"),
                    clinicData.get("address"),
                    clinicData.get("city")
            );
            if (clinicData.get("clinicId") != null) {
                clinic.setClinicId(Integer.parseInt(clinicData.get("clinicId")));
            }
            return clinic;
        } catch (NumberFormatException e) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "Invalid payload");
        }
    }
}
