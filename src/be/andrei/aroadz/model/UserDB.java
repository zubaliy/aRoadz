package be.andrei.aroadz.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import be.andrei.aroadz.utils.Config;
import be.andrei.aroadz.utils.Toasts;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDB extends SQLiteOpenHelper  {
	
	private static UserDB userDB = null;
	

	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME="aroadzDB";
	private static final String col_id="_id";
	
	private static final String table = "user";
	
	private static final String col_email = "email";
	private static final String col_password = "password";
	private static final String col_username = "username";
	private static final String col_phone_model = "phone_model";
	private static final String col_android_firmware_build = "android_firmware_build";
	private static final String col_android_language = "android_language";
	
	private static final String col_accMaximumRange = "accMaximumRange";
	private static final String col_accResolution = "accResolution";
	private static final String col_accMinDelay = "accMinDelay";
	private static final String col_accVendor = "accVendor";
	private static final String col_accVersion = "accVersion";
	private static final String col_accType = "accType";
	
	private static final String table_anomaly = "anomaly";
	private static final String col_timestamp = "timestamp";
	private static final String col_longitude = "longitude";
	private static final String col_latitude = "latitude";
	private static final String col_accurañy = "accurañy";
	private static final String col_acczar = "acczar";
	private static final String col_speed = "speed";


	private UserDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public static UserDB getInstance() {
		if (userDB == null) {
			userDB = new UserDB(Config.activity);
		}
		return userDB;
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + table + " (" + col_id + " INTEGER PRIMARY KEY, " +
			    									col_email + " TEXT, " + 
			    									col_password + " TEXT, " +
			    									col_username + " TEXT, " +
			    									col_phone_model + " TEXT, " +
			    									col_android_firmware_build + " TEXT, " +
			    									col_android_language + " TEXT, " +
			    									col_accMaximumRange + " TEXT, " +
			    									col_accResolution + " TEXT, " +
			    									col_accMinDelay + " TEXT, " +
			    									col_accVendor + " TEXT, " +
			    									col_accVersion + " TEXT, " +
			    									col_accType + " TEXT" + ")" );
		
		db.execSQL("INSERT INTO " + table + " VALUES ( " + 1 + ",'" +
														User.getInstance().getEmail() + "','" +
														User.getInstance().getPassword() + "','" +
														User.getInstance().getNickname() + "','" +
														User.getInstance().getPhone_model() + "','" +
														User.getInstance().getAndroid_firmware_build() + "','" +
														User.getInstance().getAndroid_language() + "','" +
														User.getInstance().getAccMaximumRange() + "','" +
														User.getInstance().getAccResolution() + "','" +
														User.getInstance().getAccMinDelay() + "','" +
														User.getInstance().getAccVendor() + "','" +
														User.getInstance().getAccVersion() + "','" +
														User.getInstance().getAccType() +  "')" );
		
		db.execSQL("CREATE TABLE " + table_anomaly + " (" + col_id + " INTEGER PRIMARY KEY, " +
													col_timestamp + " TEXT," +
													col_longitude + " TEXT, " + 
													col_latitude + " TEXT, " +
													col_accurañy + " TEXT, " +
													col_acczar + " TEXT, " +
													col_speed + " TEXT " + ")" );
		
		Toasts.showGreenMessage("DB created, default user added");
		
	}
	
	public void readUser(){
		
		SQLiteDatabase db = this.getReadableDatabase();
		//Cursor cur = db.rawQuery("SELECT * FROM " + table + " WHERE " + col_id + " = ?" , new String [] {"1"});
		Cursor cur = db.query(table, null, col_id +"= 1", null, null, null, null);
		cur.moveToFirst(); //row!!!

		User.getInstance().setEmail(cur.getString(cur.getColumnIndex(col_email)));
		User.getInstance().setPassword(cur.getString(cur.getColumnIndex(col_password)));
		User.getInstance().setNickname(cur.getString(cur.getColumnIndex(col_username)));


		cur.close();
	}
	
	public void updateUser() {
		SQLiteDatabase db = this.getWritableDatabase();
		
	   	ContentValues cv = new ContentValues();
		cv.put(col_email, User.getInstance().getEmail());
		cv.put(col_password, User.getInstance().getPassword());
		cv.put(col_username, User.getInstance().getNickname());
		cv.put(col_phone_model, User.getInstance().getPhone_model());
		cv.put(col_android_firmware_build, User.getInstance().getAndroid_firmware_build());
		cv.put(col_android_language, User.getInstance().getAndroid_language());
		cv.put(col_accMaximumRange, User.getInstance().getAccMaximumRange());
		cv.put(col_accResolution, User.getInstance().getAccResolution());
		cv.put(col_accMinDelay, User.getInstance().getAccMinDelay());
		cv.put(col_accVendor, User.getInstance().getAccVendor());
		cv.put(col_accVersion, User.getInstance().getAccVersion());
		cv.put(col_accType, User.getInstance().getAccType());

		int i = db.update(table, cv, col_id + "= 1", 	null);   // return the number of rows affected 
		db.close();
		Toasts.showGreenMessage("DB updated");
		
	  }

	public void addAnomaly(Data data) {
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		String currentDateTimeString = df.format(System.currentTimeMillis());
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put(col_timestamp, currentDateTimeString);
		cv.put(col_longitude, data.getLongitude());
		cv.put(col_latitude, data.getLatitude());
		cv.put(col_acczar, data.getZAR());
		cv.put(col_speed, data.getSpeed());
		cv.put(col_accurañy, data.getAccuracy());
		
		long i = db.insert(table_anomaly, null, cv);   
		
		db.close();
		Toasts.showGreenMessage("DB anomaly added");	
	}

	public List<Anomaly> getAnomalies() {
	    List<Anomaly> anomalyList = new ArrayList<Anomaly>();
	    String selectQuery = "SELECT  * FROM " + table_anomaly;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	        	Anomaly anomaly = new Anomaly();
	        	anomaly.setTimestamp(cursor.getString(cursor.getColumnIndex(col_timestamp)));
	        	anomaly.setLongitude(cursor.getString(cursor.getColumnIndex(col_longitude)));
	        	anomaly.setLatitude(cursor.getString(cursor.getColumnIndex(col_latitude)));
	        	anomaly.setAcczar(cursor.getString(cursor.getColumnIndex(col_acczar)));
	        	anomaly.setSpeed(cursor.getString(cursor.getColumnIndex(col_speed)));
	        	anomaly.setAccurañy(cursor.getString(cursor.getColumnIndex(col_accurañy)));

	            // Adding anomaly to list
	            anomalyList.add(anomaly);
	        } while (cursor.moveToNext());
	    }
	 
	    return anomalyList;
	}
	
	public String getAnomaliesToString() {
		List<Anomaly> list = this.getAnomalies();
		ListIterator<Anomaly> li =  list.listIterator();
		
		String s = new String();
		s = "timestamp" + ",\t" + "longitude" + ",\t" + "latitude" + ",\t" + "acczar" + ",\t" + "speed" + ",\t" +"accuracy" + "\n";
		
		while (li.hasNext()){
			Anomaly a = li.next();
			s += a.getTimestamp() + ",\t" + a.getLongitude() + ",\t" + a.getLatitude() + ",\t" + a.getAcczar() + ",\t" + a.getSpeed() + ",\t" + a.getAccurañy() + "\n";	
		}
		return s;
		
	}
	
	public void deleteAnomalies(){
		// Delete all rows
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(table_anomaly,null,null);
	    Toasts.showGreenMessage("All rows were deleted.");
	
	}



	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " + table);
		onCreate(db);
		Toasts.showError("onUpgared db activated");
		
	}

	
}
