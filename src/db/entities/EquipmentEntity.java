package src.db.entities;

import src.equipment.EquipmentStatus;

public class EquipmentEntity {

    private int equipmentId;
    private String name;
    private EquipmentStatus status;
    private int clinicId;

    public EquipmentEntity(int equipment_id, String name, EquipmentStatus status, int clinicId) {
        this.equipmentId = equipment_id;
        this.name = name;
        this.status = status;
        this.clinicId = clinicId;
    }

    public EquipmentEntity(String name, EquipmentStatus status, int clinicId) {
        this.name = name;
        this.status = status;
        this.clinicId = clinicId;
    }

    public EquipmentEntity() {
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EquipmentStatus getStatus() {
        return status;
    }

    public void setStatus(EquipmentStatus status) {
        this.status = status;
    }

    public int getClinicId() {
        return clinicId;
    }

    public void setClinicId(int clinicId) {
        this.clinicId = clinicId;
    }

    @Override
    public String toString() {
        return "EquipmentEntity{" +
                "equipmentId=" + equipmentId +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", clinicId=" + clinicId +
                '}';
    }
}
