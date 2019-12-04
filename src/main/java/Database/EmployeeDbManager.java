/*
Created by: Taymoor Ghazanfar
R.no: 3625-BSSE-F17-C
Date: 28-Nov-19
Time: 1:28 AM
Lau ji Ghauri aya fir
*/

package Database;

import Model.Employee;
import Model.Lecturer;
import Model.SecurityGuard;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static Utils.DatabaseConstants.*;

public class EmployeeDbManager {

    private static PreparedStatement statement;
    private static ResultSet resultSet;
    private static String query;

    /******************************************EMPLOYEE QUERIES********************************************************/

    private static int insertEmployee(String name, int age, float salary){

        query = "INSERT INTO " + employeeTableLabel + " VALUES (?, ?, ?)";

        try {
            statement = DatabaseManager
                    .getConnection(databasePath)
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setFloat(3, salary);

            if(statement.executeUpdate() != 0){

                int key = -1;
                resultSet = statement.getGeneratedKeys();

                if(resultSet.next()){

                    key = resultSet.getInt(1);
                }
                return key;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (resultSet != null) {
                    statement.close();
                    resultSet.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            DatabaseManager.closeConnection();
        }
        return -1;
    }

    public static Employee getEmployee(int employeeID){

        Lecturer lecturer = getLecturer(employeeID);

        if(lecturer != null) {

            query = "SELECT * FROM " + academicTableLabel + " WHERE " + academicIdLabel + " = ?";

            try {
                statement = DatabaseManager.getConnection(databasePath).prepareStatement(query);
                statement.setInt(1, employeeID);
                resultSet = statement.executeQuery();

                if (resultSet.next()) {

                    lecturer.setCourseRate(resultSet.getFloat(courseRateLabel));

                    query = "SELECT * FROM " + employeeTableLabel + " WHERE "+ employeeIdLabel + " = ?";

                    statement = DatabaseManager.getConnection(databasePath).prepareStatement(query);
                    statement.setInt(1, employeeID);
                    resultSet = statement.executeQuery();

                    if (resultSet.next()) {

                        lecturer.setName(resultSet.getString(employeeNameLabel));
                        lecturer.setAge(resultSet.getInt(employeeAgeLabel));
                        lecturer.setBasicSalary(resultSet.getFloat(employeeBasicSalaryLabel));

                        return lecturer;
                    }
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (statement != null && resultSet != null) {
                        statement.close();
                        resultSet.close();
                    }
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                DatabaseManager.closeConnection();
            }
        }
        SecurityGuard securityGuard = getSecurityGuard(employeeID);

        if(securityGuard != null){

            query = "SELECT * FROM " + nonAcademicTableLabel + " WHERE " + nonAcademicIdLabel + " = ?";

            try {
                statement = DatabaseManager.getConnection(databasePath).prepareStatement(query);
                statement.setInt(1, employeeID);
                resultSet = statement.executeQuery();

                if(resultSet.next()){

                    securityGuard.setHourlyRate(resultSet.getFloat(hourlyRateLabel));

                    query = "SELECT * FROM " + employeeTableLabel + " WHERE " + employeeIdLabel + " = ?";
                    statement = DatabaseManager.getConnection(databasePath).prepareStatement(query);
                    statement.setInt(1, employeeID);
                    resultSet = statement.executeQuery();

                    if(resultSet.next()){

                        securityGuard.setName(resultSet.getString(employeeNameLabel));
                        securityGuard.setAge(resultSet.getInt(employeeAgeLabel));
                        securityGuard.setBasicSalary(resultSet.getFloat(employeeBasicSalaryLabel));

                        return securityGuard;
                    }
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (statement != null && resultSet != null) {
                        statement.close();
                        resultSet.close();
                    }
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                DatabaseManager.closeConnection();
            }
        }
        return null;
    }

    public static boolean updateEmployee(int employeeID, Employee updatedEmployee){

        if(updatedEmployee instanceof  Lecturer){

            Lecturer updatedLecturer = (Lecturer) updatedEmployee;

            query = "UPDATE " + lecturerTableLabel + " SET " +
                    totalCoursesLabel + " = ? WHERE " + lecturerIdLabel + " = ?";

            try {
                statement = DatabaseManager.getConnection(databasePath).prepareStatement(query);
                statement.setInt(1, updatedLecturer.getTotalCourses());
                statement.setInt(2, employeeID);

                if(statement.executeUpdate() != 0){

                    query = "UPDATE " + academicTableLabel + " SET " +
                            courseRateLabel + " = ? WHERE " + academicIdLabel + " = ?";

                    statement = DatabaseManager.getConnection(databasePath).prepareStatement(query);
                    statement.setFloat(1, updatedLecturer.getCourseRate());
                    statement.setInt(2, employeeID);

                    if(statement.executeUpdate() != 0){

                        query = "UPDATE " + employeeTableLabel + " SET " + employeeNameLabel + " = ?, "
                                + employeeAgeLabel + " = ?, " + employeeBasicSalaryLabel
                                + " = ? WHERE " + employeeIdLabel + " = ?";

                        statement = DatabaseManager.getConnection(databasePath).prepareStatement(query);
                        statement.setString(1, updatedLecturer.getName());
                        statement.setInt(2, updatedLecturer.getAge());
                        statement.setFloat(3, updatedLecturer.getBasicSalary());
                        statement.setInt(4,employeeID);

                        return statement.executeUpdate() != 0;
                    }
                }
                return false;
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (statement != null) {
                        statement.close();
                    }
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                DatabaseManager.closeConnection();
            }
        }
        else {

            SecurityGuard updatedSecurityGuard = (SecurityGuard) updatedEmployee;

            query = "UPDATE " + securityGuardTableLabel + " SET " +
                    totalHoursLabel + " = ? WHERE " + securityGuardIDLabel + " = ?";

            try {
                statement = DatabaseManager.getConnection(databasePath).prepareStatement(query);
                statement.setInt(1, updatedSecurityGuard.getTotalHours());
                statement.setInt(2, employeeID);

                if(statement.executeUpdate() != 0){

                    query = "UPDATE " + nonAcademicTableLabel + " SET "
                            + hourlyRateLabel + " = ? WHERE non_academic_id = ?";

                    statement = DatabaseManager.getConnection(databasePath).prepareStatement(query);
                    statement.setFloat(1, updatedSecurityGuard.getHourlyRate());
                    statement.setInt(2, employeeID);

                    if(statement.executeUpdate() != 0){

                        String query3 = "UPDATE " + employeeTableLabel + " SET " + employeeNameLabel + " = ?, "
                                + employeeAgeLabel + " = ?, " + employeeBasicSalaryLabel
                                + " = ? WHERE " + employeeIdLabel + " = ?";

                        statement = DatabaseManager.getConnection(databasePath).prepareStatement(query3);
                        statement.setString(1, updatedSecurityGuard.getName());
                        statement.setInt(2, updatedSecurityGuard.getAge());
                        statement.setFloat(3, updatedSecurityGuard.getBasicSalary());
                        statement.setInt(4, employeeID);

                        return statement.executeUpdate() != 0;
                    }
                }
                return false;
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (statement != null) {
                        statement.close();
                    }
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                DatabaseManager.closeConnection();
            }
        }
        return false;
    }

    public static boolean deleteEmployee(int employeeID){

        query = "DELETE FROM " + employeeTableLabel + " WHERE " + employeeIdLabel + " = ?";

        try {
            statement = DatabaseManager.getConnection(databasePath).prepareStatement(query);
            statement.setInt(1, employeeID);

            return statement.executeUpdate() != 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {

            try {
                if (statement != null) {
                    statement.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            DatabaseManager.closeConnection();
        }
        return false;
    }

    /******************************************ACADEMIC QUERIES********************************************************/

    private static void insertAcademic(int academicID, float courseRate) {

        query = "INSERT INTO " + academicTableLabel + " VALUES (?, ?)";

        try {
            statement = DatabaseManager
                    .getConnection(databasePath)
                    .prepareStatement(query);

            statement.setInt(1, academicID);
            statement.setFloat(2, courseRate);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            DatabaseManager.closeConnection();
        }
    }

    /******************************************NON-ACADEMIC QUERIES****************************************************/
    private static void insertNonAcademic(int nonAcademicID, float hourlyRate) {

       query = "INSERT INTO " + nonAcademicTableLabel + " VALUES (?, ?)";

        try {
            statement = DatabaseManager
                    .getConnection(databasePath)
                    .prepareStatement(query);

            statement.setInt(1, nonAcademicID);
            statement.setFloat(2, hourlyRate);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            DatabaseManager.closeConnection();
        }
    }

    /******************************************LECTURER QUERIES********************************************************/

    public static boolean addLecturer(Lecturer lecturer){

        int key = insertEmployee(lecturer.getName(), lecturer.getAge(), lecturer.getBasicSalary());

        if(key != 0){

            insertAcademic(key, lecturer.getCourseRate());
            insertLecturer(key, lecturer.getTotalCourses());

            return true;
        }
        return false;
    }

    private static void insertLecturer(int lecturerID, int totalCourses) {

        query = "INSERT INTO " + lecturerTableLabel + " VALUES (?, ?)";

        try {
            statement = DatabaseManager
                    .getConnection(databasePath)
                    .prepareStatement(query);

            statement.setInt(1, lecturerID);
            statement.setInt(2, totalCourses);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            DatabaseManager.closeConnection();
        }
    }

    private static Lecturer getLecturer(int id) {

        query = "SELECT * FROM " + lecturerTableLabel + " WHERE " + lecturerIdLabel + "= ?";

        try {
            statement = DatabaseManager.getConnection(databasePath).prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            Lecturer lecturer = null;

            if (resultSet.next()) {
                lecturer = new Lecturer();
                lecturer.setId(resultSet.getInt(lecturerIdLabel));
                lecturer.setTotalCourses(resultSet.getInt(totalCoursesLabel));
            }
            return lecturer;

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null && resultSet != null) {
                    statement.close();
                    resultSet.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            DatabaseManager.closeConnection();
        }
        return null;
    }

    public static ArrayList<Lecturer> getAllLecturer(){

        query = "SELECT " + employeeIdLabel + ",\n" +
                employeeNameLabel + ",\n" +
                employeeAgeLabel + ",\n" +
                employeeBasicSalaryLabel + ",\n" +
                courseRateLabel + ",\n" +
                totalCoursesLabel + "\n" +
                "\n" +
                "FROM " + employeeTableLabel + ", " + academicTableLabel + ", " + lecturerTableLabel + "\n" +
                "WHERE " + academicIdLabel + " = " + employeeIdLabel + " AND " +
                lecturerIdLabel + " = " + employeeIdLabel + ";";

        try {
            statement = DatabaseManager.getConnection(databasePath).prepareStatement(query);
            resultSet = statement.executeQuery();

            if(resultSet != null){
                ArrayList<Lecturer> lecturers = new ArrayList<>();

                while (resultSet.next()){
                    lecturers.add(new Lecturer(
                            resultSet.getInt(employeeIdLabel),
                            resultSet.getString(employeeNameLabel),
                            resultSet.getInt(employeeAgeLabel),
                            resultSet.getFloat(employeeBasicSalaryLabel),
                            resultSet.getFloat(courseRateLabel),
                            resultSet.getInt(totalCoursesLabel)));
                }

                return lecturers;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null && resultSet != null) {
                    statement.close();
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DatabaseManager.closeConnection();
        }
        return null;
    }

    public static float lecturerTotalSalary(){

       query = "SELECT \n" +
                employeeBasicSalaryLabel + ",\n" +
                courseRateLabel + ",\n" +
                totalCoursesLabel + "\n" +
                "FROM\n" + employeeTableLabel + ", " + academicTableLabel + ", " + lecturerTableLabel +
                " WHERE\n" + academicIdLabel + " = " + employeeIdLabel +
                " AND " + lecturerIdLabel + " = " + employeeIdLabel + ";";

        try {
            statement = DatabaseManager.getConnection(databasePath).prepareStatement(query);
            resultSet = statement.executeQuery();

            if(resultSet != null){

                float totalSalaries = 0;

                while (resultSet.next()){

                    totalSalaries += ((resultSet.getFloat(employeeBasicSalaryLabel)
                            + resultSet.getFloat(courseRateLabel))
                            * resultSet.getInt(totalCoursesLabel));
                }

                return totalSalaries;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null && resultSet != null) {
                    statement.close();
                    resultSet.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            DatabaseManager.closeConnection();
        }
        return -1;
    }

    /******************************************SECURITY GUARD QUERIES**************************************************/

    public static boolean addSecurityGuard(SecurityGuard securityGuard){

        int key = insertEmployee(securityGuard.getName(), securityGuard.getAge(), securityGuard.getBasicSalary());

        if(key != 0){

            insertNonAcademic(key, securityGuard.getHourlyRate());
            insertSecurityGuard(key, securityGuard.getTotalHours());

            return true;
        }
        return false;
    }

    private static void insertSecurityGuard(int securityGuardID, int totalHours) {

        query = "INSERT INTO " + securityGuardTableLabel + " VALUES (?, ?)";

        try {
            statement = DatabaseManager
                    .getConnection(databasePath)
                    .prepareStatement(query);

            statement.setInt(1, securityGuardID);
            statement.setInt(2, totalHours);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            DatabaseManager.closeConnection();
        }
    }

    private static SecurityGuard getSecurityGuard(int id) {

        query = "SELECT * FROM " + securityGuardTableLabel + " WHERE " + securityGuardIDLabel + " = ?";

        try {
            statement = DatabaseManager.getConnection(databasePath).prepareStatement(query);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            SecurityGuard security_guard = null;
            if (resultSet.next()) {

                security_guard = new SecurityGuard();
                security_guard.setId(resultSet.getInt(securityGuardIDLabel));
                security_guard.setTotalHours(resultSet.getInt(totalHoursLabel));
            }
            return security_guard;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (statement != null && resultSet != null) {
                try {
                    statement.close();
                    resultSet.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            DatabaseManager.closeConnection();
        }
        return null;
    }

    public static ArrayList<SecurityGuard> getAllSecurityGuard(){

        query = "SELECT " + employeeIdLabel + ",\n" +
                employeeNameLabel + ",\n" +
                employeeAgeLabel + ",\n" +
                employeeBasicSalaryLabel + ",\n" +
                hourlyRateLabel + ",\n" +
                totalHoursLabel + "\n" +
                "\n" +
                "FROM " + employeeTableLabel + ", " + nonAcademicTableLabel + ", " + securityGuardTableLabel + "\n" +
                "WHERE " + nonAcademicIdLabel + " = " + employeeIdLabel + " AND " +
                securityGuardIDLabel + " = " + employeeIdLabel + ";";

        try {
            statement = DatabaseManager.getConnection(databasePath).prepareStatement(query);
            resultSet = statement.executeQuery();

            if(resultSet != null){
                ArrayList<SecurityGuard> securityGuards = new ArrayList<>();

                while (resultSet.next()){

                    securityGuards.add(new SecurityGuard(
                            resultSet.getInt(employeeIdLabel),
                            resultSet.getString(employeeNameLabel),
                            resultSet.getInt(employeeAgeLabel),
                            resultSet.getFloat(employeeBasicSalaryLabel),
                            resultSet.getFloat(hourlyRateLabel),
                            resultSet.getInt(totalHoursLabel)));
                }
                return securityGuards;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null && resultSet != null) {
                    statement.close();
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DatabaseManager.closeConnection();
        }
        return null;
    }

    public static float securityGuardTotalSalary(){

       query = "SELECT \n" +
                employeeBasicSalaryLabel + ",\n" +
                hourlyRateLabel + ",\n" +
                totalHoursLabel + "\n" +
                "FROM\n" + employeeTableLabel + ", " + nonAcademicTableLabel + ", " + securityGuardTableLabel +
                " WHERE\n" + nonAcademicIdLabel + " = " + employeeIdLabel +
                " AND " + securityGuardIDLabel + " = " + employeeIdLabel + ";";

        try {
            statement = DatabaseManager.getConnection(databasePath).prepareStatement(query);
            resultSet = statement.executeQuery();

            if(resultSet != null){

                float totalSalaries = 0;

                while (resultSet.next()){

                    totalSalaries += ((resultSet.getFloat(employeeBasicSalaryLabel)
                            + resultSet.getFloat(hourlyRateLabel))
                            * resultSet.getInt(totalHoursLabel));
                }
                return totalSalaries;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null && resultSet != null) {
                    statement.close();
                    resultSet.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            DatabaseManager.closeConnection();
        }
        return -1;
    }
}