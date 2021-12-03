package com.EmployeeManagement.controller;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmployeeListener {
    @KafkaListener(topics = "employee_topic", groupId = "employee")
    public void consume(String employee) {
        System.out.println("Consumed Employee: " + employee);
    }
}
