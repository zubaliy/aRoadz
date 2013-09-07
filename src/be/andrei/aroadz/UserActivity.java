package be.andrei.aroadz;

import be.andrei.aroadz.model.User;
import be.andrei.aroadz.model.UserDB;
import be.andrei.aroadz.utils.Config;
import be.andrei.aroadz.utils.GUI;
import be.andrei.aroadz.utils.Toasts;

import be.andrei.aroadz.utils.GUI_user;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TextView;

public class UserActivity extends Activity{
	
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_user);
		
		GUI_user.init(this);

		GUI_user.txt_user.setText(User.getInstance().toString());

		this.addListeners();

    }
    

    
    private void addListeners() {
    	GUI_user.btn_save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				User.getInstance().setNickname(GUI_user.txtb_name.getText().toString());
				User.getInstance().setEmail(GUI_user.txtb_email.getText().toString());
				User.getInstance().setPassword(GUI_user.txtb_password.getText().toString());


				UserDB.getInstance().updateUser();
				
			}
		});

    	

/* Test feature   	
		GUI_user.txtb_name.setOnFocusChangeListener(new OnFocusChangeListener() {          

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus){
					User.getInstance().setNickname(GUI_user.txtb_name.getText().toString());
				}				
			}
        });
    	
    	GUI_user.txtb_email.setOnFocusChangeListener(new OnFocusChangeListener() {          

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus){
					User.getInstance().setNickname(GUI_user.txtb_email.getText().toString());
				}				
			}
        });
    	
    	GUI_user.txtb_password.setOnFocusChangeListener(new OnFocusChangeListener() {          

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus){
					User.getInstance().setNickname(GUI_user.txtb_password.getText().toString());
				}				
			}
        });
*/
    
    }
    
}
