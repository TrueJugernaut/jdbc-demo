package connection;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
    public static final String URL = "jdbc:mysql://localhost:3306/developers    ";
    public static final String USER = "root";
    public static final String PASS = "Confection1";

    public static Connection getConnection() {
        try {
            DriverManager.registerDriver(new Driver());
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException ex) {
            throw new RuntimeException("Error connecting to the database", ex);
        }
    }

}
