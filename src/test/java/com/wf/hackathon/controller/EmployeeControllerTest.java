package com.wf.hackathon.controller;

import com.wf.hackathon.model.EmployeeResponse;
import com.wf.hackathon.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;
    @Mock
    private EmployeeService employeeService;

    @Test
    void testEmployeeController_SearchEmployee() {
        List<EmployeeResponse> list = new ArrayList<>();
        Mockito.when(employeeService.searchEmployees(Mockito.anyString())).thenReturn(list);
        employeeController.employeeSearch("DD");
    }

    @Test
    void testEmployeeController_GetAllEmployees() {
        List<EmployeeResponse> list = new ArrayList<>();
        Mockito.when(employeeService.getAllEmployees()).thenReturn(list);
        employeeController.getAllEmployees();
    }

    @Test
    void testEmployeeController_GetEmployee() {
        Mockito.when(employeeService.getEmployee(Mockito.anyString())).thenReturn(new EmployeeResponse());
        employeeController.getEmployee("DD");
    }


}