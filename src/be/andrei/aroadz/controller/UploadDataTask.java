package be.andrei.aroadz.controller;

import be.andrei.aroadz.model.User;
import be.andrei.aroadz.utils.Config;

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

/*
 *  Send csv files
 */

public class UploadDataTask extends AsyncTask<String, String, String >{
	private int statuscode;
	private File file;
	
	@Override
	protected String doInBackground(String... arg0) {
		try {

			this.file = new File(arg0[0]);
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Config.url_upload);

			MultipartEntity entity = new MultipartEntity();
			
			entity.addPart("user_email", new StringBody(User.getInstance().getEmail()));
			entity.addPart("user_password", new StringBody(User.getInstance().getPassword()));
			entity.addPart("user_nickname", new StringBody(User.getInstance().getNickname()));
			entity.addPart("user_phone_model", new StringBody(User.getInstance().getPhone_model()));
			entity.addPart("user_android_version", new StringBody(User.getInstance().getAndroid_version()));
			entity.addPart("user_android_language", new StringBody(User.getInstance().getAndroid_language()));
			entity.addPart("user_accMaxRange", new StringBody(User.getInstance().getAccMaximumRange()));
			entity.addPart("user_accResolution", new StringBody(User.getInstance().getAccResolution()));
			entity.addPart("user_accPower", new StringBody(User.getInstance().getAccPower()));
			entity.addPart("user_accMinDelay", new StringBody(User.getInstance().getAccMinDelay()));
			entity.addPart("user_accVendor", new StringBody(User.getInstance().getAccVendor()));
			
			entity.addPart("myfile", new FileBody(file));
			httppost.setEntity(entity);			
			HttpResponse response = httpclient.execute(httppost);
			
			System.err.println("#############################\n\n");
			System.err.println("##        " + response.getStatusLine());
			System.err.println("\n\n#############################");
			
			this.statuscode = response.getStatusLine().getStatusCode();
			
			// Nooit interactie met GUI in background
			// Altijd via onPostExecute
			
			} catch (ClientProtocolException e) {
			} catch (IOException e) {
			}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(statuscode >= 200 && statuscode <= 206){
			Log.deleteFile(this.file.getAbsolutePath());
			Uploader.getUploader().subCounter();
			Toasts.showGreenMessage("Upload succeeded, file deleted: " + this.statuscode);
		} else {
			Toasts.showError("Error: " + statuscode);
		}
			
		
	}
	

}
