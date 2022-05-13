package com.wf.hackathon.controller;

import com.wf.hackathon.model.EmployeeSignupRequest;
import com.wf.hackathon.model.LoginRequest;
import com.wf.hackathon.model.SuccessResponse;
import com.wf.hackathon.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse> register(@Valid @RequestBody EmployeeSignupRequest loginRequest) {
        employeeService.register(loginRequest);
        return new ResponseEntity(new SuccessResponse(200, "Success", "Hello"), HttpStatus.OK);

    }
}
