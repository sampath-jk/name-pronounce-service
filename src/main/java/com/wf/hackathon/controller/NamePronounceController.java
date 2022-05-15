package com.wf.hackathon.controller;

import com.wf.hackathon.model.PronounceRequest;
import com.wf.hackathon.model.SuccessResponse;
import com.wf.hackathon.service.NamePronounceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NamePronounceController {

    private NamePronounceService namePronounceService;

    public NamePronounceController(NamePronounceService namePronounceService) {
        this.namePronounceService = namePronounceService;
    }

    @PostMapping("/pronounceName")
    public ResponseEntity<SuccessResponse> pronounceName(@RequestBody PronounceRequest request) {
        namePronounceService.pronounceName(request);
        return new ResponseEntity(new SuccessResponse(200, "Success", "Hello"), HttpStatus.OK);
    }

    @PostMapping("/customPronounce")
    public ResponseEntity<SuccessResponse> customPronounce() {
        return new ResponseEntity(new SuccessResponse(200, "Success", "Hello"), HttpStatus.OK);
    }
}
