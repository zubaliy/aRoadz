package be.andrei.aroadz.model;

public class Anomaly {
					
	private String 	timestamp,
					longitude,
					latitude,
					accura�y,
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
		this.accura�y = gps_accuracy;
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

	public String getAccura�y() {
		return accura�y;
	}

	public void setAccura�y(String accura�y) {
		this.accura�y = accura�y;
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
