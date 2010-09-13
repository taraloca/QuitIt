package com.quitit.android;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

public class QuitItProvider extends AppWidgetProvider {
	private final String DEB_TAG 				= "QuitItProvider.java";
	private static final String PREFS_NAME 		= "com.quitit.appwidget.AppWidget";
	private static final String PREF_PREFIX_KEY = "id_prefix_";
	
	@Override
    public void onDeleted(Context context, int[] appWidgetIds){
    	super.onDeleted(context, appWidgetIds);
    }
	
	@Override
	public void onDisabled(Context context){
		super.onDisabled(context);
	}
	
	@Override
	public void onEnabled(Context context){
		super.onEnabled(context);
	}
	
	@Override
	public void onReceive(Context context, Intent intent){
		
		// fix for onDelete not working in Android 1.5
		final String action = intent.getAction();
		
		if (AppWidgetManager.ACTION_APPWIDGET_DELETED.equals(action)){
			final int appWidgetId = intent.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, 
													AppWidgetManager.INVALID_APPWIDGET_ID);
			if(appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID){
				this.onDeleted(context, new int []{appWidgetId});
			}
		}else{
			super.onReceive(context, intent);
		}
	}
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		final int N = appWidgetIds.length;
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];
            updateWidgetView(context, appWidgetManager, appWidgetId);
        }
	}
	
	//retrieve the startDate
    public String getStoredStartDate(Context context, int appWidgetId){
    	Log.d(DEB_TAG, "Inside getStoredStartDate");
        SharedPreferences sp = context.getSharedPreferences(PREFS_NAME, 0);
		String startPref = sp.getString(PREF_PREFIX_KEY + appWidgetId,  null);
		Log.d(DEB_TAG, "Value of startPref is " + startPref);
		
		return startPref = sp.getString(PREF_PREFIX_KEY, null);
    }
	
	public void updateWidgetView(Context context, AppWidgetManager appWidgetManager,
            int appWidgetId) {
    	Log.d(DEB_TAG, "Inside updateAppWidget");
    	String dayCount;
        
    	String date = getStoredStartDate(context, appWidgetId);
    	
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