package com.capgemini.training.rules;


import java.util.List;

public class RuleEngine {
    List<IRule> rules;
    public RuleEngine(List<IRule> rules) {
        this.rules = rules;
    }
    public boolean validate(Object input) {
        return rules.stream().allMatch(rule -> rule.validate(input));
    }
}
