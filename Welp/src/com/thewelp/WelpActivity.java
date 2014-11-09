package com.thewelp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;

public class WelpActivity extends ActionBarActivity {
	
	public static final String PREFS_NAME = "VersionPref";
	private String firstName = "";
	private String[] places;
	private AutoCompleteTextView act;
	
	public static Activity activityVar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welp);
		activityVar = this;
/*		 try {
	            PackageInfo info = getPackageManager().getPackageInfo(
	                    "com.thewelp", 
	                    PackageManager.GET_SIGNATURES);
	            for (Signature signature : info.signatures) {
	                MessageDigest md = MessageDigest.getInstance("SHA");
	                md.update(signature.toByteArray());
	                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
	                }
	        } catch (NameNotFoundException e) {

	        } catch (NoSuchAlgorithmException e) {

	        }*/
		
		/*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { 

		    boolean translucentNavigation = true; 
		    Window w = getWindow();  
		    w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); 

		}*/

		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff4444")));
		
		initPlaces();
		
		//welcomeCheck();
		//startActivity(new Intent(this,com.thewelp.intro.FullScreenViewActivity.class));
		startActivityForResult(new Intent(this,LoginActivity.class),1);
		
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
		switch(id)
		{
			case R.id.action_settings :
					Toast.makeText(getApplicationContext(), "Coming Soon.. :)", Toast.LENGTH_LONG).show();
					break;
			case R.id.signOut :
					startActivity(new Intent(WelpActivity.this, LoginActivity.class).putExtra("fromMenu", "fromMenu"));
					break;
			
		}
		/*if (id == R.id.action_settings) {
			return true;
		}*/
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
	
	@Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) 
				{return;}

	    	firstName = data.getStringExtra("name");//explicitly
	    	//updateProPic();
	    	ActivityCompat.invalidateOptionsMenu(WelpActivity.this); //refiring onprepareoptions menu
	    	
	    	
	    
	  }
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
	    MenuItem score = menu.findItem(R.id.firstName);
	    score.setTitle(firstName);
	    return true;
	}
	
	void initPlaces()
	{
		act= (AutoCompleteTextView) findViewById(R.id.actPlaces);
		
		InputStream buildinginfo = getResources().openRawResource(R.raw.places);
		BufferedReader br = new BufferedReader(new InputStreamReader(buildinginfo));

		//you've now got an instance of DataInputStream called myDIS
		
		ArrayList<String> list = new ArrayList<String>();
		String myLine; //declare a variable

		//now loop through and check if we have input, if so append it to list

		try {
			while((myLine=br.readLine())!=null) 
				list.add(myLine);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		places = list.toArray(new String[list.size()]);
		
		
		
		ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.select_dialog_item,places);
		act.setThreshold(1);
	    act.setAdapter(adapter);
	}
	
	
	
	
	/*public void updateProPic()
	{
		profilePictureView = (ProfilePictureView) findViewById(R.id.profilePicture);

		// start Facebook Login
		  Session.openActiveSession(WelpActivity.this, true, new Session.StatusCallback() {

		    // callback when session changes state
		    @Override
		    public void call(Session session, SessionState state, Exception exception) {
		    	
		    	if (session.isOpened()) {
		    		// make request to the /me API
		    		Request.newMeRequest(session, new Request.GraphUserCallback() {

		    		  // callback after Graph API response with user object
		    		  @Override
		    		  public void onCompleted(GraphUser user, Response response) {
		    			  
		    			  if (user != null) {

		    				  profilePictureView.setProfileId(user.getId());
		    			}
		    		  }
		    		}).executeAsync();
		    		}

		    }
		  });
	}*/
	
}
