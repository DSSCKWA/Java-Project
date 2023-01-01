package src.db.entities;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleEntity that = (ScheduleEntity) o;
        return doctorId == that.doctorId && clinicId == that.clinicId && day == that.day && Objects.equals(startHour, that.startHour) && Objects.equals(endHour, that.endHour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctorId, clinicId, day, startHour, endHour);
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
