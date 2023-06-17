package github.io.ylongo.ch14.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static Connection connection;

    public static Connection getConnection() {
        return connection;
    }

    public static Connection openConnection() {

        try {
            Class.forName("org.h2.Driver"); // this is driver for H2

            // 默认的账号密码
            connection = DriverManager.getConnection("jdbc:h2:~/passenger", "sa", "");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection() {
        if (null != connection) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}