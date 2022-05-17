package com.wf.hackathon.controller;

import java.util.Map;

import com.wf.hackathon.model.CustomPronounceRequest;
import com.wf.hackathon.model.PronounceRequest;
import com.wf.hackathon.model.SuccessResponse;
import com.wf.hackathon.service.NamePronounceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;

@SecurityRequirement(name = "NamePronunciationSecurity")
@RestController
@Slf4j
public class NamePronounceController {

    private NamePronounceService namePronounceService;

    public NamePronounceController(NamePronounceService namePronounceService) {
        this.namePronounceService = namePronounceService;
    }

    @Operation(summary = "Name pronunciation", description = "Provides employee name standard/custom pronunciation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employees name pronunciation received"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal error") })
    @PostMapping("/pronounceName")
    public ResponseEntity<SuccessResponse> pronounceName(@RequestBody PronounceRequest request) {
        Map<String, String> data = namePronounceService.pronounceName(request);
        return new ResponseEntity(new SuccessResponse("Success", data), HttpStatus.OK);
    }

    @Operation(summary = "Custom Name pronunciation", description = "Provides employee name recording & custom pronunciation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employees name pronunciation received"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal error") })
    @PostMapping("/customPronounce")
    public ResponseEntity<SuccessResponse> customPronounce(@RequestBody CustomPronounceRequest request) {
        log.debug("Entered customPronounce controller");
        Map<String, String> data=null;
        try {
            data = namePronounceService.customPronounceNameTest(request);
            log.debug("audio" + data);
        } catch (Exception e) {
            log.debug(e.getLocalizedMessage());
            e.printStackTrace();
        }

        return new ResponseEntity(new SuccessResponse("Success", data), HttpStatus.OK);
    }

    @Operation(summary = "Reset user recording", description = "Reset user pronunciation data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employees name pronunciation reset successful"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal error") })
    @GetMapping("/resetPronunciation/{employeeId}")
    public ResponseEntity<SuccessResponse> resetPronunciation(@PathVariable String employeeId) {
        Map<String, String> data = namePronounceService.resetPronunciation(employeeId);
        return new ResponseEntity(new SuccessResponse("Success", data), HttpStatus.OK);

    }

    @Operation(summary = "Save pronunciation recording", description = "Saves Name pronunciation recording")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employees name pronunciation saved successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal error") })
    @PostMapping("/savePronunciation")
    public ResponseEntity<SuccessResponse> savePronunciation(@RequestBody CustomPronounceRequest request) {
        namePronounceService.customPronounceName(request);
        return new ResponseEntity(new SuccessResponse("Success", "Hello"), HttpStatus.OK);
    }

}
