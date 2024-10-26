package com.example.webForDB.login;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DBConnectHelper {

    @Value("${database_source}")
    private String dbSource;
    @Value("${database_connection_provider}")
    private String dbConnectionProvider;

    private static Connection connection;

    private static String username;
    private static String password;

    public boolean openConnection() throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            Class.forName(dbConnectionProvider);
            connection = DriverManager.getConnection(dbSource, username, password);
            //System.out.println("Connection opened successfully.");

            return connection.isValid(2);
        }
        return false;
    }

    public boolean openConnection(String username, String password) throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            DBConnectHelper.username = username;
            DBConnectHelper.password = password;

            Class.forName(dbConnectionProvider);
            connection = DriverManager.getConnection(dbSource, username, password);

            //System.out.println("Connection opened successfully.");

            return connection.isValid(2);
        }
        return false;
    }

    public boolean checkConnection() {
        try {
            if (openConnection()) {
                closeConnection();
                return true;
            }
        } catch (Exception ignored) {
        }

        return false;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                //System.out.println("Connection closed successfully.");
            } catch (SQLException e) {
                //e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
