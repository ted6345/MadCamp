package com.example.android_explorer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	String source = null;
	WebView mwebview;
	EditText urlinput;
	Button button;
	private ValueCallback<Uri> mUploadMessage;
	private final static int FILECHOOSER_RESULTCODE = 1;
	
	@Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent intent) {
    if (requestCode == FILECHOOSER_RESULTCODE) {
        if (null == mUploadMessage)
            return;
        Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
        mUploadMessage.onReceiveValue(result);
        mUploadMessage = null;
        }
    }
	private class mWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}
	mWebViewClient webclient=new mWebViewClient()
	{
         @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
        	// TODO Auto-generated method stub
        	urlinput.setText(url);
        	 super.onPageStarted(view, url, favicon);
        }
};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button = (Button) findViewById(R.id.button);
		Button html_button = (Button) findViewById(R.id.html_button);
		urlinput = (EditText) findViewById(R.id.url);
		urlinput.setText("http://m.naver.com");
		final Editable urlstr = urlinput.getText();
		
		mwebview = (WebView) findViewById(R.id.webview);
		mwebview.getSettings().setJavaScriptEnabled(true);
		mwebview.getSettings().setPluginState(PluginState.ON);
		mwebview.setWebChromeClient(new WebChromeClient()
		  {
		         @SuppressWarnings("unused")
		       public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
		             mUploadMessage = uploadMsg;
		             Intent i = new Intent(Intent.ACTION_GET_CONTENT);
		             i.addCategory(Intent.CATEGORY_OPENABLE);
		             i.setType("*/*");
		             MainActivity.this.startActivityForResult(
		            		 Intent.createChooser(i, "사진을 선택하세요"),
		                     FILECHOOSER_RESULTCODE);
		             }
		   });
		mwebview.loadUrl("http://m.naver.com");
		mwebview.setWebViewClient(webclient);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mwebview.loadUrl(urlstr.toString());
			}
		});
		html_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e("ButtonClick","ButtonClick");
				String html=null;
				try {
					html = new MyAsyncTask().execute(urlinput.getText().toString()).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.print("Htmlloginstead"+html);
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.layout.activity_main, menu);
		return true;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mwebview.canGoBack()) {
			mwebview.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	private class MyAsyncTask extends AsyncTask<String, Void, String>{
	    @Override
	    protected String doInBackground(String... arg) {
	    	String documentURL = arg[0];
			StringBuffer url_content = new StringBuffer();
			try{
				URL url = new URL(documentURL);
				InputStream is = url.openStream();
				InputStreamReader isr = new InputStreamReader(is,"utf-8");
				BufferedReader br = new BufferedReader(isr);
				String inStr = "";
				while((inStr=br.readLine())!=null){
					url_content.append(inStr+"\n");
				}
				source = new String(url_content);
				Log.e("source = new String(url_content);","source = new String(url_content);");
			}
			catch(Exception e){
				Log.e("GetSource","Exception e");
			}
			return source;
	    }
	}
}
