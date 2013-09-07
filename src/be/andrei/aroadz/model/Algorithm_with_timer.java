package be.andrei.aroadz.model;

import java.util.Scanner;

import be.andrei.aroadz.controller.Log2;
import be.andrei.aroadz.controller.WriteDataTask2;
import be.andrei.aroadz.utils.GUI;
import be.andrei.aroadz.utils.Toasts;
/*
 * TODO: AddWindow to write longer when anomaly were detected more times in the same interval
 */
public class Algorithm_with_timer implements Filter{
	// Filter settings
	private double threshold_pos = 4.0;
	private double threshold_neg = -4.0;
	
	private Data data = null;
	
	private boolean detected = false; // detected is TRUE until the timer finished
	private int window = 1; //start window in seconds
	private int addwindow = 1; // how long we will measure in seconds
	
	// Bepaal de maximale waarde
	private int max_value_counter = 0;
	private int max_value_interval = 20; // binnen interval van 20 opeenvolgende metingen
	private Data max_value_data = null;
	
	// Buffer
	private String buffer = null;
	private int buffer_size = 20; // lines
	private int buffer_counter = 0;
	
	private Counter2 counter = null;
	
	//private String debug = new String();

	public Algorithm_with_timer() {
		this.counter = new Counter2(window);
		this.data = new Data();
		this.max_value_data = new Data();
		this.buffer = new String("");
	}

	@Override
	public Data Algorithm(Data d) {
		this.data = d;
		double ZAR = data.getZAR();

		
		boolean threshold = ZAR >= threshold_pos || ZAR <= threshold_neg;
		System.err.println("THRESHOLD:" + threshold + "     " + ZAR);
		
		if (threshold && counter.isActivated() == false ){
			// Detected
			
			max_value_data.setData(data); // (!) clone object
			//UserDB.getInstance().addAnomaly(max_value_data); // the ZAR value detected on threshold
			
			Toasts.showGreenMessage("DETECTED");
			System.err.println("NEW FILE CREATED");
			
			GUI.chronometer_start();
			counter.start();
			detected = true;
			bufferInit();
			
			// Create new logfile and write the last buffered data
			Log2.createNewLogFile();
			writeDataTask(buffer);
		}
		
		if (counter.isActivated() == true){
			counter.setTimer(addwindow);
			
			System.err.println("DATA WRITTEN");
			writeDataTask(d.toString());
			
			
			//debug += max_value_data.getZAR() + "\n";
			//debug += data.getZAR() + "\n";
			
			// set new max
			boolean newmax = Math.abs(ZAR) >= Math.abs(max_value_data.getZAR()) ;
			if (newmax){
				max_value_data.setZAR(data.getZAR());
			}
			
		} else {
			this.addLineToBuffer(d.toString());
		}
		
		if (threshold && detected ){
			//counter.addTime(addwindow);
			
			System.err.println("TIMER RENEWED");
			
		}		
		
		if (counter.isFinished()){
			detected = false;
			
			max_value_counter = 0;
			UserDB.getInstance().addAnomaly(max_value_data); 
			//Toasts.showError(debug);
			//debug = "";
			//Toasts.showGreenMessage("TIMER FINISHED");
			System.err.println("TIMER FINISHED");
			counter.stop();
			GUI.chronometer_stop();
			
			
			bufferReset();
		}
		
		return data;
	}
	
	
	private void writeDataTask(String line){
		try {
			// TODO crash, when too many tasks. Need to be solved
			new WriteDataTask2().execute(line);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	private void addLineToBuffer(String line){
		if (buffer_counter <= buffer_size){
			buffer += line + "\n";
			buffer_counter++;
		} else {
			String firstLine = new Scanner(buffer).nextLine();
			buffer = buffer.replace(firstLine+"\n", "");
			buffer += line + "\n";
		}
	}
	
	private void bufferInit(){
		buffer_counter = 0;
	}
	private void bufferReset(){
		buffer = "";
		buffer_counter = 0;
	}
	
			

}
