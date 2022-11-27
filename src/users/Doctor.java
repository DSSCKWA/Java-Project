package src.users;

import src.clinic.Clinic;
import src.db.client.DBClient;
import src.db.repository.DoctorsRepository;
import src.db.tables.DoctorsTable;
import src.expertise.Expertise;
import src.schedule.Schedule;
import src.visit.Status;
import src.visit.Visit;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Objects;
import java.time.LocalTime;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.*;

public class Doctor extends User {
    private HashSet<Clinic> doctorClinics;
    private HashSet<Schedule> doctorSchedules;
    private HashSet<Visit> doctorVisits;
    private HashSet<Expertise> doctorexpertise;
    private final DBClient dbClientAutoCommit;

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

    public HashSet<Expertise> getDoctorexpertise() {
        return doctorexpertise;
    }

    public int getId(){return super.getId();}

    //</editor-fold>

    //<editor-fold desc="Setters">

    public void setDoctorClinics(HashSet<Clinic> doctorClinics) {
        this.doctorClinics = doctorClinics;
    }

    public void setDoctorSchedules(HashSet<Schedule> doctorSchedules) {
        this.doctorSchedules = doctorSchedules;
    }

    public void setDoctorVisits(HashSet<Visit> doctorVisits) {
        this.doctorVisits = doctorVisits;
    }

    public void setDoctorexpertise(HashSet<Expertise> doctorexpertise) {
        this.doctorexpertise = doctorexpertise;
    }

    //</editor-fold>

    //<editor-fold desc="Constructor">
    public Doctor(String name, String surname, String address, String city, int phoneNumber, String email, String password) {
        super(name, surname, address, city, phoneNumber, email, password, Permissions.DOCTOR); ///TODO: konstruktor w User
        this.doctorClinics = new HashSet<Clinic>();
        this.doctorSchedules = new HashSet<Schedule>();
        this.doctorVisits = new HashSet<Visit>();
        this.doctorexpertise = new HashSet<Expertise>();
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
        if (!super.equals(o)) return false;
        Doctor doctor = (Doctor) o;
        return doctorClinics.equals(doctor.doctorClinics) && doctorSchedules.equals(doctor.doctorSchedules) && Objects.equals(doctorVisits, doctor.doctorVisits) && doctorexpertise.equals(doctor.doctorexpertise);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), doctorClinics, doctorSchedules, doctorVisits, doctorexpertise);
    }
    //</editor-fold>

    //<editor-fold desc="Database Handling">
    public void insertToDB(Clinic clinic) {
            DoctorsTable doctor = new DoctorsTable(super.getId(),clinic.getClinicId());
            DoctorsRepository doctorsRepository = new DoctorsRepository(dbClientAutoCommit);
            doctorsRepository.insertDoctor(doctor);
            doctor.setDoctorId(super.getId());
            System.out.println(doctorsRepository.getAllDoctors());
    }

    public void removeFromDB(Doctor doctor) {
        DoctorsRepository doctorsRepository = new DoctorsRepository(dbClientAutoCommit);
        doctorsRepository.deleteDoctorFromClinic(doctor.getId());
    }
    //</editor-fold>
public void addClinic(Clinic clinic)
{
    doctorClinics.add(clinic);
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
        }
        else {
            System.out.println("Doctor isn't working add chosen clinic");
            /// W domyśle nie będzie opcji wybrania kliniki w listy
        }
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

public void cancelVisit(Visit visit)
{
    visit.setStatus(Status.CANCELED);
}
public void postponeVisit(Visit visit,LocalDate date,LocalTime startTime,LocalTime endTime) {
        if(checkIfAvaliable(doctorVisits,date,startTime,endTime)) {
            visit.setDate(date);
            visit.setTime(startTime);
            visit.setDuration((int) endTime.until(startTime, MINUTES));
        }
        else {
            System.out.println("Not a valid visit date");
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
            doctorSchedules.add(new Schedule(super.getId(),clinicId,day,startTime,endTime));
        }
        else {
            System.out.println("Not a valid work date");
        }
    }

    public void removeFromSchedule( Schedule schedule) {
        boolean cleared=true;
        for (Visit x:doctorVisits
             ) {
            ///TODO: metoda/nadpisanie konstruktora w Visit przypisująca Schedule do wizyty
            if(x.getSchedule().equals(schedule) && !x.getStatus().equals(Status.CANCELED) && !x.getStatus().equals(Status.POSTPONED)) {
                cleared=false;
            }
        }
            if(cleared)
            {
                doctorSchedules.remove(schedule);
            }
            else{
                System.out.println("Doctor still has active visits during chosen schedule");
            }
        }

    public void addVisit(Visit visit) {
        if(checkIfAvaliable(doctorVisits,visit.getDate(),visit.getTime(),visit.getTime().plusMinutes(visit.getDuration()))) {
            doctorVisits.add(visit);
        }
        else {
            System.out.println("Not a valid visit date");
        }
        doctorVisits.add(visit);
    }

    public void addExpertise(Expertise expertise) {
        doctorexpertise.add(expertise);
    }

    public void removeExpertise(Expertise expertise) {
        doctorexpertise.remove(expertise);
    }
}
