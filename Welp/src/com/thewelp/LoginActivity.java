package com.thewelp;


import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Session;
import com.facebook.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;




public class LoginActivity extends ActionBarActivity implements OnClickListener,
ConnectionCallbacks, OnConnectionFailedListener {
	
	//this variable
	public static Activity finishVar ;
	
	//google button
	private SignInButton btnG;
	private SignInButton btnGLogout;
	//fb
	private LoginButton btnFB;
	//Image view to set pro pic in signup activity
	private ImageView imgProfilePic;
	//strings for g+ data
	String personName , personPhotoUrl,  personGooglePlusProfile,  email ;

	//sign in var.. copied dont know much :D
	private static final int RC_SIGN_IN = 0;
    // Logcat tag
    private static final String TAG = "MainActivity";
 
    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;
 
    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
 
    /**
     * A flag indicating that a PendingIntent is in progress and prevents us
     * from starting further intents.
     */
    private boolean mIntentInProgress;
 
    private boolean mSignInClicked;
 
    private ConnectionResult mConnectionResult;
    int count=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_login);
		finishVar = this;
		//checkOpenSession();
		
		//initializing all the google components
		
		btnGLogout = (SignInButton) findViewById(R.id.btnGLogout);
		
		btnG=(SignInButton) findViewById(R.id.btnG);
		setGooglePlusButtonText(btnG,"Sign in using Google");//setting required name to button 
		imgProfilePic=(ImageView) findViewById(R.id.profileImage);
		btnG.setOnClickListener(this);		//on click listener
		mGoogleApiClient = new GoogleApiClient.Builder(this)//google api client :P
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this).addApi(Plus.API)
        .addScope(Plus.SCOPE_PLUS_LOGIN).build();
		
		
		
		//going translucent in kitkat and more.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { 

		    boolean translucentNavigation = true; 
		    Window w = getWindow();  
		    w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); 

		} 
		
		//init fb components 
		btnFB = (LoginButton) findViewById(R.id.btnFB);
		btnFB.setReadPermissions(Arrays.asList("user_likes", "public_profile","user_checkins","email","user_location","user_birthday"));//permissions
		btnFB.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				
				if(isNetworkConnected())//check net connecticity else get crashed :P
					startActivity(new Intent(LoginActivity.this, LoginFacebook.class));
				else
					Toast.makeText(LoginActivity.this, "Um..It seems theres no Internet Connection.!", Toast.LENGTH_LONG).show();
				
			}
		});
		
	}
	
	
	/**
	 * here this method is checking whether network is connected or not.returns true if connected.
	 */
	public  boolean isNetworkConnected() {
    	boolean isConnected=false;
    	try{
    		ConnectivityManager cm =
    	        (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
    	 
    		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    		isConnected = activeNetwork != null &&
    	                      activeNetwork.isConnectedOrConnecting();
    		return isConnected;
    	}
    	catch(Exception e)
    	{    	
    		e.printStackTrace();  
    	}
    	return isConnected;
    }


	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		
		if(count==0)
		{
			Toast.makeText(getApplicationContext(), "Press back once more to exit! Don't do this! :)", Toast.LENGTH_SHORT).show();
			count++;
		}
		else
		{
			super.onBackPressed();		
			finishAll();
		}
		
	}
	
	/**
	 * @param v view is needed for clickable things
	 * this is user agreement.
	 * should popup auto first time.
	 */
	public void userAgree(View v){
		WebView webView = new WebView(this);
	    webView.loadUrl("file:///android_asset/changelog-en.html");
	    
	    //adding it on dialog box.
	    
	    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
	    
	    dialog.setView(webView);//this is it :D added on it.. jus like old days java panel.
	    
	    dialog.setTitle("User Agreement");
	    // Add action buttons
        dialog.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // sign in the user ...
            	dialog.cancel();
            }
        });
        dialog.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finishAll();
               
            }
        }); 
	    dialog.show();// show it man.. dont hide it :P
		
	}
	
	/**
	 * in case you want to kill all... urgh :D
	 */
	public void finishAll()
	{
		WelpActivity.activityVar.finish();
		overridePendingTransition( android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}
	
	
	/**
	 * damn... you should be moron... no social network?? or privacy issue ? :D
	 * whateva welcome to welp then :D
	 */
	public void signUp(View view){

				startActivity(new Intent(LoginActivity.this,SignupActivity.class));
				overridePendingTransition( android.R.anim.slide_in_left, android.R.anim.slide_out_right);

	}
	
	
	/**
	 * @param signInButton sign in button of google baba.
	 * baba forgot easy way to update name of buttons :P
	 * so doing this shit stuff. :P
	 */
	protected void setGooglePlusButtonText(SignInButton signInButton,String str) {
	    for (int i = 0; i < signInButton.getChildCount(); i++) {
	        View v = signInButton.getChildAt(i);
	        if (v instanceof TextView) {
	            TextView mTextView = (TextView) v;
	            mTextView.setText(str);
	            mTextView.setTextSize(16);
	            return;
	        }
	    }
	}
	
	/**
	 * as name suggest :D
	 */
	public void checkOpenSession() throws Exception
	{
		Session session = Session.getActiveSession();
        if (session != null && session.isOpened())
        {
        	Toast.makeText(LoginActivity.this, "Session opened", Toast.LENGTH_LONG).show();
        }
        
        
        finish();
        overridePendingTransition( android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}


	
	
	protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }
 
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
 
    /**
     * Method to resolve any signin errors
     * */
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }
    
    
 
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }
 
        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;
 
            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }
 
    }
 

 
    @Override
    public void onConnected(Bundle arg0) {
        mSignInClicked = false;
       //Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
 
        // Get user's information
        getProfileInformation();
 
        // proceed the UI after signin
        proceedUI(true);
 
    }
 
    /**
     * Updating the UI, showing/hiding buttons and profile layout
     * */
    private void proceedUI(boolean isSignedIn) {
        if (isSignedIn) {
        	
        	Bundle bundle = getIntent().getExtras();
        	if(bundle != null)
        	{
        		Toast.makeText(getApplicationContext(), "bundle null nai.", Toast.LENGTH_SHORT).show();
        		
        		setGooglePlusButtonText(btnGLogout,"Log Out");
        		btnGLogout.setVisibility(View.VISIBLE);
        		btnGLogout.setOnClickListener(this);
        		
        	}
        	else
        	{
            //Toast.makeText(getApplicationContext(), "yes bro logged in.", Toast.LENGTH_SHORT).show();
        	Intent intent = new Intent();
        	intent.putExtra("name", personName.split(" ")[0].toString());
        	setResult(RESULT_OK, intent);
            finish();
        	}
            
        } else {
        	
        	  String stringArray[] = {personName.split(" ")[0].toString(),personName.split(" ")[1].toString(),email,
					  " "," ",personPhotoUrl};
           
           
			  Intent intent = new Intent (LoginActivity.this,SignupActivity.class);
			  intent.putExtra("signUpG", stringArray);
			  
			  startActivity(intent);
			  
			  overridePendingTransition( android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
    }
 
    /**
     * Fetching user's information name, email, profile pic
     * */
    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            	
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                 personName = currentPerson.getDisplayName();
                 personPhotoUrl = currentPerson.getImage().getUrl();
                 personGooglePlusProfile = currentPerson.getUrl();
                 email = Plus.AccountApi.getAccountName(mGoogleApiClient);
                
 
                Log.e(TAG, "Name: " + personName + ", plusProfile: "
                        + personGooglePlusProfile + ", email: " + email
                        + ", Image: " + personPhotoUrl);
 
                
 
                // by default the profile url gives 50x50 px image only
                // we can replace the value with whatever dimension we want by
                // replacing sz=X
                personPhotoUrl = personPhotoUrl.substring(0,
                        personPhotoUrl.length() - 2)
                        + PROFILE_PIC_SIZE;
                /*
                new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);*/
                
               
 
            } else {
                //Toast.makeText(getApplicationContext(),
                        //"Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
        proceedUI(false);
    }
 

 
    /**
     * Button on click listener
     * */
    @Override
    public void onClick(View v) {
    	//Toast.makeText(getApplicationContext(), "in on click google", Toast.LENGTH_LONG).show();
    	
    	
        switch (v.getId()) {
        case R.id.btnG:
            // Signin button clicked
        	if(isNetworkConnected()){
            	
    			signInWithGplus();
    		}
    		else
    			Toast.makeText(LoginActivity.this, "Um..It seems theres no Internet Connection.!", Toast.LENGTH_LONG).show();
            break;
        case R.id.btnGLogout:
            // Signout button clicked
            
            if(isNetworkConnected()){
            	
            	signOutFromGplus();
            	finish();
    		}
    		else
    			Toast.makeText(LoginActivity.this, "Um..It seems theres no Internet Connection.!", Toast.LENGTH_LONG).show();
            break;
        /*case R.id.btn_revoke_access:
            // Revoke access button clicked
            revokeGplusAccess();
            break;*/
        }
    }
 
    /**
     * Sign-in into google
     * */
    private void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();	//seems issue with login hm???
        }
    }
 
    /**
     * Sign-out from google
     * */
    private void signOutFromGplus() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
            proceedUI(false);
        }
    }
 
    /**
     * Revoking access from google
     * */
    private void revokeGplusAccess() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status arg0) {
                            /*Log.e(TAG, "User access revoked!");*/
                            mGoogleApiClient.connect();
                            proceedUI(false);
                        }
 
                    });
        }
    }
 
    /**
     * Background Async task to load user profile picture from url
     * */
    /*private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
 
        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }
 
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }
 
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }*/
    
	@Override
    protected void onActivityResult(int requestCode, int responseCode,
            Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }
 
            mIntentInProgress = false;
 
            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }
	
	
 
}
	


