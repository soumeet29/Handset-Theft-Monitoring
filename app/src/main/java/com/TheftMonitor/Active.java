package com.TheftMonitor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Active extends Activity {
	Button b1, b2;
	ToggleButton t;
	SharedPreferences share;
	SharedPreferences.Editor edit;
	TelephonyManager tm;
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.active);
		tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		share = getSharedPreferences("Mobiletheft", 0);
		edit = share.edit();

		b1 = (Button) findViewById(R.id.button1);
		b2 = (Button) findViewById(R.id.button2);
		t = (ToggleButton) findViewById(R.id.toggleButton1);

		t.setChecked(share.getBoolean("State", false));
		

		
	
	}

	public void activate(View v) {
		
		
	if(share.getString("phno", "no").matches("no"))
		{
			Intent in= new Intent(this,Configure.class);
			startActivity(in);
		}
		else {
			
		
		if (t.isChecked()) {
			
			

			String oldss = tm.getSimSerialNumber();

			edit.putBoolean("State", true);

			edit.putString("oldss", oldss);
			edit.commit();
			Toast.makeText(this, "oldss" + oldss, Toast.LENGTH_LONG).show();
			Toast.makeText(this, "activated", Toast.LENGTH_LONG).show();
			Intent i = new Intent(this, SimchangeService.class);
			startService(i);

		} else {
			edit.putBoolean("State", false);
			edit.commit();
			Intent i = new Intent(this, SimchangeService.class);
			stopService(i);
			Toast.makeText(this, "deactivated", Toast.LENGTH_LONG).show();
		}//else

		}//main else
	
		

	
	}// on click

	public void conf(View v) {
		Intent i = new Intent(this, Configure.class);
		startActivity(i);

	}

	public void changepswd(View v) {

		Intent i = new Intent(this, ChangePassword.class);
		startActivity(i);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		t.setChecked(share.getBoolean("State", false));
		Toast.makeText(this, "App In Active Mode", Toast.LENGTH_LONG).show();
	}
	
	
	
	
}
