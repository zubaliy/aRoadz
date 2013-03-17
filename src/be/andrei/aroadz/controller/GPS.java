package be.andrei.aroadz.controller;

import java.util.Observable;


import be.andrei.aroadz.GUI;
import be.andrei.aroadz.utils.Config;
import be.andrei.aroadz.utils.Toasts;




import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.SystemClock;

public class GPS extends Observable implements LocationListener, GpsStatus.Listener{
	private static Location location = null;
	private Location mLastLocation;
	boolean isGPSFix;
	private double mLastLocationMillis;
	private LocationManager locationManager;
	
	
	public static boolean gps_enabled = false;
	public static boolean location_changed = false;

	
	public GPS() {
		super();
		
		locationManager = (LocationManager) Config.activity.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Config.GPS_UPDATE_TIME_INTERVAL, Config.GPS_UPDATE_DISTANCE, this);

		checkIfGpsIsEnabledOnStart();
	}
	
	private void checkIfGpsIsEnabledOnStart() {
		if(locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER )){
			GUI.btn_gps_WAITING();
			Toasts.gps_enabled();
		} else {
			GUI.btn_gps_OFF();
			Toasts.gps_disabled();
		}
	}

	public Location getLocation(){
		if(location == null){
			return null;
		}

		return location;
	}
	
	public double[] getData(){
		if(location == null || gps_enabled == false){
			double[] dgps = {0,0,0,0};
			return dgps;
		}
		
		double[] data= {location.getLatitude() , location.getLongitude(), location.getSpeed(), location.getAccuracy()};
		return data;
	}
	
	public static boolean GpsHasSpeed(){
		if(location == null){
			return false;
		}
		
		return location.getSpeed() >= Config.GPS_SPEED_THRESHOLD;
		
	}
	
	public void onLocationChanged(Location loc) {
		//location_changed = false; // regel echt nodig???
		//if (location == null) return; // regel echt nodig???
		
		location = loc;
		location_changed = true; // write logs only on gps position changed
		
		
		// used in onGpsStatusChanged 
	    mLastLocationMillis = SystemClock.elapsedRealtime();
	    mLastLocation = location;
		
		
		GUI.txt_gps.setText(	  "My current location is: " 
								+ "\nLat: " + location.getLatitude() 
								+ "\nLong: "	+ location.getLongitude());
		
		if (GUI.btn_record.isChecked()) {
	        setChanged();
	        notifyObservers();
        }
		
		//Toasts.showGreenMessage(Boolean.valueOf(location.hasSpeed()).toString());
		//GUI.txt_speed.setText("Speed: " + String.format("%.2f", location.getSpeed()) + "[m/s]" );
		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		/* This is called when the GPS status alters */
		
		switch (status) {
		case LocationProvider.OUT_OF_SERVICE:
						Toasts.showError("GPS Status Changed: Out of Service");
						//GUI.btn_gps_WAITING();
						break;
		case LocationProvider.TEMPORARILY_UNAVAILABLE:
						//GUI.btn_gps_WAITING();
						Toasts.showError("GPS Status Changed: Temporarily Unavailable");
						break;
		case LocationProvider.AVAILABLE:
						//GUI.btn_gps_ON();
						Toasts.showError("GPS Status Changed: Available");
						break;
		}
	}
	
	public void onProviderDisabled(String provider) {
		Toasts.gps_disabled();
		GUI.btn_gps_OFF();
		gps_enabled = false;
	}

	public void onProviderEnabled(String provider) {
		Toasts.gps_enabled();
		GUI.btn_gps_WAITING();
		gps_enabled = true;
	}


	
	public static void askToEnableGPS(){
		Intent gpsOptionsIntent = new Intent(  
			    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);  
				Config.activity.startActivity(gpsOptionsIntent);
	}


/*	@Override
	public void onGpsStatusChanged(int event) {
        boolean isGPSFix = false;
        switch (event) {
            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                if (mLastLocation != null)
                	if ((SystemClock.elapsedRealtime() - mLastLocationMillis) < Config.GPS_UPDATE_TIME_INTERVAL * 2) {
                		isGPSFix = true;
                	}

                if (isGPSFix) { // A fix has been acquired.
                	GUI.btn_gps_ON();
                } else { // The fix has been lost.
                	GUI.btn_gps_WAITING();
                }

                break;

            case GpsStatus.GPS_EVENT_FIRST_FIX:
            	GUI.btn_gps_ON();
                break;
            case GpsStatus.GPS_EVENT_STARTED:
            	GUI.btn_gps_ON();
                break;
            case GpsStatus.GPS_EVENT_STOPPED:
            	GUI.btn_gps_OFF();
                break;
        }
    }*/


	public void onGpsStatusChanged(int event) {
		
		switch (event) {
        case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
            if (mLastLocation != null)
                isGPSFix = (SystemClock.elapsedRealtime() - mLastLocationMillis) < 3000;

            if (isGPSFix) { // A fix has been acquired.
            	GUI.btn_gps_ON();
            } else { // The fix has been lost.
            	GUI.btn_gps_WAITING();
            }

            break;
            
        case GpsStatus.GPS_EVENT_FIRST_FIX:
            // Do something.
            isGPSFix = true;

            break;
		}
	}
	
	public void addMyObserver(DataCombiner dc){
		this.addObserver(dc);
	}

	
	
	
}