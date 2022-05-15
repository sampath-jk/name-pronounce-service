package com.wf.hackathon.controller;

import com.wf.hackathon.model.EmployeeResponse;
import com.wf.hackathon.model.EmployeeSignupRequest;
import com.wf.hackathon.model.SuccessResponse;
import com.wf.hackathon.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@SecurityRequirement(name = "NamePronunciationSecurity")
@RestController
@RequestMapping("/user")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(method = "GET", summary = "Greet", description = "A generic greeting service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "General greeting") })
    @GetMapping("/hello")
    public ResponseEntity<SuccessResponse> hello() {
        return new ResponseEntity(new SuccessResponse(200, "Success", "Hello"), HttpStatus.OK);

    }

    @Operation(summary = "register", description = "Provides User registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registration successful") })
    @PostMapping("/register")
    public ResponseEntity<SuccessResponse> register(@Valid @RequestBody EmployeeSignupRequest loginRequest) {
        employeeService.register(loginRequest);
        return new ResponseEntity(new SuccessResponse(200, "Success", "Hello"), HttpStatus.OK);

    }
    @Operation(summary = "Get employee", description = "Provides employee information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee information") })
    @GetMapping("/getEmployee/{id}")
    public ResponseEntity<SuccessResponse> getEmployee(@PathVariable String id) {
        EmployeeResponse employee = employeeService.getEmployee(id);
        return new ResponseEntity(new SuccessResponse( "Success", employee), HttpStatus.OK);

    }
    @Operation(summary = "Employee search", description = "Provides employee name auto-suggest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employees matching requested name") })
    @GetMapping("/employeeSearch/{name}")
    public ResponseEntity<SuccessResponse> employeeSearch(@PathVariable String name) {
        List<EmployeeResponse> allEmployees = employeeService.getAllEmployees(name);
        return new ResponseEntity(new SuccessResponse( "Success", allEmployees), HttpStatus.OK);

    }
}
