package com.wf.hackathon.service;

import com.wf.hackathon.entity.Employee;
import com.wf.hackathon.model.CustomPronounceRequest;
import com.wf.hackathon.model.PronounceRequest;
import com.wf.hackathon.repo.EmployeeRepo;
import com.wf.hackathon.service.ai.SpeechToSpeechService;
import com.wf.hackathon.service.ai.TextToSpeechService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        Employee employee = employeeRepo.findById(request.getEmployeeId())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + request.getEmployeeId()));
        TextToSpeechService service = new TextToSpeechService();
        String audio = service.getSpeech(request.getName(), request.getCountry());
        azureStorageService.uploadAudio(audio, request.getEmployeeId() + "_defaultAudio");
        response.put("employeeId", request.getEmployeeId());
        response.put("audio", audio);
        return response;
    }

    public Map<String, String> customPronounceName(CustomPronounceRequest request) {
        Map<String, String> response = new HashMap<>();
        SpeechToSpeechService service = new SpeechToSpeechService();
        String audio = service.getSpeech(request.getAudio(), request.getCountry(), request.getGender(), request.getSpeed());
        Employee employee = employeeRepo.findById(request.getEmployeeId())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + request.getEmployeeId()));
        azureStorageService.uploadAudio(audio, request.getEmployeeId() + "_customAudio");
        response.put("employeeId", request.getEmployeeId());
        response.put("audio", audio);
        return response;
    }

}
