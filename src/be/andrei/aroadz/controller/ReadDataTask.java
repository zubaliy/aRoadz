package be.andrei.aroadz.controller;

import be.andrei.aroadz.GUI;
import be.andrei.aroadz.model.Data;
import android.os.AsyncTask;

public class ReadDataTask extends AsyncTask<Prefilter, Data, Data > {
	private boolean running = true;
	private Data data = null;
	
	protected void onPreExecute() {
		super.onPreExecute();
		data = new Data();
		
	}

	protected Data doInBackground(Prefilter... prefilter) {
		//while( running ) {
			data = prefilter[0].getData();
			//new WriteDataTask().execute(data);
		//}
		
		return data;
	}

	protected void onProgressUpdate(Data... values) {
		super.onProgressUpdate(values);
		

		
		
	}

	@Override
	protected void onPostExecute(Data result) {
		super.onPostExecute(result);
		GUI.txt_acc.setText( "accelerometer values:  " +
	    		" \nx: " + result.getX() + 
	    		" \ny: " + result.getY() +  
	    		" \nz: " + result.getZ() 
	    		);
		GUI.txt_gps.setText( "GPS Position:  " +
	    		" \nLat: " + result.getLatitude() + 
	    		" \nLong: " + result.getLongitude() 
	    		);
	}

	
}
