package com.wf.hackathon.service;

import com.wf.hackathon.entity.Employee;
import com.wf.hackathon.model.LoginRequest;
import com.wf.hackathon.repo.EmployeeRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class EmployeeService {

    private EmployeeRepo employeeRepo;

    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }


    public void login(LoginRequest loginRequest) {
        log.info("Start EmployeeService.login");
        Optional<Employee> byId = employeeRepo.findById(loginRequest.getUserName());

    }

}
