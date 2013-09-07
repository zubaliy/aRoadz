package be.andrei.aroadz.controller;

import java.util.Observable;
import java.util.Observer;

import be.andrei.aroadz.model.Algorithm_with_timer;
import be.andrei.aroadz.model.Counter;
import be.andrei.aroadz.model.Data;
import be.andrei.aroadz.model.Filter;
import be.andrei.aroadz.utils.GUI;
import be.andrei.aroadz.utils.Toasts;

public class Prefilter implements Observer {
	private static Prefilter prefilter = null;
	private DataCombiner dc = null;
	
	private Algorithm_with_timer z_algorithm = null;
	private boolean awt_enabled = false;
	
	private Prefilter() {
		dc = new DataCombiner();
		dc.addObserver(this);
		
		z_algorithm = new Algorithm_with_timer();
	}
	
	public static Prefilter getInstance(){
		if (prefilter == null) {
			prefilter = new Prefilter();
		}
		return prefilter;
	}
	
	public Data getData() {
		Data data = dc.getData();

		return data;
	}


	
	public void zalgorithm_enable(){
		this.awt_enabled = true;
	}
	
	public void zalgorithm_disable(){
		this.awt_enabled = false;
	}


	@Override
	public void update(Observable observable, Object data) {
		
		if (awt_enabled){
			
			z_algorithm.Algorithm(dc.getData());
		}
		
		if (GUI.btn_record.isChecked()) {	

			
			if (Counter.timerFinished) {
				// check if speed is reached otherwise write all data
				if (GPS.gps_enabled && GPS.GpsHasSpeed() && GPS.gps_speedcheck){
					writeDataTask();
				} else if (!GPS.gps_speedcheck){
					writeDataTask();
				}
				
			}
			
        }
		
	}
	
	private void writeDataTask(){
		try {
			// TODO crash, when too many tasks. How to solve?
			new WriteDataTask().execute(getData().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}



}
