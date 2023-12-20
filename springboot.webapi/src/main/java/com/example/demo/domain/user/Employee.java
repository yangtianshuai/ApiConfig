package com.example.demo.domain.user;

public class Employee {
    private final String employeeId;
    private String phone;

    public Employee(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }
    public String getPhone() {
        return phone;
    }
}
