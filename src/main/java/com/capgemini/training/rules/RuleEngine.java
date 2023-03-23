package com.capgemini.training.rules;


import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RuleEngine {
    List<IRule> rules;

    public RuleEngine() {
        this.rules = new ArrayList<>();
    }

    public boolean validate(Object input) {
        boolean isValid = rules.stream().allMatch(rule -> rule.validate(input));
        rules.clear();
        return isValid;
    }

    public void addRule(IRule rule) {
        rules.add(rule);
    }

}
