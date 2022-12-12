package src.db.repository;

import src.db.client.DBClient;
import src.db.tables.EquipmentTable;
import src.db.tables.ExpertiseTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EquipmentRepository extends Repository {
    public EquipmentRepository(DBClient client) {
        super(client);
    }

    public ArrayList<EquipmentTable> getAllEquipment() {
        return null;
    }

    public ArrayList<EquipmentTable> getEquipmentById(int equipmentId) {
        return null;
    }

    public void insertEquipment(EquipmentTable equipment) {
    }

    public void deleteExpertise(int equipmentId) {
    }

}
