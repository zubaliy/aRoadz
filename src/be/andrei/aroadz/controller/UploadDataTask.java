package be.andrei.aroadz.controller;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import be.andrei.aroadz.utils.Toasts;

import android.os.AsyncTask;

public class UploadDataTask extends AsyncTask<String, String, String >{

	@Override
	protected String doInBackground(String... arg0) {
		try {

			File file = new File(arg0[0]);
			String url = "http://aroadz.herokuapp.com/upload";
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);

			MultipartEntity entity = new MultipartEntity();
			entity.addPart("type", new StringBody("file"));
			entity.addPart("myfile", new FileBody(file));
			httppost.setEntity(entity);
			HttpResponse response = httpclient.execute(httppost);
			
			System.err.println("#############################");
			System.err.println(response.getStatusLine());
			System.err.println("#############################");
			if(response.getStatusLine().getStatusCode() == 200){
				Log.deleteFile(file.getAbsolutePath());
				Toasts.showGreenMessage("Upload succeeded");
			} else {
				Toasts.showError(Integer.toString(response.getStatusLine().getStatusCode()));
			}
			  
			} catch (ClientProtocolException e) {
			} catch (IOException e) {
			}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
	}
	

}
