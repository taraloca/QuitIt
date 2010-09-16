package com.quitit.android;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

public class QuitItProvider extends AppWidgetProvider {
	private static final String DEB_TAG 				= "QuitItProvider.java";
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		final int N = appWidgetIds.length;
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];
            updateWidgetView(context, appWidgetManager, appWidgetId);
        }
	}
	
	@Override
    public void onDeleted(Context context, int[] appWidgetIds){
		Log.d(DEB_TAG, "onDeleted");
		//String locCode;
        SharedPreferences sp = context.getSharedPreferences(QUITIT.Preferences.PREF_NAME, 0);
		Editor editor = sp.edit();
		
		// remove preference
		for(int appWidgetId : appWidgetIds){
			//locCode = sp.getString(QUITIT.Preferences.WIDGET_PREFIX + appWidgetId, null);
			Log.d(DEB_TAG, "Value of id being removed is " + appWidgetId);
			editor.remove(QUITIT.Preferences.WIDGET_PREFIX + appWidgetId);
		}
		editor.commit();
		
		super.onDeleted(context, appWidgetIds);
    }
	
	@Override
	public void onDisabled(Context context){
		
        Log.d(DEB_TAG, "onDisabled");
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(
            new ComponentName(QUITIT.PKG_QUITIT, ".appwidget.ExampleBroadcastReceiver"),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP);
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
	
	// Write the startDate to the SharedPreferences object for this widget
    public static void saveStartPref(Context context, int appWidgetId, String startDate) {
    	Log.d(DEB_TAG, "Inside of saveStartPref");
    	Log.d(DEB_TAG, "appId is " + appWidgetId);
    	Log.d(DEB_TAG, "string pref is " + startDate);
        SharedPreferences.Editor prefs = context.getSharedPreferences(QUITIT.Preferences.PREF_NAME, 0).edit();
        prefs.putString(QUITIT.Preferences.WIDGET_PREFIX + appWidgetId, startDate);
        prefs.commit();
    }
    
    //retrieve the startDate
    public static String getStoredStartDate(Context context, int appWidgetId){
    	Log.d(DEB_TAG, "Inside getStoredStartDate");
        SharedPreferences sp = context.getSharedPreferences(QUITIT.Preferences.PREF_NAME, 0);
		String startPref = sp.getString(QUITIT.Preferences.WIDGET_PREFIX + appWidgetId,  null);
		Log.d(DEB_TAG, "Value of startPref is " + startPref);
		
		return startPref = sp.getString(QUITIT.Preferences.WIDGET_PREFIX + appWidgetId, null);
    }
    
    /*
     * Updates the view of the widget
     */
    public static void updateWidgetView(Context context, AppWidgetManager appWidgetManager,
            int appWidgetId) {
    	Log.d(DEB_TAG, "Inside updateAppWidget");
    	String dayCount;
        
    	String date = getStoredStartDate(context, appWidgetId);
    	Log.d(DEB_TAG, "Inside updateWidgetView via onUpdate and date is " + date);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        
        if(date == null){
        	views.setTextViewText(R.id.days, "wtf");
        }
        else{
        
        dayCount = TimeDifference.getDaysDifference(date);
        Log.d(DEB_TAG, "Value of dayCount is " + dayCount);
        views.setTextViewText(R.id.days, dayCount);
        
        //Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.scottagarman.com"));  
        
        Intent openApp = new Intent(context, RunningTally.class);
        openApp.putExtra("widgetId", appWidgetId);
        Log.d(DEB_TAG, "$$$$$$$$$id is " + appWidgetId);
        PendingIntent pendingAppIntent = PendingIntent.getActivity(context, appWidgetId, openApp, PendingIntent.FLAG_CANCEL_CURRENT  );
        views.setOnClickPendingIntent(R.id.openFull, pendingAppIntent);
        
        // Tell the widget manager
        appWidgetManager.updateAppWidget(appWidgetId, views);       	
        	
        }
    }
}