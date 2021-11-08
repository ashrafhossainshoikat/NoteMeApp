package com.ashraf.rokomariassignment.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utility {


    public static String getDateFromMillisecond(long millisecond,
                                                SimpleDateFormat dateFormat) {

        // Create a calendar object that will convert the date and time value in
        // milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisecond);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        String dateStr = dateFormat.format(calendar.getTime());

        return dateStr;
    }
}
