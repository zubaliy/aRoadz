package be.andrei.aroadz.model;

public class Data {
					
	private double[] data = new double[20];	
	
	/* DATA STRUCTURE 
	 *  lon, lat, 
	 *  accx, accy, accz, 				RAW Accelerometer
	 *  accxa, accya, accza, 			RAW Acceleration
	 *  speed, gps_accuracy, altitude
	 *  gyrox, goroy, gyroz				RAW
	 *  accxR, accyR, acczR				REMAPPED 
	 *  accxaR, accyaR, acczaR, 		REMAPPED
	 *  
	 */
	public Data() {
		this.setData(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}
	
	public void setData(int time, 
						double lon, double lat, 
						float accx, float accy, float accz, 
						float accxa, float accya, float accza,  
						float accxR, float accyR, float acczR,
						float accxaR, float accyaR, float acczaR,
						double speed, double gps_accuracy, double altitude,
						float gyrox, float gyroy, float gyroz) {
		
		
		double[] dataline= {time, 								// 0
							lon, lat, 							// 1, 2
							accx, accy, accz, 					// 3, 4, 5
							accxa, accya, accza, 				// 6, 7, 8
							accxR, accyR, acczR, 				// 9, 10, 11
							accxaR, accyaR, acczaR,				// 12, 13, 14
							speed, gps_accuracy, altitude, 		// 15, 16, 17
							gyrox, gyroy, gyroz };
		data = dataline;
	}
	
	public void setData(Data d){
		double[] dataline= {d.getTime(), 								// 0
				d.getLongitude(), d.getLatitude(), 							// 1, 2
				d.getX(), d.getY(), d.getZ(), 					// 3, 4, 5
				d.getXA(), d.getYA(), d.getZA(), 				// 6, 7, 8
				d.getXR(), d.getYR(), d.getZR(), 				// 9, 10, 11
				d.getXAR(), d.getYAR(), d.getZAR(),				// 12, 13, 14
				d.getSpeed(), d.getAccuracy(), d.getAltitude(), 		// 15, 16, 17
				d.getGyroX(), d.getGyroY(), d.getGyroZ() }; //18, 19, 20
		
		data = dataline;
	}

	


	public double getLongitude(){
		return data[1];
	}
	
	public double getLatitude(){
		return data[2];
	}
	
	public int getTime() {
	
		return (int) data[0];
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
	public double getXA(){
		return data[6];
	}
	public double getYA(){
		return data[7];
	}
	public double getZA(){
		return data[8];
	}
	public double getXR(){
		return data[9];
	}
	public double getYR(){
		return data[10];
	}
	public double getZR(){
		return data[11];
	}
	public double getXAR(){
		return data[12];
	}
	public double getYAR(){
		return data[13];
	}
	public double getZAR(){
		return data[14];
	}
	public void setZAR(double input){
		data[14] = input;
	}

	public double getSpeed() {
		return data[15];
	}

	public double getAccuracy() {
		return data[16];
	}
	
	public double getAltitude() {
		return data[17];
	}
	public double getGyroX() {
		return data[18];
	}

	public double getGyroY() {
		return data[19];
	}
	
	public double getGyroZ() {
		return data[20];
	}	
	public double[] getData(){
		return data;
	}
	
	

	@Override
	public String toString() { //separator for the db is ";\t"
		String text = data[0] 	
			+ ",\t" + data[1] 	+ ",\t" + data[2] 	+ ",\t" + data[3] 
			+ ",\t" + data[4] 	+ ",\t" + data[5] 	+ ",\t" + data[6]
			+ ",\t" + data[7] 	+ ",\t" + data[8] 	+ ",\t" + data[9]
			+ ",\t" + data[10] 	+ ",\t" + data[11] 	
			+ ",\t" + data[12] 	+ ",\t" + data[13] 	+ ",\t" + data[14]
			+ ",\t" + data[15] 	+ ",\t" + data[16] 	+ ",\t" + data[17]
			+ ",\t" + data[18] 	+ ",\t" + data[19] 	+ ",\t" + data[20];
		return text;
	}

	
}
