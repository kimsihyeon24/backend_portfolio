package org.example.board.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
    private static final String URL = DbConfig.URL;
    private static final String USER = DbConfig.USER;
    private static final String PASS = DbConfig.PASS;

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver class not found", e);
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
