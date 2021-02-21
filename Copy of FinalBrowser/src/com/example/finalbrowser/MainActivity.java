package com.example.finalbrowser;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.HttpResponse;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.ViewFlipper;

import com.example.finalbrowser.htmlview.JSInterpreter;

// DONE  ?É≠ ?ÅÑÎ©? Ï£ºÏÜåÏ∞? Ï¥àÍ∏∞?ôî + ?É≠ ?òÆÍ∏∞Î©¥ url?èÑ Î∞îÎ?åÎèÑÎ°? , ?ã§?ö¥Î°úÎìú(Í∏∞Î°ù),?éò?ù¥Ïß? ?Ç¥ Í≤??Éâ,

//TODO Í≥µÏú† ,  Íæ? ?àÑÎ•¥Î©¥ ?ã§?ö¥ , Î∞©Î¨∏ Í∏∞Î°ù,  ?ãú?Å¨Î¶? Î™®Îìú, ?ä§?Å¨Î¶∞Ïª∑, ?ïî?ò∏
//TODO  Í∏∞Î≥∏ Î∏åÎùº?ö∞??,
//TODO Í∞??Åî ?ù¥?èô?ï† ?ïå Ï£ºÏÜåÏ∞? ?†ÑÏ≤? ?Ñ†?Éù
// TODO ?ã§?ö¥Î°úÎìú ÎßÅÌÅ¨ ?àÑÎ•¥Î©¥ Îπ? ?éò?ù¥Ïß?Î°? Í∞?Î≤ÑÎ¶¨?äî Î¨∏Ï†ú
// Í≥µÏú† : Ïπ¥Ïπ¥?ò§?Ü° SDK ?†Å?ö©?ù¥ ?ïà?êú?ã§
// Íæ? ?àÑÎ•¥Î©¥ ?ã§?ö¥ : ????
// ?ãú?Å¨Î¶? Î™®Îìú : easy ( Ïª§Ïä§?? ?õπÎ∑? ÎßåÎì§?ñ¥?ïº ?ï† ?ìØ)
// ?ä§?Å¨Î¶∞Ïª∑ : http://stackoverflow.com/questions/14555439/take-a-partial-screenshot-on-android
//            http://androi.tistory.com/43

@SuppressWarnings("deprecation")
@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends FragmentActivity {

	private ViewFlipper mViewFlipper;
	private PopupMenu mPopupMenu;
	String[] url_rec;

	private AutoCompleteTextView edtUrl;
	public boolean mhome = true;
	private ImageView mSearchIv;
	private ImageView mStopSearchIv;
	private ProgressBar mProgressBar;

	private ImageButton mPopupMenuBtn;
	private FrameLayout mTabNumBtn;
	public TextView mTabNumTv;
	private ImageButton mPrePageIb;
	private ImageButton mNextPageIb;
	private ImageButton mHomeIb;

	private ImageView mPageCount;

	private String error_buffer;

	DownloadManager manager;
	File destinationDir;

	public boolean isMobile;
	public int textsize;

	// / HTML ÔøΩÔøΩÔøΩÔøΩÔøΩÔøΩ ÔøΩÔøΩÔøΩÔøΩ ÔøΩÔøΩÔøΩÔøΩÔøΩÔøΩ

	public String html, homepage;
	public static Context mContext;
	public String base_url;
	public String temp_repo;

	LinearLayout root = null;
	private WebView web;

	public boolean inJS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SharedPreferences pref = getPreferences(0);
		homepage = pref.getString("homepage", "");

		mContext = this;
		isMobile = true;

		root = (LinearLayout) findViewById(R.id.root);
//		mCVP = (CustomVP) findViewById(R.id.launcher);
//		mCVP.setOnPageChangeListener(new MainPageChangeListener());
//		mViewFlipper = (ViewFlipper) mCVP.getPageList().get(0);
		mViewFlipper = (ViewFlipper)findViewById(R.id.launcher);
		mHomeIb = (ImageButton) findViewById(R.id.home_btn);
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
		inJS = false;

		// ?ã§?ö¥Î°úÎìú
		manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

		destinationDir = new File(Environment.getExternalStorageDirectory(),
				getPackageName());
		if (!destinationDir.exists()) {
			destinationDir.mkdir(); // Don't forget to make the directory if
									// it's not there
		}

		// favicon Í¥?Î¶?
		WebIconDatabase.getInstance().open(
				getDir("icons", MODE_PRIVATE).getPath());

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
				getWeb().stopLoading();
				v.setVisibility(View.GONE);
				mSearchIv.setVisibility(View.VISIBLE);
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
					System.out.println("ÔøΩÔøΩÔøΩÃ∞ÔøΩÔøΩÔøΩÔøΩÔøΩ");
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
		// GO Î≤ÑÌäº ?àÑÎ•¥Î©¥ Í∞??èÑÎ°?.
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

		setWeb(new WebView(this));
		mViewFlipper.addView(getWeb());
		root.requestFocus();
		webInit();

	}

	public void webInit() {
		// ÔøΩÔøΩÔøΩÔøΩÔøΩÔøΩ ÔøΩ‚∫ª ÔøΩÔøΩÔøΩÔøΩ
		this.registerForContextMenu(web);
		getWeb().getSettings().setJavaScriptEnabled(true);
		web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		getWeb().getSettings().setPluginState(PluginState.ON);
		getWeb().getSettings().setBuiltInZoomControls(true);
		getWeb().setDrawingCacheEnabled(true);
		SharedPreferences pref = this.getPreferences(0);
		web.getSettings().setTextZoom(30 + pref.getInt("TextSize", 70));
		System.out.println("prefgetint" + pref.getInt("TextSize", 70));

		if (isMobile)
			web.getSettings().setUserAgentString(""); // PCÎ≤ÑÏ†Ñ : 1 Î™®Î∞î?ùºÎ≤ÑÏ†Ñ : 0
		else
			web.getSettings().setUserAgentString("Mozilla/5.0");
		getWeb().getSettings().setSupportZoom(true);

		// ?ã§?ö¥Î°úÎìú ?Ñ∏?åÖ ( DC Í∞ôÏ??ç∞?Ñú ?ã§?ö¥Î°úÎìú ÎßÅÌÅ¨ ?Å¥Î¶??ïòÎ©? ?êò?èÑÎ°? )
		web.setDownloadListener(new DownloadListener() {

			@SuppressLint("NewApi")
			@Override
			public void onDownloadStart(String url, String userAgent,
					String contentDisposition, String mimetype,
					long contentLength) {
				System.out.println("?ã§?ö¥Î°úÎìú Î™®Îìú 2 : DC ?ì±?ùò ?ã§?ö¥Î°úÎìú ÎßÅÌÅ¨ // " + mimetype);

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
				// String fileExtension =
				// fileName.substring(fileName.lastIndexOf(".") + 1,
				// fileName.length()).toLowerCase();
				// String mimeType =
				// mtm.getMimeTypeFromExtension(fileExtension);

				// Make a new request pointing to the url
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

			}
		});

		if (!homepage.equals("") && mhome) {
			web.loadUrl(homepage);
		}
		mhome = true;

		// ÔøΩÔøΩÔøΩÔøΩ ÔøΩÔøΩÔøΩÔøΩ
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
						Intent.createChooser(i, "ÔøΩÔøΩÔøΩÔøΩÔøΩÔøΩ ÔøΩÔøΩÔøΩÔøΩÔøΩœºÔøΩÔøΩÔøΩ"),
						FILECHOOSER_RESULTCODE);
			}

			@Override
			public boolean onConsoleMessage(ConsoleMessage cm) {
				if (inJS) {
					((JSInterpreter) JSInterpreter.mContext).log.append("\n"
							+ cm.message());
				} else {
					html = cm.message();
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

			// ???ÜìÍ≥? ?ã§?ö¥Î°úÎìú ?ûà?ùÑ ?ïå
			@SuppressLint("NewApi")
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				boolean shouldOverride = false;
				root.requestFocus();
				if (url.endsWith(".mp3") || url.endsWith(".jpg")
						|| url.endsWith(".png") || url.endsWith(".ppt")) {
					System.out.println("?ã§?ö¥Î°úÎìú Î™®Îìú 1 : ???ÜìÍ≥? ?ã§?ö¥Î°úÎìú");
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
				mProgressBar.setVisibility(View.VISIBLE);
				root.requestFocus();

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
				if (temp_10.length > 2)
					base_url = temp_10[0] + "//" + temp_10[2];
				root.requestFocus();

			}

		});

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
			setWeb((WebView) mViewFlipper.getCurrentView());
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

	// URL ?ù¥?èô
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
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
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
			System.out.println("?ù¥ÎØ∏Ï? ?Å¥Î¶?");
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

	public WebView getWeb() {
		return web;
	}

	public void setWeb(WebView web) {
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
			// Add it to the manager
			manager.enqueue(request);

			return null;
		}

		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			System.out.println("?ã§?ö¥Î°úÎìú Î™®Îìú 3 : Î°±ÌÅ¥Î¶? ?ã§?ö¥Î°úÎìú");

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

	public String getedtUrl() {
		return edtUrl.getText().toString();
	}

}

class CookWebViewClient extends WebViewClient {

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		return super.shouldOverrideUrlLoading(view, url);
	}

}
