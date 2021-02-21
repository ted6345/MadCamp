package com.example.finalbrowser.menu;

import java.util.ArrayList;

import com.example.finalbrowser.R;

import android.content.Context;
import android.content.res.TypedArray;

public class MenuData {
	public final static int MENU_TYPE_NORMAL = 0x00;
	public final static int MENU_TYPE_SETTING = 0x01;
	public final static int MENU_TYPE_TOOL = 0x02;
	
	String[] mMenuTitle;
	int[] mMenuIcon;
	
	public MenuData(Context context) {

		// 데이터를 받아옴(스트링과 아이콘)
		mMenuTitle = context.getResources().getStringArray(R.array.popupmenu_srtirg_array);
		TypedArray typedArray = context.getResources().obtainTypedArray(R.array.popupmenu_icon_array);
		
		// mMenuIcon에 아이콘들의 아이디를 넣음
		mMenuIcon = new int[typedArray.length()];
		for (int i = 0; i < typedArray.length(); i++) {
			mMenuIcon[i] = typedArray.getResourceId(i, 0);
		}	
		typedArray.recycle();
	}
	
	public ArrayList<Menu> getMenuList(int menuType) {
		ArrayList<Menu> list = new ArrayList<Menu>();
		
		int k, j;
		if (menuType == MENU_TYPE_NORMAL) {
			k = 0;
			j = 7;
		} else if (menuType == MENU_TYPE_SETTING) {
			k = 7;
			j = 11;
		} else {
			k = 11;
			j = 15;
		}
		
		for (int i = k; i < j; i++) {
			Menu menu = new Menu();
			menu.title = mMenuTitle[i];
			menu.iconId = mMenuIcon[i];
			list.add(menu);
		}
		return list;
	}
	
	public class Menu {
		public String title;
		public int iconId;
	}
}
