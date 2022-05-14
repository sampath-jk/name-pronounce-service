package com.wf.hackathon.repo;

import com.wf.hackathon.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, String> {
    List<Employee> findByFirstNameStartingWith(String name);
    List<Employee> findByLastNameStartingWith(String name);
//    List<Employee> findByPreferredNameStartingWith(String prefix);
}
