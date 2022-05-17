package com.wf.hackathon.exception;

import com.wf.hackathon.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NamePronounceException.class)
    public ResponseEntity<Object> handleNamePronounceException(NamePronounceException e) {
        log.error(e.getLocalizedMessage());
        e.printStackTrace();
        ErrorResponse response = new ErrorResponse("Exception while processing request");
        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOtherExceptions(Exception e) {
        log.error(e.getLocalizedMessage());
        e.printStackTrace();
        ErrorResponse response = new ErrorResponse("Exception while processing request");
        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
