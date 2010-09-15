package com.quitit.android;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.util.Log;

public class TimeBreakDown {
	
	double timeOld;
	double secTimeOld;
	double secondsOld;
	double msPerDay;
	double daysOld_e;
	double daysOld;
	double hrsOld_e;
	double hrsOld;
	double minsOld_e;
	double minsOld;
	double secOld_e;
	double secOld;
	double msOld_e;
	double msOld;
	double breathTimeOld;
	double breathsOld;
	Date today;
	Date cleanDay;
	
	public void calculate(String startDate){
		today 		= new Date();  
	    cleanDay 	= getStartTime(startDate);         
	    timeOld 	= (today.getTime() - cleanDay.getTime());  
	    msPerDay 	= 24 * 60 * 60 * 1000 ;
	    
	    daysOld_e 	= timeOld / msPerDay;         
	    daysOld 	= Math.floor(daysOld_e);         
	    hrsOld_e 	= (daysOld_e - daysOld)*24;
	    hrsOld 		= Math.floor(hrsOld_e); 
	    minsOld_e	= (hrsOld_e - hrsOld)*60;
	    minsOld 	= Math.floor(minsOld_e);
	    secOld_e 	= (minsOld_e - minsOld)*60;
	    secOld		= Math.floor(secOld_e);
	    msOld_e		= (secOld_e - secOld)*1000;
	    msOld		= Math.floor(msOld_e);
	}
	
	private Date getStartTime (String startDate){
    	//Log.d(DEB_TAG, "INSIDE SET CALENDARS");
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
		
	    return sd;
    }
	/*
	 * <!-- // Hide from Old Browsers
						// NOTE: The algorithm was broken, and was fixed May 27th, 2002.
						// If you have an older version, then replace it with this one.
			
						// This function tells you how many days are in the given month.
						// Remember that inMonth is 0-based (i.e. January is 0, December is 11).
						// The year must be a complete year (1980, as opposed to 80)
						function DaysInMonth(inYear,inMonth)
							{
							// We measure from the first of the current month to the first of the next month.
							var MonthOf=new Date(inYear,inMonth,1);
							inMonth++;
							if(inMonth>11)	// We may need to roll over to the next year.
								{
								inMonth=0;
								inYear++;
								}
							var MonthAfter=new Date(inYear,inMonth,1);
							// The 86400000 is how many milliseconds are in one day. getTime() returns milliseconds.
							return parseInt((MonthAfter.getTime()-MonthOf.getTime())/86400000);
							}
						
						// This function is called when the form is submitted. The user either clicks the "Calculate"
						// button, or presses the "Return/Enter" key.
						function NewDateEntered(inForm)
							{
							// This is today.
							var	eDate = new Date();
							
							// We save this for use a bit later.
							var EndYear = eDate.getFullYear();
							var EndMonth = eDate.getMonth();
							var EndDay = eDate.getDate();
							
							// We parse the entered date into month, day and year.
							// This is why we are so literal about the format.
							var month = inForm.MONTH.value;
							var day = inForm.DAY.value;
							var year = inForm.YEAR.value;
							
							// This is how we adjust for two-digit years.
							// Less than 1953 is impossible in NA. They must mean after 2000.
							if ( year < 53 )
								year = parseInt(year)+2000;
							
							// Otherwise, we add 1900.
							if ( year < 100 )
								year = parseInt(year)+1900;
							
							// Make sure we're legal. Can't have a clean year later than this year.
							// This won't catch people entering clean dates in months of this year past today, but
							// most of the mistakes made will be in the year.
							if((year>1952)&&(year<=EndYear))
								{
								// Months in JavaScript are 0-based (0 is January, 11 is December)
								var inDate = new Date(year,month-1,day);
								
								var StartYear = inDate.getFullYear();
								var StartMonth = inDate.getMonth();
								var StartDay = inDate.getDate();
								
								// We first find out how many total days have passed since the clean date.
								var	timeNow = parseInt(eDate.getTime());
								var	timeThen = parseInt(inDate.getTime());
								
								// 90 days or less, we actually count days.
								var TotalDays = parseInt((timeNow-timeThen)/86400000);
								var	Days = TotalDays;
								var Years = 0;
								var Months = 0;
								
								// More than 90 days, we count by date
								// This can be a bit weird. Check out our explanation of how clean time
								// is calculated (http://www.LongIslandNA.com/main/cleantime/HowItsDone.htm)
								if(TotalDays>90)
									{
									// We first see how many years have passed.
									Years = EndYear-StartYear;
									
									// We then check months.
									Months = EndMonth-StartMonth;
									
									// If this month is earlier than the cleandate month, we reduce the
									// year by one, and figure out how many months it's been since the
									// last annual anniversary of the clean date.
									if (Months<0)
										{
										Months = (11-parseInt(StartMonth));
										Months += (parseInt(EndMonth))+1;
										if (Years>0) Years--;
										}
									
									// We do a similar thing with days.
									Days=EndDay-StartDay;
									
									// We need to figure out how many days it was between the last monthly
									// anniversary and today.
									if(Days<0)
										{
										Months--;
										EndMonth--;
										if(EndMonth<0)
											{
											EndMonth=11;
											EndYear--;
											}
										
										// If we went too far with the months, we go back to the previous year
										if (Months<0)
											{
											Months = (11-parseInt(StartMonth));
											Months += (parseInt(EndMonth))+1;
											if (Years>0) Years--;
											}
										
										Days = DaysInMonth(EndYear,EndMonth)-StartDay;
										Days += parseInt(EndDay);
										}
									}
								
								inForm.Years.value = Years;
								inForm.Months.value = Months;
								inForm.Days.value = Days;
								inForm.TotalDays.value = TotalDays;
								}
							else	// We blank the fields if we don't like the answer
								{
								inForm.Years.value = "";
								inForm.Months.value = "";
								inForm.Days.value = "";
								inForm.TotalDays.value = "";
								}
							return false;
							}
							// Un-hide -->

	 */
}
