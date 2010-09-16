package com.quitit.android;
/**
 * <p>Convenience class for organizing constants used throughout the
 * application.  Ensures that constants are only defined once and
 * that common constants passed between various classes are consistent
 * across the application.</p> 
 * 
 * @author Tara Verity
 *
 */
public class QUITIT {

	private final String DEB_TAG 				= "AppWidgetConfigure.java";
	
	public static class Intents {
		public static final String ACTION_ABOUT 			= "quitit.intent.action.ABOUT";
		public static final String CATEGORY_ABOUT 			= "quitit.intent.category.ABOUT";
		public static final String ACTION_START_FROM_WIDGET = "quitit.intent.action.START_FROM_WIDGET";
	}
	
	public static class Preferences {
		public static final String ALL 				= "quitit_preferences";
		public static final String PREF_NAME	 	= "quitit.appwidget.AppWidget";
		public static final String WIDGET_PREFIX	= "widget_id_"; //PREF_PREIX_KEY
	}
	
	public static class Extras {
		public static final String WIDGET_ID = "widgetId";
	}
}
