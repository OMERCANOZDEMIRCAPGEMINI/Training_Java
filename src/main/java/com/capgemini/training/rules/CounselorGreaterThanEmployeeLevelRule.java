package com.capgemini.training.rules;

import com.capgemini.training.models.Level;
import com.capgemini.training.rules.inputs.CounselorCounseleeLevelInput;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Component
public class CounselorGreaterThanEmployeeLevelRule implements Rule {

    @Override
    public boolean validate(Object input) {
        if (input instanceof CounselorCounseleeLevelInput counselorCounseleeLevelInput) {
            Level counselorLevel = counselorCounseleeLevelInput.getCounselorLevel();
            Level counseleeLevel = counselorCounseleeLevelInput.getCounseleeLevel();
            return counselorLevel.compareTo(counseleeLevel) > 0;
        }
        return false;
    }

    @Override
    public String errorMessage() {
        return "Counselor level must be greater than employer level";
    }
}
