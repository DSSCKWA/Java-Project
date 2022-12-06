package src.db.tables;

import src.db.client.DBClient;
import src.db.repository.DoctorsRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class DoctorsTable {
    private int doctorId;
    private int clinicId;

    public DoctorsTable(int doctorId, int clinicId) {
        this.doctorId = doctorId;
        this.clinicId = clinicId;
    }

    public ArrayList<DoctorsTable> getDoctorsTableArrayByDoctorId(int id){
        DoctorsRepository db;
        try {
            db = new DoctorsRepository(new DBClient(false));
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
       return db.getDoctorById(id);
    }

    public DoctorsTable(){
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
