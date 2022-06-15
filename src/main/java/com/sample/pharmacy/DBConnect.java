package com.sample.pharmacy;

import java.sql.*;

public class DBConnect {

    private static String connectionURL = "jdbc:postgresql://localhost:5432/pharmacy";
    private static String username = "postgres";
    private static String password = "niyazbek";

    public Connection getConnection() {

        try {
            Class.forName("org.postgresql.Driver");

            Connection con = DriverManager.getConnection(connectionURL, username, password);

            return con;
        } catch (Exception e) { // exception
            System.out.println(e);
            return null;
        }
    }
}
