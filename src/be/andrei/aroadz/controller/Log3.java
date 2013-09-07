package be.andrei.aroadz.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;



import android.annotation.SuppressLint;
import be.andrei.aroadz.model.User;
import be.andrei.aroadz.utils.Config;
import be.andrei.aroadz.utils.Toasts;


/*
 * Save detected to CSV
 */

public class Log3 {
	
	private static File logfile = null;
	public static String filelist = new String();
	
	@SuppressLint("SimpleDateFormat")
	public static void createNewLogFile() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd-kk.mm.ss");
		String currentDateTimeString = df.format(System.currentTimeMillis());
		
		logfile = new File(Config.workfolder
				+ "detected-"
				+ User.getInstance().getEmail() 
				+ "-" + currentDateTimeString
				+ ".csv");
		
		Log3.createNewFile(logfile);
	}
	
	public static void createNewFile(File file){
		logfile = file;
		if (!file.exists()) {
		      try
		      {
		         file.createNewFile();
		         Toasts.showError("New logfile was created"); 
		      } 
		      catch (IOException e)
		      {
		         // TODO Auto-generated catch block
		         e.printStackTrace();
		      }
		   }
		}
	
	public static void listFilesForFolder(final File folder) {
		File[] listOfFiles = folder.listFiles();

		for (File element : listOfFiles)
		    if (element.isFile()){
		        filelist += element.getName() + "\n";
		    }

	}
	
	public static void deleteFile(String filepath){
		File file = new File(filepath);
		file.delete();
	}
	
	public static void deleteFiles(){
		File folder = new File(Config.workfolder);
		
		File[] listOfFiles = folder.listFiles();


		for (File element : listOfFiles)
		    if (element.isFile()){
		    	//Toasts.showGreenMessage(element.getAbsolutePath());
		        deleteFile(element.getAbsolutePath());
		    }
		
		Toasts.showGreenMessage("All files were deleted");
		
	}

	public static void saveListOfFiles(final File file){
		if (!file.exists())
		   {
		      try
		      {
		         file.createNewFile();
		         Toasts.showGreenMessage("New listfile was created");
		      } 
		      catch (IOException e)
		      {
		         // TODO Auto-generated catch block
		         e.printStackTrace();
		      }
		   }
		 try
		   {
		      //BufferedWriter for performance, true to set append to file flag
		      BufferedWriter buf = new BufferedWriter(new FileWriter(file, true)); 
		      buf.write(filelist);
		      buf.newLine();
		      buf.close();
		   }
		   catch (IOException e)
		   {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		   }
		}
	
	
	public static void appendLogLine(String dataline) {
		if (!logfile.exists()) {
		     Toasts.showError("Logfile doesn't exist");
		} else {
	   
			   try
			   {
			      //BufferedWriter for performance, true to set append to file flag
			      BufferedWriter buf = new BufferedWriter(new FileWriter(logfile, true)); 
			      buf.append(dataline);
			      buf.newLine();
			      buf.close();
			   }
			   catch (IOException e)
			   {
			      // TODO Auto-generated catch block
			      e.printStackTrace();
			   }
		}
	}
	


}
