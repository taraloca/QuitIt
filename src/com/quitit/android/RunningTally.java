package com.quitit.android;

import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RunningTally extends Activity {
	// Debug tag
	private final String DEB_TAG = "RunningTally.java";
	private TimeBreakDown bd;
	private TimeZone tz  = TimeZone.getDefault();
	private TextView tvDays;
    private TextView tvMonths;
    private TextView tvYrs;
    private TextView tvHours;
    private TextView tvMins;
    private TextView tvSecs;
    private TextView tvMilSecs;
    private Button btnExtras;
    
    private Timer autoUpdate;
    
   // private TimeDifference td;
    
    final Context context = RunningTally.this;
    private static final String PREFS_NAME = "com.quitit.appwidget.AppWidget";
    private static final String PREF_PREFIX_KEY = "id_prefix_";
    
    String appWidgetId;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateTally();
        /*int id = 0;
        Log.d(DEB_TAG, "Inside RUNNINGTALLY ONCREATE");
        setContentView(R.layout.running_tally);
        
        // Find the widget id from the intent. 
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
        	Log.d(DEB_TAG, "INSIDE IF EXTRAS NOT NULL");
        	id = extras.getInt("widgetId");
        	Log.d(DEB_TAG, "Value of widget id grabbed from intent " + id);
        }
        
        SharedPreferences prefs = context.getSharedPreferences(QUITIT.Preferences.PREF_NAME, 0);
        String prefix = prefs.getString(QUITIT.Preferences.WIDGET_PREFIX + id, null);

        bd = new TimeBreakDown();
        bd.calculate(prefix);
        
        Log.d(DEB_TAG, "########Value of prefix is " + prefix);
        Log.d(DEB_TAG, "#########appWidgetId" + id);

        tvDays 		= (TextView)findViewById(R.id.days);
        //tvMonths	= (TextView)findViewById(R.id.months);
        //tvYrs		= (TextView)findViewById(R.id.years);
        tvHours		= (TextView)findViewById(R.id.hours);
        tvMins		= (TextView)findViewById(R.id.minutes);
        tvSecs		= (TextView)findViewById(R.id.seconds);
        tvMilSecs	= (TextView)findViewById(R.id.millisecs);
        btnExtras	= (Button)findViewById(R.id.btnExtra);
        
         tvDays.setText(TimeDifference.getDaysDifference(prefix));
        tvHours.setText(TimeDifference.getHoursDifference(prefix));
        tvMins.setText(TimeDifference.getMinutesDifference(prefix));
        tvSecs.setText(TimeDifference.getSecondsDifference(prefix));
        tvMilSecs.setText(TimeDifference.getMilisecondsDifference(prefix));
        
        tvDays.setText(Double.toString(bd.daysOld));
        tvHours.setText(Double.toString(bd.hrsOld));
        tvMins.setText(Double.toString(bd.minsOld));
        tvSecs.setText(Double.toString(bd.secOld));
        tvMilSecs.setText(Double.toString(bd.msOld));
        
        btnExtras.setOnClickListener(refreshScreen);*/
        
        // Set the default time zone
        TimeZone.setDefault(tz);
        Toast.makeText(this, tz.getDisplayName(), Toast.LENGTH_LONG).show();

    }
    @Override
    public void onResume() {
    	super.onResume();
    	autoUpdate = new Timer();
    	autoUpdate.schedule(new TimerTask() {
	      @Override
	      public void run() {
	    	  	runOnUiThread(new Runnable() {
	    	  		public void run() {
	    	  			updateTally();
	    	  		}
	    });
      }
     }, 0, 1000); // updates each second
    }
    
    @Override
    public void onPause() {
    	autoUpdate.cancel();
    	super.onPause();
    }


    private void updateTally(){
    	int id = 0;
        Log.d(DEB_TAG, "Inside RUNNINGTALLY ONCREATE");
        setContentView(R.layout.running_tally);
        
        // Find the widget id from the intent. 
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
        	Log.d(DEB_TAG, "INSIDE IF EXTRAS NOT NULL");
        	id = extras.getInt("widgetId");
        	Log.d(DEB_TAG, "Value of widget id grabbed from intent " + id);
        }
        
        SharedPreferences prefs = context.getSharedPreferences(QUITIT.Preferences.PREF_NAME, 0);
        String prefix = prefs.getString(QUITIT.Preferences.WIDGET_PREFIX + id, null);

        bd = new TimeBreakDown();
        bd.calculate(prefix);
        
        Log.d(DEB_TAG, "########Value of prefix is " + prefix);
        Log.d(DEB_TAG, "#########appWidgetId" + id);

        tvDays 		= (TextView)findViewById(R.id.days);
        //tvMonths	= (TextView)findViewById(R.id.months);
        //tvYrs		= (TextView)findViewById(R.id.years);
        tvHours		= (TextView)findViewById(R.id.hours);
        tvMins		= (TextView)findViewById(R.id.minutes);
        tvSecs		= (TextView)findViewById(R.id.seconds);
        //tvMilSecs	= (TextView)findViewById(R.id.millisecs);
        //btnExtras	= (Button)findViewById(R.id.btnExtra);
        
        /* tvDays.setText(TimeDifference.getDaysDifference(prefix));
        tvHours.setText(TimeDifference.getHoursDifference(prefix));
        tvMins.setText(TimeDifference.getMinutesDifference(prefix));
        tvSecs.setText(TimeDifference.getSecondsDifference(prefix));
        tvMilSecs.setText(TimeDifference.getMilisecondsDifference(prefix));*/
        
        tvDays.setText(Double.toString(bd.daysOld));
        tvHours.setText(Double.toString(bd.hrsOld));
        tvMins.setText(Double.toString(bd.minsOld));
        tvSecs.setText(Double.toString(bd.secOld));
        tvMilSecs.setText(Double.toString(bd.msOld));
    }

	/* ------------------ LocationListener Interface functions ---------------------- */
	public void onLocationChanged(Location location) {
		
		Log.d(DEB_TAG, "*************In 'onLocationChanged'.");
		
		// get new time zone
		TimeZone tz  = TimeZone.getDefault();
		
		// Set the default time zone
        TimeZone.setDefault(tz);
        Toast.makeText(this, tz.getDisplayName(), Toast.LENGTH_LONG).show();
		
	}
	private View.OnClickListener refreshScreen = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent myIntent = new Intent(context, RunningTally.class);
	        myIntent.putExtra("foo", "wtf");
			startActivity(myIntent);
	        
		}
	};
	
}
