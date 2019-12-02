/*
Created by: Taymoor
Date: 08-Oct-19
Time: 1:34 AM
Lau ji Ghauri aya fir
*/

package Data;

import Database.EmployeeDbManager;
import Model.Employee;
import Model.Lecturer;
import Model.SecurityGuard;

import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeSystem {

    private static Scanner scanner = new Scanner(System.in);

    public static void insertEmployee() {

        System.out.println("Enter new entry for:\n" +
                "1- Lecturer\n" +
                "2- Security guard\n" +
                "0- Cancel");

        char choice = scanner.next().charAt(0);
        scanner.nextLine();

        switch (choice) {

            case '1':
                insertLecturer();
                break;

            case '2':
                insertSecurityGuard();
                break;

            case '0':
                return;

            default:
                System.out.println("Error: Invalid choice");
        }
    }

    public static void updateEmployee() {

        System.out.println("Enter existing employee ID (0 to cancel):");
        int id = scanner.nextInt();
        scanner.nextLine();

        if(id == 0){

            return;
        }

        Employee employee = EmployeeDbManager.getEmployee(id);

        if (employee != null) {

            if (employee instanceof Lecturer) {

                updateLecturer(id, (Lecturer) employee);
            } else {

                updateSecurityGuard(id, (SecurityGuard) employee);
            }
        } else {
            System.out.println("Error: No employee found by ID: " + id);
        }
    }

    public static void deleteEmployee(){

        System.out.println("Enter existing employee ID (0 to cancel): ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if(id == 0){

            return;
        }

        if(EmployeeDbManager.deleteEmployee(id)){

            System.out.println("Record with ID: " + id + " deleted successfully");
        }
        else{

            System.out.println("Error: Employee deletion failed");
        }
    }

    public static void searchEmployee() {

        System.out.println("Enter existing employee ID (0 to cancel): ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if(id == 0){

            return;
        }

        Employee employee = EmployeeDbManager.getEmployee(id);

        if (employee == null) {

            System.out.println("Error: No employee found by ID: " + id);
            return;
        }
        if (employee instanceof Lecturer) {

            showLabels(true);

        } else {

            showLabels(false);
        }
        showEmployee(employee);
        for(int x = 0; x < 110; x++){

            System.out.print("=");
        }
        System.out.println();
    }

    public static void showAllEmployees(){

        ArrayList<Lecturer> lecturers = EmployeeDbManager.getAllLecturer();

        if(lecturers != null){

            System.out.println("Lecturers\n" + "---------");
            showLabels(true);

            for (Lecturer lecturer : lecturers) {

                showEmployee(lecturer);
            }
            for(int x = 0; x < 110; x++){

                System.out.print("=");
            }
            System.out.println();
        }

        ArrayList<SecurityGuard> securityGuards = EmployeeDbManager.getAllSecurityGuard();

        if(securityGuards != null){

            System.out.println("Security guards\n" + "---------------");
            showLabels(false);

            for (SecurityGuard securityGuard : securityGuards){

                showEmployee(securityGuard);
            }
            for(int x = 0; x < 110; x++){

                System.out.print("=");
            }
            System.out.println();
        }
    }

    public static void computeSpecificSalary(){

        System.out.println("Compute salaries for:\n" +
                "1- Lecturer\n" +
                "2- Security guard\n" +
                "0- Cancel");
        char choice = scanner.next().charAt(0);

        float salary;
        switch (choice){

            case '1':
                salary = EmployeeDbManager.lecturerTotalSalary();
                if (salary != -1){

                    System.out.println("Total lecturer salaries to be paid: $" + salary);
                }
                else{

                    System.out.println("Error: cannot calculate salary for lecturers");
                }
                break;
            case '2':
                salary = EmployeeDbManager.securityGuardTotalSalary();
                if (salary != -1){

                    System.out.println("Total security guard salaries to be paid: $" + salary);
                }
                else{

                    System.out.println("Error: cannot calculate salary for security guards");
                }
                break;

            case '0':
                return;

            default:
                System.out.println("Error: Invalid choice");
                break;
        }
    }

    public static void computeAllSalaries(){

        float salary = EmployeeDbManager.lecturerTotalSalary() + EmployeeDbManager.securityGuardTotalSalary();

        if(salary < 0){

            System.out.println("Error: Cannot calculate total salaries");
        }
        else{

            System.out.println("Total salaries to be paid: " + salary);
        }
    }

    private static void insertLecturer() {

        System.out.println("Enter lecturer name: ");
        String name = scanner.nextLine();

        System.out.println("Enter lecturer age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter lecturer basic salary: ");
        float salary = scanner.nextFloat();
        scanner.nextLine();

        System.out.println("Enter lecturer course rate: ");
        float courseRate = scanner.nextFloat();
        scanner.nextLine();

        System.out.println("Enter lecturer total courses: ");
        int totalCourses = scanner.nextInt();
        scanner.nextLine();

        Lecturer lecturer = new Lecturer(0, name, age, salary, courseRate, totalCourses);

        if (EmployeeDbManager.addLecturer(lecturer)) {

            System.out.println("New entry for lecturer added.");
        } else {

            System.out.println("Error: cannot add new entry for lecturer");
        }
    }

    private static void insertSecurityGuard() {

        System.out.println("Enter security guard name: ");
        String name = scanner.nextLine();

        System.out.println("Enter security guard age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter security guard basic salary: ");
        float salary = scanner.nextFloat();
        scanner.nextLine();

        System.out.println("Enter security guard hourly rate: ");
        float hourlyRate = scanner.nextFloat();
        scanner.nextLine();

        System.out.println("Enter security guard total hours: ");
        int totalHours = scanner.nextInt();
        scanner.nextLine();

        SecurityGuard securityGuard = new SecurityGuard(0, name, age, salary, hourlyRate, totalHours);

        if (EmployeeDbManager.addSecurityGuard(securityGuard)) {

            System.out.println("New entry for security guard added.");
        } else {

            System.out.println("Error: cannot add new entry for security guard");
        }
    }

    private static void showEmployee(Employee employee) {

        if (employee instanceof Lecturer) {

            Lecturer lecturer = (Lecturer) employee;
            System.out.format("%-10d%-30s%-10d%-20f%-20f%-20d",
                    lecturer.getId(),
                    lecturer.getName(),
                    lecturer.getAge(),
                    lecturer.getBasicSalary(),
                    lecturer.getCourseRate(),
                    lecturer.getTotalCourses());
            System.out.println();
        }
        else{

            SecurityGuard securityGuard = (SecurityGuard) employee;
            System.out.format("%-10d%-30s%-10d%-20f%-20f%-20d",
                    securityGuard.getId(),
                    securityGuard.getName(),
                    securityGuard.getAge(),
                    securityGuard.getBasicSalary(),
                    securityGuard.getHourlyRate(),
                    securityGuard.getTotalHours());
            System.out.println();
        }
    }

    private static void updateLecturer(int id, Lecturer lecturer) {

        System.out.println("Enter updated lecturer name (* to skip): ");
        String name = scanner.nextLine();

        if (!name.equals("*")) {

            lecturer.setName(name);
        }

        System.out.println("Enter updated lecturer age (0 to skip): ");
        int age = scanner.nextInt();
        scanner.nextLine();

        if (age != 0) {

            lecturer.setAge(age);
        }

        System.out.println("Enter updated lecturer basic salary (0 to skip): ");
        float salary = scanner.nextFloat();
        scanner.nextLine();

        if (salary != 0) {

            lecturer.setBasicSalary(salary);
        }

        System.out.println("Enter updated lecturer course rate (0 to skip): ");
        float courseRate = scanner.nextFloat();
        scanner.nextLine();

        if (courseRate != 0) {

            lecturer.setCourseRate(courseRate);
        }

        System.out.println("Enter updated lecturer total courses (0 to skip): ");
        int totalCourses = scanner.nextInt();
        scanner.nextLine();

        if (totalCourses != 0) {

            lecturer.setTotalCourses(totalCourses);
        }

        if (EmployeeDbManager.updateEmployee(id, lecturer)) {

            System.out.println("Updated data for lecturer: " + lecturer.getName());
        } else {

            System.out.println("Error: Employee update failed");
        }
    }

    private static void updateSecurityGuard(int id, SecurityGuard securityGuard) {

        System.out.println("Enter updated security guard name (* to skip): ");
        String name = scanner.nextLine();

        if (!name.equals("*")) {

            securityGuard.setName(name);
        }

        System.out.println("Enter updated security guard age (0 to skip): ");
        int age = scanner.nextInt();
        scanner.nextLine();

        if (age != 0) {

            securityGuard.setAge(age);
        }

        System.out.println("Enter updated security guard basic salary (0 to skip): ");
        float salary = scanner.nextFloat();
        scanner.nextLine();

        if (salary != 0) {

            securityGuard.setBasicSalary(salary);
        }

        System.out.println("Enter updated security guard hourly rate (0 to skip): ");
        float hourlyRate = scanner.nextFloat();
        scanner.nextLine();

        if (hourlyRate != 0) {

            securityGuard.setHourlyRate(hourlyRate);
        }

        System.out.println("Enter updated security guard total hours (0 to skip): ");
        int totalHours = scanner.nextInt();
        scanner.nextLine();

        if (totalHours != 0) {

            securityGuard.setTotalHours(totalHours);
        }

        if (EmployeeDbManager.updateEmployee(id, securityGuard)) {

            System.out.println("Updated data for security guard: " + securityGuard.getName());
        } else {

            System.out.println("Error: Employee update failed");
        }
    }

    private static void showLabels(boolean isLecturer){

        if(isLecturer){
            System.out.format("%-10s%-30s%-10s%-20s%-20s%-20s",
                    "id", "name", "age", "basic salary", "course rate", "total courses");
            System.out.println();
        }
        else{
            System.out.format("%-10s%-30s%-10s%-20s%-20s%-20s",
                    "id", "name", "age", "basic salary", "hourly rate", "total hours");
            System.out.println();
        }
        for(int x = 0; x < 110; x++){

            System.out.print("=");
        }
        System.out.println();
    }
}