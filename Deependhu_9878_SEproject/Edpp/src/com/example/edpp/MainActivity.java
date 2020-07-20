package com.example.edpp;

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

public class MainActivity extends Activity {
	EditText e1;
	Button b1;
	SharedPreferences sp;
	public static String namespace="urn:demo";
	String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1=(EditText)findViewById(R.id.editText1);
        b1=(Button)findViewById(R.id.button1);
        sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1.setText(sp.getString("ip", ""));
        
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
        b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			String ip=e1.getText().toString();
			if(ip.equalsIgnoreCase(""))
			{
				e1.setError("Enter Ip");
				
			}
				
			else
			{
				url="http://"+ip+"/Online%20Pharmacy/web%20service.php?wsdl";
				Editor ed=sp.edit();
				ed.putString("ip", ip);
				ed.putString("url", url);
				ed.putString("path", "http://"+ip+"/Online%20Pharmacy/");
				ed.commit();
				
				if(sp.getString("lid", "0").equalsIgnoreCase("0")){
				Intent i=new Intent(getApplicationContext(),Login.class);
				startActivity(i);
				}else {
					Intent i=new Intent(getApplicationContext(),Home.class);
					startActivity(i);
				}
			}
				
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
