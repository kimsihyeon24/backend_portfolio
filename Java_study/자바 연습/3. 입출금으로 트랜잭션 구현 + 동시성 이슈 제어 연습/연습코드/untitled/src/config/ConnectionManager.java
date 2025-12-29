package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    String url = DbConfig.url;
    String username = DbConfig.username;
    String password = DbConfig.password;

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

}
