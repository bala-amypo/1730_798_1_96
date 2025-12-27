package com.example.demo.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateRangeUtil {
    public static List<LocalDate> daysBetween(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate current = startDate;
        
        while (!current.isAfter(endDate)) {
            dates.add(current);
            current = current.plusDays(1);
        }
        
        return dates;
    }
}