package src.db.repository;

import src.expertise.Expertise;
import src.db.client.DBClient;
import src.db.entities.ExpertiseEntity;

import java.sql.*;
import java.util.ArrayList;

public class ExpertiseRepository extends Repository {
    public ExpertiseRepository(DBClient client) {
        super(client);
    }

    public Expertise toExpertise(ExpertiseEntity expertiseEntity) {
        return new Expertise(
                expertiseEntity.getDoctorId(),
                expertiseEntity.getAreaOfExpertise()
        );
    }

    public ArrayList<Expertise> toExpertiseList(ArrayList<ExpertiseEntity> expertiseEntities) {
        ArrayList<Expertise> expertises = new ArrayList<>();
        for (ExpertiseEntity expertise : expertiseEntities) {
            expertises.add(toExpertise(expertise));
        }
        return expertises;
    }

    public ArrayList<ExpertiseEntity> getAllExpertises() {
        String query = "SELECT * FROM expertise";
        ArrayList<ExpertiseEntity> expertises = new ArrayList<>();
        try (Connection connection = client.getConnection(); Statement stmt = connection.createStatement()) {
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

    public ArrayList<ExpertiseEntity> getExpertise(int doctorId) {
        String query = "SELECT * FROM expertise WHERE doctor_id = ?";
        ArrayList<ExpertiseEntity> expertises = new ArrayList<>();
        try (Connection connection = client.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
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

    public ArrayList<ExpertiseEntity> getExpertise(String areaOfExpertise) {
        String query = "SELECT * FROM expertise WHERE area_of_expertise = ?";
        ArrayList<ExpertiseEntity> expertises = new ArrayList<>();
        try (Connection connection = client.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
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

    public ExpertiseEntity getExpertise(int doctorId, String areaOfExpertise) {
        String query = "SELECT * FROM expertise WHERE doctor_id = ? AND area_of_expertise = ?";
        ExpertiseEntity expertise = new ExpertiseEntity();
        try (Connection connection = client.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            stmt.setString(2, areaOfExpertise);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                expertise.setDoctorId(rs.getInt("doctor_id"));
                expertise.setAreaOfExpertise(rs.getString("area_of_expertise"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return expertise;
    }

    public void insertExpertise(ExpertiseEntity expertise) {
        String query = "INSERT INTO expertise(doctor_id,area_of_expertise) VALUES (?,?)";
        try (Connection connection = client.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, expertise.getDoctorId());
            stmt.setString(2, expertise.getAreaOfExpertise());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteExpertise(int doctorId) {
        String query = "DELETE FROM expertise where doctor_id = ?";
        try (Connection connection = client.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteExpertise(int doctorId, String areaOfExpertise) {
        String query = "DELETE FROM expertise where doctor_id = ? and area_of_expertise = ?";
        try (Connection connection = client.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            stmt.setString(2, areaOfExpertise);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteExpertise(String areaOfExpertise) {
        String query = "DELETE FROM expertise where area_of_expertise = ?";
        try (Connection connection = client.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, areaOfExpertise);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
