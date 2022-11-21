package src.users;

import src.clinic.Clinic;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class Doctor extends User{


    private List<Integer> expertiseId;
    private HashSet<Integer> clinicId;
    public List<Integer> getExpertiseId() {
        return expertiseId;
    }

    public void setExpertiseId(List<Integer> expertiseId) {
        this.expertiseId = expertiseId;
    }

    public HashSet<Integer> getClinicId() {
        return clinicId;
    }

    public void setClinicId(HashSet<Integer> clinicId) {
        this.clinicId = clinicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Doctor doctor = (Doctor) o;
        User user = (User) o;
        return expertiseId == doctor.expertiseId && clinicId == doctor.clinicId && super.equals(user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), expertiseId, clinicId);
    }

    @Override
    public String toString() {
        return "Doctor{" +
                super.toString() +
                ", expertiseId='" + expertiseId + '\'' +
                ", clinicId='" + clinicId + '\'' +
                '}';
    }
}
