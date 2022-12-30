package src.db.entities;

import src.visit.VisitStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class VisitEntity {
    private int visitId;
    private VisitStatus visitStatus;
    private LocalDate date;
    private LocalTime time;
    private int duration;
    private int clientId;
    private int doctorId;
    private int rating;

    public VisitEntity(int visitId, VisitStatus visitStatus, LocalDate date, LocalTime time, int duration, int clientId, int doctorId, int rating) {
        this.visitId = visitId;
        this.visitStatus = visitStatus;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.clientId = clientId;
        this.doctorId = doctorId;
        this.rating = rating;
    }

    public VisitEntity(VisitStatus visitStatus, LocalDate date, LocalTime time, int duration, int clientId, int doctorId, int rating) {
        this.visitStatus = visitStatus;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.clientId = clientId;
        this.doctorId = doctorId;
        this.rating = rating;
    }

    public VisitEntity() {
    }

    public int getVisitId() {
        return visitId;
    }

    public void setVisitId(int visitId) {
        this.visitId = visitId;
    }

    public VisitStatus getStatus() {
        return visitStatus;
    }

    public void setStatus(VisitStatus visitStatus) {
        this.visitStatus = visitStatus;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisitEntity that = (VisitEntity) o;
        return visitId == that.visitId && duration == that.duration && clientId == that.clientId && doctorId == that.doctorId && rating == that.rating && visitStatus == that.visitStatus && Objects.equals(date, that.date) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(visitId, visitStatus, date, time, duration, clientId, doctorId, rating);
    }

    @Override
    public String toString() {
        return "VisitsEntity{" +
                "status=" + visitStatus +
                ", date=" + date +
                ", time=" + time +
                ", duration=" + duration +
                ", clientId=" + clientId +
                ", doctorId=" + doctorId +
                ", rating=" + rating +
                '}';
    }
}
