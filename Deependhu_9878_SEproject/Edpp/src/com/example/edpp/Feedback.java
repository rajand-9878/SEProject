package com.example.edpp;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Feedback extends Activity {

	EditText ed;
	Button b;
	String method="feedback";
	String soapaction=MainActivity.namespace+method;
	String lid="";
	SharedPreferences sh;
	
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		
		ed=(EditText)findViewById(R.id.editText1);
		b=(Button)findViewById(R.id.button1);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		lid=sh.getString("lid", "");
		
		try {
			if(android.os.Build.VERSION.SDK_INT>9){
				StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
				String fb=ed.getText().toString();
				if(fb.equalsIgnoreCase(""))
				{
					ed.setError("Enter Your Feedback");
					ed.setFocusable(true);
				}
				else
				{
				try {
					SoapObject sop=new SoapObject(MainActivity.namespace, method);
					sop.addProperty("lid", lid);
					sop.addProperty("fb", fb);
					
					SoapSerializationEnvelope senv=new SoapSerializationEnvelope(SoapEnvelope.VER11);
					senv.setOutputSoapObject(sop);
					
					HttpTransportSE htp=new HttpTransportSE(sh.getString("url", ""));
					htp.call(soapaction, senv);
					
					String result=senv.getResponse().toString();
					if(result.equalsIgnoreCase("ok")){
						Toast.makeText(getApplicationContext(), "Your Feedback added", Toast.LENGTH_LONG).show();
						
						Intent i=new Intent(getApplicationContext(), Home.class);
						startActivity(i);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				}
			}
		});
	}
}
