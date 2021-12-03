package com.EmployeeManagement.entity;

import lombok.Data;

@Data
public class Employee {
    private int eid;
    private String name;
    private String email;
    private String city;
    private String designation;
    private int salary;
}