package src.http.service;

import src.db.client.DBClient;
import src.db.entities.EquipmentEntity;
import src.db.repository.EquipmentRepository;
import src.equipment.Equipment;
import src.equipment.EquipmentStatus;
import src.http.constants.HttpStatus;
import src.http.util.HttpException;
import src.validator.Validator;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

public class EquipmentService {
    private static final Logger LOGGER = Logger.getLogger(EquipmentService.class.getName());
    private static final DBClient dbClient;

    static {
        dbClient = new DBClient(true);
    }

    private static final EquipmentRepository equipmentRepository = new EquipmentRepository(dbClient);

    public ArrayList<Equipment> getAllEquipment() {
        return equipmentRepository.toEquipmentList(equipmentRepository.getAllEquipment());
    }

    public ArrayList<Equipment> getAllEquipmentByClinic(int clinicId) {
        return equipmentRepository.toEquipmentList(equipmentRepository.getEquipmentByClinicId(clinicId));
    }

    public Equipment getEquipmentById(int equipmentId) {
        EquipmentEntity equipment = equipmentRepository.getEquipmentById(equipmentId);
        if (equipment.equals(new EquipmentEntity())) {
            return null;
        }
        return equipmentRepository.toEquipment(equipment);
    }

    public Equipment addEquipment(Map<String, String> equipmentData) {
        EquipmentEntity equipment = toEquipmentEntity(equipmentData);
        int equipmentId = equipmentRepository.insertEquipment(equipment);
        equipment.setEquipmentId(equipmentId);
        return equipmentRepository.toEquipment(equipment);
    }

    public Equipment updateEquipment(int equipmentId, Map<String, String> equipmentData) {
        EquipmentEntity equipmentEntity = toEquipmentEntity(equipmentData);
        equipmentEntity.setEquipmentId(equipmentId);
        equipmentRepository.updateEquipment(equipmentEntity);
        return equipmentRepository.toEquipment(equipmentEntity);
    }

    public void deleteEquipment(int equipmentId) {
        equipmentRepository.deleteEquipmentById(equipmentId);
    }

    private EquipmentEntity toEquipmentEntity(Map<String, String> equipmentData) {
        try {
            if (!Validator.isValidString(equipmentData.get("name"))) {
                throw new HttpException(HttpStatus.BAD_REQUEST, "Data did not pass validation");
            }
            EquipmentEntity equipment = new EquipmentEntity(
                    equipmentData.get("name"),
                    EquipmentStatus.valueOf(equipmentData.get("equipmentStatus").toUpperCase(Locale.ROOT)),
                    Integer.parseInt(equipmentData.get("clinicId"))
            );
            if (equipmentData.get("equipmentId") != null) {
                equipment.setEquipmentId(Integer.parseInt(equipmentData.get("equipmentId")));
            }
            return equipment;
        } catch (NumberFormatException e) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "Invalid payload");
        }
    }
}
