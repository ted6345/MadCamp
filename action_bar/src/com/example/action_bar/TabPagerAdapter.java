package com.example.action_bar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
	public TabPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public Fragment getItem(int i) {
		switch (i) {
		case 0:
			// Fragment for Page1 Tab
			return new Page1();
		case 1:
			// Fragment for Page2 Tab
			return new Page2();
		case 2:
			// Fragment for Page3 Tab
			return new Page3();
		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3; // No of Tabs
	}
	
	@Override
	public int getItemPosition(Object object){
		return POSITION_NONE;
	}
}