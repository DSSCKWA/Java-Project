package src.db.tables;

import src.equipment.EquipmentStatus;

public class EquipmentTable {

    private int equipmentId;
    private String name;
    private EquipmentStatus status;
    private int clinicId;

    public EquipmentTable(int equipment_id, String name, EquipmentStatus status, int clinicId) {
        this.equipmentId = equipment_id;
        this.name = name;
        this.status = status;
        this.clinicId = clinicId;
    }

    public EquipmentTable( String name, EquipmentStatus status, int clinicId) {
        this.name = name;
        this.status = status;
        this.clinicId = clinicId;
    }

    public EquipmentTable() {}

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
        return "EquipmentTable{" +
                "equipmentId=" + equipmentId +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", clinicId=" + clinicId +
                '}';
    }
}
