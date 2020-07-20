package com.example.edpp;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Pay extends Activity {

	String oid="",tot;
	TextView tv;
	EditText ed,ed2;
	Button b;
	
	SharedPreferences sp;
	String method="order";
	String soapaction=MainActivity.namespace+method;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);
		
		
		ed=(EditText)findViewById(R.id.editText1);
		ed2=(EditText)findViewById(R.id.editText2);
		tv=(TextView)findViewById(R.id.textView12);
		b=(Button)findViewById(R.id.button1);
		tot=ViewOrderedMedicen.tot;
		tv.setText(tot);
		oid=getIntent().getStringExtra("oid");
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					SoapObject sop=new SoapObject(MainActivity.namespace,method);
					sop.addProperty("oid", oid);
					sop.addProperty("tot", tot);
					sop.addProperty("acn", ed.getText().toString());
					sop.addProperty("scode",  ed2.getText().toString());
					SoapSerializationEnvelope senv=new SoapSerializationEnvelope(SoapEnvelope.VER11);
					senv.setOutputSoapObject(sop);
					
					HttpTransportSE htp=new HttpTransportSE(sp.getString("url", ""));
					htp.call(soapaction, senv);
					
					String result=senv.getResponse().toString();
					
					
						Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();
						
					if(result.equalsIgnoreCase("Paid Successful.......")){
						startActivity(new Intent(getApplicationContext(), Result.class));
					}
				} catch (Exception e) {
					// TODO: handle exception
					
					Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
					
				}	
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pay, menu);
		return true;
	}

}
