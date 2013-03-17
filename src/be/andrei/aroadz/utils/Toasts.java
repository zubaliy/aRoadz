package be.andrei.aroadz.utils;



import be.andrei.aroadz.R;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

public class Toasts {
	private static Toast t;
	public static void gps_enabled(){
		Toast.makeText(Config.activity.getApplicationContext(), R.string.gps_enabled, Toast.LENGTH_SHORT).show();	
	}
	public static void gps_disabled(){
		Toast.makeText(Config.activity.getApplicationContext(), R.string.gps_disabled, Toast.LENGTH_SHORT).show();
	}
	
	public static void showError(String s){
		t = Toast.makeText(Config.activity.getApplicationContext(), s, Toast.LENGTH_LONG);
		t.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
		TextView v = (TextView) t.getView().findViewById(android.R.id.message);
		v.setTextColor(Color.RED);
		
		t.show();
	}
	public static void showGreenMessage(String s) {
		t = Toast.makeText(Config.activity.getApplicationContext(), s, Toast.LENGTH_LONG);
		TextView v = (TextView) t.getView().findViewById(android.R.id.message);
		v.setTextColor(Color.GREEN);
		t.show();
		
	}
	
	

}
