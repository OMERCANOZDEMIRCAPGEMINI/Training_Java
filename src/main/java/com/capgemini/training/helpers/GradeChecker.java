package com.capgemini.training.helpers;

import com.capgemini.training.models.Level;

public class GradeChecker {

    public static boolean checkGradeCounselorConselee(Level counselorLevel, Level employeeLevel){
        return counselorLevel.compareTo(Level.C1) >= 0 && counselorLevel.compareTo(employeeLevel) > 0;
    }

}