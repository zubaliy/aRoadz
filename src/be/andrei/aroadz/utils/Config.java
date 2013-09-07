package be.andrei.aroadz.utils;



import java.io.File;

import android.app.Activity;
import android.hardware.SensorManager;



public class Config {
	
	public final static String workfolder = new String("sdcard/!aRoadz/");
	public final static String wf_detected = new String("sdcard/!aRoadz/Detected");
	
	public final static String url_upload = "http://aroadz.herokuapp.com/upload";
	public final static String url_upload_string = "http://aroadz.herokuapp.com/aroadz/upload_string";
	
	public final static int GPS_SPEED_THRESHOLD = 2; // [m/s]
	public static final int GPS_UPDATE_TIME_INTERVAL = 0; // continu nieuwe positie opvragen
	public static final float GPS_UPDATE_DISTANCE = 0; // continu nieuwe positie opvragen
	public static int SENSOR_DELAY = SensorManager.SENSOR_DELAY_UI;
	/*
	 0.006493 km
	 0.006455 km
	 0.006362 km 
	 */
	
	public static Activity activity = null;
	
	public static void init(Activity ac){
		activity = new Activity();
		activity = ac;
		createFolder();
	}
	
	private static void createFolder(){
		File file = new File(workfolder);
		if ( !file.exists() ) {
			file.mkdirs();
			Toasts.showGreenMessage("'sdcard/!aRoadz/' was created.");
		} else {
			//Toasts.showError("Workfolder: " + file.exists() );
		}
		
		file = new File(wf_detected);
		if ( !file.exists() ) {
			file.mkdirs();
			Toasts.showGreenMessage("'sdcard/!aRoadz/Detected' was created.");
		} else {
			//Toasts.showError("Workfolder/Detected: " + file.exists() );
		}
	}
	
	

}
