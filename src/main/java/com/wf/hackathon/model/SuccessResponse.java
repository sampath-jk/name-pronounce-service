package com.wf.hackathon.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SuccessResponse<T> {

    private String message;
    private T data;

    public SuccessResponse(String message, T data) {
        this.message = message;
        this.data= data;
    }

}
