package com.capgemini.training.rules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RuleEngine {
    @Autowired
    private List<Rule> rules;

    public List<String> validate(Object input) {
        return rules.stream()
                .filter(rule -> !rule.validate(input))
                .map(Rule::errorMessage)
                .collect(Collectors.toList());
    }
}
