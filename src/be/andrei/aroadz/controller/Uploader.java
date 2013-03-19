package be.andrei.aroadz.controller;

import java.io.File;

public class Uploader {

	private static Uploader uploader;
	
				
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
	    	new UploadDataTask().execute(element.getAbsolutePath());
	    }
	}
	
}
