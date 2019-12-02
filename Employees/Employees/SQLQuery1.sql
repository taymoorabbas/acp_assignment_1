/*
CREATE TABLE Employee(

employee_id INT NOT NULL IDENTITY PRIMARY KEY,
employee_name VARCHAR(30),
employee_age INT,
employee_salary FLOAT

);

CREATE TABLE Academic(

academic_id INT NOT NULL PRIMARY KEY FOREIGN KEY REFERENCES Employee(employee_id) ON UPDATE CASCADE ON DELETE CASCADE,
course_rate FLOAT

);

CREATE TABLE Non_academic(

non_academic_id INT NOT NULL PRIMARY KEY FOREIGN KEY REFERENCES Employee(employee_id) ON UPDATE CASCADE ON DELETE CASCADE,
hourly_rate FLOAT
);

CREATE TABLE Lecturer(

lecturer_id INT NOT NULL PRIMARY KEY FOREIGN KEY REFERENCES Academic(academic_id) ON UPDATE CASCADE ON DELETE CASCADE,
total_courses INT

);

CREATE TABLE Security_guard(

security_guard_id INT NOT NULL PRIMARY KEY FOREIGN KEY REFERENCES Non_academic(non_academic_id) ON UPDATE CASCADE ON DELETE CASCADE,
total_courses INT

);
*/