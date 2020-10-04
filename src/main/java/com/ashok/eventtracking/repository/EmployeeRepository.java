package com.ashok.eventtracking.repository;

import com.ashok.eventtracking.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ashok
 * 03/10/20
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
