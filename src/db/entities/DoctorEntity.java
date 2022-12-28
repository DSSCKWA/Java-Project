package src.db.entities;

import src.db.client.DBClient;
import src.db.repository.DoctorsRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class DoctorEntity {
    private int doctorId;
    private int clinicId;

    public DoctorEntity(int doctorId, int clinicId) {
        this.doctorId = doctorId;
        this.clinicId = clinicId;
    }

    public ArrayList<DoctorEntity> getDoctorEntityArrayByDoctorId(int id) {
        DoctorsRepository db;
        try {
            db = new DoctorsRepository(new DBClient(false));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return db.getDoctorById(id);
    }

    public DoctorEntity() {
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
        return "DoctorsEntity{" +
                "doctorId=" + doctorId +
                ", clinicId=" + clinicId +
                '}';
    }
}
