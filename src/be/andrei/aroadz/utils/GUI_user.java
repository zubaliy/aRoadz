package be.andrei.aroadz.utils;

import be.andrei.aroadz.controller.GPS;
import be.andrei.aroadz.controller.Internet;
import be.andrei.aroadz.model.User;
import be.andrei.aroadz.R;


import android.app.Activity;
import android.graphics.Color;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

public class GUI_user {


	
	//layout_user
	public static EditText  txtb_name, 
							txtb_email, 
							txtb_password;
	
	public static Button	btn_save;
	
	public static TextView txt_user;
	
	
	
	public static void init(Activity activity){

		
		//== layout_user
		txtb_name = (EditText) activity.findViewById(R.id.txtb_name);
		txtb_email = (EditText) activity.findViewById(R.id.txtb_email);
		txtb_password = (EditText) activity.findViewById(R.id.txtb_password);
		
		txtb_name.setText(User.getInstance().getNickname());
		txtb_email.setText(User.getInstance().getEmail());
		txtb_password.setText(User.getInstance().getPassword());
		
		
		btn_save = (Button) activity.findViewById(R.id.btn_save);
		
		
		txt_user = (TextView) activity.findViewById(R.id.txt_user);
		

	}
	


}
