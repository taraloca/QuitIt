package com.quitit.android;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class QuitItProvider extends AppWidgetProvider {

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
	}
}