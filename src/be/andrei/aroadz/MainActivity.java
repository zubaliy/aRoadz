package be.andrei.aroadz;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import be.andrei.aroadz.controller.GPS;
import be.andrei.aroadz.controller.Internet;
import be.andrei.aroadz.controller.Log;
import be.andrei.aroadz.controller.Prefilter;
import be.andrei.aroadz.controller.ReadDataTask;
import be.andrei.aroadz.controller.Uploader;
import be.andrei.aroadz.model.User;
import be.andrei.aroadz.utils.Config;
import be.andrei.aroadz.utils.Toasts;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MainActivity extends Activity {

	private Prefilter prefilter = null;
	private ReadDataTask rdt = null;
	private Timer timer = null;
	private User user = null;
	private Uploader uploader = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		user = User.getUser();
		uploader = Uploader.getUploader();
		Config.init(this);
		GUI.init();

		prefilter = new Prefilter();
		timer = new Timer();

		addListenerOnButton();

		Toasts.showGreenMessage("aRoadz started succesful");
	}

	/* defaultText();  text wordt niet geupdate na onCreate... bij gebruik van annotations
	 * 
	 * @Click void btn_gps() { GUI.txt_acc.setText( "FFFFFFFFFFFF"); }
	 */
	
	public void addListenerOnButton() {
		
		GUI.btn_record.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if (GUI.btn_record.isChecked()) {
							createNewLogFile();
							GUI.btn_record_ON();
						} else {
							GUI.btn_record_OFF();	
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
					
					uploader.httpPostFilesInFolder(Config.workfolder);
					
				} else {
					Internet.askToEnableInternet();
					Toasts.showError("No Internet Connection");
				}
			}
		});

		// TODO implement long press or double click on gps_button to disable
		// GPS met popup with ask to disable or not

	}

	public void createNewLogFile() { 
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd-kk.mm.ss");
		String currentDateTimeString = df.format(Calendar.getInstance().getTime());
		File logfile = new File(Config.workfolder + user.getEmail() + "-"+ currentDateTimeString + ".csv");
		Log.createNewFile(logfile);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	protected void onResume() {
		super.onResume();
	}

	protected void onPause() {
		super.onPause();
	}

	public void updateGUI() {

		/*
		 * if (GUI.btn_record.isChecked()){
		 * Log.appendLogLine(prefilter.getData());
		 * Toasts.showGreenMessage("writing"); }
		 */

		TimerTask doAsynchronousTask;
		final Handler handler = new Handler();
		Timer timer = new Timer();

		doAsynchronousTask = new TimerTask() {
			@Override
			public void run() {
				handler.post(new Runnable() {
					public void run() {

						try {
							new ReadDataTask().execute(prefilter);
							// PerformBackgroundTask this class is the class that extends AsynchTask
							// performBackgroundTask.execute(prefilter);
							// Toasts.showGreenMessage("OK");

						} catch (Exception e) {
							
						}
					}
				});
			}
		};

		timer.schedule(doAsynchronousTask, 0, 10000);

	}
	




	  

}
