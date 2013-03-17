package be.andrei.aroadz.model;

import java.util.Locale;

public class User {
	private static User user;
	
	private String email;
	private String password;
	private String nickname;
	private String phone_model;
	private String android_version;
	private String android_firmware_build;
	private String android_language;
	private String gps_type;

	private String accMaximumRange;
	private String accResolution;
	private String accAccMinDelay;
	
	private User() {
		this.email = new String("zubaliy@gmail.com");
		this.password = new String("test");
		this.nickname = new String("azertiy");
		this.phone_model = new String(android.os.Build.MODEL);
		this.android_version = new String(android.os.Build.VERSION.RELEASE);
		this.android_firmware_build = new String("null");
		this.android_language = new String(Locale.getDefault().getLanguage());
		
		this.gps_type = new String("null");
		
		this.accMaximumRange = new String("null");
		this.accResolution = new String("null");
		this.accAccMinDelay = new String("null");
		
	}

	public static User getUser() {
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
		return accAccMinDelay;
	}

	public void setAccMinDelay(String accAccMinDelay) {
		this.accAccMinDelay = accAccMinDelay;
	}




}
