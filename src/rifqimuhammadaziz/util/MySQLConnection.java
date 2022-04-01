package rifqimuhammadaziz.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/swing_database";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL versi 8
        // Class.forName("com.mysql.jdbc.Driver"); // MySQL versi 5
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        connection.setAutoCommit(false);
        return connection;
    }
}
