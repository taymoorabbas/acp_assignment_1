/*
Created by: Taymoor Ghazanfar
R.no: 3625-BSSE-F17-C
Date: 28-Nov-19
Time: 6:58 PM
Lau ji Ghauri aya fir
*/

package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static Connection connection;
    private static Statement statement;

    private static void createConnection(String databasePath) {

        try {
            System.out.println("Requesting for connection. . .");
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            connection = DriverManager.getConnection("jdbc:ucanaccess://" + databasePath);
            statement = connection.createStatement();
            System.out.println("Connection established");
        }
        catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public static Statement getStatement() {

        return statement;
    }

    public static Connection getConnection(String databasePath) {

        createConnection(databasePath);
        return connection;
    }

    public static void closeConnection() {

        try {
            connection.close();
            statement.close();
            System.out.println("Connection closed");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}