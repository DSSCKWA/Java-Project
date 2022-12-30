package src.users;

import src.clinic.Clinic;
import src.db.client.DBClient;
import src.db.entities.DoctorEntity;
import src.db.entities.ExpertiseEntity;
import src.db.entities.UserEntity;
import src.db.repository.DoctorsRepository;
import src.db.repository.ExpertiseRepository;
import src.expertise.Expertise;
import src.schedule.Schedule;
import src.visit.Visit;
import src.visit.VisitStatus;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.MINUTES;

public class Doctor extends User {
    private ArrayList<Clinic> doctorClinics;
    private ArrayList<Schedule> doctorSchedules;
    private ArrayList<Expertise> doctorExpertise;
    private DBClient dbClientAutoCommit;

    public Doctor(int id, String name, String lastName, String email, String password, String address, String city, int phoneNumber, ArrayList<Clinic> doctorClinics, ArrayList<Schedule> doctorSchedules, ArrayList<Expertise> doctorExpertise) {
        super(id, name, lastName, email, password, address, city, phoneNumber, Permissions.DOCTOR);
        this.doctorClinics = doctorClinics;
        this.doctorSchedules = doctorSchedules;
        this.doctorExpertise = doctorExpertise;
    }

    public Doctor(String name, String lastName, String email, String password, String address, String city, int phoneNumber, Permissions permissions, ArrayList<Clinic> doctorClinics, ArrayList<Schedule> doctorSchedules, ArrayList<Expertise> doctorExpertise) {
        super(name, lastName, email, password, address, city, phoneNumber, permissions);
        this.doctorClinics = doctorClinics;
        this.doctorSchedules = doctorSchedules;
        this.doctorExpertise = doctorExpertise;
    }

    public Doctor(int id, String firstName, String lastName, String email, String password, String address, String city, int phoneNumber, ArrayList<Clinic> doctorClinics, ArrayList<Schedule> doctorSchedules, ArrayList<Expertise> doctorExpertise, DBClient dbClientAutoCommit) {
        super(id, firstName, lastName, email, password, address, city, phoneNumber, Permissions.DOCTOR);
        this.doctorClinics = doctorClinics;
        this.doctorSchedules = doctorSchedules;
        this.doctorExpertise = doctorExpertise;
        this.dbClientAutoCommit = dbClientAutoCommit;
    }

    public Doctor(String firstName, String lastName, String email, String password, String address, String city, int phoneNumber, Permissions permissions) {
        super(firstName, lastName, email, password, address, city, phoneNumber, permissions);
    }

    public Doctor(String name, String surname, String email, String password, String address, String city, int phoneNumber) {
        super(name, surname, email, password, address, city, phoneNumber, Permissions.DOCTOR);
//        super.insertToDB(); // chyba może zostac, co ?
        this.doctorClinics = new ArrayList<>();
        this.doctorSchedules = new ArrayList<>();
        this.doctorExpertise = new ArrayList<>();
        try {
            dbClientAutoCommit = new DBClient(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //<editor-fold desc="Getters">

    public ArrayList<Clinic> getDoctorClinics() {
        return doctorClinics;
    }

    public ArrayList<Schedule> getDoctorSchedules() {
        return doctorSchedules;
    }


    public ArrayList<Expertise> getDoctorExpertise() {
        return doctorExpertise;
    }

    public int getId() {
        return super.getId();
    }

    //</editor-fold>

    //<editor-fold desc="Setters">

//    public Doctor readDoctorFromDBById(int id) {
//        ArrayList<DoctorEntity> doctors = new DoctorEntity().getDoctorEntityArrayByDoctorId(id);
//        ArrayList<ExpertiseEntity> expertises = new ExpertiseEntity().getExpertiseEntityArrayListByDoctorId(id);
//        UserEntity userEntity = new UserEntity().getUserEntityByUserId(id);
//
//        for (ExpertiseEntity exp : expertises) {
//            this.expertiseId.add(exp.getAreaOfExpertise());
//        }
//
//        for (DoctorEntity doc : doctors) {
//            this.clinicId.add(doc.getClinicId());
//        }
//
//        super.setId(id);
//        super.setName(userEntity.getName());
//        super.setSurname(userEntity.getSurname());
//        super.setEmail(userEntity.getEmail());
//        super.setPassword(userEntity.getPassword());
//        super.setAddress(userEntity.getAddress());
//        super.setCity(userEntity.getCity());
//        super.setPhoneNumber(userEntity.getPhoneNumber());
//        super.setPermissions(userEntity.getPermissions());
//
//        return this;
//    }

    public void setDoctorClinics(ArrayList<Clinic> doctorClinics) {
        this.doctorClinics = doctorClinics;
    }

    public void setDoctorSchedules(ArrayList<Schedule> doctorSchedules) {
        this.doctorSchedules = doctorSchedules;
    }

    public void setDoctorExpertise(ArrayList<Expertise> doctorexpertise) {
        this.doctorExpertise = doctorexpertise;
    }

    //</editor-fold>

    //<editor-fold desc="Constructor">


    //</editor-fold>

    //<editor-fold desc="Equals & HashCode">

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(doctorClinics, doctor.doctorClinics) && Objects.equals(doctorSchedules, doctor.doctorSchedules) && Objects.equals(doctorExpertise, doctor.doctorExpertise) && Objects.equals(dbClientAutoCommit, doctor.dbClientAutoCommit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), doctorClinics, doctorSchedules, doctorExpertise, dbClientAutoCommit);
    }

    //</editor-fold>

    //<editor-fold desc="Database Handling">
    public void insertToDB(Clinic clinic) {
        DoctorEntity doctor = new DoctorEntity(super.getId(), clinic.getClinicId());
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
        boolean exists = false;
        for (Clinic x : doctorClinics) {
            if (x.equals(clinic)) {
                exists = true;
                break;
            }
        }
        if (exists) {
            doctorClinics.remove(clinic);
            this.removeFromClinic(clinic);
        }
    }

    public void removeClinics() {
        doctorClinics.clear();
        this.removeFromAllClinicsDB(this);
    }

    public boolean checkIfAvaliable(ArrayList<Visit> doctorVisits, LocalDate date, LocalTime startTime, LocalTime endTime) {
        boolean ava = true;
        for (Visit x : doctorVisits) {
            if (date == x.getDate() && (((startTime.compareTo(x.getTime().plusMinutes(x.getDuration()))) < 0 && (startTime.compareTo(x.getTime())) > 0) || ((endTime.compareTo(x.getTime().plusMinutes(x.getDuration()))) < 0 && (endTime.compareTo(x.getTime())) > 0))) {
                ava = false;
                break;
            }
        }
        return ava;
    }

    public void cancelVisit(Visit visit) {
        visit.setStatus(VisitStatus.CANCELED);
    }

    public void postponeVisit(Visit visit, LocalDate date, LocalTime startTime, LocalTime endTime, ArrayList<Visit> doctorVisits) {
        if (checkIfAvaliable(doctorVisits, date, startTime, endTime)) {
            visit.setDate(date);
            visit.setTime(startTime);
            visit.setDuration((int) endTime.until(startTime, MINUTES));
        }
    }

    public void completeVisit(Visit visit) {
        visit.setStatus(VisitStatus.COMPLETED);
    }

    public void addToSchedule(int clinicId, DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        boolean exists = false;
        for (Schedule x : doctorSchedules) {
            if (day == x.getDay() && ((startTime.compareTo(x.getEndTime()) < 0 && startTime.compareTo(x.getStartTime()) > 0) || (endTime.compareTo(x.getEndTime()) < 0 && endTime.compareTo(x.getStartTime()) > 0))) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            Schedule schedule = new Schedule(this.getId(), clinicId, day, startTime, endTime);
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
    public void addVisit(Visit visit, ArrayList<Visit> doctorVisits) {
        if (checkIfAvaliable(doctorVisits, visit.getDate(), visit.getTime(), visit.getTime().plusMinutes(visit.getDuration()))) {
            doctorVisits.add(visit);
        } else {
            System.out.println("Not a valid visit date");
        }
        doctorVisits.add(visit);
    }

    public void addExpertise(Expertise expertise) throws SQLException {
        doctorExpertise.add(expertise);
        ExpertiseRepository expertiseRepository;
        expertiseRepository = new ExpertiseRepository(new DBClient(true));
        ExpertiseEntity expertiseEntity = new ExpertiseEntity(expertise.getDoctorId(), expertise.getExpertise());
        expertiseRepository.insertExpertise(expertiseEntity);
    }

    public void removeExpertise(Expertise expertise) throws SQLException {
        doctorExpertise.remove(expertise);
        ExpertiseRepository expertiseRepository;
        expertiseRepository = new ExpertiseRepository(new DBClient(true));
        expertiseRepository.deleteExpertise(expertise.getDoctorId(), expertise.getExpertise());
    }
//</editor-fold>

}
