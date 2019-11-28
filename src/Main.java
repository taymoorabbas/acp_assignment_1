/*
Created by: Taymoor
Date: 07-Oct-19
Time: 7:57 PM
Lau ji Ghauri aya fir
*/

import Data.EmployeeSystem;
import Database.EmployeeManager;
import Model.Lecturer;
import Model.SecurityGuard;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        employeeInfoSystem(new EmployeeSystem(), new EmployeeManager());
    }

    private static void employeeInfoSystem(EmployeeSystem employeeSystem, EmployeeManager employeeManager) {

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
                            scanner.nextLine();

                            Lecturer lecturer = new Lecturer(id, age, name, basicSalary, courseRate, totalCourses);
                            employeeManager.insertLecturer(lecturer);

                            //insertLecturer returns false in case of success (because insert is a update query)
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

                            SecurityGuard securityGuard = new SecurityGuard(id, age, name, basicSalary, hourlyRate, totalHours);
                             employeeManager.insertSecurityGuard(securityGuard);

                            //addSecurityGuard returns false in case of success (because insert is a update query)
                            if(employeeSystem.insert(securityGuard)){

                                System.out.println("new Employee: " + securityGuard.getName() + " added successfully");
                            }

                            else {

                                System.out.println("Error: Cannot add new employee: " + securityGuard.getName());
                            }
                            break;

                        default:
                            System.out.println("Error: Invalid choice");
                            break;
                    }
                    break;

                case '2':
                    System.out.println("Display from: \n" +
                            "1- Database\n" +
                            "2- File");
                    char show = scanner.next().charAt(0);

                    switch (show){

                        case '1':
                            System.out.println("All lecturers\n" +
                                    "-------------");
                            System.out.println(employeeManager.getAllLecturer());
                            System.out.println("All security guards\n" +
                                    "-------------------");
                            System.out.println(employeeManager.getAllSecurityGuard());
                            break;

                        case '2':
                            System.out.println("All records\n" +
                                    "-----------");
                            employeeSystem.showAllRecords();
                            break;

                            default:
                                System.out.println("Error: Invalid choice");
                                break;
                    }
                    break;

                case '3':
                    System.out.println("Sorted records\n" +
                            "--------------");
                    employeeSystem.showSortedRecords();
                    break;

                case '4':
                    System.out.println("Search from:\n" +
                            "1- Database\n" +
                            "2- File");

                    char search = scanner.next().charAt(0);
                    int existingId;

                    switch (search){

                        case '1':
                            System.out.println("Search:\n" +
                                    "1- Lecturer\n" +
                                    "2- Security guard");

                            search = scanner.next().charAt(0);
                            System.out.println("Enter existing employee's ID: ");
                            existingId = scanner.nextInt();
                            scanner.nextLine();

                            switch (search){

                                case '1':
                                    if(employeeManager.getLecturer(existingId) != null){

                                        System.out.println(employeeManager.getLecturer(existingId).toString());
                                    }
                                    else{

                                        System.out.println("Error: No employee found with ID: " + existingId);
                                    }
                                    break;

                                case '2':
                                    if(employeeManager.getSecurityGuard(existingId) != null){

                                        System.out.println(employeeManager.getSecurityGuard(existingId).toString());
                                    }
                                    else{

                                        System.out.println("Error: No employee found with ID: " + existingId);
                                    }
                                    break;

                                    default:
                                        System.out.println("Error: Invalid choice");
                                        break;
                            }
                            break;

                        case '2':
                            System.out.println("Enter existing employee's ID: ");
                            existingId = scanner.nextInt();
                            scanner.nextLine();

                            if(employeeSystem.searchEmployee(existingId) == null){

                                System.out.println("Error: No employee found with ID: " + existingId);
                            }

                            else {

                                System.out.println("Employee found: " + employeeSystem.searchEmployee(existingId).toString());
                            }
                            break;

                            default:
                                System.out.println("Error: Invalid choice");
                                break;
                    }
                    break;

                case '5':
                    System.out.println("Update data for:\n" +
                            "1- Lecturer\n" +
                            "2- Security guard");
                    type = scanner.next().charAt(0);

                    switch (type){

                        case '1':
                            System.out.println("Enter existing lecturer id: ");
                            existingId = scanner.nextInt();
                            scanner.nextLine();

                            Lecturer lecturer = employeeManager.getLecturer(existingId);
                            if(lecturer != null){

                                System.out.println("Enter updated age (0 to skip): ");
                                age = scanner.nextInt();
                                scanner.nextLine();

                                if(age != 0){

                                    lecturer.setAge(age);
                                }

                                System.out.println("Enter updated name (0 to skip): ");
                                name = scanner.nextLine();

                                if(!name.equals("0")){

                                    lecturer.setName(name);
                                }

                                System.out.println("Enter updated basic salary (0 to skip): ");
                                basicSalary = scanner.nextFloat();
                                scanner.nextLine();

                                if(basicSalary != 0){

                                    lecturer.setBasicSalary(basicSalary);
                                }

                                System.out.println("Enter updated course rate (0 to skip): ");
                                float courseRate = scanner.nextFloat();
                                scanner.nextLine();

                                if(courseRate != 0){

                                    lecturer.setCourseRate(courseRate);
                                }

                                System.out.println("Enter updated total courses (0 to skip): ");
                                int totalCourses = scanner.nextInt();
                                scanner.nextLine();

                                if(totalCourses != 0){

                                    lecturer.setTotalCourses(totalCourses);
                                }

                                employeeManager.updateLecturer(existingId, lecturer);
                            }
                            else {

                                System.out.println("Error: No Employee found with id: " + existingId);
                            }
                            break;

                        case '2':
                            System.out.println("Enter existing security guard id: ");
                            existingId = scanner.nextInt();
                            scanner.nextLine();

                            SecurityGuard securityGuard = employeeManager.getSecurityGuard(existingId);
                            if(securityGuard != null){

                                System.out.println("Enter updated age (0 to skip): ");
                                age = scanner.nextInt();
                                scanner.nextLine();

                                if(age != 0){

                                    securityGuard.setAge(age);
                                }

                                System.out.println("Enter updated name (0 to skip): ");
                                name = scanner.nextLine();

                                if(!name.equals("0")){

                                    securityGuard.setName(name);
                                }

                                System.out.println("Enter updated basic salary (0 to skip): ");
                                basicSalary = scanner.nextFloat();
                                scanner.nextLine();

                                if(basicSalary != 0){

                                    securityGuard.setBasicSalary(basicSalary);
                                }

                                System.out.println("Enter updated hourly rate (0 to skip): ");
                                float hourlyRate = scanner.nextFloat();
                                scanner.nextLine();

                                if(hourlyRate != 0){

                                    securityGuard.setHourlyRate(hourlyRate);
                                }

                                System.out.println("Enter updated total hours (0 to skip): ");
                                int totalHours = scanner.nextInt();
                                scanner.nextLine();

                                if(totalHours != 0){

                                    securityGuard.setTotalHours(totalHours);
                                }

                                employeeManager.updateSecurityGuard(existingId, securityGuard);
                            }
                            else {

                                System.out.println("Error: No Employee found with id: " + existingId);
                            }
                            break;

                            default:
                                System.out.println("Error: Invalid choice");
                                break;
                    }
                    break;

                case '6':
                    System.out.println("Delete from:\n" +
                            "1- Lecturer\n" +
                            "2- Security guard");
                    type = scanner.next().charAt(0);

                    switch (type){

                        case '1':
                            System.out.println("Enter existing lecturer id: ");
                            existingId = scanner.nextInt();
                            scanner.nextLine();

                            if(employeeManager.getLecturer(existingId) != null){

                                employeeManager.deleteLecturer(existingId);
                            }
                            else{

                                System.out.println("Error: No employee found wit id: " + existingId);
                            }
                            break;

                        case '2':
                            System.out.println("Enter existing security guard id: ");
                            existingId = scanner.nextInt();
                            scanner.nextLine();

                            if(employeeManager.getSecurityGuard(existingId) != null){

                                employeeManager.deleteSecurityGuard(existingId);
                            }
                            else{

                                System.out.println("Error: No employee found wit id: " + existingId);
                            }
                            break;

                            default:
                                System.out.println("Error: Invalid choice");
                                break;
                    }
                    break;

                case '7':
                    System.out.println("Total salaries to be paid: " + employeeSystem.calculateTotalSalaries());
                    break;

                case '8':
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

                case '9':
                    if(employeeSystem.writeToFile()){

                        System.out.println("Data entered to \'" + EmployeeSystem.FILE_NAME + "\' successfully");
                    }

                    else {

                        System.out.println("Error: Cannot enter data to \'" + EmployeeSystem.FILE_NAME + "\'");
                    }
                    break;

                case '0':
                    if(employeeSystem.readFromFile()){

                        System.out.println("Data read from \'" + EmployeeSystem.FILE_NAME + "\' successfully");
                    }

                    else{

                        System.out.println("Error: Cannot read data from \'" + EmployeeSystem.FILE_NAME + "\'");
                    }
                    break;

                case 'x':
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
                "5- Update Employee\n" +
                "6- Delete Employee\n" +
                "7. Compute total Salaries to be paid\n" +
                "8. Compute Salaries for Specific Type of Employees\n" +
                "9. Write all the Data to File\n" +
                "0. Read Data from File\n" +
                "x. Exit");
    }
}
