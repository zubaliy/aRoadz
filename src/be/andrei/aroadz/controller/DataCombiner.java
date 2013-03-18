package be.andrei.aroadz.controller;

import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.Observer;

import be.andrei.aroadz.GUI;
import be.andrei.aroadz.model.Data;
import be.andrei.aroadz.utils.Toasts;



public class DataCombiner implements Observer{
	
	private Accelerometer acc = null;
	private GPS gps  = null;
	private Data data = null;
	private 
			SimpleDateFormat format;
			double time = 0;
			String dateString;
			String dataline;


	
	public DataCombiner(){
			acc = new Accelerometer();
			gps  = new GPS();
			data = new Data();
			
			format = new SimpleDateFormat("ddkkmmssSSS");
		 	dateString = new String();
		 	dataline = new String();
		 	
		 	acc.addMyObserver(this);
		 	gps.addMyObserver(this);	 	
	}


	public Data getData(){
		
		double[] dgps = gps.getData();
		double[] dacc = acc.getData();
		
		time = System.currentTimeMillis();
		dateString = format.format(time);
		data.setData(dateString, dgps[0], dgps[1], dacc[0], dacc[1], dacc[2], dgps[2], dgps[3]);
		
		return data;
	}


	@Override
	public void update(Observable observable, Object data) {
		if (GUI.btn_record.isChecked()) {
			
			Data tdata = getData();
			System.out.println(tdata.toString());
			if(tdata.getLatitude() > 0) {
				
				GUI.txt_welcome.setText(tdata.toString());
				new WriteDataTask().execute(getData().toString());
			}
			
        }
		
	}

}
