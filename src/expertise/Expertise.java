package src.expertise;

import src.db.client.DBClient;
import src.db.repository.ExpertiseRepository;
import src.db.entities.ExpertiseEntity;
import src.users.Doctor;

import java.sql.SQLException;
import java.util.Objects;

public class Expertise {
    private int doctorId;
    private String expertise;
    private final DBClient dbClientAutoCommit;

    //<editor-fold desc="Getters">
    public int getDoctorId() {
        return doctorId;
    }

    public String getExpertise() {
        return expertise;
    }
    //</editor-fold>

    //<editor-fold desc="Setters">
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public Expertise(int doctorId, String expertise) {
        this.doctorId = doctorId;
        this.expertise = expertise;
        try {
            dbClientAutoCommit = new DBClient(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //</editor-fold>

    //<editor-fold desc="Equals & HashCode">
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expertise expertise1 = (Expertise) o;
        return doctorId == expertise1.doctorId && expertise.equals(expertise1.expertise);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctorId, expertise);
    }
    //</editor-fold>

    //<editor-fold desc="Database Handling">
    public void insertToDB(Expertise expertise) {
        ExpertiseRepository expertiseRepository = new ExpertiseRepository(dbClientAutoCommit);
        expertiseRepository.insertExpertise(new ExpertiseEntity(doctorId, this.expertise));
        System.out.println(expertiseRepository.getAllExpertises());
    }

    public void removeFromDB(Doctor doctor) {
        ExpertiseRepository expertiseRepository = new ExpertiseRepository(dbClientAutoCommit);
        expertiseRepository.deleteExpertise(doctor.getId(), expertise);
    }
    //</editor-fold>

    //<editor-fold desc="ToString">
    @Override
    public String toString() {
        return "Expertise{" +
                "doctorId=" + doctorId +
                ", expertise='" + expertise + '\'' +
                '}';
    }
    //</editor-fold>
}
