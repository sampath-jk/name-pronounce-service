package com.wf.hackathon.controller;

import com.azure.storage.blob.specialized.BlobAsyncClientBase;
import com.azure.storage.blob.specialized.BlobClientBase;
import com.wf.hackathon.model.CustomPronounceRequest;
import com.wf.hackathon.model.EmployeeResponse;
import com.wf.hackathon.model.PronounceRequest;
import com.wf.hackathon.model.SuccessResponse;
import com.wf.hackathon.service.NamePronounceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class NamePronounceController {

    private NamePronounceService namePronounceService;

    public NamePronounceController(NamePronounceService namePronounceService) {
        this.namePronounceService = namePronounceService;
    }

    @PostMapping("/pronounceName")
    public ResponseEntity<SuccessResponse> pronounceName(@RequestBody PronounceRequest request) {
        Map<String, String> data = namePronounceService.pronounceName(request);
        return new ResponseEntity(new SuccessResponse("Success", data), HttpStatus.OK);
    }

    @PostMapping("/customPronounce")
    public ResponseEntity<SuccessResponse> customPronounce(@RequestBody CustomPronounceRequest request) {
        Map<String, String> data = namePronounceService.customPronounceNameTest(request);
        return new ResponseEntity(new SuccessResponse("Success", data), HttpStatus.OK);
    }

    @GetMapping("/resetPronunciation/{employeeId}")
    public ResponseEntity<SuccessResponse> resetPronunciation(@PathVariable String employeeId) {
        Map<String, String> data = namePronounceService.resetPronunciation(employeeId);
        return new ResponseEntity(new SuccessResponse( "Success", data), HttpStatus.OK);

    }
    @PostMapping("/savePronunciation")
    public ResponseEntity<SuccessResponse> savePronunciation(@RequestBody CustomPronounceRequest request) {
        namePronounceService.customPronounceName(request);
        return new ResponseEntity(new SuccessResponse("Success", "Hello"), HttpStatus.OK);
    }

}
