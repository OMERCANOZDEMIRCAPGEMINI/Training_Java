package com.capgemini.training.rules;

import com.capgemini.training.models.Level;
import com.capgemini.training.rules.inputs.CounselorCounseleeLevelInput;
import org.springframework.stereotype.Component;

@Component
public class CounselorMinimumLevelRule implements Rule {

    @Override
    public boolean validate(Object input) {
        if (input instanceof CounselorCounseleeLevelInput counselorCounseleeLevelInput) {
            Level counselorLevel = counselorCounseleeLevelInput.getCounselorLevel();
            return counselorLevel.compareTo(Level.C2) > 0;
        }
        return false;
    }

    @Override
    public String errorMessage() {
        return "Employee must be level greater than C2 to be a counselor";
    }
}
