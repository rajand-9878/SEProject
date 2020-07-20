package com.example.edpp;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.example.edpp.R.string;

import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Viewappoint extends Activity {
	
	EditText appoint;
	Button b;
	SharedPreferences sp;
	String method="viewappoint";
	String soapaction=MainActivity.namespace+method;
   
	
	
	public static String opid="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewappoint);
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		appoint=(EditText)findViewById(R.id.editText1);
		b=(Button)findViewById(R.id.button1);
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
				
//		b.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				String apid=appoint.getText().toString();
				//opid=apid;
				try
				{
					SoapObject sop=new SoapObject(MainActivity.namespace,method);
					sop.addProperty("pid", sp.getString("lid", ""));
					SoapSerializationEnvelope senv=new SoapSerializationEnvelope(SoapEnvelope.VER11);
					senv.setOutputSoapObject(sop);
					HttpTransportSE htp=new HttpTransportSE(sp.getString("url", ""));
					htp.call(soapaction, senv);
					String result=senv.getResponse().toString();
					if(!result.equalsIgnoreCase("na"))
					{
					Intent i=new Intent(getApplicationContext(), Viewappoint1.class);
				    i.putExtra("re", result);
					startActivity(i);
					
					}
					}
				catch(Exception e)
				{
					Toast.makeText(getApplicationContext(), "Error"+e, Toast.LENGTH_SHORT).show();
				}
				
				

//			}
//		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.viewappoint, menu);
		return true;
	}

}
