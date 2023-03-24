package com.capgemini.training.rules;


public interface Rule {
    boolean validate(Object input);
    String errorMessage();
}
