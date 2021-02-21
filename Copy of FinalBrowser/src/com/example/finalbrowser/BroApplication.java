package com.example.finalbrowser;

import android.app.Application;
import android.widget.ViewFlipper;


public class BroApplication extends Application {
	private static BroApplication instance;
	
	private ViewFlipper mTabList;
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}
	
	public static BroApplication getInstance() {
		return instance;
	}
	
	public void setTabList(ViewFlipper viewFlipper) {
		mTabList = viewFlipper;
	}
	
	public ViewFlipper getTabList() {
		return mTabList;
	}
}
