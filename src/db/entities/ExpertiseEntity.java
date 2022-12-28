package src.db.entities;

import src.db.client.DBClient;
import src.db.repository.ExpertiseRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class ExpertiseEntity {
    private int doctorId;
    private String areaOfExpertise;

    public ExpertiseEntity(int doctorId, String areaOfExpertise) {
        this.doctorId = doctorId;
        this.areaOfExpertise = areaOfExpertise;
    }

    public ExpertiseEntity() {
    }

    public ArrayList<ExpertiseEntity> getExpertiseEntityArrayListByDoctorId(int doctorId) {
        ExpertiseRepository db;
        this.doctorId = doctorId;
        try {
            db = new ExpertiseRepository(new DBClient(false));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return db.getExpertiseByDoctorId(doctorId);
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
        return "ExpertiseEntity{" +
                "doctorId=" + doctorId +
                ", areaOfExpertise='" + areaOfExpertise + '\'' +
                '}';
    }
}
