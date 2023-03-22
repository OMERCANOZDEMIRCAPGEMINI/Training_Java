package com.capgemini.training.rules;

import com.capgemini.training.models.Level;
import org.springframework.data.util.Pair;

public class LevelRule implements IRule {
    private Level counselorLevel;
    private Level employeeLevel;

    public LevelRule(Level counselorLevel, Level employeeLevel) {
        this.counselorLevel = counselorLevel;
        this.employeeLevel = employeeLevel;
    }

    @Override
    public boolean validate(Object input) {
        if (input instanceof Pair) {
            Pair pair = (Pair) input;
            Level counselorLevel = (Level) pair.getFirst();
            Level employeeLevel = (Level) pair.getSecond();
            return counselorLevel.compareTo(Level.C1) >= 0 && counselorLevel.compareTo(employeeLevel) > 0;
        }
        return false;
    }
}
