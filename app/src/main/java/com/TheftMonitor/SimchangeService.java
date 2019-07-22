package com.TheftMonitor;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class SimchangeService extends Service {
	TelephonyManager tm;
	SharedPreferences share;
	LocationManager locationManager;
	SmsManager sms;
	String result_address, location_lati_long;
	
	
	Location currentLocation;	
     double currentLongitude;
	double currentLatitude;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		share = getSharedPreferences("Mobiletheft", 0);

		tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		// getting location information
		Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();
		
		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE); 

	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();

	}

	@SuppressWarnings("rawtypes")
	@Override
	public void onStart(Intent intent, int startId) {

		super.onStart(intent, startId);
		// getting location
		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				updateLocation(location);
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
			}

			public void onProviderDisabled(String provider) {
			}
		};

		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		
		if(locationListener!=null){
			
		}

		try {
			Geocoder gcd = new Geocoder(this, Locale.getDefault());
			List addresses = gcd.getFromLocation(currentLatitude,
					currentLongitude, 100);
			
			
			// get latitude and longitude
			
			
			if (addresses.size() > 0) {
				StringBuilder result = new StringBuilder();
			
					Address address = (Address) addresses.get(0);
					int maxIndex = address.getMaxAddressLineIndex();
					for (int x = 0; x <= maxIndex; x++) {
						result.append(address.getAddressLine(x));
						result.append(",");
					}
					result.append(address.getLocality());
					result.append(",");
					//result.append(address.getPostalCode());
					result.append("\n");
					
					// getting total address
					result_address=result.toString();
				
		Toast.makeText(this, location_lati_long, Toast.LENGTH_LONG).show();	
		Toast.makeText(this, result_address, Toast.LENGTH_LONG).show();	
			}
		} catch (IOException ex) {
			Toast.makeText(this, location_lati_long, Toast.LENGTH_LONG).show();	
			Toast.makeText(this, (ex.getMessage().toString())+"no service", Toast.LENGTH_LONG).show();
			result_address=ex.getMessage().toString();
		}

		
		
		String ss = tm.getSimSerialNumber();

		String oldss = share.getString("oldss", "nonum");
		sendingsms();
		if (!(ss.matches(oldss))) {
			//sendingsms();
			// sendMail();
			Toast.makeText(this, "sim changed", Toast.LENGTH_LONG).show();

		} else {
			Toast.makeText(this, oldss + "=" + ss, Toast.LENGTH_LONG).show();
		}

		Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

	}

	void sendingsms() {

		String s2, s3;

		s2 = share.getString("phno", "");
		s3 = share.getString("msg", "") + "  \n" +location_lati_long+ "  \n"+ result_address;
				
		Toast.makeText(this, " " + s2 + " /n" + "location :" + s3,
				Toast.LENGTH_LONG).show();
		sms = SmsManager.getDefault();
		sms.sendTextMessage(s2, null, s3, null, null);

	}

	void sendMail() {
		String s1, s3;
		s1 = share.getString("mailid", "");
		s3 = share.getString("msg", "") + "  \n" + result_address
				+ location_lati_long;
		Intent mail = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:" + s1));
		mail.putExtra(Intent.EXTRA_SUBJECT, s3);
		mail.setType("message/rfc822");
		startActivity(mail);

	}

	// location update
	void updateLocation(Location location) {
		currentLocation = location;
		currentLatitude = currentLocation.getLatitude();
		currentLongitude = currentLocation.getLongitude();
	}
	
}
