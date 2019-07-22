package com.TheftMonitor;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Password extends Activity {

	SharedPreferences share;
	SharedPreferences.Editor edit;
	String password;
	Dialog d;
	EditText etnewpass, etnewrepass, etpass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password);

		share = getSharedPreferences("Mobiletheft", 0);
		edit = share.edit();

		etpass = (EditText) findViewById(R.id.etpassword);

		password = share.getString("password", null);
		if (password == null) {

			d = new Dialog(Password.this);
			d.setTitle("New Password");
			d.setContentView(R.layout.newpassword);
			d.show();
			etnewpass = (EditText) d.findViewById(R.id.newpass_etpass);
			etnewrepass = (EditText) d.findViewById(R.id.newpass_etrepass);
			Button newpass = (Button) d.findViewById(R.id.newpass_bok);
			newpass.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String newpass = etnewpass.getText().toString();
					String renewpass = etnewrepass.getText().toString();

					
							
					if (!(newpass.equals("") && renewpass.equals(""))) {
						if (newpass.equals(etnewrepass.getText().toString())) {
							edit.putString("password", etnewpass.getText()
									.toString());
							edit.commit();
							Toast.makeText(Password.this, "New PAssword Saved",
									3000).show();
							d.dismiss();
						} else {
							Toast.makeText(Password.this,
									"password miss match", 3000).show();
						}
					} else {
						Toast.makeText(Password.this, "Enter New password",
								3000).show();
						
						

					}
				}
			});

		}

	}

	public void ok(View v) {
	
		password = share.getString("password", null);
		if (password == null) 
				{
			Toast.makeText(Password.this, "Must Create New password",
					3000).show();	
			d.show();
				
				} 
		
			else {
			String etpasword = etpass.getText().toString();
			if (etpasword.matches(password)) {
				Toast.makeText(Password.this,
						"HOME", 3000).show();
				etpass.setText("");
				Intent i = new Intent(this, Active.class);
				startActivity(i);

			}
			else{
				Toast.makeText(Password.this,
						"Wrong Password", 3000).show();				
				
			}
		}
	}

}
