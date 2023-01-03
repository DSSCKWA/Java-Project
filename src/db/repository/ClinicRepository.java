package src.db.repository;

import src.clinic.Clinic;
import src.db.client.DBClient;
import src.db.entities.ClinicEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ClinicRepository extends Repository {
    public ClinicRepository(DBClient client) {
        super(client);
    }

    public Clinic toClinic(ClinicEntity clinicEntity) {
        return new Clinic(
                clinicEntity.getClinicId(),
                clinicEntity.getName(),
                clinicEntity.getAddress(),
                clinicEntity.getCity()
        );
    }

    public ArrayList<Clinic> toClinicList(ArrayList<ClinicEntity> clinicEntities) {
        ArrayList<Clinic> clinics = new ArrayList<>();
        for (ClinicEntity clinic : clinicEntities) {
            clinics.add(toClinic(clinic));
        }
        return clinics;
    }

    public ArrayList<ClinicEntity> getAllClinics() {
        String query = "SELECT * FROM clinics";
        ArrayList<ClinicEntity> clinics = new ArrayList<>();
        try (Statement stmt = client.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                clinics.add(new ClinicEntity(
                        rs.getInt("clinic_id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("city")
                ));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clinics;
    }

    public ArrayList<ClinicEntity> getClinicsByAddress(String address) {
        String query = "SELECT * FROM clinics WHERE address = ?";
        ArrayList<ClinicEntity> clinics = new ArrayList<>();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setString(1, address);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                clinics.add(new ClinicEntity(
                        rs.getInt("clinic_id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("city")
                ));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clinics;
    }

    public ArrayList<ClinicEntity> getClinicsByCity(String city) {
        String query = "SELECT * FROM clinics WHERE city = ?";
        ArrayList<ClinicEntity> clinics = new ArrayList<>();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setString(1, city);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                clinics.add(new ClinicEntity(
                        rs.getInt("clinic_id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("city")
                ));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clinics;
    }

    public ClinicEntity getClinic(int clinicId) {
        String query = "SELECT * FROM clinics WHERE clinic_id = ?";
        ClinicEntity clinic = new ClinicEntity();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, clinicId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                clinic.setClinicId(rs.getInt("clinic_id"));
                clinic.setName(rs.getString("name"));
                clinic.setAddress(rs.getString("address"));
                clinic.setCity(rs.getString("city"));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clinic;
    }

    public int insertClinic(ClinicEntity clinic) {
        String query = "INSERT INTO clinics(name,address,city) VALUES (?,?,?)";
        int clinicId = 0;
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setString(1, clinic.getName());
            stmt.setString(2, clinic.getAddress());
            stmt.setString(3, clinic.getCity());
            int rowAffected = stmt.executeUpdate();
            if (rowAffected == 1) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    clinicId = rs.getInt(1);
                }
                rs.close();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clinicId;
    }

    public void updateClinic(ClinicEntity clinic) {
        String query = "UPDATE clinics SET name = ?, address = ?, city = ? WHERE clinic_id = ?";
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setString(1, clinic.getName());
            stmt.setString(2, clinic.getAddress());
            stmt.setString(3, clinic.getCity());
            stmt.setInt(4, clinic.getClinicId());
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
