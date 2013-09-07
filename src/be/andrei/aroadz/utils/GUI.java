package be.andrei.aroadz.utils;

import be.andrei.aroadz.controller.GPS;
import be.andrei.aroadz.controller.Internet;
import be.andrei.aroadz.model.User;
import be.andrei.aroadz.R;


import android.graphics.Color;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

public class GUI {

	public static TextView txt_welcome;
	public static TextView txt_acc;
	public static TextView txt_acc_x;
	public static TextView txt_acc_y;
	public static TextView txt_acc_z;
	
	public static TextView txt_acc_xa;
	public static TextView txt_acc_ya;
	public static TextView txt_acc_za;
	
	public static TextView txt_offset_x;
	public static TextView txt_offset_y;
	public static TextView txt_offset_z;
	
	public static TextView txt_gps;
	public static TextView txt_speed;
	public static TextView txt_accuracy;
	public static TextView txt_gps_lat;
	public static TextView txt_gps_long;
	public static TextView txt_time;
	public static TextView txt_delay;
	
	
	public static ToggleButton btn_record;
	public static ToggleButton btn_record_with_filter;
	
	public static Button btn_internet;
	public static Button btn_gps;
	public static Button btn_upload;
	public static Button btn_upload_parse;
	public static Button btn_calibrate;
	public static Button btn_kalman;
	public static Button btn_login;
	public static Button btn_menu;
	
	public static ToggleButton btn_gps_speedcheck;
	public static ToggleButton btn_preRecordTimer;
	
	public static Spinner spnr_delay;
	public static Spinner spnr_timer;
	

	public static Chronometer chronometer;
	
	
	public static void init(){
		txt_welcome = (TextView) Config.activity.findViewById(R.id.txt_welcome); 

		txt_welcome = (TextView) Config.activity.findViewById(R.id.txt_welcome); 
		txt_acc = (TextView) Config.activity.findViewById(R.id.txt_acc); 
		txt_acc_x = (TextView) Config.activity.findViewById(R.id.txt_acc_x);
		txt_acc_y = (TextView) Config.activity.findViewById(R.id.txt_acc_y);
		txt_acc_z = (TextView) Config.activity.findViewById(R.id.txt_acc_z);
		
		txt_acc_xa = (TextView) Config.activity.findViewById(R.id.txt_acc_xa);
		txt_acc_ya = (TextView) Config.activity.findViewById(R.id.txt_acc_ya);
		txt_acc_za = (TextView) Config.activity.findViewById(R.id.txt_acc_za);
		
		txt_offset_x = (TextView) Config.activity.findViewById(R.id.txt_offset_x);
		txt_offset_y = (TextView) Config.activity.findViewById(R.id.txt_offset_y);
		txt_offset_z = (TextView) Config.activity.findViewById(R.id.txt_offset_z);
		
		txt_gps = (TextView) Config.activity.findViewById(R.id.txt_gps); 
		txt_speed = (TextView) Config.activity.findViewById(R.id.txt_speed);
		txt_accuracy = (TextView) Config.activity.findViewById(R.id.txt_accuracy);
		txt_gps_lat = (TextView) Config.activity.findViewById(R.id.txt_gps_lat);
		txt_gps_long = (TextView) Config.activity.findViewById(R.id.txt_gps_long);
		
		txt_time = (TextView) Config.activity.findViewById(R.id.txt_time);
		txt_delay = (TextView) Config.activity.findViewById(R.id.txt_delay);
		
		
		btn_internet = (Button) Config.activity.findViewById(R.id.btn_internet);
		btn_gps = (Button) Config.activity.findViewById(R.id.btn_gps);
		btn_record = (ToggleButton) Config.activity.findViewById(R.id.btn_record);
		btn_record_with_filter = (ToggleButton) Config.activity.findViewById(R.id.btn_record_with_filter);
		btn_upload = (Button) Config.activity.findViewById(R.id.btn_upload);
		btn_upload_parse = (Button) Config.activity.findViewById(R.id.btn_upload_parse);
		btn_calibrate = (Button) Config.activity.findViewById(R.id.btn_calibrate);
		btn_kalman = (Button) Config.activity.findViewById(R.id.btn_kalman);
		btn_login = (Button) Config.activity.findViewById(R.id.btn_login);
		btn_menu = (Button) Config.activity.findViewById(R.id.btn_menu);
		
		btn_gps_speedcheck = (ToggleButton) Config.activity.findViewById(R.id.btn_gps_speedcheck);
		//btn_preRecordTimer = (ToggleButton) Config.activity.findViewById(R.id.btn_preRecordTimer);
		
		spnr_delay = (Spinner) Config.activity.findViewById(R.id.spnr_delay);
		spnr_delay.setSelection(Config.SENSOR_DELAY);
		
		spnr_timer = (Spinner) Config.activity.findViewById(R.id.spnr_timer);
		spnr_timer.setSelection(1);
		
		chronometer = (Chronometer) Config.activity.findViewById(R.id.chronometer1);
		
		checkBtnStates();
		refreshGUIlabels();
		


	}
	
	public static void checkBtnStates(){
		if(Internet.isNetworkAvailable())
			GUI.btn_internet_ON();		
		
		if(GPS.gps_enabled)
			btn_gps_WAITING();
	}
	
	public static void chronometer_start(){
		chronometer.setBase(SystemClock.elapsedRealtime());
	    chronometer.start();
	}
	
	public static void chronometer_stop(){
		chronometer.stop();
	}
	
/*	public static void btn_preRecordTimer_ON(){
		btn_preRecordTimer.setBackgroundColor(Color.GREEN);
	}
	
	public static void btn_preRecordTimer_OFF(){
		btn_preRecordTimer.setBackgroundColor(Color.LTGRAY);
	}*/
	
	public static void btn_gps_speedcheck_ON(){
		btn_gps_speedcheck.setBackgroundColor(Color.GREEN);
	}
	
	public static void btn_gps_speedcheck_OFF(){
		btn_gps_speedcheck.setBackgroundColor(Color.LTGRAY);
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
	
	public static void btn_record_with_filter_ON(){
		btn_record_with_filter.setBackgroundColor(Color.GREEN);
	}
	
	public static void btn_record_with_filter_OFF(){
		btn_record_with_filter.setBackgroundColor(Color.LTGRAY);
	}
	
	public static void btn_kalman_ON(){
		btn_kalman.setBackgroundColor(Color.GREEN);
	}
	
	public static void btn_kalman_OFF(){
		btn_kalman.setBackgroundColor(Color.LTGRAY);
	}
	
	public static void refreshGUIlabels(){
		String strMeatFormat = Config.activity.getString(R.string.txt_welcome);
		txt_welcome.setText(String.format(strMeatFormat, User.getInstance().getNickname()));
		
		
	}
}
