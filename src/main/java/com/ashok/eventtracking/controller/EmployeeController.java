package com.ashok.eventtracking.controller;

import com.ashok.eventtracking.model.Employee;
import com.ashok.eventtracking.model.Event;
import com.ashok.eventtracking.repository.EmployeeRepository;
import com.ashok.eventtracking.repository.EventRepository;
import com.ashok.eventtracking.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller to handle all the Employee entity activities.
 *
 * @author ashok
 * 03/10/20
 */
@RestController
@RequestMapping("api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee")
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @PostMapping("/employee")
    public Employee addNewEmployee(@Valid @RequestBody Employee employee){
        return employeeService.addEmployee(employee);
    }
}
