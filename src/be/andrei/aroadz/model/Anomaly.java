package be.andrei.aroadz.model;

public class Anomaly {
					
	private String 	timestamp,
					longitude,
					latitude,
					accurañy,
					acczar,
					speed;
	
	public Anomaly(){};
	
	public Anomaly(String timestamp, String lon, String lat, 
			String acczaR, String speed, String gps_accuracy) {
		
		this.timestamp = timestamp;
		this.longitude = lon;
		this.latitude = lat;
		this.acczar = acczaR;
		this.speed = speed;
		this.accurañy = gps_accuracy;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getAccurañy() {
		return accurañy;
	}

	public void setAccurañy(String accurañy) {
		this.accurañy = accurañy;
	}

	public String getAcczar() {
		return acczar;
	}

	public void setAcczar(String acczar) {
		this.acczar = acczar;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	



	
}
