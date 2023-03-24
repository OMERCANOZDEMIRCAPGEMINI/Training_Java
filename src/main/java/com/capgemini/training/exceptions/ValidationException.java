package com.capgemini.training.exceptions;

import java.util.Collections;
import java.util.List;

public class ValidationException extends Exception {
    private List<String> errors;

    public ValidationException(String message) {
        super();
        this.errors = Collections.singletonList(message);
    }

    public ValidationException(List<String> errors) {
        super();
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
