package com.wf.hackathon.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CustomPronounceRequest {

    @NotBlank
    private String employeeId;

//    @NotBlank
    private String name;
    @NotBlank
    private String country;
    @NotBlank
    private String speed;
    @NotBlank
    private String gender;
    @NotBlank
    private String audio;

}
