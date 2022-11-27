package src.schedule;

import src.clinic.Clinic;
import src.db.client.DBClient;
import src.db.repository.DoctorsRepository;
import src.db.tables.DoctorsTable;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;

public class Schedule {
    private int doctorId;
    private int clinicId;
    private DayOfWeek day; // jednostki czasu
    private LocalTime startTime;// jednostki czasu
    private LocalTime endTime;// jednostki czasu


    public Schedule(int doctorId,int clinicId,DayOfWeek day, LocalTime startTime, LocalTime endTime)
    {

    }








}
