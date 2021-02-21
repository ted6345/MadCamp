package com.example.action_bar;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity {
	ViewPager Tab;
	TabPagerAdapter TabAdapter;
	ActionBar actionBar;
	
		 
	public int getPageTag(){
		int tag = Integer.parseInt(Tab.getTag() + "");
		return tag;
	}
	
	public void setPageTag(int tag){
		Tab.setTag(tag);
	}
	public void notifybyframent()
	{
		TabPagerAdapter TabAdapter2 = new TabPagerAdapter(getSupportFragmentManager());
		TabAdapter2.notifyDataSetChanged();
	}
	
	 	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TabAdapter = new TabPagerAdapter(getSupportFragmentManager());
			
		
		Tab = (ViewPager) findViewById(R.id.pager);
		Tab.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar = getActionBar();
				actionBar.setSelectedNavigationItem(position);
				TabAdapter.notifyDataSetChanged();
			}
		});
		Tab.setTag(0);
		Tab.setAdapter(TabAdapter);
		actionBar = getActionBar();
		// Enable Tabs on Action Bar
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			@Override
			public void onTabReselected(android.app.ActionBar.Tab tab,
					FragmentTransaction ft) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
				Tab.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(android.app.ActionBar.Tab tab,
					FragmentTransaction ft) {
				// TODO Auto-generated method stub
			}
		};
		// Add New Tab
		actionBar.addTab(actionBar.newTab().setText("Page1")
				.setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("Page2")
				.setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("Page3")
				.setTabListener(tabListener));
	}
}