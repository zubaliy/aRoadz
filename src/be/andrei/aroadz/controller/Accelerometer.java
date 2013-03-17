package be.andrei.aroadz.controller;

import java.text.SimpleDateFormat;
import java.util.Observable;

import be.andrei.aroadz.GUI;
import be.andrei.aroadz.R;
import be.andrei.aroadz.model.User;
import be.andrei.aroadz.utils.Config;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Accelerometer extends Observable {
	public boolean writeEnabled = false;
	private SensorManager sensorManager;
	private SensorEventListener sensorEventListener;
	private Sensor accelerometer;
	private float x, y, z;
	double[] data = {0, 0, 0};
	
	SimpleDateFormat format;
	
	public Accelerometer() {
		super();
		
		sensorManager = (SensorManager) Config.activity.getSystemService(Context.SENSOR_SERVICE);
	    accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	    
	    User user = User.getUser();
        user.setAccMaximumRange(Float.toString(accelerometer.getMaximumRange()));
        user.setAccResolution(Float.toString(accelerometer.getResolution()));
        user.setAccMinDelay(Float.toString(accelerometer.getMinDelay())); //enkel vanaf api 9
        
        sensorEventListener = new SensorEventListener() {	
		    public void onAccuracyChanged(Sensor sensor, int accuracy) {
		    	//do nothing
		    }
		
		    public void onSensorChanged(SensorEvent event) {
		        x = event.values[0];
		        y = event.values[1];
		        z = event.values[2];
		        
		        data[0] = x;
		        data[1] = y;
		        data[2] = z;
		        
		        if (GUI.btn_record.isChecked()) {
			        setChanged();
			        notifyObservers();
		        }

		        updateGUI(data);
		        
		    }
		};
		
		sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_UI);
		
	}
	
	public void updateGUI(double[] data) {
    	GUI.txt_acc.setText(Config.activity.getString(R.string.txt_accelerometer) +
	    		"\n x: " + data[0] + 
	    		"\n y: " + data[1] + 
	    		"\n z: " + data[2]
	    		);	
	}
	
	public double[] getData(){
		return data;
	}
	
/*
	public void onResume() {
	    sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	public void onPause() {
	    sensorManager.unregisterListener(sensorEventListener);
	} 
	
*/
	public void addMyObserver(DataCombiner dc){
		this.addObserver(dc);
	}

}
