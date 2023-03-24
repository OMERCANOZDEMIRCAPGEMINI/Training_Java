package com.capgemini.training.rules.inputs;

import com.capgemini.training.models.Level;

public class CounselorCounseleeLevelInput {
    private Level CounselorLevel;
    private Level CounseleeLevel;

    public CounselorCounseleeLevelInput(Level counselorLevel, Level counseleeLevel) {
        CounselorLevel = counselorLevel;
        CounseleeLevel = counseleeLevel;
    }

    public Level getCounselorLevel() {
        return CounselorLevel;
    }

    public Level getCounseleeLevel() {
        return CounseleeLevel;
    }
}
