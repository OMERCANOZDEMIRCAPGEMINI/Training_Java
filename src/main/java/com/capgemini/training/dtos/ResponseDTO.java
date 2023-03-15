package com.capgemini.training.dtos;

import java.util.List;

public class ResponseDTO {
    private Object responseObject;
    private List<String> errorMessages;
    private String error;

    public ResponseDTO() {
    }



    public ResponseDTO(Object responseObject){
        this.responseObject = responseObject;
    }

    public ResponseDTO(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public ResponseDTO(String error) {
        this.error = error;
    }

    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Object responseObject) {
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
