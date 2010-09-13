package com.quitit.android;

import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RemoteViews;

/**
 * The configuration screen for the QuitItProvider widget.
 */
public class AppWidgetConfigure extends Activity {
	// Debug tag
	private final String DEB_TAG 				= "AppWidgetConfigure.java";
	private static final String PREFS_NAME 		= "com.quitit.appwidget.AppWidget";
	private static final String PREF_PREFIX_KEY = "id_prefix_";
	
	GregorianCalendar mDate;
	StringBuilder mSb;
	
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    
    public AppWidgetConfigure() {
        super();
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	Log.d(DEB_TAG, "inside onCreate");
    	
    	// get the appWidgetId of the appWidget being configured
    	Intent launchIntent = getIntent();
    	Bundle extras 		= launchIntent.getExtras();
    	mAppWidgetId 		= extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
    			                                AppWidgetManager.INVALID_APPWIDGET_ID);
    	
    	Log.d(DEB_TAG, "Value of id is " + mAppWidgetId);
    	
    	/*
    	 * Set the result to CANCELED.  This will cause the widget host to cancel
    	 * out of the widget placement if they press the back button.
    	 */
    	 Intent cancelResultValue = new Intent();
    	 cancelResultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
    	 setResult(RESULT_CANCELED, cancelResultValue);
    	 
    	 //Set view
    	 setContentView(R.layout.quitit_configure);
    	 
    	 /*
    	  * Action with ok button
    	  */
    	 Button ok = (Button)findViewById(R.id.okbutton);
    	 
    	 ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Context context = AppWidgetConfigure.this;
				
				int month;
				int day;
				int year;
				
				//get the date from the DatePicker
				DatePicker dp = (DatePicker)findViewById(R.id.DatePicker);
				
				month 	= dp.getMonth();
				day 	= dp.getDayOfMonth();
				year 	= dp.getYear();
				
				//create string to store in prefs
				mSb = new StringBuilder();
				mSb.append(month +1).append("-")
				  .append(day).append("-")
				  .append(year).append(" ");
				
				Log.d(DEB_TAG, "STRINGBUILDER " + mSb);
				
				mDate = new GregorianCalendar(dp.getYear(), dp.getMonth(), dp.getDayOfMonth());
				saveStartPref(AppWidgetConfigure.this, mAppWidgetId, mSb.toString());
				
				// Push widget update to surface with newly set days
		    	AppWidgetManager awm = AppWidgetManager.getInstance(context);
		    	updateWidgetView(context, awm, mAppWidgetId);
		    	
		    	// Make sure we pass back the original appWidgetId
	            Intent resultValue = new Intent();
	            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
	            setResult(RESULT_OK, resultValue);
	            finish();
			}
    	 });
    	 
    	 
    	 /*
    	  * Action with cancel button
    	  */
    	 Button cancel = (Button)findViewById(R.id.cancelbutton);
    	 cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// finish sends the already configured cancel result and closes activity
				finish();
			}
		});
    }
    	
    // Write the startDate to the SharedPreferences object for this widget
    public void saveStartPref(Context context, int appWidgetId, String mSb2) {
    	Log.d(DEB_TAG, "Inside of saveStartPref");
    	Log.d(DEB_TAG, "appId is " + appWidgetId);
    	Log.d(DEB_TAG, "string pref is " + mSb2);
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, mSb2);
        prefs.commit();
    }
    
    //retrieve the startDate
    public String getStoredStartDate(Context context, int appWidgetId){
    	Log.d(DEB_TAG, "Inside getStoredStartDate");
        SharedPreferences sp = context.getSharedPreferences(PREFS_NAME, 0);
		String startPref = sp.getString(PREF_PREFIX_KEY + appWidgetId,  null);
		Log.d(DEB_TAG, "Value of startPref is " + startPref);
		
		return startPref = sp.getString(PREF_PREFIX_KEY + appWidgetId, null);
    }
    
    public void updateWidgetView(Context context, AppWidgetManager appWidgetManager,
            int appWidgetId) {
    	Log.d(DEB_TAG, "Inside updateAppWidget");
    	String dayCount;
        
    	String date = getStoredStartDate(context, appWidgetId);
    	Log.d(DEB_TAG, "date is " + date);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        
        if(date == null){
        	views.setTextViewText(R.id.days, "wtf");
        }
        else{
        
        dayCount = TimeDifference.getDaysDifference(date);
        Log.d(DEB_TAG, "Value of dayCount is " + dayCount);
        views.setTextViewText(R.id.days, dayCount);
        
        Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.scottagarman.com"));  
        //Intent openApp = new Intent(context, DayCount.class);
        //openApp.putExtra("widgetId", appWidgetId);
        Log.d(DEB_TAG, "$$$$$$$$$id is " + appWidgetId);
        PendingIntent pendingAppIntent = PendingIntent.getActivity(context, 0, viewIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        views.setOnClickPendingIntent(R.id.openFull, pendingAppIntent);
        
        // Tell the widget manager
        appWidgetManager.updateAppWidget(appWidgetId, views);       	
        	
        }

    }
}
