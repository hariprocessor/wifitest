package com.nunuplanet.test;

import android.util.Log;

import java.util.Calendar;

/**
 * Created by hari on 2016-10-08.
 */
public class TimeStamp {
    public static long getTimeStamp(){
        return System.currentTimeMillis()/1000;
    }

    public static long getTodayTimeStamp(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Log.i("hari", String.valueOf(calendar.getTimeInMillis()/1000000));
        return calendar.getTimeInMillis()/1000;
    }

    public static long getYesterdayTimeStamp(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTimeInMillis()/1000;
    }
}
