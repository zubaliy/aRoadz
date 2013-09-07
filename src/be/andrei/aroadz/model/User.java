package be.andrei.aroadz.model;

import java.util.Locale;

/**
 * @author Andriy
 *
 *	TODO: Save user information in the SQLite database
 *	http://developer.android.com/guide/topics/data/data-storage.html#db
 *
 */
public class User {
	
	
	private static User user;
	
	private String	email,
					password,
					nickname,
					phone_model,
					android_version,
					android_firmware_build,
					android_language,
					gps_type; // no API to get GPS vendor

	private String 	accMaximumRange,
					accResolution,
					accMinDelay, //the minimum delay allowed between two events in microsecond or zero if this sensor only returns a value when the data it's measuring changes.
					accPower,
					accVendor,
					accVersion,
					accType;
	
	
	private User() {
		this.email = new String("default@default.com");
		this.password = new String("default");
		this.nickname = new String("Default");
		this.phone_model = new String(android.os.Build.MODEL);
		this.android_version = new String(android.os.Build.VERSION.RELEASE);
		this.android_firmware_build = new String("null");
		this.android_language = new String(Locale.getDefault().getLanguage());
		
		this.gps_type = new String("null");
		
		this.accMaximumRange = new String("null");
		this.accResolution = new String("null");
		this.accMinDelay = new String("null");
		this.accPower = new String("null"); 
		
	}

	public static User getInstance() {
		if (user == null) {
			user = new User();
		}
		return user;
	}
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPhone_model() {
		return phone_model;
	}

	public void setPhone_model(String phone_model) {
		this.phone_model = phone_model;
	}

	public String getAndroid_version() {
		return android_version;
	}

	public void setAndroid_version(String android_version) {
		this.android_version = android_version;
	}

	public String getAndroid_firmware_build() {
		return android_firmware_build;
	}

	public void setAndroid_firmware_build(String android_firmware_build) {
		this.android_firmware_build = android_firmware_build;
	}

	public String getAndroid_language() {
		return android_language;
	}

	public void setAndroid_language(String android_language) {
		this.android_language = android_language;
	}

	public String getGps_type() {
		return gps_type;
	}

	public void setGps_type(String gps_type) {
		this.gps_type = gps_type;
	}

	public String getAccMaximumRange() {
		return accMaximumRange;
	}

	public void setAccMaximumRange(String accMaximumRange) {
		this.accMaximumRange = accMaximumRange;
	}

	public String getAccResolution() {
		return accResolution;
	}

	public void setAccResolution(String accResolution) {
		this.accResolution = accResolution;
	}

	public String getAccMinDelay() {
		return accMinDelay;
	}

	public void setAccMinDelay(String accAccMinDelay) {
		this.accMinDelay = accAccMinDelay;
	}

	public String getAccPower() {
		return accPower;
	}

	public void setAccPower(String accPower) {
		this.accPower = accPower;
	}

	public String getAccVendor() {
		return accVendor;
	}

	public void setAccVendor(String accVendor) {
		this.accVendor = accVendor;
	}

	public String getAccVersion() {
		return accVersion;
	}

	public void setAccVersion(String accVersion) {
		this.accVersion = accVersion;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	@Override
	public String toString() {
		String s = new String();
		s  = "Accelerometer: " 
		   + "Power: " + this.getAccPower()
		   + "\nVendor: " + this.getAccVendor()
		   + "\nResolution: " + this.getAccResolution()
		   + "\nMin delay: " + this.getAccMinDelay()
		   + "\nMax range: " + this.getAccMaximumRange()
		   + "\nVersion: " + this.getAccVersion()
		   + "\tType: " + this.getAccType()
		
		   + "\nAndroid lang: " + this.getAndroid_language()
		   + "\tVersion: " + this.getAndroid_version()
		   + "\nPhone model: " + this.getPhone_model();
		
		
		
		return s;
	}


}
