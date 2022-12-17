package src.db.entities;

import java.util.Objects;

public class ClinicEntity {
    private int clinicId;
    private String name;
    private String address;
    private String city;

    public ClinicEntity(int clinicId, String name, String address, String city) {
        this.clinicId = clinicId;
        this.name = name;
        this.address = address;
        this.city = city;
    }

    public ClinicEntity(String name, String address, String city) {
        this.name = name;
        this.address = address;
        this.city = city;
    }

    public ClinicEntity() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "ClinicsEntity{" +
                "clinicId=" + clinicId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClinicEntity that = (ClinicEntity) o;
        return clinicId == that.clinicId && Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clinicId, name, address, city);
    }
}
