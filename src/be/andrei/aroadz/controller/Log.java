package be.andrei.aroadz.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;



import be.andrei.aroadz.utils.Toasts;




public class Log {
	
	private static File logfile = null;
	public static String filelist = new String();
	
	//Toast.makeText(getApplicationContext(), currentDateTimeString, Toast.LENGTH_LONG).show();
	
	public static void createNewFile(File file){
		logfile = file;
		if (!file.exists()) {
		      try
		      {
		         file.createNewFile();
		         // first line used as header.
		         // need to parse csv
		         appendLogLine("timestamp;	longitude;	latitude;	x;	y;	z;	speed;	gps_accuracy");
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

		for (File listOfFile : listOfFiles)
		    if (listOfFile.isFile()){
		        filelist += listOfFile.getName() + "\n";
		    }

	}

	public static void saveListOfFiles(final File file){
		if (!file.exists())
		   {
		      try
		      {
		         file.createNewFile();
		         Toasts.showError("New listfile was created");
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
