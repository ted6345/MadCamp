package com.example.finalbrowser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.HttpResponse;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebIconDatabase;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebView.HitTestResult;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;
import android.widget.ViewFlipper;

import com.example.finalbrowser.CustomWebView.OnScrollChangedCallback;
import com.example.finalbrowser.htmlview.JSInterpreter;
import com.example.finalbrowser.normal.History;
import com.example.finalbrowser.tool.DrawView;

// DONE  탭 끄면 주소창 초기화 + 탭 옮기면 url도 바뀌도록 , 다운로드(기록),페이지 내 검색, 공유 ,다운로드 링크 누르면 빈 페이지로 가버리는 문제, 기본 브라우저
// 꾹 누르면 다운 , 방문 기록,  시크릿 모드, 스크린컷, 즐겨찾기 자동완성, 스와이프 구현, 캐시, 방문페이지 지우기
// 가끔 이동할 때 주소창 전체 선택

//TODO 시크릿 모드 더 보완, 암호

// 시크릿 모드 : easy ( 커스텀 웹뷰 만들어야 할 듯)

@SuppressWarnings("deprecation")
@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends FragmentActivity {

	private ViewFlipper mViewFlipper;
	private PopupMenu mPopupMenu;
	String[] url_rec;

	private AutoCompleteTextView edtUrl;
	public boolean mhome = true;
	public ImageView mSearchIv;
	private ImageView mStopSearchIv;
	public ImageView mCamera;
	private ProgressBar mProgressBar;

	private ImageButton mPopupMenuBtn;
	private FrameLayout mTabNumBtn;
	public TextView mTabNumTv;
	private ImageButton mPrePageIb;
	private ImageButton mNextPageIb;
	private ImageButton mHomeIb;

	private String error_buffer;

	DownloadManager manager;
	File destinationDir;

	public boolean isMobile;
	public int textsize;
	public boolean isRecursive;

	public LinearLayout urltoolbar, toolbar;
	public RelativeLayout web_lay;

	// / HTML ������ ���� ������

	public String html, homepage;
	public static Context mContext;
	public String base_url;
	public String temp_repo;
	public int bar_size;

	private boolean isDownload;

	private String overUrl;
	private String preUrl;

	LinearLayout root = null;
	private CustomWebView web;

	public boolean inJS;
	public boolean inSave;
	public boolean hiddenBar;
	
	int pre_delta = 0;

	public DrawView mDrawView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SharedPreferences pref = getPreferences(0);
		homepage = getIntent().getDataString();
		if (homepage == null)
			homepage = pref.getString("homepage", "");

		setwindowsize(pref.getBoolean("FullScreen", false));

		isDownload = false;
		mContext = this;
		isMobile = true;
		inSave = false;
		hiddenBar = false;

		root = (LinearLayout) findViewById(R.id.root);
		web_lay = (RelativeLayout) findViewById(R.id.web_lay);
		urltoolbar = (LinearLayout) findViewById(R.id.urltoolbar);
		toolbar = (LinearLayout) findViewById(R.id.toolbar);
		// mCVP = (CustomVP) findViewById(R.id.launcher);
		// mCVP.setOnPageChangeListener(new MainPageChangeListener());
		// mViewFlipper = (ViewFlipper) mCVP.getPageList().get(0);
		mViewFlipper = (ViewFlipper) findViewById(R.id.launcher);
		mHomeIb = (ImageButton) findViewById(R.id.home_btn);
		mSearchIv = (ImageView) findViewById(R.id.search_btn);
		mStopSearchIv = (ImageView) findViewById(R.id.stop_search_btn);
		mCamera = (ImageView) findViewById(R.id.camera_btn);
		mProgressBar = (ProgressBar) findViewById(R.id.WebViewProgress);
		mTabNumBtn = (FrameLayout) findViewById(R.id.tab_num_btn);
		mTabNumTv = (TextView) findViewById(R.id.tab_num_main);
		mPopupMenuBtn = (ImageButton) findViewById(R.id.popup_menu_btn);
		mPrePageIb = (ImageButton) findViewById(R.id.pre_page);
		mNextPageIb = (ImageButton) findViewById(R.id.next_page);
		mDrawView = (DrawView) findViewById(R.id.dv_screencut);
		url_rec = getResources().getStringArray(R.array.recom_array);
		edtUrl = (AutoCompleteTextView) findViewById(R.id.input_url);
		inJS = false;

		// 다운로드
		manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

		destinationDir = new File(Environment.getExternalStorageDirectory(),
				getPackageName());
		if (!destinationDir.exists()) {
			destinationDir.mkdir(); // Don't forget to make the directory if
									// it's not there
		}

		// favicon 관리
		WebIconDatabase.getInstance().open(
				getDir("icons", MODE_PRIVATE).getPath());

		mSearchIv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				v.setVisibility(View.GONE);
				mStopSearchIv.setVisibility(View.VISIBLE);
				goUrl();
			}
		});

		mStopSearchIv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getWeb().stopLoading();
				v.setVisibility(View.GONE);
				mProgressBar.setVisibility(View.INVISIBLE);
				mSearchIv.setVisibility(View.VISIBLE);
			}
		});

		mCamera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("ddd");
				try {
					screenshot(web, mDrawView.get_rect());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		mPrePageIb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (getWeb().canGoBack()) {
					getWeb().goBack();
					edtUrl.setText(getWeb().getUrl());
				}
			}
		});

		mNextPageIb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (getWeb().canGoForward()) {
					getWeb().goForward();
					edtUrl.setText(getWeb().getUrl());
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
					System.out.println("���̰�����");
					mPopupMenu.showAtLocation(findViewById(R.id.root),
							Gravity.BOTTOM, 0, 125);
				}

			}
		});

		mHomeIb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!homepage.equals(""))
					web.loadUrl(homepage);

			}
		});

		mTabNumBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getWeb().setWebChromeClient(null);
				getWeb().setWebViewClient(null);
				Intent intent = new Intent(MainActivity.this, TabManager.class);
				startActivityForResult(intent, 0x00);
			}
		});

		// 자동완성

		getBookmark();

		edtUrl.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				goUrl();

			}

		});
		// GO 버튼 누르면 가도록.
		edtUrl.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if ((actionId == EditorInfo.IME_ACTION_DONE)
						|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					goUrl();
				}
				return false;
			}
		});
		edtUrl.setImeOptions(EditorInfo.IME_ACTION_GO);

		BroApplication.getInstance().setTabList(mViewFlipper);

		setWeb(new CustomWebView(this));
		mViewFlipper.addView(getWeb());
		root.requestFocus();
		webInit();

	}

	public void webInit() {
		// ������ �⺻ ����
		this.registerForContextMenu(web);
		getWeb().getSettings().setJavaScriptEnabled(true);
		web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		getWeb().getSettings().setPluginState(PluginState.ON);
		getWeb().getSettings().setBuiltInZoomControls(true);
		getWeb().setDrawingCacheEnabled(true);
		SharedPreferences pref = this.getPreferences(0);
		web.getSettings().setTextZoom(pref.getInt("TextSize", 100));
		System.out.println("prefgetint" + pref.getInt("TextSize", 70));

		if (isMobile)
			web.getSettings().setUserAgentString(""); // PC버전 : 1 모바일버전 : 0
		else
			web.getSettings().setUserAgentString("Mozilla/5.0");
		getWeb().getSettings().setSupportZoom(true);

		// 다운로드 세팅 ( DC 같은데서 다운로드 링크 클릭하면 되도록 )
		web.setDownloadListener(new DownloadListener() {

			@SuppressLint("NewApi")
			@Override
			public void onDownloadStart(String url, String userAgent,
					String contentDisposition, String mimetype,
					long contentLength) {
				System.out.println("다운로드 모드 2 : DC 등의 다운로드 링크 // " + mimetype);
				isDownload = true;
				Uri source = Uri.parse(url);

				String fileName = source.getLastPathSegment();
				int pos = 0;
				if ((pos = contentDisposition.toLowerCase().lastIndexOf(
						"filename=")) >= 0) {
					fileName = contentDisposition.substring(pos + 9);
					pos = fileName.lastIndexOf(";");
					if (pos > 0) {
						fileName = fileName.substring(0, pos - 1);
					}
				}

				if (fileName.startsWith("\""))
					fileName = fileName.substring(1);
				if (fileName.endsWith("\""))
					fileName = fileName.substring(0, fileName.length() - 1);

				source = Uri.parse(encodePath(source.toString()));
				final DownloadManager.Request request = new DownloadManager.Request(
						source);

				request.setTitle(fileName);
				request.setDescription(url);
				request.setMimeType(mimetype);
				request.addRequestHeader("User-Agent", userAgent);
				String cookies = CookieManager.getInstance().getCookie(url);
				request.addRequestHeader("cookie", cookies);
				request.addRequestHeader("Referer", source.toString());
				request.setShowRunningNotification(true);
				request.setVisibleInDownloadsUi(true);
				request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
				// Use the same file name for the destination
				File destinationFile = new File(destinationDir, fileName);
				request.setDestinationUri(Uri.fromFile(destinationFile));
				// Add it to the manager

				final DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
				new Thread("Browser download") {
					public void run() {
						manager.enqueue(request);
					}
				}.start();

				// if(!overUrl.equals(preUrl))
				// edtUrl.setText(preUrl);
			}
		});

		if (!homepage.equals("") && mhome) {
			web.loadUrl(homepage);
		}
		mhome = true;

		// ���� ����
		getWeb().setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				mProgressBar.setProgress(newProgress);

			}

			@Override
			public void onReceivedIcon(WebView view, Bitmap icon) {
				super.onReceivedIcon(view, icon);
				Drawable temp = (new BitmapDrawable(icon));
				temp.setBounds(0, 0, 60, 60);
				edtUrl.setCompoundDrawables(temp, null, null, null);
			}

			@SuppressWarnings("unused")
			public void openFileChooser(ValueCallback<Uri> uploadMsg,
					String acceptType, String capture) {
				mUploadMessage = uploadMsg;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("*/*");
				MainActivity.this.startActivityForResult(
						Intent.createChooser(i, "파일을 선택하세요"),
						FILECHOOSER_RESULTCODE);
			}

			@Override
			public boolean onConsoleMessage(ConsoleMessage cm) {
				if (inJS) {
					((JSInterpreter) JSInterpreter.mContext).log.append("\n"
							+ cm.message());
				} else {
					html = cm.message();
					if (inSave) {
						inSave = false;
						SharedPreferences pref = ((MainActivity) MainActivity.mContext)
								.getPreferences(0);
						SharedPreferences.Editor mEdit1 = pref.edit();
						mEdit1.putString("page", ((MainActivity) mContext).html);
						mEdit1.putString("base_url",
								((MainActivity) mContext).base_url);
						mEdit1.commit();
						Toast.makeText(mContext, "저장되었습니다.", Toast.LENGTH_SHORT)
								.show();
					}

					Log.d("CONTENT",
							String.format("%s", cm.message().toString()));
				}
				return true;
			}

		});

		getWeb().setWebViewClient(new CookWebViewClient() {

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				if (error_buffer != null) {
					getWeb().loadUrl(
							"https://www.google.co.kr/search?btnG=1&pws=0&q="
									+ error_buffer
									+ "&gws_rd=cr,ssl&ei=wXfMVIP2AoW5mAWO9oL4Aw");
					error_buffer = null;
				}
				super.onReceivedError(view, errorCode, description, failingUrl);

			}

			// 대놓고 다운로드 있을 때
			@SuppressLint("NewApi")
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				System.out.println("오버라이드1 : " + url);
				boolean shouldOverride = false;
				root.requestFocus();
				isDownload = false;
				preUrl = overUrl;
				overUrl = url;
				if (url.endsWith(".mp3") || url.endsWith(".jpg")
						|| url.endsWith(".png") || url.endsWith(".ppt")) {
					System.out.println("다운로드 모드 1 : 대놓고 다운로드");
					shouldOverride = true;
					Uri source = Uri.parse(url);

					// Make a new request pointing to the mp3 url
					DownloadManager.Request request;
					request = new DownloadManager.Request(source);
					// Use the same file name for the destination
					File destinationFile = new File(destinationDir, source
							.getLastPathSegment());
					request.setDestinationUri(Uri.fromFile(destinationFile));
					// Add it to the manager
					manager.enqueue(request);
				}
				return shouldOverride; // Allow WebView to load url
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				edtUrl.setText(url);
				mSearchIv.setVisibility(View.GONE);
				mStopSearchIv.setVisibility(View.VISIBLE);
				mProgressBar.setVisibility(View.VISIBLE);
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

				imm.hideSoftInputFromWindow(edtUrl.getWindowToken(), 0);
				root.requestFocus();

			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				System.out.println("페이지로드끝");
				if (isDownload) {
					edtUrl.setText(preUrl);
					isDownload = false;
				} else {
					if (web.getTitle().startsWith("www")
							|| web.getTitle().startsWith("http://")
							|| web.getTitle().startsWith("https://")) {
					} else
						History.addOneHistory(web.getTitle(), edtUrl.getText()
								.toString());
				}
				mProgressBar.setVisibility(View.INVISIBLE);
				mProgressBar.setProgress(0);
				mSearchIv.setVisibility(View.VISIBLE);
				mStopSearchIv.setVisibility(View.GONE);
				String[] temp_10;
				temp_10 = edtUrl.getText().toString().split("/");
				if (temp_10.length > 2)
					base_url = temp_10[0] + "//" + temp_10[2];
				root.requestFocus();

			}

		});
		
//		web.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				// TODO Auto-generated method stub
//				if(event.getAction() == MotionEvent.ACTION_UP ||event.getAction() == MotionEvent.ACTION_CANCEL ){
//					Log.v("test","1234");
//					if(pre_delta>0 && urltoolbar.getY()>-bar_size){
//						urltoolbar.setY(-bar_size);
//						urltoolbar
//						.setLayoutParams(new LinearLayout.LayoutParams(
//								LayoutParams.MATCH_PARENT, 0));
//					}else if(pre_delta<0 && urltoolbar.getY()<0){
//						urltoolbar.setY(0);
//						urltoolbar
//						.setLayoutParams(new LinearLayout.LayoutParams(
//								LayoutParams.MATCH_PARENT, bar_size));
//					}
//				}
//				Log.v("test","5678" + event.getAction());
//				return false;
//			}
//		});

		urltoolbar.measure(
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		bar_size = urltoolbar.getMeasuredHeight();
//		web.setOnScrollChangedCallback(new OnScrollChangedCallback() {
//			int delta = 0;
//			int temp;
//			LinearLayout.LayoutParams lintemp = new LinearLayout.LayoutParams(
//					LayoutParams.MATCH_PARENT, temp);
//			
//			// TODO 마지막!!
//			
//			public void onScroll(int oldt, int t) {
//				delta = t - oldt; // 양수면 내려가는거
//				int measuredHeight = urltoolbar.getMeasuredHeight();
//				if (delta > 0) {
//					if (!hiddenBar ) {
//						web.setScrollY(web.getScrollY() - delta);
//						if (measuredHeight > 0) {
//							if (measuredHeight - delta > 0){
//								temp = measuredHeight - delta;
//								urltoolbar.setY(urltoolbar.getY()-measuredHeight+temp);
//							}
//							else {
//								temp = 0;
//								hiddenBar = true;
//								urltoolbar.setY(-bar_size);
//							}
//							Log.v("템프", "" + temp);
//							
//							lintemp.height = temp;
//							urltoolbar
//									.setLayoutParams(lintemp);
//							
//
//						} else {
//							// hiddenBar = true;
//						}
//					} else if(urltoolbar.getY()>-bar_size && pre_delta>0){
////						urltoolbar.setY(-bar_size);
////						urltoolbar
////						.setLayoutParams(new LinearLayout.LayoutParams(
////								LayoutParams.MATCH_PARENT, 0));
//					} else{}// 히든바일경우 걍 엔조이!
//				} else { // 올라갑니다
//					if (hiddenBar ) { // 바가 숨겨져있으면 먼저 보여줍니다.
//						web.setScrollY(web.getScrollY() - delta);
//						if (bar_size > measuredHeight) {
//							if (measuredHeight - delta <bar_size){
//								temp = measuredHeight - delta;
//								urltoolbar.setY(urltoolbar.getY()-measuredHeight+temp);
//							}
//							else {
//								temp = bar_size;
//								hiddenBar = false;
//								urltoolbar.setY(0);
//							}
//							
//							lintemp.height = temp;
//							urltoolbar
//									.setLayoutParams(lintemp);
//							
//							
//						}
//
//					}
//					else if(urltoolbar.getY()<0 && pre_delta<0){
////						urltoolbar.setY(0);
////						urltoolbar
////						.setLayoutParams(new LinearLayout.LayoutParams(
////								LayoutParams.MATCH_PARENT, bar_size));
//					}else{}// 엔조이!
//				}
//				
//				pre_delta = delta;
//			}
//		});

		WebSettings webSet = getWeb().getSettings();
		webSet.setBuiltInZoomControls(true);

		edtUrl.setText(getWeb().getUrl());
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
		} else if (requestCode == 0x00) {
			super.onActivityResult(requestCode, resultCode, intent);
			mTabNumTv.setText(mViewFlipper.getChildCount() + "");
			setWeb((CustomWebView) mViewFlipper.getCurrentView());
			webInit();
		} else if (requestCode == 0x03) {
			root.requestFocus();
			if (resultCode == RESULT_OK && !temp_repo.equals("")) {
				getWeb().loadDataWithBaseURL(base_url,
						"<html>" + temp_repo + "</html>",
						"text/html; charset=UTF-8", "utf-8", null);
				temp_repo = "";
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (getWeb().canGoBack())
				getWeb().goBack();
			else {
				finish();
			}
			// edtUrl.setText(web.getUrl());
			break;
		}
		return true;
	}

	// URL 이동
	public void goUrl() {
		String inedtUrl = edtUrl.getText().toString();
		if (inedtUrl.startsWith("http://") || inedtUrl.startsWith("https://")) {
			getWeb().loadUrl(edtUrl.getText().toString());
		} else {
			getWeb().loadUrl("http://" + inedtUrl);
		}
		error_buffer = inedtUrl;
		root.requestFocus();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		HitTestResult result = web.getHitTestResult();
		MenuItem.OnMenuItemClickListener handler = new MenuItem.OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				HitTestResult result = web.getHitTestResult();
				new ProcessImageTask().execute(result, null, null);
				return true;
			}

		};

		if (result.getType() == HitTestResult.IMAGE_TYPE
				|| result.getType() == HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
			System.out.println("이미지 클릭");
			// Menu options for an image.
			// set the header title to the image url
			menu.setHeaderTitle(result.getExtra());
			menu.add(0, android.view.Menu.NONE, 0, "Save Image")
					.setOnMenuItemClickListener(handler);
			menu.add(0, android.view.Menu.NONE, 0, "View Image")
					.setOnMenuItemClickListener(handler);
		} else if (result.getType() == HitTestResult.ANCHOR_TYPE
				|| result.getType() == HitTestResult.SRC_ANCHOR_TYPE) {
			// Menu options for a hyperlink.
			// set the header title to the link url
			menu.setHeaderTitle(result.getExtra());
			menu.add(0, android.view.Menu.NONE, 0, "Save Link")
					.setOnMenuItemClickListener(handler);
			menu.add(0, android.view.Menu.NONE, 0, "Share Link")
					.setOnMenuItemClickListener(handler);
		}

	}

	public CustomWebView getWeb() {
		return web;
	}

	public void setWeb(CustomWebView web) {
		this.web = web;
	}

	public class ProcessImageTask extends AsyncTask<HitTestResult, Void, Void> {
		HttpResponse response = null;

		@SuppressLint("NewApi")
		@Override
		protected Void doInBackground(HitTestResult... params) {
			HitTestResult result = params[0];
			// HttpClient httpclient = new DefaultHttpClient();
			// HttpGet httpget = new HttpGet(result.getExtra());
			// //
			Uri source = Uri.parse(result.getExtra());

			// Make a new request pointing to the mp3 url
			DownloadManager.Request request;
			request = new DownloadManager.Request(source);
			// Use the same file name for the destination
			File destinationFile = new File(destinationDir,
					source.getLastPathSegment());
			request.setDestinationUri(Uri.fromFile(destinationFile));
			request.addRequestHeader("Referer", source.toString());
			request.setShowRunningNotification(true);
			request.setVisibleInDownloadsUi(true);
			request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
			// Use the same file name for the destination
			request.setDestinationUri(Uri.fromFile(destinationFile));
			// Add it to the manager

			// Add it to the manager
			manager.enqueue(request);

			return null;
		}

		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			System.out.println("다운로드 모드 3 : 롱클릭 다운로드");

		}
	}

	public void reload() {
		String temp = edtUrl.getText().toString().split("//")[1];
		if (temp != null && temp.startsWith("m.")) {
			web.loadUrl("http://" + temp.substring(2));
		} else
			web.reload();
	}

	private static String encodePath(String path) {
		char[] chars = path.toCharArray();
		boolean needed = false;
		for (char c : chars) {
			if (c == '[' || c == ']' || c == '|') {
				needed = true;
				break;
			}
		}
		if (needed == false) {
			return path;
		}
		StringBuilder sb = new StringBuilder("");
		for (char c : chars) {
			if (c == '[' || c == ']' || c == '|') {
				sb.append('%');
				sb.append(Integer.toHexString(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public void settextsize(int t) {
		textsize = t;
	}

	public void setwindowsize(boolean t) {
		if (t)
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		else
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

	}

	void showBookmarkDialog() {
		android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
		BookMarkDialog bookmarkdialog = new BookMarkDialog();
		bookmarkdialog.show(fm, "fragment_bookmark");
	}

	void showAddBookmarkDialog() {
		android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
		AddBookMarkDialog addbookmarkdialog = new AddBookMarkDialog();
		addbookmarkdialog.show(fm, "fragment_addbookmark");
	}

	void showHomeBookmarkDialog() {
		android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
		HomeBookMarkDialog homebookmarkdialog = new HomeBookMarkDialog();
		homebookmarkdialog.show(fm, "fragment_homebookmark");
	}

	public String getedtUrl() {
		return edtUrl.getText().toString();
	}

	public void screenshot(View view, Rect rect) throws Exception {
		view.setDrawingCacheEnabled(true);
		Bitmap screenshot = view.getDrawingCache();

		System.out.println(rect.width());
		Bitmap temp = Bitmap.createBitmap(screenshot, rect.left, rect.top,
				rect.width(), rect.height());

		Bitmap bmOverlay = Bitmap.createBitmap(rect.width(), rect.height(),
				Bitmap.Config.ARGB_8888);
		Paint p = new Paint();
		p.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
		Canvas c = new Canvas(bmOverlay);
		c.drawBitmap(screenshot, 0, 0, null);
		c.drawRect(rect, p);

		String filename = "screenshot.png";

		try {
			File f = new File(Environment.getExternalStorageDirectory(),
					filename);
			f.createNewFile();
			OutputStream outStream = new FileOutputStream(f);
			temp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
			outStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		view.setDrawingCacheEnabled(false);
		mDrawView.setVisibility(View.GONE);
		mSearchIv.setVisibility(View.VISIBLE);
		mCamera.setVisibility(View.GONE);

	}

	public void getBookmark() {
		ArrayList<String> bookmark = new ArrayList<String>();
		SharedPreferences pref = getPreferences(0);
		bookmark.clear();
		int size = pref.getInt("Bookmark_size", 0);
		Log.v("Debugging", "bookmarksize" + size);
		for (int i = 0; i < size; i++) {
			bookmark.add(pref.getString("Bookmark_" + i, null));
		}

		ArrayList<String> mNewList = new ArrayList<String>(
				Arrays.asList(url_rec));
		mNewList.addAll(bookmark);
		ArrayAdapter<String> adapter = new MyAdapter(this,
				android.R.layout.simple_list_item_1, mNewList);
		edtUrl.setAdapter(adapter);

		return;
	}
}

class CookWebViewClient extends WebViewClient {

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		System.out.println("오버라이드2");
		return super.shouldOverrideUrlLoading(view, url);
	}

}
