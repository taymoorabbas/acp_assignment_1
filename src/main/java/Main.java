/*
Created by: Taymoor
Date: 07-Oct-19
Time: 7:57 PM
Lau ji Ghauri aya fir
*/

import java.util.Scanner;

import static Data.EmployeeSystem.*;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        employeeInfoSystem();
    }

    private static void employeeInfoSystem() {

        char choice;
        boolean exit = false;

        do{
            showMenu();
            System.out.println("Your action: ");
            choice = scanner.next().charAt(0);
            scanner.nextLine();

            switch (choice){

                case '1':
                    insertEmployee();
                    break;

                case '2':
                    searchEmployee();
                    break;

                case '3':
                    updateEmployee();
                    break;

                case '4':
                    deleteEmployee();
                    break;

                case '5':
                    showAllEmployees();
                    break;

                case '6':
                    computeAllSalaries();
                    break;

                case '7':
                    computeSpecificSalary();
                    break;

                case '0':
                    System.out.println("Exiting . . . . .");
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
                "1. Add employee\n" +
                "2. Search employee\n" +
                "3- Update employee\n" +
                "4- Delete employee\n" +
                "5. Display all records\n" +
                "6. Compute total salaries\n" +
                "7. Compute salaries for specific type\n" +
                "0. Exit");
    }
}
