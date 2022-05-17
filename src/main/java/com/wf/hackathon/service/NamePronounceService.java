package com.wf.hackathon.service;

import com.wf.hackathon.entity.Employee;
import com.wf.hackathon.model.CustomPronounceRequest;
import com.wf.hackathon.model.PronounceRequest;
import com.wf.hackathon.repo.EmployeeRepo;
import com.wf.hackathon.service.ai.SpeechToSpeechService;
import com.wf.hackathon.service.ai.TextToSpeechService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class NamePronounceService {

    private EmployeeRepo employeeRepo;

    private AzureStorageService azureStorageService;
    

    public NamePronounceService(EmployeeRepo employeeRepo, AzureStorageService azureStorageService) {
        this.employeeRepo = employeeRepo;
        this.azureStorageService = azureStorageService;
    }


    public Map<String, String> pronounceName(PronounceRequest request) {
        log.debug("Entered pronounceName");
        Map<String, String> response = new HashMap<>();
        String audio=null;
        TextToSpeechService service= new TextToSpeechService();
        log.debug("Received response from AI service");
        Employee employee = employeeRepo.findById(request.getEmployeeId())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + request.getEmployeeId()));
        log.debug("Employe information received");
        if(StringUtils.isEmpty(request.getName())){
            if(employee.getAudioFoundFlag().equalsIgnoreCase("Y"))
            audio=azureStorageService.readAudioFile(request.getEmployeeId());
            else{
               
                audio = service.getSpeech(request.getName(), request.getCountry());
                azureStorageService.uploadAudio(audio, request.getEmployeeId()); 
                log.debug("Voice stored in azure"); 
                employee.setAudioFoundFlag("Y");
                employeeRepo.save(employee);
            }    
        }
        else if(StringUtils.isEmpty(request.getName()) && !request.getPreferredName().equals(employee.getPreferredName())){
            audio = service.getSpeech(request.getName(), request.getCountry());
            azureStorageService.uploadAudio(audio, request.getEmployeeId());
            employee.setPreferredName(request.getPreferredName());
            employee.setAudioFoundFlag("Y");
            employeeRepo.save(employee);
            log.debug("Employe information saved");
        }
        else if(StringUtils.isEmpty(request.getName()) && request.getPreferredName().equals(employee.getPreferredName())){
            audio=azureStorageService.readAudioFile(request.getEmployeeId());
            log.debug("Audio received from Azure store");        
        }
        response.put("employeeId", request.getEmployeeId());
        response.put("audio", audio);
        return response;
    }

    public Map<String, String> customPronounceNameTest(CustomPronounceRequest request) {
        Map<String, String> response = new HashMap<>();
        SpeechToSpeechService service = new SpeechToSpeechService();
        String audio = service.getSpeech(request.getAudio(), request.getCountry(), request.getGender(), request.getSpeed());
        response.put("employeeId", request.getEmployeeId());
        response.put("audio", audio);
        return response;
    }

    public Map<String, String> customPronounceName(CustomPronounceRequest request) {
        Map<String, String> response = new HashMap<>();
        Employee employee = employeeRepo.findById(request.getEmployeeId())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + request.getEmployeeId()));
        azureStorageService.uploadAudio(request.getAudio(), request.getEmployeeId());
        employee.setAudioFoundFlag("Y");
        employeeRepo.save(employee);
        response.put("employeeId", request.getEmployeeId());
        response.put("status","success");
        return response;
    }

    public Map<String, String> resetPronunciation(String employeeId) {
        Map<String, String> response = new HashMap<>();
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + employeeId));
        employee.setPreferredName(null);
        employee.setAudioFoundFlag("N");
        employeeRepo.save(employee);
        response.put("employeeId", employeeId);
        response.put("status","success");
        return response;
    }
}
