package com.EmployeeManagement.controller;

import com.EmployeeManagement.dao.EmployeeRepository;
import com.EmployeeManagement.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "http://localhost:8080")
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepo;

    @Autowired
    KafkaTemplate<String, Employee> kafkaTemplate;

    private static final String TOPIC = "employee_topic";

    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee emp) {
        try {
            Employee employee = employeeRepo.add(emp);
            System.out.println("Employee ID: " + employee.getEid() + ", added successfully.");
            List<Employee> employees = employeeRepo.listAllEmployee();
            System.out.println(employees.get(employees.size()-1).toString());
            kafkaTemplate.send(TOPIC, employees.get(employees.size()-1));
            System.out.println(employees.toString());
            return new ResponseEntity<Employee>(employee, HttpStatus.CREATED);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee emp) {
        try {
            Employee employee = employeeRepo.update(emp);
            System.out.println("Employee ID: " + emp.getEid() + ", updated successfully.");
            kafkaTemplate.send(TOPIC, employee);
            return new ResponseEntity<Employee>(employee, HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Employee>(null);
        }
    }

    @GetMapping("/get/{eid}")
    public ResponseEntity<Employee> findEmployeeById(@PathVariable("eid") int id) {
        System.out.println("Fetching employee with ID = " + id + "...");
        Employee employee = employeeRepo.findById(id);

        if(employee != null) {
            System.out.println(employee);
            return new ResponseEntity<Employee>(employee, HttpStatus.OK);
        }

        System.out.println("Cannot find Employee with ID = " + id);
        return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/remove/{eid}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("eid") int id) {
        System.out.println("Removing employee with ID = " + id + "...");
        int eid = employeeRepo.deleteById(id);

        if(eid == 0) {
            System.out.println("Cannot find Employee with ID = " + id);
            return new ResponseEntity<String>("Cannot find Employee with ID = " + id, HttpStatus.NOT_FOUND);
        }

        System.out.println("Employee with ID = " + id + " removed successfully.");
        return new ResponseEntity<String>("Employee with ID = " + id + " removed successfully.", HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> deleteAll() {
        try {
            int numRows = employeeRepo.deleteAllEmployees();
            System.out.println("Removed all Employees successfully");
            return new ResponseEntity<String>("Removed " + numRows + " Employee(s) successfully", HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<String>("Cannot remove Employees.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeRepo.listAllEmployee();
        System.out.println("Listing all employees..");
        return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
    }
}