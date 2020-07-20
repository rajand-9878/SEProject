package com.example.edpp;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class Booking1 extends Activity {
Spinner s;
EditText s2;
Button b;
SharedPreferences sp;
String method="booking";
String soapaction=MainActivity.namespace+method;
String[] iid,type;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_booking1);
		
		
		s=(Spinner)findViewById(R.id.spinner1);
		s2=(EditText)findViewById(R.id.editText1);
		b=(Button)findViewById(R.id.button1);
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

		
		// Soap Version support
				try
				{
					if(android.os.Build.VERSION.SDK_INT >9)
					{
						StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
						StrictMode.setThreadPolicy(policy);
					}
				}
				catch(Exception e)
				{
					
				}
				
		

				
		
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String day1=s2.getText().toString();
				String special1=s.getSelectedItem().toString();
				
				try
				{
					SoapObject sop=new SoapObject(MainActivity.namespace,method);
					sop.addProperty("day", day1);
					sop.addProperty("special",special1);
					SoapSerializationEnvelope senv=new SoapSerializationEnvelope(SoapEnvelope.VER11);
					senv.setOutputSoapObject(sop);
					HttpTransportSE htp=new HttpTransportSE(sp.getString("url", ""));
					htp.call(soapaction, senv);
					String result=senv.getResponse().toString();
					//Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
					if(result.equalsIgnoreCase("na")||result.equalsIgnoreCase("anytype{}"))
					{
						Toast.makeText(getApplicationContext(), "Not Available...", Toast.LENGTH_LONG).show();
						
					}
					else
					{
							Intent in=new Intent(getApplicationContext(),Booking2.class);
							in.putExtra("result", result);
							in.putExtra("day", day1);
							startActivity(in);
	
						
							
							
							
						}
						
						
						
						

						
					}
					
					
					
				
				catch(Exception e)
				{
					Toast.makeText(getApplicationContext(), "Error"+e, Toast.LENGTH_SHORT).show();
				}
				
				
				
				
			}
		});

		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		startActivity(new Intent(getApplicationContext(), Home.class));
	}

}
