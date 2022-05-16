package com.wf.hackathon.model;

import lombok.Data;

@Data
public class PronounceRequest {
    private String name;
    private String preferredName;
    private String country;
    private String employeeId;
}
