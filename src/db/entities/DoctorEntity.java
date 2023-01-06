package src.db.entities;

import src.db.client.DBClient;
import src.db.repository.DoctorRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class DoctorEntity {
    private int doctorId;
    private int clinicId;

    public DoctorEntity(int doctorId, int clinicId) {
        this.doctorId = doctorId;
        this.clinicId = clinicId;
    }

    public ArrayList<DoctorEntity> getDoctorEntityArrayByDoctorId(int id) {
        DoctorRepository db;
        db = new DoctorRepository(new DBClient(false));
        return db.getDoctor(id);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoctorEntity that = (DoctorEntity) o;
        return doctorId == that.doctorId && clinicId == that.clinicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctorId, clinicId);
    }

    @Override
    public String toString() {
        return "DoctorsEntity{" +
                "doctorId=" + doctorId +
                ", clinicId=" + clinicId +
                '}';
    }
}
