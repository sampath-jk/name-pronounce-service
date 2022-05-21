package com.wf.hackathon.service;

import com.wf.hackathon.entity.ERole;
import com.wf.hackathon.entity.Employee;
import com.wf.hackathon.entity.Role;
import com.wf.hackathon.model.EmployeeResponse;
import com.wf.hackathon.model.EmployeeSignupRequest;
import com.wf.hackathon.repo.EmployeeRepo;
import com.wf.hackathon.repo.RoleRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeService {

    private EmployeeRepo employeeRepo;

    private PasswordEncoder encoder;

    private RoleRepo roleRepo;

    public EmployeeService(EmployeeRepo employeeRepo, PasswordEncoder encoder, RoleRepo roleRepo) {
        this.employeeRepo = employeeRepo;
        this.encoder = encoder;
        this.roleRepo = roleRepo;
    }

    public Employee register(EmployeeSignupRequest signupRequest) {
        log.info("Start EmployeeService.register");
        Employee employee = buildEmployee(signupRequest);
        Employee save = employeeRepo.save(employee);
        log.info("End EmployeeService.register");
        return save;
    }

    private Employee buildEmployee(EmployeeSignupRequest signupRequest) {
        return Employee.builder().employeeId(signupRequest.getEmployeeId())
                .lastName(signupRequest.getLastName())
                .firstName(signupRequest.getFirstName())
                .preferredName(signupRequest.getPreferredName())
                .country(signupRequest.getCountry())
                .email(signupRequest.getEmail())
                .telephone(signupRequest.getTelephone())
                .password(encoder.encode(signupRequest.getPassword()))
                .roles(getRoles(signupRequest))
                .build();
    }

    private Set<Role> getRoles(EmployeeSignupRequest signupRequest) {
        Set<ERole> strRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        //Need to change this logic
        strRoles.forEach(role -> {
            log.debug("Role Name:"+role);
            // Role adminRole = roleRepo.findByName(role)
                    // .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
             Role r = new Role();
            r.setName(role);
            r.setIsActive(true);
            r.setRoleDescription(role.getValue());
            roles.add(r);        
            // roles.add(adminRole);
        });
        return roles;
    }

    public EmployeeResponse getEmployee(String id) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + id));

        return buildEmployeeResponse(employee);
    }

    public List<EmployeeResponse> searchEmployees(String name) {
        List<EmployeeResponse> employeeResponse = new ArrayList<>();
        List<Employee> searchEmployees = employeeRepo.findByFirstNameStartingWith(name);
        searchEmployees.addAll(employeeRepo.findByLastNameStartingWith(name));
        searchEmployees.forEach(emp -> employeeResponse.add(buildEmployeeResponse(emp)));
        return employeeResponse;
    }

    public List<EmployeeResponse> getAllEmployees() {
        List<EmployeeResponse> employeeResponse = new ArrayList<>();
        //TODO: pagination, sorting
        List<Employee> all = employeeRepo.findAll();
        all.forEach(emp -> employeeResponse.add(buildEmployeeResponse(emp)));
        return employeeResponse;
    }

    private EmployeeResponse buildEmployeeResponse(Employee employee) {
        Set<Role> roles = employee.getRoles() != null ? employee.getRoles() : Collections.<Role>emptySet();
        Set<String> roleNames = roles.stream().map(role -> role.getName().name()).collect(Collectors.toSet());

        return EmployeeResponse.builder()
                .address(employee.getAddress())
                .employeeId(employee.getEmployeeId())
                .city(employee.getCity())
                .country(employee.getCountry())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .preferredName(employee.getPreferredName())
                .telephone(employee.getTelephone())
                .email(employee.getEmail())
                .roles(roleNames)
                .build();

    }
}
