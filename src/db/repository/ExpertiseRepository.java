package src.db.repository;

import src.db.client.DBClient;
import src.db.entities.ExpertiseEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ExpertiseRepository extends Repository {
    public ExpertiseRepository(DBClient client) {
        super(client);
    }

    public ArrayList<ExpertiseEntity> getAllExpertises() {
        String query = "SELECT * FROM expertise";
        ArrayList<ExpertiseEntity> expertises = new ArrayList<>();
        try (Statement stmt = client.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                expertises.add(new ExpertiseEntity(
                        rs.getInt("doctor_id"),
                        rs.getString("area_of_expertise")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return expertises;
    }

    public ArrayList<ExpertiseEntity> getExpertiseByDoctorId(int doctorId) {
        String query = "SELECT * FROM expertise WHERE doctor_id = ?";
        ArrayList<ExpertiseEntity> expertises = new ArrayList<>();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                expertises.add(new ExpertiseEntity(
                        rs.getInt("doctor_id"),
                        rs.getString("area_of_expertise")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return expertises;
    }

    public ArrayList<ExpertiseEntity> getExpertisesByArea(String areaOfExpertise) {
        String query = "SELECT * FROM expertise WHERE area_of_expertise = ?";
        ArrayList<ExpertiseEntity> expertises = new ArrayList<>();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setString(1, areaOfExpertise);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                expertises.add(new ExpertiseEntity(
                        rs.getInt("doctor_id"),
                        rs.getString("area_of_expertise")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return expertises;
    }

    public void insertExpertise(ExpertiseEntity expertise) {
        String query = "INSERT INTO expertise(doctor_id,area_of_expertise) VALUES (?,?)";
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, expertise.getDoctorId());
            stmt.setString(2, expertise.getAreaOfExpertise());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteExpertise(int doctorId) {
        String query = "DELETE FROM expertise where doctor_id = ?";
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteExpertise(int doctorId, String areaOfExpertise) {
        String query = "DELETE FROM expertise where doctor_id = ? and area_of_expertise = ?";
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            stmt.setString(2, areaOfExpertise);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
