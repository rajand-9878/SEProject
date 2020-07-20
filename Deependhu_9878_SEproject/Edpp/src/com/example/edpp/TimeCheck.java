package com.example.edpp;


import java.text.SimpleDateFormat;
import java.util.Date;


import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;
public class TimeCheck extends Service{

	SharedPreferences sh;
	Handler hd=new Handler();
	String timee="",nowtime="";
	int flg=0;
	String[] id,med,dose,time,instr,uid;
	@Override
	public void onCreate(){
		super.onCreate();
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	hd.post(r);
	

	}	

	
	public Runnable r=new Runnable() {
	
		@SuppressLint("SimpleDateFormat")
		@Override
		public void run() {

			
					
			SimpleDateFormat sd=new SimpleDateFormat("kk:mm");
			
			String tm=sd.format(new Date());
			SQLiteDatabase db;
				if(flg==0){
			db=openOrCreateDatabase("app", Context.MODE_PRIVATE, null);
				db.execSQL("CREATE TABLE IF NOT EXISTS medicen(id integer PRIMARY KEY AUTOINCREMENT,med text,dose text,time text,instr text,uid text)");
			//	Toast.makeText(getApplicationContext(), "---"+tm, Toast.LENGTH_LONG).show();
				
				Cursor c=db.rawQuery("select * from medicen where time='"+tm+"'", null);
				if(c.getCount()>0){
					c.moveToFirst();
					
					Editor ed=sh.edit();
					ed.putString("time", tm);
					ed.commit();
					flg=1;
					viewnotification();
					
					
				
					
				}else{
					flg=0;
				}
				
				}
			hd.postDelayed(r, 55000);
		}

		
	
	};
		
	public void viewnotification() {
		// TODO Auto-generated method stub

NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
      .setSmallIcon(R.drawable.ic_launcher)
      .setContentTitle("Medicem Reminder")
      .setContentText("This is a Medicem Reminder")
      .setAutoCancel(true);
//Creates an explicit intent for an Activity in your app
Intent resultIntent = new Intent(getApplicationContext(), Notification.class);

//The stack builder object will contain an artificial back stack for the
//started Activity.
//This ensures that navigating backward from the Activity leads out of
//your application to the Home screen.
TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//Adds the back stack for the Intent (but not the Intent itself)
stackBuilder.addParentStack(Notification.class);
//Adds the Intent that starts the Activity to the top of the stack
stackBuilder.addNextIntent(resultIntent);
PendingIntent resultPendingIntent =stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
mBuilder.setContentIntent(resultPendingIntent);
//mBuilder.setVibrate();
android.app.Notification note=mBuilder.build();
note.defaults |= android.app.Notification.DEFAULT_VIBRATE;
note.defaults |= android.app.Notification.DEFAULT_SOUND;

NotificationManager mNotificationManager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//mId allows you to update the notification later on.
mNotificationManager.notify(100,note);// mBuilder.build());


	}
	
		
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
