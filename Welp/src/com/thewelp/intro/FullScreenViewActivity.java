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
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.thewelp.R;

public class FullScreenViewActivity extends Activity{
	
	public static final String PREFS_NAME = "VersionPref";

	
	
	private FullScreenImageAdapter adapter;
	private ViewPager viewPager;
	Button btn1,btn2,btn3,btn4;
	private Button btnClose,btnSkip;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welp_intro);
		
		//welcomeCheck();
		

		viewPager = (ViewPager) findViewById(R.id.pager);
		initButton();
		btnClose = (Button) findViewById(R.id.btnClose);
		btnSkip = (Button) findViewById(R.id.btnSkip);
		btnClose.setVisibility(View.GONE);
		setTab();
		

		Intent i = getIntent();
		int position = i.getIntExtra("position", 0);

		adapter = new FullScreenImageAdapter(FullScreenViewActivity.this,getFilePaths(),this);

		viewPager.setAdapter(adapter);

		// displaying selected image first
		viewPager.setCurrentItem(position);
	}
	
	private void setTab(){
		viewPager.setOnPageChangeListener(new OnPageChangeListener(){
		    		
					@Override
					public void onPageScrollStateChanged(int position) {}
					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {}
					@Override
					public void onPageSelected(int position) {
						// TODO Auto-generated method stub
						btnAction(position);
					}
					
				});

}
private void btnAction(int action){
	switch(action){
	  case 0: setButton(btn1,null,30,30); 
	  		  setButton(btn2,"",15,15);
	          setButton(btn3,"",15,15);
	          setButton(btn4,"",15,15);
	          if(btnSkip.getVisibility() != View.VISIBLE)
	          {
	        	  btnSkip.setVisibility(View.VISIBLE);
	        	  btnClose.setVisibility(View.GONE);
	          }
	  		  break;
	  
	  case 1: setButton(btn2,null,30,30); 
	  		  setButton(btn1,"",15,15); 
	  		  setButton(btn3,"",15,15);
	  		  setButton(btn4,"",15,15);
	  		  if(btnSkip.getVisibility() != View.VISIBLE)
	          {
	        	  btnSkip.setVisibility(View.VISIBLE);
	        	  btnClose.setVisibility(View.GONE);
	          }
	  		  break;
	  
	  case 2: setButton(btn3,null,30,30); 
	  		  setButton(btn1,"",15,15);
	  		  setButton(btn2,"",15,15);
	  		  setButton(btn4,"",15,15);
	  		  if(btnSkip.getVisibility() != View.VISIBLE)
	          {
	        	  btnSkip.setVisibility(View.VISIBLE);
	        	  btnClose.setVisibility(View.GONE);
	          }
	  		  break;
	  
	  case 3: setButton(btn4,null,30,30); 
	  		  setButton(btn1,"",15,15); 
	  		  setButton(btn2,"",15,15);
	  		  setButton(btn3,"",15,15);
	  		  if(btnClose.getVisibility() != View.VISIBLE)
	          {
	        	  btnClose.setVisibility(View.VISIBLE);
	        	  btnSkip.setVisibility(View.GONE);
	          }
	  		  break;
	  
	  
	}
}
	
	
	private void initButton(){
		btn1=(Button)findViewById(R.id.btn1);
    	btn2=(Button)findViewById(R.id.btn2);
    	btn3=(Button)findViewById(R.id.btn3);
    	btn4=(Button)findViewById(R.id.btn4);
    	setButton(btn1,"",30,30);
    	setButton(btn2,"",15,15);
    	setButton(btn3,"",15,15);
    	setButton(btn4,"",15,15);
    }
    private void setButton(Button btn,String text,int h, int w){
    	btn.setWidth(w);
    	btn.setHeight(h);
    	btn.setText(text);
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
	
	public void closeActivity(View v) {
		startActivity(new Intent(this,com.thewelp.WelpActivity.class));
		finish();
		overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
	}
}
