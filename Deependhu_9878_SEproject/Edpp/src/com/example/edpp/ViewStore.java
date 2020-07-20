package com.example.edpp;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.Bundle;
import android.os.Environment;
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
import android.widget.ListView;
import android.widget.Toast;

public class ViewStore extends Activity implements OnItemClickListener {

	ListView lv;
	SharedPreferences sp;
	int pos=0;
	String method="viewStore";
	String method1="makeorder";
	String[] name,place,email,phone,id,land;
	String soapaction=MainActivity.namespace+method;
	
	String prid="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_store);

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
				prid=getIntent().getStringExtra("pid");
			try
			{
				
				SoapObject sop=new SoapObject(MainActivity.namespace,method);
				SoapSerializationEnvelope senv=new SoapSerializationEnvelope(SoapEnvelope.VER11);
				senv.setOutputSoapObject(sop);

				HttpTransportSE htp=new HttpTransportSE(sp.getString("url", ""));
				htp.call(soapaction, senv);
				
				String result=senv.getResponse().toString();
				
			//	Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

		String[] list=result.split("\\^");
		if(list.length>0)
		{
			name=new String[list.length];
			place=new String[list.length];
			email=new String[list.length];
			phone=new String[list.length];
			id=new String[list.length];
			land=new String[list.length];

			for(int i=0;i<list.length;i++)
			{
				String[] item=list[i].split("\\#");
				name[i]=item[0]+" "+item[1];
				place[i]=item[1];
				email[i]=item[2];
				phone[i]=item[3];
				id[i]=item[4];
				land[i]=item[5];
			}			
			lv.setAdapter(new Customtr(getApplicationContext(), name,email,phone));
		}
		}
			catch(Exception e)
			{
				Toast.makeText(getApplicationContext(), "Error"+e, Toast.LENGTH_SHORT).show();
			}
			lv.setOnItemClickListener(this);	
		}

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				pos=arg2;
				Editor ed=sp.edit();
				ed.putString("id", id[arg2]);
				ed.commit();
				Toast.makeText(getApplicationContext(),"pos = "+pos, Toast.LENGTH_SHORT).show();		
				AlertDialog.Builder alert = new AlertDialog.Builder(ViewStore.this);
				alert.setTitle("Doctor Patient Portal");
				alert.setMessage("Order");

				alert.setPositiveButton("Place Order", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				  // Do something with value!
					
					
					sendorder( id[pos]);
				}

			
				});

				alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
					  
				
				  
				  }
				});

				alert.show();
			}

			private void sendorder(String id) {
				// TODO Auto-generated method stub
				try
				{
					SoapObject sop=new SoapObject(MainActivity.namespace,method1);
					sop.addProperty("phid", id);
					sop.addProperty("prscid", prid);
					SoapSerializationEnvelope senv=new SoapSerializationEnvelope(SoapEnvelope.VER11);
					senv.setOutputSoapObject(sop);

					HttpTransportSE htp=new HttpTransportSE(sp.getString("url", ""));
					htp.call(soapaction, senv);
					
					String result=senv.getResponse().toString();
					
					Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

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
