package com.wf.hackathon.exception;

public class NamePronounceException extends RuntimeException {
    private String message;

    public NamePronounceException(String message) {
        super(message);
        this.message = message;
    }
}
