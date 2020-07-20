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
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewOrderedMedicen extends Activity {

	
	ListView lv;
	SharedPreferences sp;
	
	public static String tot="",oid;
	String[] mname,qnty,amnt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_ordered_medicen);

		lv=(ListView)findViewById(R.id.listView1);
		TextView tv=(TextView)findViewById(R.id.textView2);	

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
		
		
		String result=sp.getString("result", "");
		oid=sp.getString("oid", "");
//		Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

String[] list=result.split("\\$");
if(list.length>0)
{
	mname=new String[list.length];
	qnty=new String[list.length];
	amnt=new String[list.length];

	for(int i=0;i<list.length;i++)
	{
		String[] item=list[i].split("\\^");
		mname[i]=item[0];
		qnty[i]=item[1];
		amnt[i]=item[2];
		tot=item[3];
	}
	tv.setText(tot);
	lv.setAdapter(new Customtr(getApplicationContext(), mname,qnty,amnt));
	
}
			
			
			

			
		}
		
		
		
	
	
	
	catch(Exception e)
	{
		Toast.makeText(getApplicationContext(), "Error"+e, Toast.LENGTH_SHORT).show();
	}
	Button b=(Button)findViewById(R.id.button1);

	b.setVisibility(View.GONE);
	if(MyRecentBooking1.status[MyRecentBooking1.pos].equalsIgnoreCase("not paid"))
	{
		
		b.setVisibility(View.VISIBLE);
	}
	b.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
		Intent i=new Intent(getApplicationContext(), Pay.class);
		i.putExtra("oid", oid);
		startActivity(i);
		}
	});
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.viewdoctor, menu);
		return true;
	}

}
