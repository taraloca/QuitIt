package com.quitit.android;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.util.Log;

/* Calculate time increments from start date to current date */
public class TimeDifference {
	private final static String DEB_TAG = "TimeDifference.java";

/**
 * Get Time Diff 
 * attempting commit and push
 *     
 */
    public static String getMilisecondsDifference(String startDate){
		Long miliCurrent = getCurrentTime();
    	Long miliStart = getStartTime(startDate);

    	return  Long.toString(miliCurrent - miliStart);
    }
    public static String getSecondsDifference(String startDate){
		Long miliCurrent = getCurrentTime();
    	Long miliStart = getStartTime(startDate);

    	return  Long.toString((miliCurrent - miliStart)/1000);
    }   
    public static String getMinutesDifference(String startDate){
		Long miliCurrent = getCurrentTime();
    	Long miliStart = getStartTime(startDate);

    	return  Long.toString((miliCurrent - miliStart)/(60*1000));
    }
    public static String getHoursDifference(String startDate){
		Long miliCurrent = getCurrentTime();
    	Long miliStart = getStartTime(startDate);

    	return  Long.toString((miliCurrent - miliStart)/(60*60*1000));
    }
    public static String getDaysDifference(String startDate){
		Long miliCurrent = getCurrentTime();
    	Long miliStart = getStartTime(startDate);

    	return  Long.toString((miliCurrent - miliStart)/(24*60*60*1000));
    }   
/**
 * Helper Methods
 * 
 */   
    private static Long getCurrentTime(){
    	Date currentDate = new Date();
    	currentDate.getDate();
	    Calendar calDateCurrent = Calendar.getInstance();
	    calDateCurrent.setTime(currentDate);
	    return calDateCurrent.getTimeInMillis();
    }
    private static Long getStartTime (String startDate){
    	Log.d(DEB_TAG, "INSIDE SET CALENDARS");
    	SimpleDateFormat sdf 	= new SimpleDateFormat("M-d-yyyy");
    	sdf.setTimeZone(TimeZone.getDefault());
    	Date sd 				= null;   
    	try {
			sd = sdf.parse(startDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// set calendar objects to start and current
		Calendar calDateStart = Calendar.getInstance();
	    calDateStart.setTime(sd); 	
	    return calDateStart.getTimeInMillis();
    }
}
