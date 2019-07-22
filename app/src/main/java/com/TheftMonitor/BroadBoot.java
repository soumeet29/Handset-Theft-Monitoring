package com.TheftMonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadBoot extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
		
			Intent serviceIntent = new Intent();
			serviceIntent.setAction("com.TheftMonitor.SimchangeService");
			context.startService(serviceIntent);

		}

		}



