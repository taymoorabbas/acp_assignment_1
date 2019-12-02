/*
Created by: Taymoor Ghazanfar
R.no: 3625-BSSE-F17-C
Date: 02-Dec-19
Time: 5:02 PM
Lau ji Ghauri aya fir
*/

package Utils;

public class DatabaseConstants {

    //database path
    public static final String databasePath =
            "jdbc:sqlserver://TAYMOOR-ENVY\\SQLEXPRESS;databaseName=Employees;integratedSecurity=true";

    //table names
    public static final String employeeTableLabel = "Employee";
    public static final String academicTableLabel = "Academic";
    public static final String nonAcademicTableLabel = "Non_academic";
    public static final String lecturerTableLabel = "Lecturer";
    public static final String securityGuardTableLabel = "Security_guard";

    //employee table columns
    public static final String employeeIdLabel = "employee_id";
    public static final String employeeNameLabel = "employee_name";
    public static final String employeeAgeLabel = "employee_age";
    public static final String employeeBasicSalaryLabel = "employee_salary";

    //academic table columns
    public static final String academicIdLabel = "academic_id";
    public static final String courseRateLabel = "course_rate";

    //non-academic table columns
    public static final String nonAcademicIdLabel = "non_academic_id";
    public static final String hourlyRateLabel = "hourly_rate";

    //lecturer table columns
    public static final String lecturerIdLabel = "lecturer_id";
    public static final String totalCoursesLabel = "total_courses";

    //security guard table columns
    public static final String securityGuardIDLabel = "security_guard_id";
    public static final String totalHoursLabel = "total_hours";
}
