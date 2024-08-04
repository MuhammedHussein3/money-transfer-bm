package com.example.day1.mapper;

import com.example.day1.entites.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public Employee toEmployee(Employee employee){
        return modelMapper.map(employee, Employee.class);
    }
}
