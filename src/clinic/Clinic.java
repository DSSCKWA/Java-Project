package src.clinic;

import src.db.client.DBClient;
import src.db.entities.ClinicEntity;
import src.db.repository.ClinicRepository;

import java.sql.SQLException;
import java.util.Objects;

public class Clinic {


    private int clinicId;
    private String name;
    private String address;
    private String city;
    private final transient DBClient dbClientAutoCommit;

    //<editor-fold desc="Getters">
    public int getClinicId() {
        return clinicId;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
    //</editor-fold>

    //<editor-fold desc="Setters">
    public void setClinicId(int clinicId) {
        this.clinicId = clinicId;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    //</editor-fold>

    public void changeName(String name) {
        this.setName(name);
        this.updateDB();
    }

    public void changeAddress(String city, String address) {
        this.setAddress(address);
        this.setCity(city);
        this.updateDB();
    }

    //<editor-fold desc="Constructors">
    public Clinic(String name, String address, String city) {
        this.name = name;
        this.address = address;
        this.city = city;
        try {
            dbClientAutoCommit = new DBClient(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Clinic(int clinicId, String name, String address, String city) {
        this.clinicId = clinicId;
        this.name = name;
        this.address = address;
        this.city = city;
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
        Clinic clinic = (Clinic) o;
        return clinicId == clinic.clinicId && Objects.equals(name, clinic.name) && Objects.equals(address, clinic.address) && Objects.equals(city, clinic.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clinicId, name, address, city);
    }
    //</editor-fold>

    //<editor-fold desc="Database Handling">
    public void insertToDB() {
        ClinicEntity clinic = new ClinicEntity(name, address, city);
        ClinicRepository clinicRepository = new ClinicRepository(dbClientAutoCommit);
        this.clinicId = clinicRepository.insertClinic(clinic);
        clinic.setClinicId(clinicId);
    }

    public void removeFromDB() {
        ClinicRepository clinicRepository = new ClinicRepository(dbClientAutoCommit);
        clinicRepository.deleteClinicById(clinicId);
    }

    public void updateDB() {
        ClinicRepository clinicRepository = new ClinicRepository(dbClientAutoCommit);
        clinicRepository.updateClinic(new ClinicEntity(clinicId, name, address, city));
    }
    //</editor-fold>

    //<editor-fold desc="ToString">
    @Override
    public String toString() {
        return "Clinic{" +
                "clinicId=" + clinicId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
    //</editor-fold>
}
