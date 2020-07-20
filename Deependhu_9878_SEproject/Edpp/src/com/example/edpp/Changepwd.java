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
import android.widget.Toast;

public class Changepwd extends Activity {
	EditText old;
	EditText newp;
	EditText cnf;
	Button change;
	SharedPreferences sp;
	String method="change";
	String soapaction=MainActivity.namespace+method;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changepwd);
		old=(EditText)findViewById(R.id.editText1);
		newp=(EditText)findViewById(R.id.editText2);
		cnf=(EditText)findViewById(R.id.editText3);
		change=(Button)findViewById(R.id.button1);
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
				
				change.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						String oldp=old.getText().toString();
						String newpass=newp.getText().toString();
						String confirm=cnf.getText().toString();
						if(oldp.equals(""))
						{
							old.setError("Field Cannot be Empty");
							old.requestFocus();
						}
						
						if(newpass.equals(""))
						{
							
							newp.setError("Field Cannot be Empty");
							newp.requestFocus();
						}
						if(confirm.equals(""))
						{
							
							cnf.setError("Field Cannot be Empty");
							cnf.requestFocus();
						}
						else{


						if(newpass.equals(confirm))
						{
						try
						{
							SoapObject sop=new SoapObject(MainActivity.namespace,method);
							sop.addProperty("old", oldp);
							sop.addProperty("new", newpass);
							sop.addProperty("lid",sp.getString("lid", ""));
							SoapSerializationEnvelope senv=new SoapSerializationEnvelope(SoapEnvelope.VER11);
							senv.setOutputSoapObject(sop);
							HttpTransportSE htp=new HttpTransportSE(sp.getString("url", ""));
							htp.call(soapaction, senv);
							String result=senv.getResponse().toString();
							
							Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
								
							}
						
						
						
						catch(Exception e)
						{
							Toast.makeText(getApplicationContext(), "Error"+e, Toast.LENGTH_SHORT).show();
						}
						
						}
					else	
						{
							Toast.makeText(getApplicationContext(), "password missmatch", Toast.LENGTH_SHORT).show();

						}
					}}
				});

				
				}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.changepwd, menu);
		return true;
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		startActivity(new Intent(getApplicationContext(), Home.class));
	}
}
