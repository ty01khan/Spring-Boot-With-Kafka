package com.EmployeeManagement.dao;

import com.EmployeeManagement.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS Employee ("
                + "EID SERIAL PRIMARY KEY,"
                + "Name VARCHAR ( 255 ) NOT NULL,"
                + "Email VARCHAR ( 255 ) UNIQUE NOT NULL,"
                + "City VARCHAR ( 50 ),"
                + "Designation VARCHAR ( 100 ),"
                + "Salary INT"
                + ")";

        jdbcTemplate.update(query);
        System.out.println("Table created");
    }

    public Employee add(Employee e) {
        String query = "INSERT INTO Employee "
                + "(Name, Email, City, Designation, Salary) "
                + "VALUES(?,?,?,?,?)";
        Object[] object = new Object[] { e.getName(), e.getEmail(), e.getCity(), e.getDesignation(), e.getSalary() };
        jdbcTemplate.update(query, object);

        query = "SELECT * FROM Employee WHERE Email=?";
        Employee emp = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(Employee.class), e.getEmail());

        return emp;
    }

    public Employee update(Employee e) {
        String query = "UPDATE Employee SET Name=?, Email=?, City=?, Designation=?, Salary=? WHERE EID=?";
        Object[] object = new Object[] { e.getName(), e.getEmail(), e.getCity(), e.getDesignation(), e.getSalary(), e.getEid() };
        jdbcTemplate.update(query, object);

        return e;
    }

    public Employee findById(int id) {
        try {
            String query = "SELECT * FROM Employee WHERE EID=?";
            Employee emp = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(Employee.class), id);

            return emp;
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int deleteById(int id) {
        String query = "DELETE FROM Employee WHERE EID=?";
        int result = jdbcTemplate.update(query, id);

        if(result == 0) {
            return result;
        }

        return id;
    }

    public List<Employee> listAllEmployee() {
        String query = "SELECT * FROM Employee";
        List<Employee> employees = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Employee.class));
        return employees;
    }

    public int deleteAllEmployees() {
        return jdbcTemplate.update("DELETE FROM Employee");
    }
}