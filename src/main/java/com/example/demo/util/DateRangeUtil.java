package com.example.demo.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateRangeUtil {

    /**
     * Generates a list of all dates between the start and end date (inclusive).
     * This is used by the CapacityAnalysisService to check headcount for every day 
     * in a requested range.
     *
     * @param start The beginning of the range
     * @param end   The end of the range
     * @return A list of LocalDate objects from start to end
     */
    public static List<LocalDate> daysBetween(LocalDate start, LocalDate end) {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate current = start;
        
        // Ensure we don't enter an infinite loop if dates are null
        if (start == null || end == null) {
            return dates;
        }

        // Add dates sequentially until the end date is reached
        while (!current.isAfter(end)) {
            dates.add(current);
            current = current.plusDays(1);
        }
        
        return dates;
    }
}