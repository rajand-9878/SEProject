package com.example.edpp;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Notification extends Activity implements OnItemClickListener{

	SharedPreferences sh;
	SQLiteDatabase db;
	ListView lv;
	String[] id,med,dose,time,instr,uid;
	int pos=0;
	String iid="",timenot="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);
		
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		db=openOrCreateDatabase("app", Context.MODE_PRIVATE, null);
		iid=sh.getString("lid", "");
		timenot=sh.getString("time", "");
		db.execSQL("CREATE TABLE IF NOT EXISTS medicen(id integer PRIMARY KEY AUTOINCREMENT,med text,dose text,time text,instr text,uid text)");
		


		lv=(ListView)findViewById(R.id.listView1);
		lv.setOnItemClickListener(this);
		viewmedicen(iid);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notification, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	private void viewmedicen(String iid) {
		// TODO Auto-generated method stub
		Cursor c=db.rawQuery("select * from medicen where time='"+timenot+"'", null);
		if(c.getCount()>0){
			c.moveToFirst();
			
			id=new String[c.getCount()];
			med=new String[c.getCount()];
			dose=new String[c.getCount()];
			time=new String[c.getCount()];
			instr=new String[c.getCount()];
			uid=new String[c.getCount()];
			int i=0;
			do {
				id[i]=c.getString(0);
				med[i]=c.getString(1);
				dose[i]=c.getString(2);
				time[i]=c.getString(3);
				instr[i]=c.getString(4);
				uid[i]=c.getString(5);
						
						i++;
			} while (c.moveToNext());
			lv.setAdapter(new Custom_medicen(getApplicationContext(), med, dose, time, instr));
		}
	}
}
