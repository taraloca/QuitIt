package com.quitit.android;

import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
//	private static final String PREFS_NAME 		= "com.quitit.appwidget.AppWidget";
//	private static final String PREF_PREFIX_KEY = "id_prefix_";
	
	GregorianCalendar mDate;
	StringBuilder mSb;
	
	private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    
    public AppWidgetConfigure() {
        super();
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	Log.d(DEB_TAG, "inside onCreate");
//    	startService(new Intent(AppWidgetConfigure.this,
//                NotifyingService.class));
    	// get the appWidgetId of the appWidget being configured
    	Intent launchIntent = getIntent();
    	Bundle extras 		= launchIntent.getExtras();
    	Log.d(DEB_TAG, "get extras oncreate " + extras);
    	//mAppWidgetId = extras.getInt("widgetId");
    	Log.d(DEB_TAG, "Inside onCreate mAppWidgetId is " + mAppWidgetId);
    	if(extras != null){
	    	if(extras.getInt("widgetId") != 0){
	    		//mAppWidgetId = extras.getInt("widgetId");
	    		mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

	        	Log.d(DEB_TAG, "Inside onCreate mAppWidgetId is " + mAppWidgetId);
	        	/*
	        	 * Set the result to CANCELED.  This will cause the widget host to cancel
	        	 * out of the widget placement if they press the back button.
	        	 */
	        	 Intent cancelResultValue = new Intent();
	        	 cancelResultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
	        	 setResult(RESULT_CANCELED, cancelResultValue);
	    	}else{
	    		mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
	                    AppWidgetManager.INVALID_APPWIDGET_ID);
	    		Log.d(DEB_TAG, "Inside onCreate mAppWidgetId is " + mAppWidgetId);
	    	}
    	}else {
    		// only launch if it's for configuration
            // Note: when you launch for debugging, this does prevent this
            // activity from running. We could also turn off the intent
            // filtering for main activity.
            // But, to debug this activity, we can also just comment the
            // following line out.
    		finish();
    	}
    	
    	 
    	 //Set view
    	 setContentView(R.layout.quitit_configure);
    	 
    	 /*
    	  * Action with ok button
    	  */
    	 Button ok = (Button)findViewById(R.id.okbutton);
    	 
    	 ok.setOnClickListener(new OnClickListener() {
			//@Override
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
				QuitItProvider.saveStartPref(AppWidgetConfigure.this, mAppWidgetId, mSb.toString());
				
				// Push widget update to surface with newly set days
		    	AppWidgetManager awm = AppWidgetManager.getInstance(context);
		    	QuitItProvider.updateWidgetView(context, awm, mAppWidgetId);
		    	
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
			
			//@Override
			public void onClick(View v) {
				// finish sends the already configured cancel result and closes activity
				finish();
			}
		});
    }
}
