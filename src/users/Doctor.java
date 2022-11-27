package src.users;

import src.clinic.Clinic;
import src.db.client.DBClient;
import src.db.repository.DoctorsRepository;
import src.db.repository.ScheduleRepository;
import src.db.tables.DoctorsTable;
import src.db.tables.ScheduleTable;
import src.schedule.Schedule;
import src.visit.Status;
import src.visit.Visit;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.ArrayList;

public class Doctor extends User {
    private ArrayList<Clinic> doctorClinics;
    private ArrayList<Schedule> doctorSchedules;
    private ArrayList<Visit> doctorVisits;
    private final DBClient dbClientAutoCommit;

    public Doctor(String name, String surname, String address, String city, int phoneNumber, String email, String password) {
        super(name, surname, address, city, phoneNumber, email, password, Permissions.DOCTOR);
        this.doctorClinics = new ArrayList<Clinic>();
        this.doctorSchedules = new ArrayList<Schedule>();
        this.doctorVisits = new ArrayList<Visit>();
        try {
            dbClientAutoCommit = new DBClient(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void commitChanges() {

        for (Clinic x:doctorClinics
             ) {
            DoctorsTable doctor = new DoctorsTable(userId, x.getClinicId());
            try

            {
                DBClient dbClientAutoCommit = new DBClient(true);
                DoctorsRepository doctorsRepository = new DoctorsRepository(dbClientAutoCommit);
                doctorId = doctorsRepository.insertDoctor(doctor); // nie wiem o co chodzi
                doctor.setDoctorId(userId);
                System.out.println(doctorsRepository.getAllDoctors());
            } catch(SQLException e)
            {
                throw new RuntimeException(e);
            }
        }

        for (Schedule x:doctorSchedules
        ) {
            ScheduleTable schedule = new ScheduleTable(userId, x.getClinicId(),x.getDay(),x.getStartTime(),x.getEndTime());
            try
            {
                DBClient dbClientAutoCommit = new DBClient(true);
                ScheduleRepository scheduleRepository = new ScheduleRepository(dbClientAutoCommit);
                scheduleRepository.insertSchedule(schedule); // nie wiem o co chodzi
                System.out.println(scheduleRepository.getAllSchedules());

            } catch(SQLException e)
            {
                throw new RuntimeException(e);
            }
        }
    }
public void addClinic(Clinic clinic)
{
    doctorClinics.add(clinic);
}
    public void removeClinic(Clinic clinic)
    {
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
            ///TODO
        }
    }



public void cancelVisit(Visit visit)
{
    visit.setStatus(Status.CANCELED);
}
public void postponeVisit(Visit visit,Date date,Time startTime,Time endTime)
    {
        visit.setDate(date);
        visit.setStartTime(startTime);
        visit.setEndTime(startTime);
    }
    public void completeVisit(Visit visit)
    {
        visit.setStatus(Status.COMPLETED);
    }

    public void addToSchedule( int clinicId, DayOfWeek day, Time startTime, Time endTime)
    {
        boolean exists=false;
        for (Schedule x:doctorSchedules) {
            if(day==x.getDay() && ((startTime<x.getEndTime() && startTime>x.getStartTime())||(endTime<x.getEndTime() && endTime>x.getStartTime())))
            {
                exists=true;
                break;
            }
        }
        if(!exists)
        {
            doctorSchedules.add(new Schedule(userID,clinicId,day,startTime,endTime));
        }
    }

    public void removeFromSchedule( Schedule schedule)
    {
        ScheduleRepository x= new ScheduleRepository(dbClientAutoCommit);
        x.deleteSchedule(doctorId,clinicId,day) ///TODO bo jest niedokończone
    };

    public void addVisit(Visit visit)
    {
        doctorVisits.add(visit);
    }
}
