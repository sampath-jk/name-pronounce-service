package com.wf.hackathon.controller;

import com.wf.hackathon.model.EmployeeResponse;
import com.wf.hackathon.model.EmployeeSignupRequest;
import com.wf.hackathon.model.SuccessResponse;
import com.wf.hackathon.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@SecurityRequirement(name = "NamePronunciationSecurity")
@RestController
@RequestMapping("/user")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(method = "GET", summary = "Greet", description = "A generic greeting service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "General greeting") })
    @GetMapping("/hello")
    public ResponseEntity<SuccessResponse> hello() {
        logger.debug("Entered hello debug");
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
        List<EmployeeResponse> searchResults = employeeService.searchEmployees(name);
        return new ResponseEntity(new SuccessResponse( "Success", searchResults), HttpStatus.OK);

    }

    @GetMapping("/getAllEmployees")
    public ResponseEntity<SuccessResponse> getAllEmployees() {
        List<EmployeeResponse> allEmployees = employeeService.getAllEmployees();
        return new ResponseEntity(new SuccessResponse( "Success", allEmployees), HttpStatus.OK);

    }
}
