package src.users;

import src.clinic.Clinic;
import src.db.repository.ExpertiseRepository;
import src.db.tables.*;
import src.db.client.DBClient;
import src.db.repository.DoctorsRepository;
import src.db.tables.DoctorsTable;
import src.expertise.Expertise;
import src.schedule.Schedule;
import src.visit.Status;
import src.visit.Visit;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.time.LocalTime;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.*;

public class Doctor extends User {
    private HashSet<Clinic> doctorClinics;
    private HashSet<Schedule> doctorSchedules;
    private HashSet<Visit> doctorVisits;
    private HashSet<Expertise> doctorExpertise;
    private DBClient dbClientAutoCommit;

    public Doctor(String firstName, String lastName, String email, String password, String address, String city, int phoneNumber, Permissions permissions) {
        super(firstName, lastName, email, password, address, city, phoneNumber, permissions);
    }

    //<editor-fold desc="Getters">

    public HashSet<Clinic> getDoctorClinics() {
        return doctorClinics;
    }

    public HashSet<Schedule> getDoctorSchedules() {
        return doctorSchedules;
    }

    public HashSet<Visit> getDoctorVisits() {
        return doctorVisits;
    }

    public HashSet<Expertise> getDoctorExpertise() {
        return doctorExpertise;
    }

    public int getId(){return super.getId();}

    //</editor-fold>

    //<editor-fold desc="Setters">

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
 public void setDoctorClinics(HashSet<Clinic> doctorClinics) {
        this.doctorClinics = doctorClinics;
    }
    
    public List<String> getExpertiseId() {
        return expertiseId;
    }

    public void setDoctorSchedules(HashSet<Schedule> doctorSchedules) {
        this.doctorSchedules = doctorSchedules;
    }

    public void setDoctorVisits(HashSet<Visit> doctorVisits) {
        this.doctorVisits = doctorVisits;
    }

    public void setDoctorExpertise(HashSet<Expertise> doctorexpertise) {
        this.doctorExpertise = doctorexpertise;
    }

    //</editor-fold>

    //<editor-fold desc="Constructor">
    public Doctor(String name, String surname, String email, String password, String address, String city, int phoneNumber) {
        super(name, surname, email, password ,address,city, phoneNumber, Permissions.DOCTOR);
        super.insertToDB(); // chyba może zostac, co ?
        this.doctorClinics = new HashSet<Clinic>();
        this.doctorSchedules = new HashSet<Schedule>();
        this.doctorVisits = new HashSet<Visit>();
        this.doctorExpertise = new HashSet<Expertise>();
        try {
            dbClientAutoCommit = new DBClient(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Doctor(int id, String firstName, String lastName, String email, String password, String address, String city, int phoneNumber, Permissions permissions, HashSet<Clinic> doctorClinics, HashSet<Schedule> doctorSchedules, HashSet<Visit> doctorVisits, HashSet<Expertise> doctorExpertise, DBClient dbClientAutoCommit, List<String> expertiseId, HashSet<Integer> clinicId) {
        super(id, firstName, lastName, email, password, address, city, phoneNumber, permissions);
        this.doctorClinics = doctorClinics;
        this.doctorSchedules = doctorSchedules;
        this.doctorVisits = doctorVisits;
        this.doctorExpertise = doctorExpertise;
        this.dbClientAutoCommit = dbClientAutoCommit;
        this.expertiseId = expertiseId;
        this.clinicId = clinicId;
    }

    //</editor-fold>

    //<editor-fold desc="Equals & HashCode">
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Doctor doctor = (Doctor) o;
        return doctorClinics.equals(doctor.doctorClinics) && doctorSchedules.equals(doctor.doctorSchedules) && Objects.equals(doctorVisits, doctor.doctorVisits) && doctorExpertise.equals(doctor.doctorExpertise);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), doctorClinics, doctorSchedules, doctorVisits, doctorExpertise);
    }
    //</editor-fold>

    //<editor-fold desc="Database Handling">
    public void insertToDB(Clinic clinic) {
            DoctorsTable doctor = new DoctorsTable(super.getId(),clinic.getClinicId());
            DoctorsRepository doctorsRepository = new DoctorsRepository(dbClientAutoCommit);
            doctorsRepository.insertDoctor(doctor);
            doctor.setDoctorId(super.getId());
    }

    // function which removes doctor from ALL clinics
    public void removeFromAllClinicsDB(Doctor doctor) {
        DoctorsRepository doctorsRepository = new DoctorsRepository(dbClientAutoCommit);
        doctorsRepository.deleteDoctorFromClinics(doctor.getId());
    }
    //</editor-fold>

    // function which removes doctor from ONE specific clinic
    public void removeFromClinic(Clinic clinic) {
        DoctorsRepository doctorsRepository = new DoctorsRepository(dbClientAutoCommit);
        doctorsRepository.deleteDoctorFromClinic(this.getId(), clinic.getClinicId());
    }

    //<editor-fold desc="ToString">
    @Override
    public String toString() {
        return "Doctor{" +
                "doctorClinics=" + doctorClinics +
                ", doctorSchedules=" + doctorSchedules +
                ", doctorVisits=" + doctorVisits +
                ", doctorExpertise=" + doctorExpertise +
                '}';
    }
    //</editor-fold>

    //<editor-fold desc="Other Methods">
    public void addClinic(Clinic clinic) {
        doctorClinics.add(clinic);
        this.insertToDB(clinic);
}
    public void removeClinic(Clinic clinic) {
        boolean exists= false;
        for (Clinic x:doctorClinics) {
            if(x.equals(clinic))
            {
                exists=true;
                break;
            }
        }
        if(exists)
        {
            doctorClinics.remove(clinic);
            this.removeFromClinic(clinic);
        }
    }

    public void removeClinics() {
        doctorClinics.clear();
        this.removeFromAllClinicsDB(this);
    }

    public boolean checkIfAvaliable(HashSet<Visit> doctorVisits,LocalDate date,LocalTime startTime,LocalTime endTime) {
    boolean ava = true;
    for (Visit x:doctorVisits) {
        if(date==x.getDate() && (((startTime.compareTo(x.getTime().plusMinutes(x.getDuration())))<0 && (startTime.compareTo(x.getTime()))>0)||((endTime.compareTo(x.getTime().plusMinutes(x.getDuration())))<0 && (endTime.compareTo(x.getTime()))>0)))
        {
            ava=false;
            break;
        }
    }
    return ava;
}
    public void cancelVisit(Visit visit) {
    visit.setStatus(Status.CANCELED);
}
    public void postponeVisit(Visit visit,LocalDate date,LocalTime startTime,LocalTime endTime) {
        if(checkIfAvaliable(doctorVisits,date,startTime,endTime)) {
            visit.setDate(date);
            visit.setTime(startTime);
            visit.setDuration((int) endTime.until(startTime, MINUTES));
        }
    }
    public void completeVisit(Visit visit) {
        visit.setStatus(Status.COMPLETED);
    }
    public void addToSchedule( int clinicId, DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        boolean exists=false;
        for (Schedule x:doctorSchedules) {
            if(day==x.getDay() && ((startTime.compareTo(x.getEndTime())<0 && startTime.compareTo(x.getStartTime())>0)||(endTime.compareTo(x.getEndTime())<0 && endTime.compareTo(x.getStartTime())>0)))
            {
                exists=true;
                break;
            }
        }
        if(!exists)
        {
            Schedule schedule = new Schedule(this.getId(),clinicId,day,startTime,endTime);
            schedule.insertToDB();
            doctorSchedules.add(schedule);
        }
    }
//    public void removeFromSchedule( Schedule schedule) {
//        boolean cleared=true;
//        for (Visit x:doctorVisits
//             ) {
//            ///TODO: metoda/nadpisanie konstruktora w Visit przypisująca Schedule do wizyty
//            if(x.getSchedule().equals(schedule) && !x.getStatus().equals(Status.CANCELED) && !x.getStatus().equals(Status.POSTPONED)) {
//                cleared=false;
//            }
//        }
//            if(cleared) {
//                doctorSchedules.remove(schedule);
//            }
//        }
    public void addVisit(Visit visit) {
        if(checkIfAvaliable(doctorVisits,visit.getDate(),visit.getTime(),visit.getTime().plusMinutes(visit.getDuration()))) {
            doctorVisits.add(visit);
        }
        else {
            System.out.println("Not a valid visit date");
        }
        doctorVisits.add(visit);
    }
    public void addExpertise(Expertise expertise) throws SQLException {
        doctorExpertise.add(expertise);
        ExpertiseRepository expertiseRepository;
        expertiseRepository = new ExpertiseRepository(new DBClient(true));
        ExpertiseTable expertiseTable = new ExpertiseTable(expertise.getDoctorId(), expertise.getExpertise());
        expertiseRepository.insertExpertise(expertiseTable);
    }
    public void removeExpertise(Expertise expertise) throws SQLException {
        doctorExpertise.remove(expertise);
        ExpertiseRepository expertiseRepository;
        expertiseRepository = new ExpertiseRepository(new DBClient(true));
        expertiseRepository.deleteExpertise(expertise.getDoctorId(), expertise.getExpertise());
    }
//</editor-fold>

}
