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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Booking2 extends Activity {
	
	Spinner s;
	EditText et1;
	EditText et2;
	EditText et3;
	EditText et4;
	EditText et5;
	Button b1;
    Button b2;
    SharedPreferences sp ;
    String method="book";
	String soapaction=MainActivity.namespace+method;
	String iid,opid,day,did;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_booking2);
		
		et1=(EditText)findViewById(R.id.editText1);
		et2=(EditText)findViewById(R.id.editText2);
		et3=(EditText)findViewById(R.id.editText3);
		et4=(EditText)findViewById(R.id.editText4);
		et5=(EditText)findViewById(R.id.editText5);
        b1=(Button)findViewById(R.id.button1);
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
				
				String result=getIntent().getStringExtra("result");
				String[] temp=result.split("\\#");
				
				et1.setText(temp[0]);
				et2.setText(temp[1]);
				et3.setText(temp[2]);
				et4.setText(temp[3]);
				et5.setText(temp[4]);
				
				opid=temp[5];
				did=temp[6];
				day=getIntent().getStringExtra("day");
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
			iid=sp.getString("lid", "");	
				try
				{
					SoapObject sop=new SoapObject(MainActivity.namespace,method);
					sop.addProperty("day", day);
					sop.addProperty("opid", opid);
					sop.addProperty("lid",iid);
					sop.addProperty("did",did);

					SoapSerializationEnvelope senv=new SoapSerializationEnvelope(SoapEnvelope.VER11);
					senv.setOutputSoapObject(sop);
					HttpTransportSE htp=new HttpTransportSE(sp.getString("url", ""));
					htp.call(soapaction, senv);
					String result=senv.getResponse().toString();
					Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();
							
					if(result.contains("Booking successfull"))
					{
						startActivity(new Intent(getApplicationContext(), Booking2.class));
						
					}
					else
					{
							
			
						
							
							
							
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
