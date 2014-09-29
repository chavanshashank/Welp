package com.thewelp.intro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.thewelp.R;

public class FullScreenViewActivity extends Activity{
	
	public static final String PREFS_NAME = "VersionPref";


	
	private FullScreenImageAdapter adapter;
	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welp_intro);
		
		welcomeCheck();

		viewPager = (ViewPager) findViewById(R.id.pager);

		

		Intent i = getIntent();
		int position = i.getIntExtra("position", 0);

		adapter = new FullScreenImageAdapter(FullScreenViewActivity.this,getFilePaths(),this);

		viewPager.setAdapter(adapter);

		// displaying selected image first
		viewPager.setCurrentItem(position);
	}
	
	
	public ArrayList<String> getFilePaths() {
		ArrayList<String> filePaths = new ArrayList<String>();

		AssetManager assetManager = getAssets();
		
		String []filePath;
		try {
			filePath = assetManager.list("intro");
			Log.d("filepath", ""+filePath.length);
			filePaths= new ArrayList<String>(Arrays.asList(filePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return filePaths;
	}
	
	
	void welcomeCheck()
	{
		String TAG = "TAG";
		SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, 0);
	    int savedVersionCode = sharedPreferences.getInt("VersionCode", 0);
	    
	    Log.d(TAG, ""+savedVersionCode);
	    int appVersionCode = 1;

	    try {
	        appVersionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;

	    } catch (NameNotFoundException nnfe) {
	        Log.w(TAG, "$ Exception caz of appVersionCode : " + nnfe);
	    }   

	    if(savedVersionCode == appVersionCode){
	        Log.d(TAG, "$$ savedVersionCode == appVersionCode");
	        startActivity(new Intent(this,com.thewelp.WelpActivity.class));
	        finish();
	    }else{
	        Log.d(TAG, "$$ savedVersionCode != appVersionCode");

	        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
	        sharedPreferencesEditor.putInt("VersionCode", appVersionCode);
	        sharedPreferencesEditor.commit();

	        
	    }
	}
}
