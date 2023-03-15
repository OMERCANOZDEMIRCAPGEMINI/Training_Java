package com.capgemini.training.dtos;

import java.util.List;

public class ResponseDTO<T> {
    private T responseObject;
    private List<String> errorMessages;
    private String error;

    public ResponseDTO() {
    }


    public ResponseDTO(T responseObject) {
        this.responseObject = responseObject;
    }

    public ResponseDTO(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public ResponseDTO(String error) {
        this.error = error;
    }


    public T getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(T responseObject) {
        this.responseObject = responseObject;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
