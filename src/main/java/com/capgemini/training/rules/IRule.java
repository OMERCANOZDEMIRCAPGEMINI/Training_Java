package com.capgemini.training.rules;

import com.capgemini.training.models.Level;

public interface IRule {
    boolean validate(Object input);
}
