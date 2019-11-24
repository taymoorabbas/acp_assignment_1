/*
Created by: Taymoor
Date: 07-Oct-19
Time: 7:57 PM
Lau ji Ghauri aya fir
*/

import Data.EmployeeSystem;
import Model.Employee;
import Model.Lecturer;
import Model.SecurityGuard;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        EmployeeSystem employeeSystem = new EmployeeSystem();
        employeeInfoSystem(employeeSystem);
    }

    private static void employeeInfoSystem(EmployeeSystem employeeSystem){

        char choice;
        boolean exit = false;

        do{
            showMenu();
            System.out.println("Your action: ");
            choice = scanner.next().charAt(0);

            switch (choice){

                case '1':
                    System.out.println("Enter new data for:\n" +
                            "1. Lecturer\n" +
                            "2. Security Guard");
                    char type = scanner.next().charAt(0);

                    System.out.println("Enter new employee id: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Enter new employee age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Enter new employee name: ");
                    String name = scanner.nextLine();

                    System.out.println("Enter new employee basic salary: ");
                    float basicSalary = scanner.nextFloat();

                    switch (type){

                        case '1':
                            System.out.println("Enter new employee course rate: ");
                            float courseRate = scanner.nextFloat();

                            System.out.println("Enter new employee total courses: ");
                            int totalCourses = scanner.nextInt();

                            Employee lecturer = new Lecturer(id, age, name, basicSalary, courseRate, totalCourses);

                            if(employeeSystem.insert(lecturer)){

                                System.out.println("new Employee: " + lecturer.getName() + " added successfully");
                            }

                            else {

                                System.out.println("Error: Cannot add new employee: " + lecturer.getName());
                            }
                            break;

                        case '2':
                            System.out.println("Enter new employee hourly rate: ");
                            float hourlyRate = scanner.nextFloat();

                            System.out.println("Enter new employee total hours: ");
                            int totalHours = scanner.nextInt();

                            Employee securityGuard = new SecurityGuard(id, age, name, basicSalary, hourlyRate, totalHours);

                            if(employeeSystem.insert(securityGuard)){

                                System.out.println("new Employee: " + securityGuard.getName() + " added successfully");
                            }

                            else {

                                System.out.println("Error: Cannot add new employee: " + securityGuard.getName());
                            }
                            break;
                    }
                    break;

                case '2':
                    System.out.println("All records\n" +
                            "-----------");
                    employeeSystem.showAllRecords();
                    break;

                case '3':
                    System.out.println("Sorted records\n" +
                            "--------------");
                    employeeSystem.showSortedRecords();
                    break;

                case '4':
                    System.out.println("Enter existing employee's ID: ");
                    int existingId = scanner.nextInt();
                    scanner.nextLine();

                    if(employeeSystem.searchEmployee(existingId) == null){

                        System.out.println("Error: No employee found with ID: " + existingId);
                    }

                    else {

                        System.out.println("Employee found: " + employeeSystem.searchEmployee(existingId).toString());
                    }
                    break;

                case '5':
                    System.out.println("Total salaries to be paid: " + employeeSystem.calculateTotalSalaries());
                    break;

                case '6':
                    System.out.println("Calculate total salaries for:\n" +
                            "1. Lecturer\n" +
                            "2. Security Guard");
                    type = scanner.next().charAt(0);

                    switch (type){

                        case '1':
                            System.out.println("Total salaries of Lecturers: "
                                    + employeeSystem.calculateSpecificSalaries(0));
                            break;

                        case '2':
                            System.out.println("Total salaries of Security Guards: "
                                    + employeeSystem.calculateSpecificSalaries(1));
                            break;
                    }
                    break;

                case '7':
                    if(employeeSystem.writeToFile()){

                        System.out.println("Data entered to \'" + EmployeeSystem.FILE_NAME + "\' successfully");
                    }

                    else {

                        System.out.println("Error: Cannot enter data to \'" + EmployeeSystem.FILE_NAME + "\'");
                    }
                    break;

                case '8':
                    if(employeeSystem.readFromFile()){

                        System.out.println("Data read from \'" + EmployeeSystem.FILE_NAME + "\' successfully");
                    }

                    else{

                        System.out.println("Error: Cannot read data from \'" + EmployeeSystem.FILE_NAME + "\'");
                    }
                    break;

                case '9':
                    System.out.println("Exiting...");
                    exit = true;
                    break;

                    default:
                        System.out.println("Error: Invalid choice");
                        break;
            }
        }while (!exit);
    }

    private static void showMenu(){

        System.out.println("Employee Information System\n" +
                "---------------------------\n"+
                "1. Enter New employee data\n" +
                "2. Display all records\n" +
                "3. Sort by Employee ID and display records\n" +
                "4. Search Employee by ID and display results\n" +
                "5. Compute total Salaries to be paid\n" +
                "6. Compute Salaries for Specific Type of Employees\n" +
                "7. Write all the Data to File\n" +
                "8. Read Data from File\n" +
                "9. Exit");
    }
}
