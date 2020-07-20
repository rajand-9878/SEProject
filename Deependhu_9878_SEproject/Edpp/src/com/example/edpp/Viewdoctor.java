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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Viewdoctor extends Activity {
	
	ListView lv;
	SharedPreferences sp;
	
	String method="viewdoc";
	String[] name,special,qual;
	String soapaction=MainActivity.namespace+method;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewdoctor);
		
		lv=(ListView)findViewById(R.id.listView1);
		

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
	

		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

	try
	{
		SoapObject sop=new SoapObject(MainActivity.namespace,method);
		
		SoapSerializationEnvelope senv=new SoapSerializationEnvelope(SoapEnvelope.VER11);
		senv.setOutputSoapObject(sop);

		HttpTransportSE htp=new HttpTransportSE(sp.getString("url", ""));
		htp.call(soapaction, senv);
		
		String result=senv.getResponse().toString();
		
	//	Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

String[] list=result.split("\\@");
if(list.length>0)
{
	name=new String[list.length];
	special=new String[list.length];
	qual=new String[list.length];
	
	for(int i=0;i<list.length;i++)
	{
		String[] item=list[i].split("\\#");
		name[i]=item[0];
		special[i]=item[1];
		qual[i]=item[2];
	
	}
	
	lv.setAdapter(new Customtr(getApplicationContext(), name,special,qual));
	
}
			
			
			

			
		}
		
		
		
	
	
	
	catch(Exception e)
	{
		Toast.makeText(getApplicationContext(), "Error"+e, Toast.LENGTH_SHORT).show();
	}
	
}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		startActivity(new Intent(getApplicationContext(), Home.class));
	}
}
