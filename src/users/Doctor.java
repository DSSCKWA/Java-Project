package src.users;

import src.db.tables.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class Doctor extends User{


    private List<String> expertiseId;
    private HashSet<Integer> clinicId;

    public Doctor readDoctorFromDBById(int id){
        ArrayList<DoctorsTable> doctors = new DoctorsTable().getDoctorsTableArrayByDoctorId(id);
        ArrayList<ExpertiseTable> expertises = new ExpertiseTable().getExpertiseTableArrayListByDoctorId(id);
        UsersTable usersTable = new UsersTable().getUsersTableByUserId(id);

        for (ExpertiseTable exp: expertises) {
            this.expertiseId.add(exp.getAreaOfExpertise());
        }

        for (DoctorsTable doc: doctors){
            this.clinicId.add(doc.getClinicId());
        }

        super.setId(id);
        super.setFirstName(usersTable.getName());
        super.setLastName(usersTable.getSurname());
        super.setEmail(usersTable.getEmail());
        super.setPassword(usersTable.getPassword());
        super.setAddress(usersTable.getAddress());
        super.setCity(usersTable.getCity());
        super.setPhoneNumber(usersTable.getPhoneNumber());
        super.setPermissions(usersTable.getPermissions());

        return this;
    }
    public List<String> getExpertiseId() {
        return expertiseId;
    }

    public void setExpertiseId(List<String> expertiseId) {
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
