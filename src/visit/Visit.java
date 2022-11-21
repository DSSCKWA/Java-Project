package src.visit;

import src.users.Doctor;
import src.users.Patient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

public class Visit {

    private LocalDate date;
    private LocalTime time;
    private int duration;
    private Doctor doctor;
    private Patient patient;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visit visit = (Visit) o;
        return Objects.equals(date, visit.date) && duration == visit.duration && time.equals(visit.time) && Objects.equals(doctor, visit.doctor) && Objects.equals(patient, visit.patient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time, duration, doctor, patient);
    }

    @Override
    public String toString() {
        return "Visit{" +
                "visitDate=" + date +
                ", doctor=" + doctor +
                ", patient=" + patient +
                ", time=" + time +
                ", duration=" + duration +
                '}';
    }
}
