package src.db.entities;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class ScheduleEntity {
    private int doctorId;
    private int clinicId;
    private DayOfWeek day;
    private LocalTime startHour;
    private LocalTime endHour;

    public ScheduleEntity(int doctorId, int clinicId, DayOfWeek day, LocalTime startHour, LocalTime endHour) {
        this.doctorId = doctorId;
        this.clinicId = clinicId;
        this.day = day;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public ScheduleEntity() {
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getClinicId() {
        return clinicId;
    }

    public void setClinicId(int clinicId) {
        this.clinicId = clinicId;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public LocalTime getStartHour() {
        return startHour;
    }

    public void setStartHour(LocalTime startHour) {
        this.startHour = startHour;
    }

    public LocalTime getEndHour() {
        return endHour;
    }

    public void setEndHour(LocalTime endHour) {
        this.endHour = endHour;
    }

    @Override
    public String toString() {
        return "ScheduleEntity{" +
                "doctorId=" + doctorId +
                ", clinicId=" + clinicId +
                ", day=" + day +
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                '}';
    }
}
