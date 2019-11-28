/*
Created by: Taymoor
Date: 08-Oct-19
Time: 1:34 AM
Lau ji Ghauri aya fir
*/

package Data;

import Model.Employee;
import Model.Lecturer;
import Model.SecurityGuard;
import Utils.HelperMethods;

import java.io.*;

public class EmployeeSystem {

    public static final String FILE_NAME = "employeesFile.txt";
    private static final int MAX_SIZE = 10;
    private int count;
    private Employee[] employees;
    private FileWriter fileWriter;
    private BufferedReader fileReader;

    public EmployeeSystem(){

        employees = new Employee[MAX_SIZE];
        count = -1;
    }

    public boolean insert(Employee employee){

        if(this.count == MAX_SIZE - 1){

            return false;
        }

        else{

            this.employees[++this.count] = employee;
            return true;
        }
    }

    public void showAllRecords(){

        if(this.count == -1){

            System.out.println("Nothing to show!");
        }

        else{

            for(int x = 0; x <= this.count; x++){

                System.out.println(x + 1 + ") " + employees[x].toString());
            }
        }
    }

    public void showSortedRecords(){

        this.bubbleSort(this.employees, this.count);
        this.showAllRecords();
    }

    public Employee searchEmployee(int id){

        for(int x = 0; x <= this.count; x++){

            if(this.employees[x].getId() == id){

                return this.employees[x];
            }
        }

        return null;
    }

    public float calculateTotalSalaries(){

        if(this.isEmpty()){

            return 0;
        }

        float totalSalaries = 0;

        for(int x = 0; x <= this.count; x++){

            totalSalaries += this.employees[x].computeSalary();
        }

        return totalSalaries;
    }

    public float calculateSpecificSalaries(int type){

        //0 == Lecturer, 1 == SecurityGuard

        if(this.isEmpty()){

            return 0;
        }

        float totalSalaries = 0;

        for(int x = 0; x <= this.count; x++){

            if(type == 0){

                if(this.employees[x] instanceof Lecturer){

                    totalSalaries += this.employees[x].computeSalary();
                }
            }

            else{

                if(this.employees[x] instanceof SecurityGuard){

                    totalSalaries +=this.employees[x].computeSalary();
                }
            }
        }

        return totalSalaries;
    }

    public boolean writeToFile(){

        if(this.isEmpty()){

            return false;
        }

        try {
            fileWriter = new FileWriter(FILE_NAME);

            for(int x = 0; x <= this.count; x++){

                if(this.employees[x] instanceof Lecturer){

                    fileWriter.write("L," + this.employees[x].toString() + "\n");
                }

                else{

                    fileWriter.write("S," + this.employees[x].toString() + "\n");
                }
            }
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        finally {

            try {
                fileWriter.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean readFromFile(){

        try {

            fileReader = new BufferedReader(new FileReader(FILE_NAME));
            this.employees = new Employee[MAX_SIZE];
            this.count = -1;

            try {
                String line = fileReader.readLine();

                if(line == null){

                    return false;
                }

                while (line != null){

                    this.insert(this.readEmployeeFromFile(line));
                    line = fileReader.readLine();
                }
                return true;
            }
            catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        finally {

            try {
                fileReader.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Employee readEmployeeFromFile(String line){
        return HelperMethods.getEmployee(line);
    }

    private void bubbleSort(Employee[] employees, int size) {

        if(!this.isEmpty()){

            for(int x = 0; x <= size; x++){

                for(int y = 1; y <= (size - x); y++){

                    if(employees[y - 1].getId() > employees[y].getId()){

                        Employee temp = employees[y - 1];
                        employees[y - 1] = employees[y];
                        employees[y] = temp;
                    }
                }
            }
        }
    }

    private boolean isEmpty(){
        return this.count == -1;
    }
}