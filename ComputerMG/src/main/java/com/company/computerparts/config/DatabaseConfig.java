package com.company.computerparts.config;
import com.company.computerparts.api.DatabaseApi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Computer?serverTimezone=UTC"; // 数据库地址
    private static final String DB_USER = "root"; // 数据库用户名
    private static final String DB_PASSWORD = "guozhihang"; //

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}