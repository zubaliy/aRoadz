package be.andrei.aroadz.controller;

import java.io.File;

import be.andrei.aroadz.R;
import be.andrei.aroadz.model.UserDB;
import be.andrei.aroadz.utils.Config;
import be.andrei.aroadz.utils.GUI;

public class Uploader {

	private static Uploader uploader = null;
	private static int taskcounter = 0;
				
	private Uploader() {
	}
	
	public static Uploader getUploader() {
		if (uploader == null) {
			uploader = new Uploader();
		}
		return uploader;
	}
	
	public void httpPostFile(String filepath) {
		// POSTmethod via asynctask, mag niet in de hoofdthread. Anders een error.
		new UploadDataTask().execute(filepath);
	}
	
	public void httpPostFilesInFolder(String folderpath) {
		File folder = new File(folderpath);
		File[] listOfFiles = folder.listFiles();
	
		for (File element : listOfFiles)
		    if (element.isFile()){
		    	addCounter();
		    	new UploadDataTask().execute(element.getAbsolutePath());
		    }
	}

	// if with TRUE option, parse the file
	public void httpPostFilesInFolder(String folderpath, boolean b) {
		File folder = new File(folderpath);
		File[] listOfFiles = folder.listFiles();

		for (File element : listOfFiles)
		    if (element.isFile()){
		    	addCounter();
		    	System.err.println("#############################\n\n");
				System.err.println(taskcounter);
				System.err.println("\n\n#############################");
		    	new UploadDataTask().execute(element.getAbsolutePath());
		    }
	}
	
	public void httpPostAnomalies() {
		new UploadDataTaskString().execute(UserDB.getInstance().getAnomaliesToString());
	}
	
	public void addCounter(){
		taskcounter++;
    	updateGUI();
	}
	
	public void subCounter(){
		taskcounter--;
		updateGUI();
	}
	
	public static void resetCounter(){
		taskcounter = 0;
		updateGUI();
	}
	
	private static void updateGUI() {
		GUI.btn_upload.setText(Config.activity.getString(R.string.btn_upload) + ": " + taskcounter);
	}
		
}
	
	
	

