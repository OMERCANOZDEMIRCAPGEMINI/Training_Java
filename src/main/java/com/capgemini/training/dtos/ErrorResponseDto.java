package com.capgemini.training.dtos;

import java.util.Collections;
import java.util.List;

public class ErrorResponseDto {
    private List<String> messages;
    private int errorCode;

    public ErrorResponseDto(String message, int errorCode) {
        this.messages = Collections.singletonList(message);
        this.errorCode = errorCode;
    }
    public ErrorResponseDto(List<String> messages, int errorCode){
        this.messages = messages;
        this.errorCode = errorCode;
    }
    public List<String> getMessages() {
        return messages;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
