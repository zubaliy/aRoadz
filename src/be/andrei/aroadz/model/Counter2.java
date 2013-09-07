package be.andrei.aroadz.model;

import android.os.CountDownTimer;
import be.andrei.aroadz.R;
import be.andrei.aroadz.controller.Log;
import be.andrei.aroadz.utils.Config;
import be.andrei.aroadz.utils.GUI;
import be.andrei.aroadz.utils.Toasts;


public class Counter2 {
	private CountDownTimer countDownTimer = null;
	private boolean timerFinished = false;
	private boolean timerActivated = false;
	private int setTime = 0;
	private long startTime = 0;
	private long interval = 1 * 1000;
	
	public Counter2(int zseconds){
		timerFinished = false;
		timerActivated = false;
		setTimer(zseconds);
		startTime();
	}
	

	private void newCounter(long zstartTime, long zinterval) {
		
		countDownTimer = new CountDownTimer(zstartTime, zinterval) {

			@Override
			public void onFinish() {
				timerFinished = true;
				timerActivated = false;				
			}

			@Override
			public void onTick(long millisUntilFinished) {
				System.err.println("Stop writing after: " + millisUntilFinished/1000);
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
		timerActivated = true;
	}


	public void stop() {
		countDownTimer.cancel();
		timerFinished = false;
		timerActivated = false;
		
	}

	public void setTimer(int zseconds) {
		this.setTime = zseconds;
		startTime();
	}
	
	public void addTime(int zseconds) {
		this.setTimer(zseconds);
		this.start();

	}

	public boolean isFinished() {
		return timerFinished;
	}
	
	public boolean isActivated() {
		return timerActivated;
	}
}


