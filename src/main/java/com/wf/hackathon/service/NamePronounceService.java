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
        String audio=null;
        TextToSpeechService service= new TextToSpeechService();;
        Employee employee = employeeRepo.findById(request.getEmployeeId())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + request.getEmployeeId()));
        if(!request.getName().isEmpty()){
            if(employee.getAudioFoundFlag().equalsIgnoreCase("Y"))
            audio=azureStorageService.readAudioFile(request.getEmployeeId());
            else{
               
                audio = service.getSpeech(request.getName(), request.getCountry());
                azureStorageService.uploadAudio(audio, request.getEmployeeId());  
                //TO-DO update audio_found_flag to "Y"      
            }    
        }
        else if(request.getName().isEmpty() && !request.getPreferredName().equals(employee.getPreferredName())){
            audio = service.getSpeech(request.getName(), request.getCountry());
            azureStorageService.uploadAudio(audio, request.getEmployeeId());
            //TO-DO update employee prefered name and audio_found_flag to "Y" in db.        
        }
        else if(request.getName().isEmpty() && request.getPreferredName().equals(employee.getPreferredName())){
            audio=azureStorageService.readAudioFile(request.getEmployeeId());        
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
        azureStorageService.uploadAudio(request.getAudio(), request.getEmployeeId());
        //TO-DO update audio_found_flag to "Y"
        response.put("employeeId", request.getEmployeeId());
        response.put("status","success");
        return response;
    }
    public Map<String, String> resetPronunciation(CustomPronounceRequest request) {
        Map<String, String> response = new HashMap<>();
        //TO-DO delete audio from azure store
        //TO-DO update audio_found_flag to "N"
        //TO-DO update prefered name to null in db
        
        response.put("employeeId", request.getEmployeeId());
        response.put("status","success");
        return response;
    }

}
