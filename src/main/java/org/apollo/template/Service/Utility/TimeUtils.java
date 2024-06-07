package org.apollo.template.Service.Utility;

import org.apollo.template.Model.Booking;
import org.jetbrains.annotations.NotNull;

import java.sql.Time;

public class TimeUtils {
    /**
     * getStringTimeFormatted takes a Booking object and returns with a formated string as HH:MM - HH:MM
     * @param booking
     * @return String
     */
    public static @NotNull String getStringTimeFormatted(Booking booking) {
        Time starttime = booking.getStartTime();
        Time endtime = booking.getEndTime();

        if (starttime == null || endtime == null) {
            return "null";
        }

        //Ensures that the String Start time and end appears as HH:MM - HH:MM
        StringBuilder startEndTime = new StringBuilder();
        startEndTime.append(starttime.toString().substring(0, 5));
        startEndTime.append(" - ");
        startEndTime.append(endtime.toString().substring(0, 5));
        String time = startEndTime.toString();
        return time;
    }
}
