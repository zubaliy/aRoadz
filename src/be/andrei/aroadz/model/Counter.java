package be.andrei.aroadz.model;

import android.os.CountDownTimer;
import be.andrei.aroadz.R;
import be.andrei.aroadz.controller.Log;
import be.andrei.aroadz.utils.Config;
import be.andrei.aroadz.utils.GUI;


public class Counter {
	private CountDownTimer countDownTimer = null;
	public static boolean timerFinished = false;
	private int setTime = 0;
	private long startTime = 0;
	private long interval = 1 * 1000;
	
	public Counter(int zseconds){
		timerFinished = false;
		setTimer(zseconds);
		startTime();
	}
	

	private void newCounter(long zstartTime, long zinterval) {
		
		countDownTimer = new CountDownTimer(zstartTime, zinterval) {

			@Override
			public void onFinish() {
				timerFinished = true;
				GUI.btn_record.setText(Config.activity.getString(R.string.btn_record_on));
				Log.createNewLogFile();
				GUI.btn_record_ON(); 
				GUI.chronometer_start();
				
			}

			@Override
			public void onTick(long millisUntilFinished) {
				GUI.btn_record.setText("Start after: " + + millisUntilFinished/1000);
				
			}
			
		};
	}
	private long startTime() {
		startTime = setTime * 1000;
		
		return startTime;
	}
	
	public void start() {
		this.newCounter(startTime,interval);
		countDownTimer.start();
		timerFinished = false;
	}


	public void stop() {
		countDownTimer.cancel();
		timerFinished = false;
		
	}

	public void setTimer(int zseconds) {
		this.setTime = zseconds;
		startTime();
	}

	public boolean isFinished() {
		return timerFinished;
	}
}


