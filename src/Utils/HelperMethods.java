/*
Created by: Taymoor
Date: 08-Oct-19
Time: 11:43 PM
Lau ji Ghauri aya fir
*/

package Utils;

import Model.Employee;
import Model.Lecturer;
import Model.SecurityGuard;

public class HelperMethods {

    private static char getEmpType(String line){

        return line.charAt(0);
    }

    private static int getEmpId(String line){

        StringBuilder id = new StringBuilder();

        for(int x = 2; x < line.length(); x++){

            if(line.charAt(x) == ','){

                break;
            }

            id.append(line.charAt(x));
        }

        return Integer.parseInt(id.toString());
    }

    private static int getEmpAge(String line){

        StringBuilder age = new StringBuilder();
        int count = 0;

        for(int x = 0; x < line.length(); x++){

            if(line.charAt(x) == ','){

                count++;
            }

            if(count == 2){

                if(line.charAt(x) != ','){

                    age.append(line.charAt(x));
                }
            }

            if(count >= 3){

                break;
            }
        }

        return Integer.parseInt(age.toString());
    }

    private static String getEmpName(String line){

        StringBuilder name = new StringBuilder();
        int count = 0;

        for(int x = 0; x < line.length(); x++){

            if(line.charAt(x) == ','){

                count++;
            }

            if(count == 3){

                if(line.charAt(x) != ','){

                    name.append(line.charAt(x));
                }
            }

            if(count >= 4){

                break;
            }
        }

        return name.toString();
    }

    private static float getEmpBasicSalary(String line){

        StringBuilder basicSalary = new StringBuilder();
        int count = 0;

        for(int x = 0; x < line.length(); x++){

            if(line.charAt(x) == ','){

                count++;
            }

            if(count == 4){

                if(line.charAt(x) != ','){

                    basicSalary.append(line.charAt(x));
                }
            }

            if(count >= 5){

                break;
            }
        }

        return Float.parseFloat(basicSalary.toString());
    }

    private static float getEmpRate(String line){

        StringBuilder rate = new StringBuilder();
        int count = 0;

        for (int x = 0; x < line.length(); x++){

            if(line.charAt(x) == ','){

                count++;
            }

            if(count == 5){

                if(line.charAt(x) != ','){

                    rate.append(line.charAt(x));
                }
            }

            if(count >= 6){

                break;
            }
        }

        return Float.parseFloat(rate.toString());
    }

    private static int getEmpHourCourse(String line){

        StringBuilder hourCourse = new StringBuilder();
        int count = 0;

        for (int x = 0; x < line.length(); x++){

            if(line.charAt(x) == ','){

                count++;
            }

            if(count == 6){

                if(line.charAt(x) != ','){

                    hourCourse.append(line.charAt(x));
                }
            }
        }

        return Integer.parseInt(hourCourse.toString());
    }

    public static Employee getEmployee(String line){

        Employee employee;

        if(getEmpType(line) == 'L'){

            employee = new Lecturer();
            employee.setId(getEmpId(line));
            employee.setAge(getEmpAge(line));
            employee.setName(getEmpName(line));
            employee.setBasicSalary(getEmpBasicSalary(line));
            ((Lecturer) employee).setCourseRate(getEmpRate(line));
            ((Lecturer) employee).setTotalCourses(getEmpHourCourse(line));
        }

        else {

            employee = new SecurityGuard();
            employee.setId(getEmpId(line));
            employee.setAge(getEmpAge(line));
            employee.setName(getEmpName(line));
            employee.setBasicSalary(getEmpBasicSalary(line));
            ((SecurityGuard) employee).setHourlyRate(getEmpRate(line));
            ((SecurityGuard) employee).setTotalHours(getEmpHourCourse(line));
        }

        return employee;
    }
}
