package src.http.service;

import src.clinic.Clinic;
import src.db.client.DBClient;
import src.db.repository.ClinicRepository;
import src.db.entities.ClinicEntity;
import src.http.constants.HttpStatus;
import src.http.util.HttpException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;

public class ClinicService {
    private static final Logger LOGGER = Logger.getLogger(ClinicService.class.getName());
    private static final DBClient dbClient;

    static {
        try {
            dbClient = new DBClient(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final ClinicRepository clinicRepository = new ClinicRepository(dbClient);

    public ArrayList<Clinic> getAllClinics() {
        return clinicRepository.toClinicList(clinicRepository.getAllClinics());
    }

    public Clinic getClinicById(int clinicId) {
        ClinicEntity clinic = clinicRepository.getClinicById(clinicId);
        if (clinic.equals(new ClinicEntity())) {
            return null;
        }
        return clinicRepository.toClinic(clinic);
    }

    public Clinic addClinic(Map<String, String> clinicData) {
        ClinicEntity clinic = toClinicEntity(clinicData);
        int clinicId = clinicRepository.insertClinic(clinic);
        clinic.setClinicId(clinicId);
        return clinicRepository.toClinic(clinic);
    }

    public Clinic updateClinic(int clinicId, Map<String, String> clinicData) {
        //TODO validate data
        ClinicEntity clinicEntity = toClinicEntity(clinicData);
        clinicEntity.setClinicId(clinicId);
        clinicRepository.updateClinic(clinicEntity);
        return clinicRepository.toClinic(clinicEntity);
    }

    public void deleteClinic(int clinicId) {
        clinicRepository.deleteClinicById(clinicId);
    }

    private ClinicEntity toClinicEntity(Map<String, String> clinicData) {
        try {
            ClinicEntity clinic = new ClinicEntity(
                    clinicData.get("name"),
                    clinicData.get("city"),
                    clinicData.get("address")
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
