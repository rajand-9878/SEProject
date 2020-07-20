package com.example.edpp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class Viewbooking1 extends Activity {
	
	EditText et;
	Button b;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewbooking1);
		
		et=(EditText)findViewById(R.id.editText1);
		b=(Button)findViewById(R.id.button1);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Viewdoctor.class);
				startActivity(i);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.viewbooking1, menu);
		return true;
	}

}
