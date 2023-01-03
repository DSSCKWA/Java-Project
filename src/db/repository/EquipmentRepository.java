package src.db.repository;

import src.db.client.DBClient;
import src.db.entities.EquipmentEntity;
import src.equipment.Equipment;
import src.equipment.EquipmentStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Locale;

public class EquipmentRepository extends Repository {

    public EquipmentRepository(DBClient client) {
        super(client);
    }

    public Equipment toEquipment(EquipmentEntity equipmentEntity) {
        return new Equipment(
                equipmentEntity.getEquipmentId(),
                equipmentEntity.getName(),
                equipmentEntity.getStatus(),
                equipmentEntity.getClinicId()
        );
    }

    public ArrayList<Equipment> toEquipmentList(ArrayList<EquipmentEntity> equipmentEntities) {
        ArrayList<Equipment> equipmentList = new ArrayList<>();
        for (EquipmentEntity equipment : equipmentEntities) {
            equipmentList.add(toEquipment(equipment));
        }
        return equipmentList;
    }

    public ArrayList<EquipmentEntity> getAllEquipment() {
        String query = "SELECT * FROM equipment";
        ArrayList<EquipmentEntity> equipment = new ArrayList<>();
        try (Statement stmt = client.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                equipment.add(new EquipmentEntity(
                        rs.getInt("equipment_id"),
                        rs.getString("name"),
                        EquipmentStatus.valueOf(rs.getString("status").toUpperCase(Locale.ROOT)),
                        rs.getInt("clinic_id")
                ));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return equipment;
    }

    public ArrayList<EquipmentEntity> getEquipmentByName(String name) {
        String query = "SELECT * FROM equipment WHERE name = ?";
        ArrayList<EquipmentEntity> equipment = new ArrayList<>();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                equipment.add(new EquipmentEntity(
                        rs.getInt("equipment_id"),
                        rs.getString("name"),
                        EquipmentStatus.valueOf(rs.getString("status").toUpperCase(Locale.ROOT)),
                        rs.getInt("clinic_id")
                ));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return equipment;
    }

    public EquipmentEntity getEquipmentById(int equipmentId) {
        String query = "SELECT * FROM equipment WHERE equipment_id = ?";
        EquipmentEntity equipment = new EquipmentEntity();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, equipmentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                equipment.setClinicId(rs.getInt("clinic_id"));
                equipment.setEquipmentId(rs.getInt("equipment_id"));
                equipment.setName(rs.getString("name"));
                equipment.setStatus(EquipmentStatus.valueOf(rs.getString("status").toUpperCase(Locale.ROOT)));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return equipment;
    }

    public ArrayList<EquipmentEntity> getEquipmentByClinicId(int clinicId) {
        String query = "SELECT * FROM equipment WHERE clinic_id = ?";
        ArrayList<EquipmentEntity> equipment = new ArrayList<>();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, clinicId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                equipment.add(new EquipmentEntity(
                        rs.getInt("equipment_id"),
                        rs.getString("name"),
                        EquipmentStatus.valueOf(rs.getString("status").toUpperCase(Locale.ROOT)),
                        rs.getInt("clinic_id")
                ));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return equipment;
    }

    public int insertEquipment(EquipmentEntity equipment) {
        String query = "INSERT INTO equipment(name,status,clinic_id) VALUES (?,?,?)";
        int equipmentId = 0;
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setString(1, equipment.getName());
            stmt.setString(2, equipment.getStatus().toString().toLowerCase(Locale.ROOT));
            stmt.setInt(3, equipment.getClinicId());
            int rowAffected = stmt.executeUpdate();
            if (rowAffected == 1) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    equipmentId = rs.getInt(1);
                }
                rs.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return equipmentId;
    }

    public void updateEquipment(EquipmentEntity equipment) {
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
