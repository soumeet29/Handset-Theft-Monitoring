package com.TheftMonitor;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Configure extends Activity{
	EditText et1,et2,et3;
	SharedPreferences share;
	String s1,s2,s3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.conf);
		et1=(EditText)findViewById(R.id.etmail);
		et2=(EditText)findViewById(R.id.etmobile);
		et3=(EditText)findViewById(R.id.etmessage);
		
		
	share=getSharedPreferences("Mobiletheft",0);
	
		et1.setText(share.getString("mailid",""));
		et2.setText(share.getString("phno",""));
		et3.setText(share.getString("msg",""));
		
	}

	
	
	public void save(View v){
		
	
		
	    Editor e=share.edit();
	    s1=et1.getText().toString();
	    s2=et2.getText().toString();
	    s3=et3.getText().toString();
	    
	      
	    if(!(s2.matches(""))){
	    	 e.putString("mailid",s1);
	 	    e.putString("phno",s2);
	 	    e.putString("msg",s3);	
	 	   
	 	    e.commit();
	 	    
	 	    Intent i=new Intent(this,Active.class);
	 		startActivity(i);
	    }
	    else{
	   Toast.makeText(this, "Must Enter Phone no. And Mail_ID", 5000).show();	    }
		
	}
   public void cancel(View v){
		System.exit(0);
	}
   public void contacts(View v){

		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
		startActivityForResult(intent, 1);
   }
   @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (data != null) {
	        Uri uri = data.getData();

	        if (uri != null) {
	            Cursor c = null;
	            try {
	       c = getContentResolver().query(uri, new String[]{ 
	                            ContactsContract.CommonDataKinds.Phone.NUMBER,  
	                            ContactsContract.CommonDataKinds.Phone.TYPE },
	                        null, null, null);

	                if (c != null && c.moveToFirst()) {
	                    String number = c.getString(0);
	                 //   int type = c.getInt(1);
	                    et2.setText(number);
	
	                  //  showSelectedNumber(type, number);
	                }
	            } finally {
	                if (c != null) {
	                    c.close();
	                }
	            }
	        }
	    }
	}

}
