package src.db.entities;

import src.visit.Status;

import java.time.LocalDate;
import java.time.LocalTime;

public class VisitEntity {
    private Status status;
    private LocalDate date;
    private LocalTime time;
    private int duration;
    private int clientId;
    private int doctorId;
    private int rating;

    public VisitEntity(Status status, LocalDate date, LocalTime time, int duration, int clientId, int doctorId, int rating) {
        this.status = status;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.clientId = clientId;
        this.doctorId = doctorId;
        this.rating = rating;
    }

    public VisitEntity() {
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "VisitsEntity{" +
                "status=" + status +
                ", date=" + date +
                ", time=" + time +
                ", duration=" + duration +
                ", clientId=" + clientId +
                ", doctorId=" + doctorId +
                ", rating=" + rating +
                '}';
    }
}
