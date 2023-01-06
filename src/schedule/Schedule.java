package src.schedule;

import src.db.client.DBClient;
import src.db.entities.ScheduleEntity;
import src.db.repository.ScheduleRepository;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

public class Schedule {
    private int doctorId;
    private int clinicId;
    private DayOfWeek day; // jednostki czasu
    private LocalTime startTime;// jednostki czasu
    private LocalTime endTime;// jednostki czasu
    private final transient DBClient dbClientAutoCommit;

    //<editor-fold desc="Getters">
    public int getDoctorId() {
        return doctorId;
    }

    public int getClinicId() {
        return clinicId;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
    //</editor-fold>

    //<editor-fold desc="Setters">
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public void setClinicId(int clinicId) {
        this.clinicId = clinicId;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public Schedule(int doctorId, int clinicId, DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        this.doctorId = doctorId;
        this.clinicId = clinicId;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        dbClientAutoCommit = new DBClient(true);
    }
    //</editor-fold>

    //<editor-fold desc="Equals & HashCode">
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return doctorId == schedule.doctorId && clinicId == schedule.clinicId && day == schedule.day && startTime.equals(schedule.startTime) && endTime.equals(schedule.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctorId, clinicId, day, startTime, endTime);
    }
    //</editor-fold>

    //<editor-fold desc="Database Handling">
    public void insertToDB() {
        ScheduleRepository scheduleRepository = new ScheduleRepository(dbClientAutoCommit);
        scheduleRepository.insertSchedule(new ScheduleEntity(doctorId, clinicId, day, startTime, endTime));
        System.out.println(scheduleRepository.getAllSchedules());
    }

    public void removeFromDB() {
        ScheduleRepository scheduleRepository = new ScheduleRepository(dbClientAutoCommit);
        scheduleRepository.deleteSchedule(doctorId, clinicId, day);
    }

    public void updateDB() {
        ScheduleRepository scheduleRepository = new ScheduleRepository(dbClientAutoCommit);
        scheduleRepository.updateSchedule(new ScheduleEntity(doctorId, clinicId, day, startTime, endTime));
    }
    //</editor-fold>

    //<editor-fold desc="ToString">
    @Override
    public String toString() {
        return "Schedule{" +
                "doctorId=" + doctorId +
                ", clinicId=" + clinicId +
                ", day=" + day +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
    //</editor-fold>
}
