package com.example.edpp;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Viewappoint1 extends Activity implements OnItemClickListener{
	ListView lv;
	SharedPreferences sp;
	String method="viewappoint";
	String soapaction=MainActivity.namespace+method;
	String method1="cancel";
	String soapaction1=MainActivity.namespace+method;

	
	
	String[] dname,special,date,time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewappoint1);
		
		lv=(ListView)findViewById(R.id.listView1);
		lv.setOnItemClickListener(this);
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		
		try {
			String result=getIntent().getStringExtra("re");
			String[] temp=result.split("\\@");
			
		if(temp.length>0){
			dname=new String[temp.length];
			special=new String[temp.length];
			date= new String[temp.length];
			
			
						
				for(int i=0;i<temp.length;i++){
					
					String[] r=temp[i].split("\\#");
					dname[i]=r[0];
					special[i]=r[1];
					date[i]=r[2];
					
				}			
				lv.setAdapter(new Customtr(getApplicationContext(), dname, special, date));

		}else {
			Intent i=new Intent(getApplicationContext(), Viewappoint.class);
		    startActivity(i);
		
		}
		} catch (Exception e) {
			// TODO: handle exception

		Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}
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
	


			
			

			
		
		
		
		
	



}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.viewappoint1, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		AlertDialog.Builder alert = new AlertDialog.Builder(Viewappoint1.this);

		alert.setTitle("Doctor Patient Portal");
		alert.setMessage("Delete Appointment");

		alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		  // Do something with value!
		  
			try
			{
				SoapObject sop=new SoapObject(MainActivity.namespace,method1);
				sop.addProperty("opid", Viewappoint.opid);
				SoapSerializationEnvelope senv=new SoapSerializationEnvelope(SoapEnvelope.VER11);
				senv.setOutputSoapObject(sop);
				HttpTransportSE htp=new HttpTransportSE(sp.getString("url", ""));
				htp.call(soapaction1, senv);
				String result=senv.getResponse().toString();
		
				Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
				
				Intent i=new Intent(getApplicationContext(), Viewappoint.class);
			    startActivity(i);
				
				
				}
			catch(Exception e)
			{
				Toast.makeText(getApplicationContext(), "Error"+e, Toast.LENGTH_SHORT).show();
			}
			
		
		}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});

		alert.show();
       	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		startActivity(new Intent(getApplicationContext(), Home.class));
	}

}
