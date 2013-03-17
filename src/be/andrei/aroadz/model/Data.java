package be.andrei.aroadz.model;

public class Data {
	
	private String time;
	private double[] data;


	
	public void setData(String dateString, double lon, double lat, double accx, double accy, double accz, double speed, double gps_accuracy) {		
		time = dateString;
		double[] dataline= {0, lon, lat, accx, accy, accz, speed, gps_accuracy};
		data = dataline;
	}
	
	public double getLatitude(){
		return data[1];
	}
	public double getLongitude(){
		return data[2];
	}
	public double getX(){
		return data[3];
	}
	public double getY(){
		return data[4];
	}
	public double getZ(){
		return data[5];
	}
	
	public double getSpeed() {
		return data[6];
	}

	public double getGps_accuracy() {
		return data[7];
	}
	
	public double[] getData(){
		return data;
	}

	@Override
	public String toString() { //separator for the db is ";\t"
		String text = time + ";\t" + data[1] +";\t" + data[2] +";\t" + data[3] + ";\t" + data[4] + ";\t" + data[5];
		return text;
	}

	
}
