package src.users;

import src.clinic.Clinic;

import java.util.Objects;

public class Doctor extends User{

    private int id;
    private int expertiseId;
    private int clinicId;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getExpertiseId() {
        return expertiseId;
    }

    public void setExpertiseId(int expertiseId) {
        this.expertiseId = expertiseId;
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
        if (!super.equals(o)) return false;
        Doctor doctor = (Doctor) o;
        User user = (User) o;
        return id == doctor.id && expertiseId == doctor.expertiseId && clinicId == doctor.clinicId && super.equals(user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, expertiseId, clinicId);
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id='" + id + '\'' +
                "firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", expertiseId='" + expertiseId + '\'' +
                ", clinicId='" + clinicId + '\''+
                '}';
    }
}
