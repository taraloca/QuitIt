package com.quitit.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * <p>About screen for application.  Provides basic info about the application
 * and the development team.  Also allows the user to call or email AccuWeather
 * customer support.</p>
 * 
 * @author Harrison Lee
 *
 */
public class AboutActivity extends Activity {
	
	// Debug tag
	public static final String DEB_TAG = "AboutActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.about);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.about_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// Create intent
		Intent i = null;
		
		switch(item.getItemId()){
		
		// Email
		case R.id.about_menu_email:
			i = new Intent(Intent.ACTION_SEND);
			i.putExtra(Intent.EXTRA_SUBJECT, "Quit It Application");
			i.putExtra(Intent.EXTRA_EMAIL, new String[]{"tmv105@gmail.com"});
			i.putExtra(Intent.EXTRA_TEXT, "");
			i.setType("message/rfc822");
			startActivity(Intent.createChooser(i, "Email Client Chooser"));
			finish();
			break;
		
		// go back
		case R.id.about_menu_back:
			i = new Intent(this, RunningTally.class);
			startActivity(i);
			this.finish();
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
	
}
