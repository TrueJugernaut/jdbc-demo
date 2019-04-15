package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactory {
    public static final String URL = "jdbc:mysql://localhost:3306/jdbc_test?serverTimezone=UTC";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String USER = "root";
    public static final String PASS = "Confection1";
    private static Connection connection;

    static {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Something wrong with driver");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Something wrong with connection");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        System.out.println("Connected to DB");
        return connection;
    }

}
