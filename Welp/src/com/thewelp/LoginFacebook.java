package com.thewelp;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.os.Build;

public class LoginFacebook extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_facebook);

		// start Facebook Login
		  Session.openActiveSession(LoginFacebook.this, true, new Session.StatusCallback() {

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
/*				    				  Toast.makeText(LoginActivity.this, "Welcome "+user.getFirstName(), Toast.LENGTH_LONG).show();
		    				  Toast.makeText(LoginActivity.this, "Bday "+user.getBirthday(), Toast.LENGTH_LONG).show();
		    				  Toast.makeText(LoginActivity.this, "Last  "+user.getLastName(), Toast.LENGTH_LONG).show();
		    				  Toast.makeText(LoginActivity.this, "Uname "+user.getUsername(), Toast.LENGTH_LONG).show();
		    				  Toast.makeText(LoginActivity.this, "Location "+user.getLocation(), Toast.LENGTH_LONG).show();
		    				  Toast.makeText(LoginActivity.this, "Link "+user.asMap().get("gender"), Toast.LENGTH_LONG).show();
		    				  Toast.makeText(LoginActivity.this, "Email "+user.asMap().get("email"), Toast.LENGTH_LONG).show();*/
		    				  Toast.makeText(LoginFacebook.this, "Bday "+user.asMap().get("birthday"), Toast.LENGTH_LONG).show();
		    				  /*setResult(RESULT_OK, new Intent()
		    				  		.putExtra("name", user.getFirstName())				    						  
		    				  );
		    				  */
		    				  String stringArray[] = {user.getFirstName(),user.getLastName(),user.asMap().get("email").toString(),
		    						  user.getBirthday(),user.asMap().get("gender").toString(),user.getId().toString()};
		    				  Intent intent = new Intent (LoginFacebook.this,SignupActivity.class);
		    				  intent.putExtra("signUp", stringArray);
		    				  
		    				  startActivityForResult(intent, 1);
		    				  finish();
		    				  overridePendingTransition( android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		    				  
		    			}
		    		  }
		    		}).executeAsync();
		    		}

		    }
		  });
		
	}

	@Override
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
	      super.onActivityResult(requestCode, resultCode, data);
	      
	      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	     
	      

	  }

}
