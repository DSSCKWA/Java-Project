package src.db.repository;

import src.db.client.DBClient;
import src.db.tables.ClinicsTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ClinicRepository extends Repository {
    public ClinicRepository(DBClient client) {
        super(client);
    }

    public ArrayList<ClinicsTable> getAllClinics() {
        String query = "SELECT * FROM clinics";
        ArrayList<ClinicsTable> clinics = new ArrayList<>();
        try (Statement stmt = client.getConnection().createStatement()) {
           ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                clinics.add(new ClinicsTable(
                        rs.getInt("clinic_id"),
                        rs.getString("address"),
                        rs.getString("city")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clinics;
    }

    public ArrayList<ClinicsTable> getClinicsByAddress(String address) {
        String query = "SELECT * FROM clinics WHERE address = ?";
        ArrayList<ClinicsTable> clinics = new ArrayList<>();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setString(1, address);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                clinics.add(new ClinicsTable(
                        rs.getInt("clinic_id"),
                        rs.getString("address"),
                        rs.getString("city")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clinics;
    }

    public ArrayList<ClinicsTable> getClinicsByCity(String city) {
        String query = "SELECT * FROM clinics WHERE city = ?";
        ArrayList<ClinicsTable> clinics = new ArrayList<>();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setString(1, city);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                clinics.add(new ClinicsTable(
                        rs.getInt("clinic_id"),
                        rs.getString("address"),
                        rs.getString("city")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clinics;
    }

    public ClinicsTable getClinicById(int clinicId) {
        String query = "SELECT * FROM clinics WHERE clinic_id = ?";
        ClinicsTable clinic = new ClinicsTable();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, clinicId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                     clinic.setClinicId(rs.getInt("clinic_id"));
                     clinic.setAddress(rs.getString("address"));
                     clinic.setCity(rs.getString("city"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clinic;
    }

    public int insertClinic(ClinicsTable clinic) {
        String query = "INSERT INTO clinics(address,city) VALUES (?,?)";
        int clinicId = 0;
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setString(1, clinic.getAddress());
            stmt.setString(2, clinic.getCity());
            int rowAffected = stmt.executeUpdate();
            if(rowAffected == 1) {
               ResultSet rs = stmt.getGeneratedKeys();
               if(rs.next()) {
                   clinicId = rs.getInt(1);
               }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clinicId;
    }

    public void updateClinic(ClinicsTable clinic) {
        String query = "UPDATE clinics SET address = ?, city = ? WHERE clinic_id = ?";
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setString(1, clinic.getAddress());
            stmt.setString(2, clinic.getCity());
            stmt.setInt(3, clinic.getClinicId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteClinicById(int clinicId) {
        String query = "DELETE FROM clinics where clinic_id = ?";
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, clinicId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
