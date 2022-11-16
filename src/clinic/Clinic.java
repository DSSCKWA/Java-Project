package src.clinic;

import src.users.Doctor;

import java.util.List;
import java.util.Objects;

public class Clinic {


    private int clinicId;
    private String name;
    private String address;
    private String city;

    public int getClinicId() {
        return clinicId;
    }

    public void setClinicId(int clinicId) {
        this.clinicId = clinicId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    @Override
    public String toString() {
        return "Clinic{" +
                "clinicId=" + clinicId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
