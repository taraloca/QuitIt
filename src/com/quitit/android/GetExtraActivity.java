package com.quitit.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class GetExtraActivity extends Activity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.d("!@#!@#!@#!@#@!#@!#Inside new activity", "Inside new activity");
        setContentView(R.layout.running_tally);
       String id = null;
        
     // Find the widget id from the intent. 
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            id= extras.getString("foo");
        }
       Log.d("INSIDE EXTRA",  id);
    }
}
