/*
Created by: Taymoor
Date: 07-Oct-19
Time: 7:57 PM
Lau ji Ghauri aya fir
*/

package Model;

public abstract class Employee {

    private int id, age;
    private String name;
    private float basicSalary;

    public Employee() {}

    public Employee(int id, String name, int age, float basicSalary) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.basicSalary = basicSalary;
    }

    public Employee(Employee employee){
        this.id = employee.id;
        this.age = employee.age;
        this.name = employee.name;
        this.basicSalary = employee.basicSalary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(float basicSalary) {
        this.basicSalary = basicSalary;
    }

    public abstract float computeSalary();

    @Override
    public String toString() {

        return this.id + "," + this.age + "," + this.name + "," + this.basicSalary;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj == this){

            return true;
        }

        if(!(obj instanceof  Employee)){

            return false;
        }

        Employee employee = (Employee) obj;

        return employee.id == this.id
                && employee.age == this.age
                && employee.name.equals(this.name)
                && employee.basicSalary == this.basicSalary;
    }
}
