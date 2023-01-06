package src.equipment;

import src.db.client.DBClient;
import src.db.entities.EquipmentEntity;
import src.db.repository.EquipmentRepository;

public class Equipment {

    private int equipmentId;
    private String name;
    private EquipmentStatus equipmentStatus;
    private int clinicId;
    private transient DBClient dbClientAutoCommit;

    //<editor-fold desc="Getters">
    public int getEquipmentId() {
        return equipmentId;
    }

    public String getName() {
        return name;
    }

    public EquipmentStatus getEquipmentStatus() {
        return equipmentStatus;
    }

    public int getClinicId() {
        return clinicId;
    }
    //</editor-fold>

    //<editor-fold desc="Setters">
    public void setEquipmentId(int equipment_id) {
        this.equipmentId = equipment_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEquipmentStatus(EquipmentStatus equipmentStatus) {
        this.equipmentStatus = equipmentStatus;
    }

    public void setClinicId(int clinicId) {
        this.clinicId = clinicId;
    }
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public Equipment(int equipmentId, String name, EquipmentStatus status, int clinicId) {
        this.equipmentId = equipmentId;
        this.name = name;
        this.equipmentStatus = status;
        this.clinicId = clinicId;
    }

    public Equipment(String name, EquipmentStatus status, int clinicId) {
        this.equipmentId = 0;
        this.name = name;
        this.equipmentStatus = status;
        this.clinicId = clinicId;
    }
    //</editor-fold>

    //<editor-fold desc="Database Handling">
    public void insertToDB() {
        EquipmentEntity equipment = new EquipmentEntity(name, equipmentStatus, clinicId);
        EquipmentRepository equipmentRepository = new EquipmentRepository(dbClientAutoCommit);
        this.equipmentId = equipmentRepository.insertEquipment(equipment);
        equipment.setEquipmentId(equipmentId);
    }

    public void removeFromDB() {
        EquipmentRepository equipmentRepository = new EquipmentRepository(dbClientAutoCommit);
        equipmentRepository.deleteEquipmentById(equipmentId);
    }

    public void updateDB() {
        EquipmentRepository equipmentRepository = new EquipmentRepository(dbClientAutoCommit);
        equipmentRepository.updateEquipment(new EquipmentEntity(equipmentId, name, equipmentStatus, clinicId));
    }
    //</editor-fold>

}
