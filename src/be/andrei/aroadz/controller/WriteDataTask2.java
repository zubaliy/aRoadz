package be.andrei.aroadz.controller;

import android.os.AsyncTask;

public class WriteDataTask2 extends AsyncTask<String, String, String > {
	
	protected void onPreExecute() {
		super.onPreExecute();
		
		
	}

	protected String doInBackground(String... params) {
		Log2.appendLogLine(params[0]); 
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
