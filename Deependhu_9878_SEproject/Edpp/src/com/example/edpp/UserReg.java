package com.example.edpp;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.view.Menu;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class UserReg extends Activity {

	WebView wb;
	private ValueCallback<Uri> mUploadMessage;
	private final static int FILECHOOSER_RESULTCODE=1;
    SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_reg);
		wb=(WebView)findViewById(R.id.webView1);
		sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String uid=sp.getString("lid", "");
				String lin=sp.getString("path", "")+"patient.php";
				wb=(WebView)findViewById(R.id.webView1);
				
				wb.setWebViewClient(new HelloWebViewClient());
				wb.setWebChromeClient(new WebChromeClient()); 
				wb.setWebViewClient(new WebViewClient()); 
				wb.getSettings().setJavaScriptEnabled(true); 
		       // wb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
				wb.loadUrl(lin);
				wb.setWebViewClient(new myWebClient());
				wb.setWebChromeClient(new WebChromeClient() {
		            //The undocumented magic method override
		            //Eclipse will swear at you if you try to put @Override here
		            // For Android 3.0+
		            public void openFileChooser(ValueCallback<Uri> uploadMsg) {

		                mUploadMessage = uploadMsg;
		                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
		                i.addCategory(Intent.CATEGORY_OPENABLE);
		                i.setType("image/*");
		                UserReg.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);

		            }

		            // For Android 3.0+
		            public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
		                mUploadMessage = uploadMsg;
		                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
		                i.addCategory(Intent.CATEGORY_OPENABLE);
		                i.setType("*/*");
		                UserReg.this.startActivityForResult(
		                        Intent.createChooser(i, "File Browser"),
		                        FILECHOOSER_RESULTCODE);
		            }

		            //For Android 4.1
		            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
		                mUploadMessage = uploadMsg;
		                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
		                i.addCategory(Intent.CATEGORY_OPENABLE);
		                i.setType("image/*");
		                UserReg.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), UserReg.FILECHOOSER_RESULTCODE);

		            }

		          });

				
				
			}

			
			private class HelloWebViewClient extends WebViewClient {
				@Override
			    public boolean shouldOverrideUrlLoading(WebView view, String url) {
			        // This line right here is what you're missing.
			        // Use the url provided in the method.  It will match the member URL!
			        view.loadUrl(url);
			        return true;
			    }
			}
			
			
			

		    public class myWebClient extends WebViewClient
		    {
		        @Override

		        public void onPageStarted(WebView view, String url, Bitmap favicon) {
		            super.onPageStarted(view, url, favicon);

		        }

		        @Override

		        public boolean shouldOverrideUrlLoading(WebView view, String url) {
		            view.loadUrl(url);
		            return true;
		        }

		        @Override
		        public void onPageFinished(WebView view, String url) {
		            super.onPageFinished(view, url);
		        }
		    }

		    //flipscreen not loading again
		    @Override
		    public void onConfigurationChanged(Configuration newConfig){
		        super.onConfigurationChanged(newConfig);
		    }

		    @Override
		    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

		        if(requestCode==FILECHOOSER_RESULTCODE){
		            if (null == mUploadMessage) return;
		            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
		            mUploadMessage.onReceiveValue(result);
		            mUploadMessage = null;

		        }
		   }
		}
