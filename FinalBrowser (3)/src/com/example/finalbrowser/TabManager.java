package com.example.finalbrowser;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.finalbrowser.swipe.SwipeDismissListViewTouchListener;

public class TabManager extends Activity {
	private ListView mTabLv;
	private TextView mNewTabTv;
	private TextView mClearTabTv;
	private TextView mTabNumTv;
	
	
	private Context mContext;
	
	private ViewFlipper mViewFlipper;
	private TabManagerAdapter mTabManagerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tab_manager);
		
		mContext = this;
		
		// 컴포넌트들 받아옴
		mViewFlipper = BroApplication.getInstance().getTabList();
		
		mTabLv = (ListView) findViewById(R.id.tab_list);
		mNewTabTv = (TextView) findViewById(R.id.new_tab);
		mClearTabTv = (TextView) findViewById(R.id.clear_tab);
		mTabNumTv = (TextView) findViewById(R.id.tab_num);
		
		// 탭매니저 어뎁터를 부름. 리스트 뷰 다룰 때 원하는거 넣기 위해.
		mTabManagerAdapter = new TabManagerAdapter(this, mViewFlipper);
		mTabLv.setAdapter(mTabManagerAdapter);
		
		// Swipe
		SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(mTabLv,
                new SwipeDismissListViewTouchListener.DismissCallbacks() {
                    @Override
                    public boolean canDismiss(int position) {
                        return true;
                    }

                    @Override
                    public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {
                        	if(listView.getChildCount()==1){
	                        	CustomWebView web_temp = new CustomWebView(BroApplication.getInstance());
	        					mViewFlipper.removeAllViews();
	        					mViewFlipper.addView(web_temp);
	        					((TabManager) mContext).finish();
                        	}
	        				else
                        	mViewFlipper.removeViewAt(position);
                        }
                        mTabManagerAdapter.notifyDataSetChanged();
                    }
                });
		
		OnItemClickListener clickListener = new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				((MainActivity)MainActivity.mContext).mhome = false;
				mViewFlipper.setDisplayedChild(position);
				((TabManager) mContext).setResult(Activity.RESULT_OK);
				((TabManager) mContext).finish();
				
			}
			
		};
		
		mTabLv.setOnTouchListener(touchListener);
		mTabLv.setOnScrollListener(touchListener.makeScrollListener());
		mTabLv.setOnItemClickListener(clickListener);
		
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
				CustomWebView customCustomWebView = new CustomWebView(BroApplication.getInstance());
				//customCustomWebView.navigateToUrl(CustomCustomWebView.URL_ABOUT_START);
				mViewFlipper.addView(customCustomWebView);
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
				CustomWebView customWebView = new CustomWebView(BroApplication.getInstance());
				//customCustomWebView.navigateToUrl(CustomWebView.URL_ABOUT_START);
				mViewFlipper.removeAllViews();
				mViewFlipper.addView(customWebView);
				setResult(Activity.RESULT_OK);
				finish();
			}
		});	
	}
	
}	