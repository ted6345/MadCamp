package com.example.finalbrowser;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class TabManager extends Activity {
	private ListView mTabLv;
	private TextView mNewTabTv;
	private TextView mClearTabTv;
	private TextView mTabNumTv;
	
	private ViewFlipper mViewFlipper;
	private TabManagerAdapter mTabManagerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tab_manager);
		
		// 컴포넌트들 받아옴
		mViewFlipper = BroApplication.getInstance().getTabList();
		
		mTabLv = (ListView) findViewById(R.id.tab_list);
		mNewTabTv = (TextView) findViewById(R.id.new_tab);
		mClearTabTv = (TextView) findViewById(R.id.clear_tab);
		mTabNumTv = (TextView) findViewById(R.id.tab_num);
		
		// 탭매니저 어뎁터를 부름. 리스트 뷰 다룰 때 원하는거 넣기 위해.
		mTabManagerAdapter = new TabManagerAdapter(this, mViewFlipper);
		mTabLv.setAdapter(mTabManagerAdapter);
		
		// 탭 갯수 정의
		mTabNumTv.setText(mViewFlipper.getChildCount()+"");
		mTabNumTv.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				setResult(Activity.RESULT_OK);
				finish();
			}
		});
		
		// 새탭 열기
		mNewTabTv.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				WebView customWebView = new WebView(BroApplication.getInstance());
				//customWebView.navigateToUrl(CustomWebView.URL_ABOUT_START);
				mViewFlipper.addView(customWebView);
				mViewFlipper.setDisplayedChild(mViewFlipper.getChildCount()-1);
				mTabNumTv.setText(mViewFlipper.getChildCount()+"");
				setResult(Activity.RESULT_OK);
				finish();
			}
		});
		
		// 탭 정리
		mClearTabTv.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				WebView customWebView = new WebView(BroApplication.getInstance());
				//customWebView.navigateToUrl(WebView.URL_ABOUT_START);
				mViewFlipper.removeAllViews();
				mViewFlipper.addView(customWebView);
				setResult(Activity.RESULT_OK);
				finish();
			}
		});	
	}
	
}	