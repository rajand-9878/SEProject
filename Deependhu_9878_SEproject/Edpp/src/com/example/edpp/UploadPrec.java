package com.example.edpp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;



import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class UploadPrec extends Activity {

	EditText ed2;
	Button b1;
	
	String namespace="urn:demo";
	String url="",no="",uid="",prid="";
	String method="testreport";
	String soapaction="urn:demo/testreport";
	
	SharedPreferences sh;
	
	
	private static final int GALLERY_CODE = 201;
	 private Uri mImageCaptureUri;
	 private File outPutFile = null;
	 
	 String path="",attach="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_prec);

	
		ed2=(EditText)findViewById(R.id.editText2);
		ed2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			     startActivityForResult(i, GALLERY_CODE);
			}
		});
		b1=(Button)findViewById(R.id.button1);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	    url=sh.getString("url", "");
		uid=sh.getString("lid", url);
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				String ddgree=ed2.getText().toString();
				if(ddgree.equalsIgnoreCase(""))
				{
					ed2.setError("upload file..");
				}
				else
				{
				try {
					SoapObject sop=new SoapObject(namespace, method);
				
					sop.addProperty("file_name", attach);
					sop.addProperty("uid", uid);
					
					SoapSerializationEnvelope senv=new SoapSerializationEnvelope(SoapEnvelope.VER11);
					senv.setOutputSoapObject(sop);
					
					HttpTransportSE htp=new HttpTransportSE(url);
					htp.call(soapaction, senv);
					String result=senv.getResponse().toString();
					Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
					if(!result.equalsIgnoreCase("invalid type")){
						startActivity(new Intent(getApplicationContext(), Home.class));
						}
				
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
				}
				}
				
				
				
			}
		});
	}

	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {
			   
			   mImageCaptureUri = data.getData();
			   System.out.println("Gallery Image URI : "+mImageCaptureUri);
			 
			   	path=getRealPathFromURI(mImageCaptureUri);
			   	ed2.setText(path);
			   	
			    File file = new File(path);
		        byte[] byteArray = null;
		        try
		        {
			        InputStream inputStream = new FileInputStream(file);
			        ByteArrayOutputStream bos = new ByteArrayOutputStream();
			        byte[] b = new byte[2048*8];
			        int bytesRead =0;
			        
			        while ((bytesRead = inputStream.read(b)) != -1)
			        {
			        	bos.write(b, 0, bytesRead);
			        }
			        
			        byteArray = bos.toByteArray();					        
			     }
		        catch (IOException e)
		        {

			        Log.d("=err====", e.getMessage()+"");
		        	Toast.makeText(this,"String :"+e.getMessage().toString(), Toast.LENGTH_LONG).show();
		        }
//		        Log.d("======", "--------");
		        String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
		        attach=str;
//		        
			   	
			  }
		
	}

	private String getRealPathFromURI(Uri contentURI) {
	    String path;
	    Cursor cursor = getContentResolver()
	            .query(contentURI, null, null, null, null);
	    if (cursor == null)
	        path=contentURI.getPath();

	    else {
	        cursor.moveToFirst();
	        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
	        path=cursor.getString(idx);

	    }
	    if(cursor!=null)
	        cursor.close();
	    return path;
	}
}