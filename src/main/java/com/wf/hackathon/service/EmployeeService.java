package com.wf.hackathon.service;

import com.wf.hackathon.model.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeService {

//    private EmployeeRepo employeeRepo;

    public EmployeeService(/*EmployeeRepo employeeRepo*/) {
//        this.employeeRepo = employeeRepo;
    }


    public void login(LoginRequest loginRequest) {
        log.info("Start EmployeeService.login");
//        Optional<Employee> byId = employeeRepo.findById(loginRequest.getUserName());

    }

}
