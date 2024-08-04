package com.example.day1.controller;

import com.example.day1.entites.Employee;
import com.example.day1.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService service;

    @GetMapping("/getAllEmployees")
    public List<Employee> getAllEmployees() {
        return service.getAllEmployees();
    }
    @PostMapping("/saveEmployee")
    public Employee saveEmployee(@RequestBody Employee employee) {
        return service.saveEmployee(employee);
    }
    @GetMapping("/getEmployeeByEmail/{emp_email}")
    public Employee getEmployeeByEmail(@PathVariable(name = "emp_email") String email) {
        return service.getEmployeeByEmail(email);
    }
    @GetMapping("/getEmployeeById/{id}")
    public Employee getEmployeeById(@PathVariable Integer id,@RequestParam(required = false) String name) {
        return service.getEmployeeById(id);
    }
    @PutMapping("/updateEmployee")
    public Employee updateEmployee(@RequestBody Employee employee) {
        return service.updateEmployee(employee);
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable Integer id,@RequestParam(required = false) String name) {
        service.deleteEmployeeById(id);
        return ResponseEntity.ok().build();
    }

}
