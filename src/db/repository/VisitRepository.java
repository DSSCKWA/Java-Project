package src.db.repository;

import src.db.client.DBClient;
import src.db.tables.VisitsTable;
import src.visit.Status;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Locale;

public class VisitRepository extends Repository{
    public VisitRepository(DBClient client) {
        super(client);
    }

    public ArrayList<VisitsTable> getAllVisits() {
        String query = "SELECT * FROM visits";
        ArrayList<VisitsTable> visits = new ArrayList<>();
        try (Statement stmt = client.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                visits.add(new VisitsTable(
                        Status.valueOf(rs.getString("status").toUpperCase(Locale.ROOT)),
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

    public ArrayList<VisitsTable> getVisitsByClientId(int clientId) {
        String query = "SELECT * FROM visits WHERE client_id = ?";
        ArrayList<VisitsTable> visits = new ArrayList<>();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                visits.add(new VisitsTable(
                        Status.valueOf(rs.getString("status").toUpperCase(Locale.ROOT)),
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

    public ArrayList<VisitsTable> getVisitsByDoctorId(int doctorId) {
        String query = "SELECT * FROM visits WHERE doctor_id = ?";
        ArrayList<VisitsTable> visits = new ArrayList<>();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                visits.add(new VisitsTable(
                        Status.valueOf(rs.getString("status").toUpperCase(Locale.ROOT)),
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

    public void insertVisit(VisitsTable visit) {
        String query = "INSERT INTO visits(status,date,time,duration,client_id,doctor_id,rating) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setString(1,visit.getStatus().toString().toLowerCase(Locale.ROOT));
            stmt.setDate(2, java.sql.Date.valueOf(visit.getDate()));
            stmt.setTime(3, java.sql.Time.valueOf(visit.getTime()));
            stmt.setInt(4, visit.getDuration());
            stmt.setInt(5, visit.getClientId());
            stmt.setInt(6, visit.getDoctorId());
            stmt.setInt(7, visit.getRating());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateVisit(VisitsTable visit) {
        String query = "UPDATE visits SET status = ?, date = ?, time = ?, duration = ?, rating = ? WHERE client_id = ? and doctor_id = ?";
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setString(1, visit.getStatus().toString().toLowerCase(Locale.ROOT));
            stmt.setDate(2, java.sql.Date.valueOf(visit.getDate()));
            stmt.setTime(3, java.sql.Time.valueOf(visit.getTime()));
            stmt.setInt(4, visit.getDuration());
            stmt.setInt(5, visit.getRating());
            stmt.setInt(6, visit.getClientId());
            stmt.setInt(7, visit.getDoctorId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
