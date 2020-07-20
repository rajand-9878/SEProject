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
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
	EditText uname;
	EditText pwd;
	Button login;
	SharedPreferences sp;
	String method="login";
	String soapaction=MainActivity.namespace+method;
	String[] iid,type;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		uname=(EditText)findViewById(R.id.editText1);
		pwd=(EditText)findViewById(R.id.editText2);
		login=(Button)findViewById(R.id.button1);
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
//		uname.setText("suraj@gmail.com");
//		pwd.setText("1111");
		
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
		TextView tv=(TextView)findViewById(R.id.textView3);
		tv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), UserReg.class));
			}
		});
		login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String user=uname.getText().toString();
				String pass=pwd.getText().toString();
				if(user.equals(""))
				{
					uname.setError("Field Cannot be Empty");
					uname.requestFocus();
				}
				
				if(pass.equals(""))
				{
					
					pwd.setError("Field Cannot be Empty");
					pwd.requestFocus();
				}
				else{

				
				try
				{
					SoapObject sop=new SoapObject(MainActivity.namespace,method);
					sop.addProperty("unm", user);
					sop.addProperty("psw", pass);
					
					SoapSerializationEnvelope senv=new SoapSerializationEnvelope(SoapEnvelope.VER11);
					senv.setOutputSoapObject(sop);
					HttpTransportSE htp=new HttpTransportSE(sp.getString("url", ""));
					htp.call(soapaction, senv);
					
					String result=senv.getResponse().toString();
					
					if(result.equalsIgnoreCase("na")||result.equalsIgnoreCase("anytype{}"))
					{
						Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_LONG).show();
					}
					else
					{
							Editor ed=sp.edit();
							ed.putString("lid", result);
							ed.commit();
							Intent in1=new Intent(getApplicationContext(),TimeCheck.class);
							startService(in1);
							Intent in=new Intent(getApplicationContext(),Home.class);
							startActivity(in);
						}	
					}
				catch(Exception e)
				{
					Toast.makeText(getApplicationContext(), "Error"+e, Toast.LENGTH_SHORT).show();
				}
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		startActivity(new Intent(getApplicationContext(), MainActivity.class));
	}


}
