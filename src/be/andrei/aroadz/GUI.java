package be.andrei.aroadz;

import be.andrei.aroadz.controller.GPS;
import be.andrei.aroadz.controller.Internet;
import be.andrei.aroadz.model.User;
import be.andrei.aroadz.utils.Config;



import android.graphics.Color;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

public class GUI {

	public static TextView txt_welcome;
	public static TextView txt_acc;
	public static TextView txt_gps;
	public static TextView txt_speed;
	
	public static Button btn_internet;
	public static Button btn_gps;
	public static ToggleButton btn_record;
	public static Button btn_upload;
	
	public static void init(){
		txt_welcome = (TextView) Config.activity.findViewById(R.id.txt_welcome); 
		String strMeatFormat = (String) txt_welcome.getText();
		txt_welcome.setText(String.format(strMeatFormat, User.getUser().getNickname()));
		

		txt_welcome = (TextView) Config.activity.findViewById(R.id.txt_welcome); 
		txt_acc = (TextView) Config.activity.findViewById(R.id.txt_accelerometer); 
		txt_gps = (TextView) Config.activity.findViewById(R.id.txt_gps); 
		txt_speed = (TextView) Config.activity.findViewById(R.id.txt_speed);
		
		btn_internet = (Button) Config.activity.findViewById(R.id.btn_internet);
		btn_gps = (Button) Config.activity.findViewById(R.id.btn_gps);
		btn_record = (ToggleButton) Config.activity.findViewById(R.id.btn_record);
		btn_upload = (Button) Config.activity.findViewById(R.id.btn_upload);
		
		checkBtnStates();

	}
	
	public static void checkBtnStates(){
		if(Internet.isNetworkAvailable())
			GUI.btn_internet_ON();		
		
		if(GPS.gps_enabled)
			btn_gps_WAITING();
	}
	
	public static void btn_gps_ON(){
		btn_gps.setBackgroundColor(Color.GREEN);
	}
	
	public static void btn_gps_OFF(){
		btn_gps.setBackgroundColor(Color.RED);
	}
	
	public static void btn_gps_WAITING() {
		btn_gps.setBackgroundColor(Color.YELLOW);	
	}
	
	public static void btn_internet_ON(){
		btn_internet.setBackgroundColor(Color.GREEN);
	}
	
	public static void btn_internet_OFF(){
		btn_internet.setBackgroundColor(Color.RED);
	}
	
	public static void btn_internet_WAITING() {
		btn_internet.setBackgroundColor(Color.YELLOW);	
	}
	
	public static void btn_record_ON(){
		btn_record.setBackgroundColor(Color.GREEN);
	}
	
	public static void btn_record_OFF(){
		btn_record.setBackgroundColor(Color.LTGRAY);
	}
	
	




}
