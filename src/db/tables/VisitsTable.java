package src.db.tables;

import src.visit.Status;

import java.time.LocalDate;
import java.time.LocalTime;

public class VisitsTable {
    private Status status;
    private LocalDate date;
    private LocalTime time;
    private int duration;
    private int clientId;
    private int doctorId;
    private int rating;

    public VisitsTable(Status status, LocalDate date, LocalTime time, int duration, int clientId, int doctorId, int rating) {
        this.status = status;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.clientId = clientId;
        this.doctorId = doctorId;
        this.rating = rating;
    }

    public VisitsTable() {
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
        return "VisitsTable{" +
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
