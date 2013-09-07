


package be.andrei.aroadz.controller;

import be.andrei.aroadz.utils.Config;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Internet {
	
	public static boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager = (ConnectivityManager)Config.activity.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
	}
	
	public static void askToEnableInternet(){
		Intent gpsOptionsIntent = new Intent(  
			    android.provider.Settings.ACTION_WIFI_SETTINGS);  
				Config.activity.startActivity(gpsOptionsIntent);
		
	}

}
