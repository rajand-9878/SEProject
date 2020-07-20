package com.example.edpp;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Presc1 extends Activity implements OnItemClickListener{
	ListView lv;
String fpath="";
String[] date,dname,presc,preid;
SharedPreferences sp;

String method="prescription";

String soapaction=MainActivity.namespace+method;

ProgressDialog mProgressDialog;
private PowerManager.WakeLock mWakeLock;
String pos;
int p=0;
String fname="";
static final int DIALOG_DOWNLOAD_PROGRESS = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_presc1);
		
		lv=(ListView)findViewById(R.id.listView1);
		lv.setOnItemClickListener(this);
		
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		fpath=sp.getString("path", "");
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
				try
				{
					String	iid=sp.getString("lid", "");	
//					Toast.makeText(getApplicationContext(), iid, Toast.LENGTH_SHORT).show();

					SoapObject sop=new SoapObject(MainActivity.namespace,method);
					sop.addProperty("uid",iid);
					SoapSerializationEnvelope senv=new SoapSerializationEnvelope(SoapEnvelope.VER11);
					senv.setOutputSoapObject(sop);

					HttpTransportSE htp=new HttpTransportSE(sp.getString("url", ""));
					htp.call(soapaction, senv);
					
					String result=senv.getResponse().toString();
					
					
if(!result.equalsIgnoreCase("na")){
			String[] list=result.split("\\@");
			if(list.length>0)
			{
				date=new String[list.length];
				dname=new String[list.length];
				presc=new String[list.length];
				preid=new String[list.length];
				for(int i=0;i<list.length;i++)
				{
					String[] item=list[i].split("\\#");
					preid[i]=item[0];
					date[i]=item[1];
					dname[i]=item[2];
					presc[i]=item[3];
				

				}
				
				lv.setAdapter(new Customtr(getApplicationContext(), date,dname,presc));
			}
			}
						
						
						

						
					}
					
					
					
				
				
				
				catch(Exception e)
				{
					Toast.makeText(getApplicationContext(), "Error"+e, Toast.LENGTH_SHORT).show();
				}

				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.presc1, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

		p=arg2;
		fpath=sp.getString("path", "")+"prescription/";
		fname=presc[arg2];
		AlertDialog.Builder alert = new AlertDialog.Builder(Presc1.this);

		alert.setTitle("Doctor Patient Portal");
		alert.setMessage("Download Prescription");

		alert.setPositiveButton("Download", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		  // Do something with value!
			
			  fpath+=fname;
			   startDownload(fpath);
			   
			  // Toast.makeText(getApplicationContext(),Environment.getExternalStorageDirectory().getAbsolutePath()+fname, Toast.LENGTH_LONG).show();
	           
		}
		});

		alert.setNegativeButton("Make Order", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
			  
		Intent i=new Intent(getApplicationContext(), ViewStore.class);
		i.putExtra("pid", preid[p]);
		startActivity(i);
		  
		  
		  }
		});

		alert.show();

	}
	private void startDownload(String url) {
		
		 new DownloadFileAsync().execute(url);
	}	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
			case DIALOG_DOWNLOAD_PROGRESS:
				mProgressDialog = new ProgressDialog(this);
				mProgressDialog.setMessage("Downloading File...");
				mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				mProgressDialog.setCancelable(false);
				mProgressDialog.show();
				return mProgressDialog;
		}
		return null;
	}

	class DownloadFileAsync extends AsyncTask<String, String, String> {
	   
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
	       		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
	        		mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
	             		getClass().getName());
	        		mWakeLock.acquire();
			showDialog(DIALOG_DOWNLOAD_PROGRESS);
		}
	
		@Override
		protected String doInBackground(String... aurl) {
			int count;
	
		try {
	
			URL url = new URL(aurl[0]);
			URLConnection conexion = url.openConnection();
			conexion.connect();
		
			int lenghtOfFile = conexion.getContentLength();
			Log.d("ANDRO_ASYNC", "Length of file: " + lenghtOfFile);
		
			String filename = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss").format(new Date(lenghtOfFile))+fname;
			InputStream input = new BufferedInputStream(url.openStream());
			OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + filename);
		
			byte data[] = new byte[1024];
		
			long total = 0;
		
				while ((count = input.read(data)) != -1) {
					total += count;
					publishProgress(""+(int)((total*100)/lenghtOfFile));
					output.write(data, 0, count);
				}
				
				output.flush();
				output.close();
				input.close();
		
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}		
		
		protected void onProgressUpdate(String... progress) {
			 Log.d("ANDRO_ASYNC",progress[0]);
			 mProgressDialog.setProgress(Integer.parseInt(progress[0]));
		}
	
		@Override
		protected void onPostExecute(String unused) {
			dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
		}
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		startActivity(new Intent(getApplicationContext(), Home.class));
	}
}
