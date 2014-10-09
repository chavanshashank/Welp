package com.thewelp.intro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thewelp.R;

public class FullScreenViewActivity extends Activity{
	

	private FullScreenImageAdapter adapter;
	private ViewPager viewPager;
	Button btn[] = new Button[4];
	Button btnFB,btnG;
	private TextView tvFinish,tvSkip;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welp_intro);
		btnFB = (Button) findViewById(R.id.btnFB);
		btnG = (Button) findViewById(R.id.btnG);
		
		
/*		Thread timer = new Thread(){
			public void run(){
				try {
					sleep(10000);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				finally{
					Intent in = new Intent(FullScreenViewActivity.this, com.thewelp.WelpActivity.class);
					startActivity(in);
				}
			}
		};
		timer.start();*/
		viewPager = (ViewPager) findViewById(R.id.pager);
		initButton();
		tvFinish = (TextView) findViewById(R.id.tvFinish);
		tvSkip = (TextView) findViewById(R.id.tvSkip);
		tvFinish.setVisibility(View.GONE);
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
	  case 0: setButton(action);
	          if(tvSkip.getVisibility() != View.VISIBLE)
	          {
	        	  tvSkip.setVisibility(View.VISIBLE);
	        	  tvFinish.setVisibility(View.GONE);
	          }
	  		  break;
	  
	  case 1: setButton(action);
	  		  if(tvSkip.getVisibility() != View.VISIBLE)
	          {
	        	  tvSkip.setVisibility(View.VISIBLE);
	        	  tvFinish.setVisibility(View.GONE);
	          }
	  		  break;
	  
	  case 2: setButton(action);
	  		  if(tvSkip.getVisibility() != View.VISIBLE)
	          {
	        	  tvSkip.setVisibility(View.VISIBLE);
	        	  tvFinish.setVisibility(View.GONE);
	          }
	  		  break;
	  
	  case 3: setButton(action);
	  		  if(tvFinish.getVisibility() != View.VISIBLE)
	          {
	        	  tvFinish.setVisibility(View.VISIBLE);
	        	  tvSkip.setVisibility(View.GONE);
	          }
	  		  break;
	  
	  
	}
}
	
	
	private void initButton(){

		for(int i=0;i<4;i++)
		{
			String btnID = "btn"+i;
			
			int resID = getResources().getIdentifier(btnID, "id", getPackageName());
			
			btn[i] = (Button)findViewById(resID);
			btn[i].setText("");
			btn[0].setBackgroundColor(Color.parseColor("#7f8c8d"));
		}
		
    }
    private void setButton(int n){
    	for(int i=0;i<4;i++)
		{
			if(i == n)
			{
				btn[i].setBackgroundColor(Color.parseColor("#7f8c8d"));
			}
			else
			{
				btn[i].setBackgroundColor(Color.parseColor("#ffffff"));
			}
		}
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
	
	
	
	
	public void closeActivity(View v) {
		//startActivity(new Intent(this,com.thewelp.WelpActivity.class));
		finish();
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}
}
