package com.capgemini.training.rules;

import com.capgemini.training.models.Level;
import org.springframework.data.util.Pair;


public class LevelRule implements IRule {
    private Level counselorLevel;
    private Level employeeLevel;

    public LevelRule() {

    }

    @Override
    public boolean validate(Object input) {
        if (input instanceof Pair levelRule) {
            counselorLevel = (Level) levelRule.getFirst();
            employeeLevel = (Level) levelRule.getSecond();
            return counselorLevel.compareTo(Level.C1) >= 0 && counselorLevel.compareTo(employeeLevel) > 0;
        }
        return false;
    }

    public Level getCounselorLevel() {
        return counselorLevel;
    }

    public void setCounselorLevel(Level counselorLevel) {
        this.counselorLevel = counselorLevel;
    }

    public Level getEmployeeLevel() {
        return employeeLevel;
    }

    public void setEmployeeLevel(Level employeeLevel) {
        this.employeeLevel = employeeLevel;
    }
}
