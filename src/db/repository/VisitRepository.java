package src.db.repository;

import src.db.client.DBClient;
import src.db.entities.*;
import src.users.Patient;
import src.visit.Visit;
import src.visit.VisitStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;

public class VisitRepository extends Repository {
    public VisitRepository(DBClient client) {
        super(client);
    }

    public Visit toVisit(VisitEntity visitEntity) {
        DoctorRepository doctorRepository = new DoctorRepository(client);
        UserRepository userRepository = new UserRepository(client);
        return new Visit(
                visitEntity.getVisitId(),
                visitEntity.getDate(),
                visitEntity.getTime(),
                visitEntity.getDuration(),
                doctorRepository.toDoctor(visitEntity.getDoctorId()),
                new Patient(userRepository.toUser(userRepository.getUser(visitEntity.getClientId()))),
                visitEntity.getRating(),
                visitEntity.getStatus()
        );
    }

    public ArrayList<Visit> toVisitList(ArrayList<VisitEntity> visitEntities) {
        ArrayList<Visit> visits = new ArrayList<>();
        for (VisitEntity visitEntity : visitEntities) {
            visits.add(toVisit(visitEntity));
        }
        return visits;
    }

    public ArrayList<VisitEntity> getAllVisits() {
        String query = "SELECT * FROM visits";
        ArrayList<VisitEntity> visits = new ArrayList<>();
        try (Statement stmt = client.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                visits.add(new VisitEntity(
                        rs.getInt("visit_id"),
                        VisitStatus.valueOf(rs.getString("status").toUpperCase(Locale.ROOT)),
                        rs.getDate("date").toLocalDate(),
                        rs.getTime("time").toLocalTime(),
                        rs.getInt("duration"),
                        rs.getInt("client_id"),
                        rs.getInt("doctor_id"),
                        rs.getInt("rating")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return visits;
    }

    public ArrayList<VisitEntity> getAllUncompletedVisits() {
        String query = "SELECT * FROM visits where status != 'completed'";
        ArrayList<VisitEntity> visits = new ArrayList<>();
        try (Statement stmt = client.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                visits.add(new VisitEntity(
                        rs.getInt("visit_id"),
                        VisitStatus.valueOf(rs.getString("status").toUpperCase(Locale.ROOT)),
                        rs.getDate("date").toLocalDate(),
                        rs.getTime("time").toLocalTime(),
                        rs.getInt("duration"),
                        rs.getInt("client_id"),
                        rs.getInt("doctor_id"),
                        rs.getInt("rating")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return visits;
    }

    public VisitEntity getVisitByVisitId(int visitId) {
        String query = "SELECT * FROM visits WHERE visit_id = ?";
        VisitEntity visit = new VisitEntity();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, visitId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                visit.setVisitId(rs.getInt("visit_id"));
                visit.setStatus(VisitStatus.valueOf(rs.getString("status").toUpperCase(Locale.ROOT)));
                visit.setDate(rs.getDate("date").toLocalDate());
                visit.setTime(rs.getTime("time").toLocalTime());
                visit.setDuration(rs.getInt("duration"));
                visit.setRating(rs.getInt("rating"));
                visit.setClientId(rs.getInt("client_id"));
                visit.setDoctorId(rs.getInt("doctor_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return visit;
    }

    public VisitEntity getVisit(int doctorId, int clientId, LocalDate date, LocalTime time) {
        String query = "SELECT * FROM visits WHERE doctor_id = ? and client_id = ? and date = ? and time = ?";
        VisitEntity visit = new VisitEntity();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            stmt.setInt(2, clientId);
            stmt.setDate(3, java.sql.Date.valueOf(date));
            stmt.setTime(4, java.sql.Time.valueOf(time));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                visit.setVisitId(rs.getInt("visit_id"));
                visit.setStatus(VisitStatus.valueOf(rs.getString("status").toUpperCase(Locale.ROOT)));
                visit.setDate(rs.getDate("date").toLocalDate());
                visit.setTime(rs.getTime("time").toLocalTime());
                visit.setDuration(rs.getInt("duration"));
                visit.setRating(rs.getInt("rating"));
                visit.setClientId(rs.getInt("client_id"));
                visit.setDoctorId(rs.getInt("doctor_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return visit;
    }

    public ArrayList<VisitEntity> getVisitsByClientId(int clientId) {
        String query = "SELECT * FROM visits WHERE client_id = ?";
        ArrayList<VisitEntity> visits = new ArrayList<>();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                visits.add(new VisitEntity(
                        rs.getInt("visit_id"),
                        VisitStatus.valueOf(rs.getString("status").toUpperCase(Locale.ROOT)),
                        rs.getDate("date").toLocalDate(),
                        rs.getTime("time").toLocalTime(),
                        rs.getInt("duration"),
                        rs.getInt("client_id"),
                        rs.getInt("doctor_id"),
                        rs.getInt("rating")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return visits;
    }

    public ArrayList<VisitEntity> getVisitsByDoctorId(int doctorId) {
        String query = "SELECT * FROM visits WHERE doctor_id = ?";
        ArrayList<VisitEntity> visits = new ArrayList<>();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                visits.add(new VisitEntity(
                        rs.getInt("visit_id"),
                        VisitStatus.valueOf(rs.getString("status").toUpperCase(Locale.ROOT)),
                        rs.getDate("date").toLocalDate(),
                        rs.getTime("time").toLocalTime(),
                        rs.getInt("duration"),
                        rs.getInt("client_id"),
                        rs.getInt("doctor_id"),
                        rs.getInt("rating")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return visits;
    }

    public int insertVisit(VisitEntity visit) {
        String query = "INSERT INTO visits(status,date,time,duration,client_id,doctor_id,rating) VALUES (?,?,?,?,?,?,?)";
        int visitId = 0;
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setString(1, visit.getStatus().toString().toLowerCase(Locale.ROOT));
            stmt.setDate(2, java.sql.Date.valueOf(visit.getDate()));
            stmt.setTime(3, java.sql.Time.valueOf(visit.getTime()));
            stmt.setInt(4, visit.getDuration());
            stmt.setInt(5, visit.getClientId());
            stmt.setInt(6, visit.getDoctorId());
            stmt.setInt(7, visit.getRating());
            int rowAffected = stmt.executeUpdate();
            if (rowAffected == 1) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    visitId = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return visitId;
    }

    public void updateVisit(VisitEntity visit) {
        String query = "UPDATE visits SET status = ?, date = ?, time = ?, duration = ?, rating = ? WHERE visit_id = ?";
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setString(1, visit.getStatus().toString().toLowerCase(Locale.ROOT));
            stmt.setDate(2, java.sql.Date.valueOf(visit.getDate()));
            stmt.setTime(3, java.sql.Time.valueOf(visit.getTime()));
            stmt.setInt(4, visit.getDuration());
            stmt.setInt(5, visit.getRating());
            stmt.setInt(6, visit.getVisitId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
