package com.thewelp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.webkit.WebView;



public class LoginActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_login);

		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
	}
	
	public void userAgree(View v){
		WebView webView = new WebView(this);
	    webView.loadUrl("file:///android_asset/changelog-en.html");
	    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
	    dialog.setView(webView);
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
                LoginActivity.this.finish();
               
            }
        }); 
	    dialog.show();
		
	}
	
}

