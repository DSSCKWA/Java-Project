package src.db.tables;

public class EquipmentTable {
    private int equipmentId;
    private int clinicId;
    private String name;
    private String status;

    public EquipmentTable(int equipmentId, int clinicId, String name, String status) {
        this.equipmentId = equipmentId;
        this.clinicId = clinicId;
        this.name = name;
        this.status = status;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getClinicId() {
        return clinicId;
    }

    public void setClinicId(int clinicId) {
        this.clinicId = clinicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "EquipmentTable{" +
                "equipmentId=" + equipmentId +
                ", clinicId=" + clinicId +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
