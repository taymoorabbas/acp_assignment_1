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

    /******************************************EMPLOYEE QUERIES********************************************************/

    private static int insertEmployee(String name, int age, float salary){

        String query = "INSERT INTO " + employeeTableLabel + " VALUES (?, ?, ?)";
        ResultSet resultSet = null;

        try {
            PreparedStatement statement = DatabaseManager
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

            String query1 = "SELECT * FROM " + academicTableLabel + " WHERE " + academicIdLabel + " = ?";
            PreparedStatement statement1 = null;
            PreparedStatement statement2 = null;
            ResultSet resultSet1 = null;
            ResultSet resultSet2 = null;

            try {
                statement1 = DatabaseManager.getConnection(databasePath).prepareStatement(query1);
                statement1.setInt(1, employeeID);
                resultSet1 = statement1.executeQuery();

                if (resultSet1.next()) {

                    lecturer.setCourseRate(resultSet1.getFloat(courseRateLabel));

                    String query2 = "SELECT * FROM " + employeeTableLabel + " WHERE "+ employeeIdLabel + " = ?";
                    statement2 = DatabaseManager.getConnection(databasePath).prepareStatement(query2);
                    statement2.setInt(1, employeeID);
                    resultSet2 = statement2.executeQuery();

                    if (resultSet2.next()) {

                        lecturer.setName(resultSet2.getString(employeeNameLabel));
                        lecturer.setAge(resultSet2.getInt(employeeAgeLabel));
                        lecturer.setBasicSalary(resultSet2.getFloat(employeeBasicSalaryLabel));

                        return lecturer;
                    }
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (statement1 != null && statement2 != null && resultSet2 != null) {
                        statement1.close();
                        statement2.close();
                        resultSet1.close();
                        resultSet2.close();
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

            String query3 = "SELECT * FROM " + nonAcademicTableLabel + " WHERE " + nonAcademicIdLabel + " = ?";
            PreparedStatement statement3 = null;
            PreparedStatement statement4 = null;
            ResultSet resultSet3 = null;
            ResultSet resultSet4 = null;

            try {
                statement3 = DatabaseManager.getConnection(databasePath).prepareStatement(query3);
                statement3.setInt(1, employeeID);
                resultSet3 = statement3.executeQuery();

                if(resultSet3.next()){

                    securityGuard.setHourlyRate(resultSet3.getFloat(hourlyRateLabel));

                    String query4 = "SELECT * FROM " + employeeTableLabel + " WHERE " + employeeIdLabel + " = ?";
                    statement4 = DatabaseManager.getConnection(databasePath).prepareStatement(query4);
                    statement4.setInt(1, employeeID);
                    resultSet4 = statement4.executeQuery();

                    if(resultSet4.next()){

                        securityGuard.setName(resultSet4.getString(employeeNameLabel));
                        securityGuard.setAge(resultSet4.getInt(employeeAgeLabel));
                        securityGuard.setBasicSalary(resultSet4.getFloat(employeeBasicSalaryLabel));

                        return securityGuard;
                    }
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (statement3 != null && statement4 != null && resultSet4 != null) {
                        statement3.close();
                        statement4.close();
                        resultSet3.close();
                        resultSet4.close();
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

            String query1 = "UPDATE " + lecturerTableLabel + " SET " +
                    totalCoursesLabel + " = ? WHERE " + lecturerIdLabel + " = ?";
            PreparedStatement statement1 = null;
            PreparedStatement statement2 = null;
            PreparedStatement statement3 = null;
            try {
                statement1 = DatabaseManager.getConnection(databasePath).prepareStatement(query1);
                statement1.setInt(1, updatedLecturer.getTotalCourses());
                statement1.setInt(2, employeeID);

                if(statement1.executeUpdate() != 0){

                    String query2 = "UPDATE " + academicTableLabel + " SET " +
                            courseRateLabel + " = ? WHERE " + academicIdLabel + " = ?";
                    statement2 = DatabaseManager.getConnection(databasePath).prepareStatement(query2);
                    statement2.setFloat(1, updatedLecturer.getCourseRate());
                    statement2.setInt(2, employeeID);

                    if(statement2.executeUpdate() != 0){

                        String query3 = "UPDATE " + employeeTableLabel + " SET " + employeeNameLabel + " = ?, "
                                + employeeAgeLabel + " = ?, " + employeeBasicSalaryLabel + " = ? WHERE " + employeeIdLabel + " = ?";
                        statement3 = DatabaseManager.getConnection(databasePath).prepareStatement(query3);
                        statement3.setString(1, updatedLecturer.getName());
                        statement3.setInt(2, updatedLecturer.getAge());
                        statement3.setFloat(3, updatedLecturer.getBasicSalary());
                        statement3.setInt(4,employeeID);

                        return statement3.executeUpdate() != 0;
                    }
                }
                return false;
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (statement1 != null && statement2 != null && statement3 != null) {
                        statement1.close();
                        statement2.close();
                        statement3.close();
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

            String query1 = "UPDATE " + securityGuardTableLabel + " SET " +
                    totalHoursLabel + " = ? WHERE " + securityGuardIDLabel + " = ?";
            PreparedStatement statement1 = null;
            PreparedStatement statement2 = null;
            PreparedStatement statement3 = null;
            try {
                statement1 = DatabaseManager.getConnection(databasePath).prepareStatement(query1);
                statement1.setInt(1, updatedSecurityGuard.getTotalHours());
                statement1.setInt(2, employeeID);

                if(statement1.executeUpdate() != 0){

                    String query2 = "UPDATE " + nonAcademicTableLabel + " SET "
                            + hourlyRateLabel + " = ? WHERE non_academic_id = ?";
                    statement2 = DatabaseManager.getConnection(databasePath).prepareStatement(query2);
                    statement2.setFloat(1, updatedSecurityGuard.getHourlyRate());
                    statement2.setInt(2, employeeID);

                    if(statement2.executeUpdate() != 0){

                        String query3 = "UPDATE " + employeeTableLabel + " SET " + employeeNameLabel + " = ?, "
                                + employeeAgeLabel + " = ?, " + employeeBasicSalaryLabel
                                + " = ? WHERE " + employeeIdLabel + " = ?";
                        statement3 = DatabaseManager.getConnection(databasePath).prepareStatement(query3);
                        statement3.setString(1, updatedSecurityGuard.getName());
                        statement3.setInt(2, updatedSecurityGuard.getAge());
                        statement3.setFloat(3, updatedSecurityGuard.getBasicSalary());
                        statement3.setInt(4, employeeID);

                        return statement3.executeUpdate() != 0;
                    }
                }
                return false;
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (statement1 != null && statement2 != null && statement3 != null) {
                        statement1.close();
                        statement2.close();
                        statement3.close();
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

        String query = "DELETE FROM " + employeeTableLabel + " WHERE " + employeeIdLabel + " = ?";
        PreparedStatement statement = null;
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

        String query = "INSERT INTO " + academicTableLabel + " VALUES (?, ?)";
        PreparedStatement statement = null;
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

        String query = "INSERT INTO " + nonAcademicTableLabel + " VALUES (?, ?)";
        PreparedStatement statement = null;
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

        String query = "INSERT INTO " + lecturerTableLabel + " VALUES (?, ?)";
        PreparedStatement statement = null;
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

        String query = "SELECT * FROM " + lecturerTableLabel + " WHERE " + lecturerIdLabel + "= ?";

        ResultSet resultSet = null;
        try {
            PreparedStatement statement = DatabaseManager.getConnection(databasePath).prepareStatement(query);
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
                if (resultSet != null) {
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

        String query = "SELECT " + employeeIdLabel + ",\n" +
                employeeNameLabel + ",\n" +
                employeeAgeLabel + ",\n" +
                employeeBasicSalaryLabel + ",\n" +
                courseRateLabel + ",\n" +
                totalCoursesLabel + "\n" +
                "\n" +
                "FROM " + employeeTableLabel + ", " + academicTableLabel + ", " + lecturerTableLabel + "\n" +
                "WHERE " + academicIdLabel + " = " + employeeIdLabel + " AND " +
                lecturerIdLabel + " = " + employeeIdLabel + ";";

        PreparedStatement statement = null;
        ResultSet resultSet = null;
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

        String query = "SELECT \n" +
                employeeBasicSalaryLabel + ",\n" +
                courseRateLabel + ",\n" +
                totalCoursesLabel + "\n" +
                "FROM\n" + employeeTableLabel + ", " + academicTableLabel + ", " + lecturerTableLabel +
                " WHERE\n" + academicIdLabel + " = " + employeeIdLabel +
                " AND " + lecturerIdLabel + " = " + employeeIdLabel + ";";

        PreparedStatement statement = null;
        ResultSet resultSet = null;
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

        String query = "INSERT INTO " + securityGuardTableLabel + " VALUES (?, ?)";
        PreparedStatement statement = null;
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

        String query = "SELECT * FROM " + securityGuardTableLabel + " WHERE " + securityGuardIDLabel + " = ?";

        ResultSet resultSet = null;
        try {
            PreparedStatement statement = DatabaseManager.getConnection(databasePath).prepareStatement(query);
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
            if (resultSet != null) {
                try {
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

        String query = "SELECT " + employeeIdLabel + ",\n" +
                employeeNameLabel + ",\n" +
                employeeAgeLabel + ",\n" +
                employeeBasicSalaryLabel + ",\n" +
                hourlyRateLabel + ",\n" +
                totalHoursLabel + "\n" +
                "\n" +
                "FROM " + employeeTableLabel + ", " + nonAcademicTableLabel + ", " + securityGuardTableLabel + "\n" +
                "WHERE " + nonAcademicIdLabel + " = " + employeeIdLabel + " AND " +
                securityGuardIDLabel + " = " + employeeIdLabel + ";";

        PreparedStatement statement = null;
        ResultSet resultSet = null;
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

        String query = "SELECT \n" +
                employeeBasicSalaryLabel + ",\n" +
                hourlyRateLabel + ",\n" +
                totalHoursLabel + "\n" +
                "FROM\n" + employeeTableLabel + ", " + nonAcademicTableLabel + ", " + securityGuardTableLabel +
                " WHERE\n" + nonAcademicIdLabel + " = " + employeeIdLabel +
                " AND " + securityGuardIDLabel + " = " + employeeIdLabel + ";";

        PreparedStatement statement = null;
        ResultSet resultSet = null;
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