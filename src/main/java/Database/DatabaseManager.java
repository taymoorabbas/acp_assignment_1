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

public class DatabaseManager {

    private static Connection connection;

    private static void createConnection(String databasePath) {

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(databasePath);
        }
        catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public static Connection getConnection(String databasePath) {

        createConnection(databasePath);
        return connection;
    }

    public static void closeConnection() {

        try {
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}