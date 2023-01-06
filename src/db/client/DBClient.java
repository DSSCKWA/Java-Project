package src.db.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBClient {
    public static final String connectionString = "jdbc:mysql://localhost:3306/medical_service";
    private static final String username = "root";
    private static final String password = "";
    private static boolean autoCommit = true;

    public DBClient(boolean autoCommit) {
        DBClient.autoCommit = autoCommit;
    }

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Connection connection = DriverManager.getConnection(connectionString, username, password);
        connection.setAutoCommit(autoCommit);
        return connection;
    }

    public static void main(String[] args) {
        DBClient db = new DBClient(true);
    }
}
