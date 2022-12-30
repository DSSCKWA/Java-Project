package src.visit;


import src.db.entities.VisitEntity;
import src.users.Doctor;
import src.users.Patient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Visit {
    private int visitId;
    private LocalDate date;
    private LocalTime time;
    private int duration;
    private Doctor doctor;
    private Patient patient;
    private int rating;
    private VisitStatus visitStatus;

    public Visit(int visitId, LocalDate date, LocalTime time, int duration, Doctor doctor, Patient patient, int rating, VisitStatus visitStatus) {
        this.visitId = visitId;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.doctor = doctor;
        this.patient = patient;
        this.rating = rating;
        this.visitStatus = visitStatus;
    }

    public Visit(LocalDate date, LocalTime time, int duration, Doctor doctor, Patient patient, int rating, VisitStatus visitStatus) {
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.doctor = doctor;
        this.patient = patient;
        this.rating = rating;
        this.visitStatus = visitStatus;
    }

    public int getVisitId() {
        return visitId;
    }

    public void setVisitId(int visitId) {
        this.visitId = visitId;
    }

    public VisitStatus getVisitStatus() {
        return visitStatus;
    }

    public void setVisitStatus(VisitStatus visitStatus) {
        this.visitStatus = visitStatus;
    }

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
        if (rating < 1) rating = 1;
        else if (rating > 5) rating = 5;
        this.rating = rating;
    }

    public VisitStatus getStatus() {
        return visitStatus;
    }

    public void setStatus(VisitStatus visitStatus) {
        this.visitStatus = visitStatus;
    }

    public VisitEntity visitToVisitEntity() {
        return new VisitEntity(this.visitStatus,
                this.date,
                this.time,
                this.duration,
                this.patient.getId(),
                this.doctor.getId(),
                this.rating);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visit visit = (Visit) o;
        return duration == visit.duration && rating == visit.rating && Objects.equals(date, visit.date) && Objects.equals(time, visit.time) && Objects.equals(doctor, visit.doctor) && Objects.equals(patient, visit.patient) && visitStatus == visit.visitStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time, duration, doctor, patient, rating, visitStatus);
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
