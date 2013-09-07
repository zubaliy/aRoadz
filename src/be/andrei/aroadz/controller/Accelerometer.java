package be.andrei.aroadz.controller;

import java.text.DecimalFormat;
import java.util.Observable;

import be.andrei.aroadz.R;
import be.andrei.aroadz.model.KalmanFilter;
import be.andrei.aroadz.model.User;
import be.andrei.aroadz.utils.Config;
import be.andrei.aroadz.utils.GUI;
import be.andrei.aroadz.utils.Toasts;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.Matrix;
import android.widget.TextView;

public class Accelerometer extends Observable {
	private static Accelerometer acc;
	
	public boolean writeEnabled = false;
	private SensorManager sensorManager;
	private SensorEventListener selAccelerometer, selAcceleration, selGyroscope, selGravity, selMagneticField;
	private Sensor accelerometer, acceleration, gravity, gyroscope, magneticfield;
	
	private	double timestamp, prevtime, period = 0.0;
	// all return values have next order: [ x, y, z ]
			float[] dacc = new float[3]; 
			float[] dlinacc = new float[3];
			float[] dlinaccLowPass = new float[3];
			float[] dgyro = new float[3];
			float[] dgravity = new float[3];
			float[] dmagfield = new float[3];
			
			float[] dacc_remap = new float[3];
			float[] dlinacc_remap  = new float[3];
		
	// offset 
	public static boolean calibrate = false;
	private float[] average = {0, 0, 0};
	private	float[] dlinacc_offset = {0, 0, 0};
	private int calls = 1;  // counter
	private int size = 50;  // samples needed for calibration,
	
	// noise filter
	public static boolean filter_kalman = false;
	private KalmanFilter[] kalman = { new KalmanFilter() , new KalmanFilter(), new KalmanFilter() };	
	
	// rotation
	private float[] zrotationMatrix = new float[16];
    private float[] zrotationVector = new float[3];
    private float[] zorientation = new float[3];
    

    private DecimalFormat df = new DecimalFormat("+#0.00000;-#");
    private DecimalFormat df2 = new DecimalFormat("#0.00;-#");
    
	public static Accelerometer getInstance() {
		if (acc == null) {
			acc = new Accelerometer();
		}
		return acc;
	}

	private Accelerometer() {
		super();
		
		sensorManager = (SensorManager) Config.activity.getSystemService(Context.SENSOR_SERVICE);
	    accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	    acceleration = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
	    gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
	    gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
	    magneticfield = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
	    //rotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
	    

	    
	    User user = User.getInstance();
        user.setAccMaximumRange(Float.toString(accelerometer.getMaximumRange()));
        user.setAccResolution(Float.toString(accelerometer.getResolution()));
        user.setAccMinDelay(Float.toString(accelerometer.getMinDelay())); 
        user.setAccPower(Float.toString(accelerometer.getPower()));
        user.setAccVendor(accelerometer.getVendor());
        user.setAccVersion(Float.toString(accelerometer.getVersion()));
        user.setAccType(Float.toString(accelerometer.getType()));
        
/*        selRotationVector = new SensorEventListener() {	
		    public void onAccuracyChanged(Sensor sensor, int accuracy) {
		    	//do nothing
		    }
		    public void onSensorChanged(SensorEvent event) {
		    	// raw accelerometer values
		    	zrotationVector = event.values.clone(); // x, y, z

		    }
		};
        */
        selMagneticField = new SensorEventListener() {	
		    public void onAccuracyChanged(Sensor sensor, int accuracy) {
		    	//do nothing
		    }
		    public void onSensorChanged(SensorEvent event) {
		    	// raw accelerometer values
		    	dmagfield = event.values.clone(); // x, y, z
		    	
		    	if(dacc != null){
		    		z_computeOrientation();	
		    	}
		        
		    }
		};
        
        selAccelerometer = new SensorEventListener() {	
		    public void onAccuracyChanged(Sensor sensor, int accuracy) {
		    	//do nothing
		    }
		    public void onSensorChanged(SensorEvent event) {
		    	// raw accelerometer values
		    	dacc = event.values.clone(); // x, y, z		
		    	
		    	processing();
		    }
		    
		};
			
		selAcceleration= new SensorEventListener() {	
			
		    public void onAccuracyChanged(Sensor sensor, int accuracy) {
		    	//do nothing
		    }
		
		    public void onSensorChanged(SensorEvent event) {
		    	// only linear acceleration
		    	dlinacc = event.values.clone();
		    	

		    }
		};
		
		selGyroscope = new SensorEventListener() {	
		    public void onAccuracyChanged(Sensor sensor, int accuracy) {
		    	//do nothing
		    }
		
		    public void onSensorChanged(SensorEvent event) {
		        dgyro = event.values.clone();  // x, y , z
		        
		    }
		};
		
		selGravity = new SensorEventListener() {	
		    public void onAccuracyChanged(Sensor sensor, int accuracy) {
		    	//do nothing
		    }
		
		    public void onSensorChanged(SensorEvent event) {
		    	dgravity = event.values.clone();  // x, y ,z
		        
		    }
		};
		
		registerListeners(Config.SENSOR_DELAY);	
	}
	
	public void changeDelay(int delay){
		registerListeners(delay);
	}
	
	private void registerListeners(int delay){
		unregisterListeners();
		
		sensorManager.registerListener(selAccelerometer, accelerometer, delay);
		sensorManager.registerListener(selAcceleration, acceleration, delay);
		sensorManager.registerListener(selGyroscope, gyroscope, delay);
		sensorManager.registerListener(selGravity, gravity, delay);	
		sensorManager.registerListener(selMagneticField, magneticfield, delay);	

	}
	
	private void unregisterListeners(){
		sensorManager.unregisterListener(selAccelerometer);
		sensorManager.unregisterListener(selAcceleration);
		sensorManager.unregisterListener(selGyroscope);
		sensorManager.unregisterListener(selGravity);
		sensorManager.unregisterListener(selMagneticField);
	}
		

	private void processing(){
		
		// remove offset
		if( calibrate == true ){
			dlinacc[0] = dlinacc[0] - dlinacc_offset[0]; // x - offset
	    	dlinacc[1] = dlinacc[1] - dlinacc_offset[1]; // y - offset
	    	dlinacc[2] = dlinacc[2] - dlinacc_offset[2]; // z - offset
	    	  	
		}   
    	
    	if (filter_kalman == true) {
	    	dlinacc[0] = kalman[0].correct(dlinacc[0]);
	    	dlinacc[1] = kalman[1].correct(dlinacc[0]);
	    	dlinacc[2] = kalman[2].correct(dlinacc[0]);
    	}
    	
        timestamp = System.currentTimeMillis();
        period = timestamp - prevtime;
        prevtime = timestamp;
        
        GUI.txt_delay.setText( Config.activity.getString(R.string.txt_delay) + " " + period );
        
        // event.timestamp; // The time in nanosecond at which the event happened. Since system startup. Not really useful
        
        if (GUI.btn_record.isChecked() || GUI.btn_record_with_filter.isChecked()) {
	        setChanged();
	        notifyObservers();
        }
        

        
        if( calibrate == true ){
        	calibrate(dlinacc);
        }
       
        updateGUI(); 
	}




	
	private void z_computeOrientation() {
    	
    	if (SensorManager.getRotationMatrix(zrotationMatrix, null, dgravity, dmagfield) ){ 
    		//zorientation = SensorManager.getOrientation(zrotationMatrix, zrotationVector); // verder niet meer gebruikt
    	}
     
    	android.opengl.Matrix.invertM(zrotationMatrix, 0, zrotationMatrix, 0);
    	
    	dacc_remap = remap(dacc);
    	dlinacc_remap = remap(dlinacc);
    	
    }
	
	private float[] remap(float data[]){	
		float dacc[] = new float [4];
		dacc[0] = data[0];
		dacc[1] = data[1];
		dacc[2] = data[2];
		dacc[3] = 0;
		
		float[] resultVec = new float [4];
		Matrix.multiplyMV(resultVec, 0, zrotationMatrix, 0, dacc, 0);
		
		return resultVec;
	}
	
	/*
	 *  Easy calibration algorithm
	 *  1) Remove offset from acc sensor
	 *  Place the phone in rest condition, collect samples of LINEAR ACCELERATION and compute average
	 */
	public void calibrate(float[] data){

		
		if ( calls <= size ) {
			average[0] += dlinacc[0];
			average[1] += dlinacc[1];
			average[2] += dlinacc[2];
			
			calls++;
		}
		
		if ( calls == size ) {
			average[0] /= size;
			average[1] /= size;
			average[2] /= size;
			calls++;
			
			dlinacc_offset[0] = average[0];
			dlinacc_offset[1] = average[1];
			dlinacc_offset[2] = average[2];
			
			Toasts.showGreenMessage("Offset removed");
			
			}
		
		// reset start conditions, to count offset again
		if ( calls > size ) {
			average[0] = 0;
			average[1] = 0;
			average[2] = 0;
		
			calls = 1;
			calibrate = false;
		}	
			GUI.txt_acc.setText("Offset: x: " + df.format(dlinacc_offset[0]) + "   y: " + df.format(dlinacc_offset[1]) + "   z: " + df.format(dlinacc_offset[2]));	
		
	}
	
	private float[] removeoffset(float[] data){
		for (int i=0;i <= 2; i++){
			data[i] -= dlinacc_offset[i];
		}
		
		return data;
	}
	
	public void updateGUI() {
		//df.setRoundingMode(RoundingMode.HALF_UP);
		dacc = removeoffset(dacc);
		dlinacc = removeoffset(dlinacc);

		GUI.txt_acc_x.setText( "x: " + df.format( dacc[0] ) ); 
	    GUI.txt_acc_y.setText( "y: " + df.format( dacc[1] ) ); 
	    GUI.txt_acc_z.setText( "z: " + df.format( dacc[2] ) );
	        
	    GUI.txt_acc_xa.setText(df.format( dacc_remap[0] ) ); 
	    GUI.txt_acc_ya.setText(df.format( dacc_remap[1] ) ); 
	    GUI.txt_acc_za.setText(df.format( dacc_remap[2] ) );
	      
	    GUI.txt_offset_x.setText(df.format( dlinacc_remap[0] ) ); 
	    GUI.txt_offset_y.setText(df.format( dlinacc_remap[1] ) ); 
	    GUI.txt_offset_z.setText(df.format( dlinacc_remap[2] ) );	  
	    
	}
	
	public int getPeriod() {
		return (int)period;
	}
	
	public double[] getData() {
		double[] data = {dacc[0], dacc[1], dacc[2], dlinacc[0], dlinacc[1], dlinacc[2], timestamp };
		return data; // x, y, z, xa, ya, za, timestamp of event
	}
	
	public float getPower() {
		return this.accelerometer.getPower();
	}
	
	public float[] getGyro() {
		return dgyro;
	}
	
	public float[] getAcc() {
		return dacc;
	}
	
	public float[] getAccR() {
		
		return dacc_remap;
		
	}
	public float[] getLinaccR() {
		
		return dlinacc_remap;
	}
	
	public double getTimestamp() {
		return timestamp;
	}
	
	public void addMyObserver(DataCombiner dc) {
		this.addObserver(dc);
	}
}
