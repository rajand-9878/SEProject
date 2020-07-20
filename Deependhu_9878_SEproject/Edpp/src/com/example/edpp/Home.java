package com.example.edpp;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends Activity {
 Button b1;
 Button b2;
 Button b4;
 Button b3;
 Button b5;
 Button b6;
 Button b7;
 Button b8;
 Button b9;
 Button b10;
 SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		  sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		  TextView ed2=(TextView)findViewById(R.id.textView2);
		  
		  ed2.setText("Patient ID: "+sp.getString("lid", ""));
		b1=(Button)findViewById(R.id.button1);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Booking1.class);
				startActivity(i);
				
				
			}
		});
		b2=(Button)findViewById(R.id.button2);
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Viewappoint.class);
				startActivity(i);
				
				
			}
		});
		b4=(Button)findViewById(R.id.button4);
		b4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Viewdoctor.class);
				startActivity(i);
				
			}
		});
		b3=(Button)findViewById(R.id.button3);
		b3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i= new Intent(getApplicationContext(),Presc1.class);
				startActivity(i);
				
			}
		});
		b5=(Button)findViewById(R.id.button5);
		b5.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i= new Intent(getApplicationContext(),Medical1.class);
				startActivity(i);
			}
		});
       b6=(Button)findViewById(R.id.button6);
       b6.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			
			Intent i= new Intent(getApplicationContext(),Changepwd.class);
			startActivity(i);
		}
	});
      b7=(Button)findViewById(R.id.button7);
      b7.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub

			Editor ed=sp.edit();
			ed.putString("lid", "0");
			
			ed.commit();
			Toast.makeText(getApplicationContext(), "Signout successfully", Toast.LENGTH_SHORT).show();

			Intent i= new Intent(getApplicationContext(),Login.class);
			startActivity(i);
		}
	});
       b9=(Button)findViewById(R.id.button9);
      b9.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			Intent i=new Intent(getApplicationContext(),MyRecentBooking1.class);
			startActivity(i);
		}
	});
      b8=(Button)findViewById(R.id.button8);
      b8.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			Intent i=new Intent(getApplicationContext(),Feedback.class);
			startActivity(i);
		}
	});
      b10=(Button)findViewById(R.id.button10);
      b10.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			Intent i=new Intent(getApplicationContext(),UploadPrec.class);
			startActivity(i);
		}
	});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	@Override
	public void onBackPressed() {
		
		Intent i=new Intent(getApplicationContext(),MainActivity.class);
		startActivity(i);
		
		
		// TODO Auto-generated method stub
		super.onBackPressed();
	}



}
