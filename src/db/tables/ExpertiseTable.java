package src.db.tables;

import src.db.client.DBClient;
import src.db.repository.ExpertiseRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class ExpertiseTable {
    private int doctorId;
    private String areaOfExpertise;

    public ExpertiseTable(int doctorId, String areaOfExpertise) {
        this.doctorId = doctorId;
        this.areaOfExpertise = areaOfExpertise;
    }

    public ExpertiseTable() {
    }

    public ArrayList<ExpertiseTable> getExpertiseTableArrayListByDoctorId(int doctorId){
        ExpertiseRepository db;
        this.doctorId = doctorId;
        try{
            db = new ExpertiseRepository(new DBClient(false));
        }catch (SQLException e){
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
        return "ExpertiseTable{" +
                "doctorId=" + doctorId +
                ", areaOfExpertise='" + areaOfExpertise + '\'' +
                '}';
    }
}
