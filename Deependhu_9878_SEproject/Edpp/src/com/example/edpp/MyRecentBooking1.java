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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MyRecentBooking1 extends Activity implements OnItemClickListener {

	ListView lv;
	SharedPreferences sp;
	
	String method="mypbook";
	public static String[] name,date,status,totalamnt,oid,other;
	String soapaction=MainActivity.namespace+method;
	public static int pos=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_recent_booking1);

lv=(ListView)findViewById(R.id.listView1);
		
lv.setOnItemClickListener(this);
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
		sop.addProperty("uid",sp.getString("lid", ""));
		SoapSerializationEnvelope senv=new SoapSerializationEnvelope(SoapEnvelope.VER11);
		senv.setOutputSoapObject(sop);

		HttpTransportSE htp=new HttpTransportSE(sp.getString("url", ""));
		htp.call(soapaction, senv);
		
		String result=senv.getResponse().toString();
		
		//Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

String[] list=result.split("\\@");
if(list.length>0)
{
	name=new String[list.length];
	date=new String[list.length];
	status=new String[list.length];
	totalamnt=new String[list.length];
	oid=new String[list.length];
	other=new String[list.length];

	for(int i=0;i<list.length;i++)
	{
		String[] item=list[i].split("\\#");
		name[i]=item[0];
		date[i]=item[1];
		status[i]=item[2];
		oid[i]=item[3];
		other[i]=item[4];

	}
	
	lv.setAdapter(new Customtr(getApplicationContext(), name,date,status));
	
}
}
	catch(Exception e)
	{
		Toast.makeText(getApplicationContext(), "Error"+e, Toast.LENGTH_SHORT).show();
	}
	
}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		pos=arg2;
		Editor ed=sp.edit();
		ed.putString("result", other[arg2]);
		ed.putString("oid", oid[arg2]);
		
		ed.commit();
		startActivity(new Intent(getApplicationContext(), ViewOrderedMedicen.class));
	}

}
