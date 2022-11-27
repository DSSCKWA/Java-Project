package src.visit;

import src.users.Doctor;
import src.users.Patient;
import src.schedule.Schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Visit {

    private LocalDate date;
    private LocalTime time;
    private int duration;
    private Doctor doctor;
    private Patient patient;
    private int rating;
    private Status status;

    private Schedule schedule;

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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating < 1)rating = 1;
        else if (rating > 5) rating = 5;
        this.rating = rating;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visit visit = (Visit) o;
        return duration == visit.duration && rating == visit.rating && Objects.equals(date, visit.date) && Objects.equals(time, visit.time) && Objects.equals(doctor, visit.doctor) && Objects.equals(patient, visit.patient) && status == visit.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time, duration, doctor, patient, rating, status);
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
