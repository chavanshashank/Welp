package com.thewelp;


import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


public class CommonSettings extends Application {
    
    

    @Override
    public void onCreate() {
    	
    	
    }
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

    
}
