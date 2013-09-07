package be.andrei.aroadz;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ListIterator;

import be.andrei.aroadz.controller.Accelerometer;
import be.andrei.aroadz.controller.GPS;
import be.andrei.aroadz.controller.Internet;
import be.andrei.aroadz.controller.Log;
import be.andrei.aroadz.controller.Log3;
import be.andrei.aroadz.controller.Prefilter;
import be.andrei.aroadz.controller.Uploader;
import be.andrei.aroadz.model.Anomaly;
import be.andrei.aroadz.model.Filter;
import be.andrei.aroadz.model.Counter;
import be.andrei.aroadz.model.User;
import be.andrei.aroadz.model.UserDB;
import be.andrei.aroadz.utils.Config;
import be.andrei.aroadz.utils.GUI;
import be.andrei.aroadz.utils.Toasts;


import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MainActivity extends Activity {
	private Counter counter;
	private Prefilter prefilter;
	@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.menu, menu);
        return true;
        
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		/*
		 *  Initialiseer eerst Config en GUI
		 *  Dan pas Prefilter die de sensoren initialiseeert
		 */
		
		Config.init(this);
		UserDB.getInstance().readUser();
		GUI.init();
		// Initialize Accelerometer and GPS sensors
		prefilter = Prefilter.getInstance();	
		// Initialize SQlite
		counter = new Counter(10);
		
		
		
		addListenerOnButton();

		Toasts.showGreenMessage("aRoadz started successfully.");
	}



	 @Override
	public boolean onOptionsItemSelected(MenuItem item)
	    {
	         
	        switch (item.getItemId())
	        {
	        case R.id.menu_savetocsv:
	        	Log3.createNewLogFile();
	        	Log3.appendLogLine(UserDB.getInstance().getAnomaliesToString());
	            return true;
	 
	        case R.id.menu_delete_files:
	        	Log3.deleteFiles();
	            return true;
	 
	        case R.id.menu_user_profile:
                Intent myIntent = new Intent(Config.activity, UserActivity.class);
                //startActivityForResult(myIntent, 0);
                this.startActivity(myIntent);
	            return true;
	 
	        case R.id.menu_upload:
	        	Uploader.getUploader().httpPostAnomalies();
	            return true;
	 
	        case R.id.menu_delete:
	        	UserDB.getInstance().deleteAnomalies();
	            return true;
	 
	        case R.id.menu_read_db:
	        	Toasts.showGreenMessage( UserDB.getInstance().getAnomaliesToString());
	            return true;
	 
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }    
	 
	private void addListenerOnButton() {
/*		GUI.btn_preRecordTimer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(GUI.btn_preRecordTimer.isChecked()) {
					GUI.btn_preRecordTimer_ON();
				} else {
					GUI.btn_preRecordTimer_OFF();
				}
				
			}
		});
*/
		GUI.btn_gps_speedcheck.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				if(GUI.btn_gps_speedcheck.isChecked()){
					GPS.gps_speedcheck = true;
					GUI.btn_gps_speedcheck_ON();
				} else {
					GPS.gps_speedcheck = false;
					GUI.btn_gps_speedcheck_OFF();
				}
				
				
			}
		});
		
		GUI.btn_menu.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				openOptionsMenu();
				String strMeatFormat = Config.activity.getString(R.string.txt_welcome);
				GUI.txt_welcome.setText(String.format(strMeatFormat, User.getInstance().getNickname()));
				
			}
		});
	
		GUI.spnr_delay.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Config.SENSOR_DELAY = GUI.spnr_delay.getSelectedItemPosition();
				//Toasts.showError(" " + Config.SENSOR_DELAY);
				Accelerometer.getInstance().changeDelay(Config.SENSOR_DELAY);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		GUI.btn_record.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (GUI.btn_record.isChecked()) {
							counter.setTimer(Integer.parseInt(GUI.spnr_timer.getSelectedItem().toString()));
							counter.start();
							
							/* see counter.onFinish()
							 * Log.createNewLogFile();
							 * GUI.btn_record_ON();
							 * GUI.chronometer_start();							 
							 */


						} else {
							GUI.btn_record_OFF();
							GUI.chronometer_stop();
							counter.stop();
						}
						
							
					}
				});
		
		GUI.btn_record_with_filter.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (GUI.btn_record_with_filter.isChecked()) {
					GUI.btn_record_with_filter_ON();
					prefilter.zalgorithm_enable();
					
					
					

				} else {
					GUI.btn_record_with_filter_OFF();
					prefilter.zalgorithm_disable();
				}
		
			}
		});

		GUI.btn_gps.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (GPS.gps_enabled == false) {
					GPS.askToEnableGPS();
				} else {
					GPS.askToEnableGPS();
				}
			}
		});
		
		GUI.btn_internet.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (Internet.isNetworkAvailable()) {
					GUI.btn_internet_ON();
					Internet.askToEnableInternet();
				} else {
					Internet.askToEnableInternet();
					GUI.btn_internet_OFF();
				}
			}
		});
		
		GUI.btn_upload.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				if (Internet.isNetworkAvailable()) {	
					
					Uploader.getUploader().httpPostFilesInFolder(Config.workfolder);
					
				} else {
					Internet.askToEnableInternet();
					Toasts.showError("No Internet Connection");
				}
			}
		});
		
		GUI.btn_calibrate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Accelerometer.calibrate = true;
			}
		});
		
		GUI.btn_kalman.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				Accelerometer.filter_kalman = !Accelerometer.filter_kalman;
				if ( Accelerometer.filter_kalman == true ) {
					GUI.btn_kalman_ON();
				} else {
					GUI.btn_kalman_OFF();
				}
	
			}
		});
		
	/*	GUI.btn_upload_parse.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				if (Internet.isNetworkAvailable()) {	
					
					uploader.httpPostFilesInFolder(Config.workfolder, true);
					
				} else {
					Internet.askToEnableInternet();
					Toasts.showError("No Internet Connection");
				}
			}
		});
	*/
		// TODO implement long press or double click on gps_button to disable

	}



	

	protected void onResume() {
		super.onResume();
	}

	protected void onPause() {
		super.onPause();
	}  
	
	

}
