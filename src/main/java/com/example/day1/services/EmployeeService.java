package com.example.day1.services;

import com.example.day1.annotation.Loggable;
import com.example.day1.entites.Employee;
import com.example.day1.exception.RecordNotFoundException;
import com.example.day1.exception.RecordNotUniqueException;
import com.example.day1.mapper.EmployeeMapper;
import com.example.day1.repostories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository repository;

    @Autowired
    private EmployeeMapper employeeMapper;


    @Cacheable(value = "Employee.saveGetAllEmployees", key = "#root.methodName")
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Caching(evict = {
            @CacheEvict(value = "Employee.saveGetAllEmployees", key = "#root.methodName", allEntries = true),
            @CacheEvict(value = "Employee.getEmployeeByEmail", key = "#entity.email",allEntries = true),
            @CacheEvict(value = "Employee.getEmployeeById", key = "#entity.id",allEntries = true)
    })
    public Employee saveEmployee(Employee employee) {
        Employee newEmployee = getEmployeeByEmail(employee.getEmail());
        if (newEmployee == null) {
            newEmployee = employeeMapper.toEmployee(employee);
            return repository.save(newEmployee);
        }
        throw new RecordNotUniqueException("        not unique");
    }

    @Loggable
    @Cacheable(value = "Employee.getEmployeeByEmail", key = "#email")
    public Employee getEmployeeByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Loggable
    @Cacheable(value = "Employee.getEmployeeById", key = "#id", condition = "#id > 10", unless = "#id <= 10 ", sync = true)
    public Employee getEmployeeById(Integer id) {
        Employee emp = repository.findById(id).orElse(null);
        if (emp != null) {
            return emp;
        }
        throw new RecordNotFoundException("Record not unique");
    }
    @CachePut(cacheNames = "Employee.updateEmployee", key = "#employee")
    public Employee updateEmployee(Employee employee) {
        Employee curEmployee = getEmployeeById(employee.getId());
        updateRecord(curEmployee, employee);
        return repository.save(curEmployee);
    }
    private void updateRecord(Employee currentEmployee, Employee newEmployee) {
        if (newEmployee.getEmail() != null)
            currentEmployee.setEmail(newEmployee.getEmail());
        if (newEmployee.getFirstName() != null)
            currentEmployee.setFirstName(newEmployee.getFirstName());
        if (newEmployee.getLastName() != null)
            currentEmployee.setLastName(newEmployee.getLastName());
    }

    @Caching(
           evict = {
                   @CacheEvict(cacheNames = "Employee.getEmployeeById", key = "#id"),
                   @CacheEvict(cacheNames = {"Employee.saveGetAllEmployees",
                           "Employee.getEmployeeByEmail", "Employee.updateEmployee"},
                           allEntries = true, beforeInvocation = true),
           }
    )
    public void deleteEmployeeById(Integer id) {
        Employee employee = getEmployeeById(id);
        repository.delete(employee);
    }
}

















