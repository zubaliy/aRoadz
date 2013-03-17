package be.andrei.aroadz.controller;

import android.os.AsyncTask;

public class WriteDataTask extends AsyncTask<String, String, String > {
	private boolean running = true;

	
	protected void onPreExecute() {
		super.onPreExecute();
		
		
	}

	protected String doInBackground(String... params) {
		Log.appendLogLine(params[0]); 
		return null;
	}

	protected void onProgressUpdate(String... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
	}

	
}
