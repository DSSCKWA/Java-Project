package src.db.tables;

public class ExpertiseTable {
    private int doctorId;
    private String areaOfExpertise;

    public ExpertiseTable(int doctorId, String areaOfExpertise) {
        this.doctorId = doctorId;
        this.areaOfExpertise = areaOfExpertise;
    }

    public ExpertiseTable() {
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getAreaOfExpertise() {
        return areaOfExpertise;
    }

    public void setAreaOfExpertise(String areaOfExpertise) {
        this.areaOfExpertise = areaOfExpertise;
    }

    @Override
    public String toString() {
        return "ExpertiseTable{" +
                "doctorId=" + doctorId +
                ", areaOfExpertise='" + areaOfExpertise + '\'' +
                '}';
    }
}
