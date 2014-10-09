package com.thewelp;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class WelpActivity extends ActionBarActivity {
	
	public static final String PREFS_NAME = "VersionPref";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welp);
		
		//welcomeCheck();
		//startActivity(new Intent(this,com.thewelp.intro.FullScreenViewActivity.class));
		startActivity(new Intent(this,LoginActivity.class));
		
		overridePendingTransition( android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welp, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	void welcomeCheck()
	{
		SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, 0);
	    int savedVersionCode = sharedPreferences.getInt("VersionCode", 0);
	    
	    int appVersionCode = 1;

	    try {
	        appVersionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;

	    } catch (NameNotFoundException nnfe) {
	        //Log.w(TAG, "$ Exception caz of appVersionCode : " + nnfe);
	    	nnfe.printStackTrace();
	    }   

	    if(savedVersionCode != appVersionCode){
	        //Log.d(TAG, "$$ savedVersionCode == appVersionCode");
	    	startActivity(new Intent(this,com.thewelp.intro.FullScreenViewActivity.class));
	        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
	        sharedPreferencesEditor.putInt("VersionCode", appVersionCode);
	        sharedPreferencesEditor.commit();
	    }
	}
}
