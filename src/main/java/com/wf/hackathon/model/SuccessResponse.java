package com.wf.hackathon.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SuccessResponse<T> {

    private String message;
    private Integer statusCode;
    private T data;

    public SuccessResponse(Integer statusCode, String message, T data){
        this.statusCode= statusCode;
        this.message = message;
        this.data= data;
    }

    public SuccessResponse(Integer statusCode, String message){
        this.statusCode= statusCode;
        this.message = message;
    }

    public SuccessResponse(String message, T data) {
        this.message = message;
        this.data= data;
    }

}
