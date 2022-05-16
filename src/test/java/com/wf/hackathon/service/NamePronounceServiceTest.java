package com.wf.hackathon.service;

import com.wf.hackathon.entity.Employee;
import com.wf.hackathon.model.PronounceRequest;
import com.wf.hackathon.repo.EmployeeRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NamePronounceServiceTest {

    @InjectMocks
    private NamePronounceService namePronounceService;

    @Mock
    private EmployeeRepo employeeRepo;

    @Mock
    private AzureStorageService azureStorageService;

    @Test
    void testPronounceName_Success() {
        Optional<Employee> employee = buildEmployee();
        Mockito.lenient().when(employeeRepo.findById(Mockito.anyString())).thenReturn(employee);
        Mockito.lenient().when(azureStorageService.uploadAudio(Mockito.anyString(), Mockito.anyString())).thenReturn("D");
        Mockito.lenient().when(employeeRepo.save(Mockito.any())).thenReturn(employee.get());
        Map<String, String> stringStringMap = namePronounceService.pronounceName(buildPronounceRequest());
        Assertions.assertNotNull(stringStringMap);
    }

    private PronounceRequest buildPronounceRequest() {
        PronounceRequest request = new PronounceRequest();
        request.setName("durga");
        request.setCountry("India");
        request.setEmployeeId("durga");
        return request;
    }

    private Optional<Employee> buildEmployee() {
        return Optional.ofNullable(Employee.builder()
                .employeeId("durga")
                .telephone("8394294929")
                .firstName("D")
                .lastName("D")
                .build());
    }


}