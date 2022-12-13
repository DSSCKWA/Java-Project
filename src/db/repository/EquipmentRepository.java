package src.db.repository;

import src.db.client.DBClient;
import src.db.tables.EquipmentTable;
import src.equipment.EquipmentStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Locale;

public class EquipmentRepository extends Repository{

    public EquipmentRepository(DBClient client) {
        super(client);
    }

    public ArrayList<EquipmentTable> getAllEquipment() {
        String query = "SELECT * FROM equipment";
        ArrayList<EquipmentTable> equipment = new ArrayList<>();
        try (Statement stmt = client.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                equipment.add(new EquipmentTable(
                        rs.getInt("equipment_id"),
                        rs.getString("name"),
                        EquipmentStatus.valueOf(rs.getString("status").toUpperCase(Locale.ROOT)),
                        rs.getInt("clinic_id")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return equipment;
    }

    public ArrayList<EquipmentTable> getEquipmentByName(String name) {
        String query = "SELECT * FROM equipment WHERE name = ?";
        ArrayList<EquipmentTable> equipment = new ArrayList<>();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                equipment.add(new EquipmentTable(
                        rs.getInt("equipment_id"),
                        rs.getString("name"),
                        EquipmentStatus.valueOf(rs.getString("status").toUpperCase(Locale.ROOT)),
                        rs.getInt("clinic_id")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return equipment;
    }

    public EquipmentTable getEquipmentById(int equipmentId) {
        String query = "SELECT * FROM equipment WHERE equipment_id = ?";
        EquipmentTable equipment = new EquipmentTable();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, equipmentId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                equipment.setClinicId(rs.getInt("clinic_id"));
                equipment.setEquipmentId(rs.getInt("equipment_id"));
                equipment.setName(rs.getString("name"));
                equipment.setStatus(EquipmentStatus.valueOf(rs.getString("status").toUpperCase(Locale.ROOT)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return equipment;
    }

    public ArrayList<EquipmentTable> getEquipmentByClinicId(int clinicId) {
        String query = "SELECT * FROM equipment WHERE clinic_id = ?";
        ArrayList<EquipmentTable> equipment = new ArrayList<>();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, clinicId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                equipment.add(new EquipmentTable(
                        rs.getInt("equipment_id"),
                        rs.getString("name"),
                        EquipmentStatus.valueOf(rs.getString("status").toUpperCase(Locale.ROOT)),
                        rs.getInt("clinic_id")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return equipment;
    }

    public int insertEquipment(EquipmentTable equipment) {
        String query = "INSERT INTO equipment(name,status,clinic_id) VALUES (?,?,?)";
        int equipmentId = 0;
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setString(1, equipment.getName());
            stmt.setString(2, equipment.getStatus().toString().toLowerCase(Locale.ROOT));
            stmt.setInt(3, equipment.getClinicId());
            int rowAffected = stmt.executeUpdate();
            if(rowAffected == 1) {
                ResultSet rs = stmt.getGeneratedKeys();
                if(rs.next()) {
                    equipmentId = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return equipmentId;
    }

    public void updateEquipment(EquipmentTable equipment) {
        String query = "UPDATE equipment SET name = ?, status = ?, clinic_id = ? WHERE equipment_id = ?";
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setString(1, equipment.getName());
            stmt.setString(2, equipment.getStatus().toString().toLowerCase(Locale.ROOT));
            stmt.setInt(3, equipment.getClinicId());
            stmt.setInt(4, equipment.getEquipmentId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteEquipmentById(int equipmentId) {
        String query = "DELETE FROM equipment where equipment_id = ?";
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, equipmentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
