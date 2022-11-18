package src.db.tables;

public class DoctorsTable {
    private int doctorId;
    private int clinicId;

    public DoctorsTable(int doctorId, int clinicId) {
        this.doctorId = doctorId;
        this.clinicId = clinicId;
    }

    public DoctorsTable() {
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getClinicId() {
        return clinicId;
    }

    public void setClinicId(int clinicId) {
        this.clinicId = clinicId;
    }

    @Override
    public String toString() {
        return "DoctorsTable{" +
                "doctorId=" + doctorId +
                ", clinicId=" + clinicId +
                '}';
    }
}
