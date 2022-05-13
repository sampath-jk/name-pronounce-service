package com.wf.hackathon.controller;

import com.wf.hackathon.model.LoginRequest;
import com.wf.hackathon.model.SuccessResponse;
import com.wf.hackathon.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/hello")
    public ResponseEntity<SuccessResponse> hello() {
        return new ResponseEntity(new SuccessResponse(200, "Success", "Hello"), HttpStatus.OK);

    }

    @GetMapping("/login")
    public ResponseEntity<SuccessResponse> login(LoginRequest loginRequest) {

        return new ResponseEntity(new SuccessResponse(200, "Success", "Hello"), HttpStatus.OK);

    }
}
