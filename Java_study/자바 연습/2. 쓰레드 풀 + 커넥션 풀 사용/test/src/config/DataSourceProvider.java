package config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceProvider {

    private static final String URL = DbConfig.URL;
    private static final String USER = DbConfig.USER;
    private static final String PASSWORD = DbConfig.PASSWORD;

    private final HikariDataSource dataSource;

    public DataSourceProvider() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);

        config.setMaximumPoolSize(2);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(10000);

        this.dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close() {
        dataSource.close();
    }
}
