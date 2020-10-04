package com.ashok.eventtracking.service;

import com.ashok.eventtracking.model.Employee;
import com.ashok.eventtracking.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class to handle the Employee related activities/functionalities.
 *
 * @author ashok
 * 04/10/20
 */
@Service
public class EmployeeService {

    private final Logger LOG = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee addEmployee(Employee employee){
        LOG.debug("addEmployee called for [{}]",employee);
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees(){
        LOG.debug("getAllEmployees called");
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(long id){
        LOG.debug("getEmployeeById called");
        return employeeRepository.findById(id).get();
    }
}
