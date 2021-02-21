package com.story.browser;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends  FragmentActivity {
	public static Context mContext;
	EditText edtUrl;
	Button btnmenu,btngo;
	WebView web;
	ProgressBar progressBar;
	String html,homepage=null;
	ArrayList <String> bookmark= new ArrayList <String>();
	private ValueCallback<Uri> mUploadMessage;
	private final static int FILECHOOSER_RESULTCODE = 1;
	private void showBookmarkDialog(){
		android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
		BookMarkDialog bookmarkdialog = new BookMarkDialog();
		bookmarkdialog.show(fm,"fragment_bookmark");
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (requestCode == FILECHOOSER_RESULTCODE) {
			if (null == mUploadMessage)
				return;
			Uri result = intent == null || resultCode != RESULT_OK ? null
					: intent.getData();
			mUploadMessage.onReceiveValue(result);
			mUploadMessage = null;
		}
	}

	
	//인터텟 화면에서 back으로 돌아가기
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
     // TODO Auto-generated method stub
     switch (keyCode) {
     	case KeyEvent.KEYCODE_BACK:
     		web.goBack();  
     		//edtUrl.setText(web.getUrl());
     		Log.d("My Webview", web.getUrl());
     		break;
     }
     return true;
	}

	
	@SuppressWarnings("deprecation")
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;
		edtUrl = (EditText) findViewById(R.id.edtUrl);
		btngo = (Button) findViewById(R.id.btngo);
		btnmenu = (Button) findViewById(R.id.btnmenu);
		web = (WebView) findViewById(R.id.webView1);
		progressBar = (ProgressBar) findViewById(R.id.prog);
		//webview 설정
		web.getSettings().setJavaScriptEnabled(true);
		web.getSettings().setPluginState(PluginState.ON);
		web.getSettings().setBuiltInZoomControls(true);
		web.getSettings().setSupportZoom(true);
		SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
		homepage = prefs.getString("homepage","");
		btnmenu.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openOptionsMenu();
			}
		});
		

		
		//web에 이미지 올리기
		web.setWebChromeClient(new WebChromeClient() {	
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				progressBar.setProgress(newProgress);	
			}
			@SuppressWarnings("unused")
			public void openFileChooser(ValueCallback<Uri> uploadMsg,
					String acceptType, String capture) {
				mUploadMessage = uploadMsg;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("*/*");
				MainActivity.this.startActivityForResult(
						Intent.createChooser(i, "사진을 선택하세요"),
						FILECHOOSER_RESULTCODE);
			}
			 @Override
			    public boolean onConsoleMessage(ConsoleMessage cm)
			    {
				 	html = cm.message();
			        Log.d("CONTENT", String.format("%s", 
			                    cm.message().toString()));
			        return true;
			    }
		});
		
	
		if(homepage.equals(null)){}
		else	web.loadUrl(homepage);		
		
		
		//webViewClient 설정
		web.setWebViewClient(new CookWebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return false; // Allow WebView to load url
			}
			
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				edtUrl.setText(url);
				progressBar.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				progressBar.setVisibility(View.INVISIBLE);
			}

		});
		WebSettings webSet = web.getSettings();
		webSet.setBuiltInZoomControls(true);

		
		//해동 주소 접속하기
		btngo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String inedtUrl = edtUrl.getText().toString();
				if (inedtUrl.startsWith("http://")
						|| inedtUrl.startsWith("https://")) {
					web.loadUrl(edtUrl.getText().toString());
				}
				// TODO Auto-generated method stub
				else {
					web.loadUrl("http://" + inedtUrl);
				}
			}
		});
	}
	//메뉴 설정하기!!
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.menu, menu);
	    return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    String text = null;
	    switch(item.getItemId()){
	    case R.id.it_html:
			web.loadUrl("javascript:console.log(document.documentElement.innerHTML)");
			Intent myIntent = new Intent(getApplicationContext(),
					HtmlModify.class);
			startActivity(myIntent);
	        break;
	    case R.id.it_bookmark:
	        showBookmarkDialog();
	        break;
	    case R.id.it_homepage:
	    	SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
	    	SharedPreferences.Editor editor = prefs.edit();
	    	editor.putString("homepage",edtUrl.getText().toString() );
	    	editor.commit();
	    	Toast.makeText(this, ""+edtUrl.getText().toString(), Toast.LENGTH_SHORT).show();
	    	break;
	    default:
	        return false;
	    }
	    return true;
	}
	
	public void loadurl(String url)
	{
		web.loadUrl(url);
	}
}

	

class CookWebViewClient extends WebViewClient {
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		// TODO Auto-generated method stub
		return super.shouldOverrideUrlLoading(view, url);
	}

}