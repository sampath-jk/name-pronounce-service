package com.wf.hackathon.service;

import com.wf.hackathon.entity.Employee;
import com.wf.hackathon.model.EmployeeResponse;
import com.wf.hackathon.repo.EmployeeRepo;
import com.wf.hackathon.repo.RoleRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepo employeeRepo;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private RoleRepo roleRepo;



    @Test
    void testGetEmployee_Success() {
        Mockito.when(employeeRepo.findById(Mockito.anyString())).thenReturn(buildEmployee());
        EmployeeResponse response = employeeService.getEmployee("id");
        Assertions.assertNotNull(response);
    }

    @Test
    void testGetEmployee_EmployeeNotFound() {
        Mockito.when(employeeRepo.findById(Mockito.anyString())).thenThrow(UsernameNotFoundException.class);
        Assertions.assertThrows(UsernameNotFoundException.class, () -> employeeService.getEmployee("id"));
    }

    /*@Test
    void searchEmployees() {
    }

    @Test
    void getAllEmployees() {
    }*/

    private Optional<Employee> buildEmployee() {
        return Optional.ofNullable(Employee.builder()
                .employeeId("durga")
                .telephone("8394294929")
                .firstName("D")
                .lastName("D")
                .build());
    }
}