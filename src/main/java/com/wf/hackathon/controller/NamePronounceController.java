package com.wf.hackathon.controller;

import com.wf.hackathon.model.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NamePronounceController {

    public ResponseEntity<SuccessResponse> pronounceName() {
        return new ResponseEntity(new SuccessResponse(200, "Success", "Hello"), HttpStatus.OK);
    }
}
