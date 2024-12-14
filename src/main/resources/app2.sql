-- Database schema for App2

-- Create Role table
CREATE TABLE role (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(50) NOT NULL
);

-- Create Employee table
CREATE TABLE employee (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          firstname VARCHAR(50) NOT NULL,
                          surname VARCHAR(50) NOT NULL,
                          role_id INT,
                          FOREIGN KEY (role_id) REFERENCES role(id)
);

-- Create Project table
CREATE TABLE project (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         employee_id INT,
                         FOREIGN KEY (employee_id) REFERENCES employee(id)
);

-- Stored procedure to delete a role and its associated employees and projects
DELIMITER $$
CREATE PROCEDURE delete_role(IN role_id INT)
BEGIN
    DECLARE employee_id INT;

    -- Reassign all projects to a default employee
UPDATE project SET employee_id = 1 WHERE employee_id IN (SELECT id FROM employee WHERE role_id = role_id);

-- Delete all employees with the specified role
DELETE FROM employee WHERE role_id = role_id;

-- Delete the role
DELETE FROM role WHERE id = role_id;
END$$
DELIMITER ;
