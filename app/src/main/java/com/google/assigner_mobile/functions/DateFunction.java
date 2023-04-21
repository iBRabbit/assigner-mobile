package com.google.assigner_mobile.functions;


import android.annotation.SuppressLint;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateFunction {

    public Integer calculateDateDiffFromNow(LocalDate deadline) {
        LocalDate today = LocalDate.now();
        Long dueInDays = (Long) ChronoUnit.DAYS.between(today, deadline);
        return dueInDays.intValue();
    }

}
