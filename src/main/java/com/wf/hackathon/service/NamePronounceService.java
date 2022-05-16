package com.wf.hackathon.service;

import com.wf.hackathon.entity.Employee;
import com.wf.hackathon.entity.EmployeeAudio;
import com.wf.hackathon.model.PronounceRequest;
import com.wf.hackathon.repo.EmployeeRepo;
import com.wf.hackathon.service.ai.TextToSpeechService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class NamePronounceService {

    private EmployeeRepo employeeRepo;

    private AzureStorageService azureStorageService;

    public NamePronounceService(EmployeeRepo employeeRepo, AzureStorageService azureStorageService) {
        this.employeeRepo = employeeRepo;
        this.azureStorageService = azureStorageService;
    }


    public Map<String, String> pronounceName(PronounceRequest request) {
        Map<String, String> response = new HashMap<>();
        TextToSpeechService service = new TextToSpeechService();
        String audio = service.getSpeech(request.getName(), request.getCountry());
        Employee employee = employeeRepo.findById(request.getEmployeeId())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + request.getEmployeeId()));
        azureStorageService.writeBlobFile(audio, request.getEmployeeId() + "_defaultAudio");
        response.put("employeeId", request.getEmployeeId());
        response.put("audio", audio);
        return response;
    }

}
