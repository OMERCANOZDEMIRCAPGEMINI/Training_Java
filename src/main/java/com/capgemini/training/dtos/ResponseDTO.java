package com.capgemini.training.dtos;

import java.util.List;

public class ResponseDTO<T> {
    private T responseObject;
    private List<String> bindingErrors;
    private String error;

    public ResponseDTO() {
    }


    public ResponseDTO(T responseObject) {
        this.responseObject = responseObject;
    }

    public ResponseDTO(List<String> bindingErrors) {
        this.bindingErrors = bindingErrors;
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

    public List<String> getBindingErrors() {
        return bindingErrors;
    }

    public void setBindingErrors(List<String> bindingErrors) {
        this.bindingErrors = bindingErrors;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
