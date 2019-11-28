/*
Created by: Taymoor Ghazanfar
R.no: 3625-BSSE-F17-C
Date: 28-Nov-19
Time: 1:28 AM
Lau ji Ghauri aya fir
*/

package Database;

import Model.Lecturer;
import Model.SecurityGuard;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeManager {

    private static final String databasePath = "C:\\IT_Projects\\ACP\\assignment_1\\employees.accdb";
    public EmployeeManager() {}

    public void insertLecturer(Lecturer lecturer) {

        String query = "INSERT INTO Lecturer (age, lecturer_name, basic_salary, course_rate, total_courses)" +
                " VALUES (?, ?, ?, ?, ?);";

        try {
            PreparedStatement statement = DatabaseManager.getConnection(databasePath).prepareStatement(query);
            statement.setInt(1, lecturer.getAge());
            statement.setString(2, lecturer.getName());
            statement.setFloat(3, lecturer.getBasicSalary());
            statement.setFloat(4, lecturer.getCourseRate());
            statement.setInt(5, lecturer.getTotalCourses());

            System.out.println(statement.executeUpdate() + "row(s) affected");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DatabaseManager.closeConnection();
        }
    }

    public void insertSecurityGuard(SecurityGuard securityGuard) {

        String query = "INSERT INTO Security_guard (age, guard_name, basic_salary, hourly_rate, total_hours)" +
                " VALUES (?, ?, ?, ?, ?);";

        try {
            PreparedStatement statement = DatabaseManager.getConnection(databasePath).prepareStatement(query);
            statement.setInt(1, securityGuard.getAge());
            statement.setString(2, securityGuard.getName());
            statement.setFloat(3, securityGuard.getBasicSalary());
            statement.setFloat(4, securityGuard.getHourlyRate());
            statement.setInt(5, securityGuard.getTotalHours());

            System.out.println(statement.executeUpdate() + "row(s) affected");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DatabaseManager.closeConnection();
        }
    }

    public Lecturer getLecturer(int id){

        String query = "SELECT * FROM Lecturer WHERE id = ?";

        try {
            PreparedStatement statement = DatabaseManager.getConnection(databasePath).prepareStatement(query);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            Lecturer lecturer = new Lecturer();
            while (resultSet.next()){

                lecturer.setAge(resultSet.getInt("age"));
                lecturer.setName(resultSet.getString("lecturer_name"));
                lecturer.setBasicSalary(resultSet.getFloat("basic_salary"));
                lecturer.setCourseRate(resultSet.getFloat("course_rate"));
                lecturer.setTotalCourses(resultSet.getInt("total_courses"));
            }

            resultSet.close();
            return lecturer;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DatabaseManager.closeConnection();
        }
        return null;
    }

    public SecurityGuard getSecurityGuard(int id){

        String query = "SELECT * FROM Security_guard WHERE id = ?";

        try {
            PreparedStatement statement = DatabaseManager.getConnection(databasePath).prepareStatement(query);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            SecurityGuard security_guard = new SecurityGuard();
            while (resultSet.next()){

                security_guard.setAge(resultSet.getInt("age"));
                security_guard.setName(resultSet.getString("guard_name"));
                security_guard.setBasicSalary(resultSet.getFloat("basic_salary"));
                security_guard.setHourlyRate(resultSet.getFloat("hourly_rate"));
                security_guard.setTotalHours(resultSet.getInt("total_hours"));
            }

            resultSet.close();
            return security_guard;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DatabaseManager.closeConnection();
        }
        return null;
    }

    public StringBuilder getAllLecturer() {

        StringBuilder data = new StringBuilder();
        String query = "SELECT * FROM Lecturer";

        try {
            PreparedStatement statement = DatabaseManager.getConnection(databasePath).prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){

                Lecturer lecturer = new Lecturer();
                lecturer.setAge(resultSet.getInt("age"));
                lecturer.setName(resultSet.getString("lecturer_name"));
                lecturer.setBasicSalary(resultSet.getFloat("basic_salary"));
                lecturer.setCourseRate(resultSet.getFloat("course_rate"));
                lecturer.setTotalCourses(resultSet.getInt("total_courses"));
                data.append(lecturer.toString()).append("\n");
            }

            resultSet.close();
            return data;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DatabaseManager.closeConnection();
        }
        return null;
    }

    public StringBuilder getAllSecurityGuard(){

        StringBuilder data = new StringBuilder();
        String query = "SELECT * FROM Security_guard";

        try {
            PreparedStatement statement = DatabaseManager.getConnection(databasePath).prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){

                SecurityGuard securityGuard = new SecurityGuard();
                securityGuard.setAge(resultSet.getInt("age"));
                securityGuard.setName(resultSet.getString("guard_name"));
                securityGuard.setBasicSalary(resultSet.getFloat("basic_salary"));
                securityGuard.setHourlyRate(resultSet.getFloat("hourly_rate"));
                securityGuard.setTotalHours(resultSet.getInt("total_hours"));
                data.append(securityGuard.toString()).append("\n");
            }

            return data;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {

            DatabaseManager.closeConnection();
        }
        return null;
    }

    public void updateLecturer(int id, Lecturer lecturer){

        String query = "UPDATE Lecturer SET " +
                "age = ?, lecturer_name = ?, basic_salary = ?, course_rate = ?, total_courses = ? WHERE id = ?;";
        try {

            PreparedStatement statement = DatabaseManager.getConnection(databasePath).prepareStatement(query);
            statement.setInt(1, lecturer.getAge());
            statement.setString(2, lecturer.getName());
            statement.setFloat(3, lecturer.getBasicSalary());
            statement.setFloat(4, lecturer.getCourseRate());
            statement.setInt(5, lecturer.getTotalCourses());
            statement.setInt(6, id);

            System.out.println(statement.executeUpdate() + " row(s) affected");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DatabaseManager.closeConnection();
        }
    }

    public void updateSecurityGuard(int id, SecurityGuard securityGuard){

        String query = "UPDATE Security_guard SET " +
                "age = ?, guard_name = ?, basic_salary = ?, hourly_rate = ?, total_hours = ? WHERE id = ?;";
        try {

            PreparedStatement statement = DatabaseManager.getConnection(databasePath).prepareStatement(query);
            statement.setInt(1, securityGuard.getAge());
            statement.setString(2, securityGuard.getName());
            statement.setFloat(3, securityGuard.getBasicSalary());
            statement.setFloat(4, securityGuard.getHourlyRate());
            statement.setInt(5, securityGuard.getTotalHours());
            statement.setInt(6, id);

            System.out.println(statement.executeUpdate() + " row(s) affected");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DatabaseManager.closeConnection();
        }
    }

    public void deleteLecturer(int id){

        String query = "DELETE FROM Lecturer WHERE id = ?";

        try {
            PreparedStatement statement = DatabaseManager.getConnection(databasePath).prepareStatement(query);
            statement.setInt(1,id);
            System.out.println(statement.executeUpdate() + " row(s) affected");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DatabaseManager.closeConnection();
        }
    }

    public void deleteSecurityGuard(int id){

        String query = "DELETE FROM Security_guard WHERE id = ?";

        try {
            PreparedStatement statement = DatabaseManager.getConnection(databasePath).prepareStatement(query);
            statement.setInt(1,id);
            System.out.println(statement.executeUpdate() + " row(s) affected");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DatabaseManager.closeConnection();
        }
    }
}
