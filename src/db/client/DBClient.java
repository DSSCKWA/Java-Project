package src.db.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBClient {
    public static final String connectionString= "jdbc:mysql://localhost:3306/medical_service";
    private static final String username= "root";
    private static final String password="";
    private final Connection connection;
    
    public DBClient(boolean autoCommit) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.connection = DriverManager.getConnection(connectionString, username,password);
        this.connection.setAutoCommit(autoCommit);
    }

    public Connection getConnection() {
        return connection;
    }

    public static void main(String[] args) {
        try {
            DBClient db = new DBClient(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
