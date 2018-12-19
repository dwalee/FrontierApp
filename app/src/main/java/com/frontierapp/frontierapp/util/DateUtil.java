package com.frontierapp.frontierapp.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static String timeElapsed(Date newDate){
        long difference = Calendar.getInstance().getTimeInMillis() - newDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long weeksInMilli = daysInMilli * 7;
        double monthsInMilli = weeksInMilli * 4.5;
        long yearsInMilli = weeksInMilli * 52;

        long elapsedYears = difference / yearsInMilli;
        long elapsedMonths = difference / (long) monthsInMilli;
        long elapsedWeeks = difference / weeksInMilli;
        long elapsedDays = difference / daysInMilli;
        long elapsedHours = difference / hoursInMilli;
        long elapsedMinutes = difference / minutesInMilli;
        long elapsedSeconds = difference / secondsInMilli;

        String ago = "ago";
        if(elapsedYears > 0){
            if(elapsedYears > 1)
                return elapsedYears + " years " + ago;
            return elapsedYears + " year " + ago;
        }else if(elapsedMonths > 0){
            if(elapsedMonths > 1 && elapsedMonths != 12)
                return elapsedMonths + " months " + ago;

            return elapsedMonths + " month " + ago;
        }else if(elapsedWeeks > 0){
            if(elapsedWeeks > 1) {
                return elapsedWeeks + " weeks " + ago;
            }
            return elapsedWeeks + " week " + ago;
        }else if(elapsedDays > 0){
            if(elapsedDays > 1)
                return elapsedDays + " days " + ago;
            return elapsedDays + " day " + ago;
        }else if(elapsedHours > 0){
            if(elapsedHours > 1)
                return elapsedHours + " hours " + ago;
            return elapsedHours + " hour " + ago;
        }else if(elapsedMinutes > 0 ){
            if(elapsedMinutes > 1)
                return elapsedMinutes + " minutes " + ago;
            return elapsedMinutes + " minute " + ago;
        }else if(elapsedSeconds > 0){
            if(elapsedSeconds > 1)
                return elapsedSeconds + " seconds " + ago;
            return elapsedSeconds + " second " + ago;
        }

        return "";
    }
}
