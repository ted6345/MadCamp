package com.example.finalbrowser;


import java.util.ArrayList;
import java.util.Arrays;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity {

	private ViewFlipper mViewFlipper;
	private PopupMenu mPopupMenu;
	private CustomVP mCVP;
	
	String[] url_rec;
	
	private AutoCompleteTextView edtUrl;
	private ImageView mSearchIv;
	private ImageView mStopSearchIv;
	private ProgressBar mProgressBar;

	private ImageButton mPopupMenuBtn;
	private FrameLayout mTabNumBtn;
	private TextView mTabNumTv;
	private ImageButton mPrePageIb;
	private ImageButton mNextPageIb;
	private ImageButton mHomeIb;

	private ImageView mPageCount;
	
	String html;
	String base_url;

	LinearLayout root = null;
	private WebView web;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);

		root = (LinearLayout) findViewById(R.id.root);
		mCVP = (CustomVP) findViewById(R.id.launcher);
		mCVP.setOnPageChangeListener(new MainPageChangeListener());
		mViewFlipper = (ViewFlipper) mCVP.getPageList().get(0);
		mSearchIv = (ImageView) findViewById(R.id.search_btn);
		mStopSearchIv = (ImageView) findViewById(R.id.stop_search_btn);
		mProgressBar = (ProgressBar) findViewById(R.id.WebViewProgress);
		mTabNumBtn = (FrameLayout) findViewById(R.id.tab_num_btn);
		mTabNumTv = (TextView) findViewById(R.id.tab_num_main);
		mPopupMenuBtn = (ImageButton) findViewById(R.id.popup_menu_btn);
		mPrePageIb = (ImageButton) findViewById(R.id.pre_page);
		mNextPageIb = (ImageButton) findViewById(R.id.next_page);
		url_rec = getResources().getStringArray(R.array.recom_array);
		edtUrl = (AutoCompleteTextView) findViewById(R.id.input_url);

		//getWindow().setSoftInputMode(
		//		WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		
		
		mSearchIv.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				goUrl();
				v.setVisibility(View.GONE);
				mStopSearchIv.setVisibility(View.VISIBLE);
			}
		});
		
		mStopSearchIv.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				web.stopLoading();
				v.setVisibility(View.GONE);
				mSearchIv.setVisibility(View.VISIBLE);
			}
		});
		
		mPrePageIb.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if (web.canGoBack()) {
					web.goBack();
					edtUrl.setText(web.getUrl());
				}				
			}
		});
		
		mNextPageIb.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if (web.canGoForward()) {
					web.goForward();
					edtUrl.setText(web.getUrl());
				}				
			}
		});
		
		mPopupMenuBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mPopupMenu == null) {
					mPopupMenu = new PopupMenu(MainActivity.this);
				}
				if (mPopupMenu.isShowing()) {
					mPopupMenu.dismiss();
				} else {
					System.out.println("보이게하자");
					mPopupMenu.showAtLocation(findViewById(R.id.root), Gravity.BOTTOM, 0, 125);
				}			
			
				
			}
		});
		
		mTabNumBtn.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				web.setWebChromeClient(null);
				web.setWebViewClient(null);
				Intent intent = new Intent(MainActivity.this,TabManager.class);
				startActivityForResult(intent, 0x00);
			}
		});
		
		ArrayList<String> mNewList = new ArrayList<String>(
				Arrays.asList(url_rec));

		ArrayAdapter<String> adapter = new MyAdapter(this,
				android.R.layout.simple_list_item_1, mNewList);
		edtUrl.setAdapter(adapter);
		edtUrl.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				goUrl();
			
				
			}
			
		});
		
		BroApplication.getInstance().setTabList(mViewFlipper);
		
		
		web = new WebView(this);
		mViewFlipper.addView(web);
		root.requestFocus();
		webInit();

	}

	@SuppressWarnings("deprecation")
	private void webInit() {
		// 브라우저 기본 세팅
		web.getSettings().setJavaScriptEnabled(true);
		web.getSettings().setPluginState(PluginState.ON);
		web.getSettings().setBuiltInZoomControls(true);
		web.setDrawingCacheEnabled(true);

		web.getSettings().setSupportZoom(true);

		// 웹뷰 세팅
		web.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				mProgressBar.setProgress(newProgress);

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
			public boolean onConsoleMessage(ConsoleMessage cm) {
				html = cm.message();
				Log.d("CONTENT", String.format("%s", cm.message().toString()));

				return true;
			}

		});

		web.setWebViewClient(new CookWebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Log.d("My Webview", url);
				// edtUrl.setText(url);
				// return true; //Indicates WebView to NOT load the url;
				return false; // Allow WebView to load url
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				edtUrl.setText(url);
				mProgressBar.setVisibility(View.VISIBLE);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				mProgressBar.setVisibility(View.INVISIBLE);
				mProgressBar.setProgress(0);
				mSearchIv.setVisibility(View.VISIBLE);
				mStopSearchIv.setVisibility(View.GONE);
				String[] temp_10;
				temp_10 = edtUrl.getText().toString().split("/");
				base_url = temp_10[0] + "//" + temp_10[2];
				System.out.println("베이스 : " + base_url);
			}

		});

		WebSettings webSet = web.getSettings();
		webSet.setBuiltInZoomControls(true);

	}

	

	
	private ValueCallback<Uri> mUploadMessage;
	private final static int FILECHOOSER_RESULTCODE = 1;

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
		if (requestCode == 0x00) {
			super.onActivityResult(requestCode, resultCode, intent);
			mTabNumTv.setText(mViewFlipper.getChildCount()+"");
			web = (WebView) mViewFlipper.getCurrentView();
			webInit();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (web.canGoBack())
				web.goBack();
			else {
				finish();
			}
			// edtUrl.setText(web.getUrl());
			break;
		}
		return true;
	}
	

	// 현재 주소창에 적혀있는 주소로 이동하는 함수
	public void goUrl() {
		String inedtUrl = edtUrl.getText().toString();
		if (inedtUrl.startsWith("http://") || inedtUrl.startsWith("https://")) {
			web.loadUrl(edtUrl.getText().toString());
		}
		else {
			web.loadUrl("http://" + inedtUrl);
		}
		System.out.println("gogo");
		root.requestFocus();
	}
	
	private class MainPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}

		@Override
		public void onPageSelected(int arg0) {
			
		}
	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String text = null;

		switch (item.getItemId()) {

		case R.id.it_html:
			text = "HTML 보기는 아직 지원되지 않습니다.";
			break;

		case R.id.it_bookmark:
			text = "북마크는 ...";
			break;

		default:
			return false;
		}
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
		return true;
	}

}


class CookWebViewClient extends WebViewClient {

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		return super.shouldOverrideUrlLoading(view, url);
	}

}
