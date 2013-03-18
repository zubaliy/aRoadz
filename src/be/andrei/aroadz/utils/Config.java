package be.andrei.aroadz.utils;



import android.app.Activity;



public class Config {
	
	public final static String workfolder = new String("sdcard/!aRoadz/");
	
	public final static int GPS_SPEED_THRESHOLD = 1; // [m/s]
	public static final int GPS_UPDATE_TIME_INTERVAL = 0;
	public static final float GPS_UPDATE_DISTANCE = 0;
	
	public static Activity activity = null;
	
	public static void init(Activity ac){
		activity = new Activity();
		activity = ac;
	}
	
	

}
