package com.beginagain.hyclub00;

import java.util.ArrayList;

import com.beginagain.hyclub00.adapter.CopyFromExcel;
import com.beginagain.hyclub00.adapter.MySQLiteOpenHelper;
import com.beginagain.hyclub00.adapter.SQLiteFunction;
import com.beginagain.hyclub00.data.ClubData;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	private final long	FINSH_INTERVAL_TIME    = 2000;
	private long		backPressedTime        = 0;
	
	private MySQLiteOpenHelper helper;
	private SQLiteDatabase db;
	private SQLiteFunction order;
	private ArrayList<ClubData> allArray;
	
	private int NUM_PAGES = 2;
	
	public final static int FRAGMENT_HC = 0;
	public final static int FRAGMENT_MC = 1;
	
	private ViewPager pager;
	
	private Button hcBtn, mcBtn, dev;
	
	private pagerAdapter pA;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.activity_main);
		
		helper = new MySQLiteOpenHelper(this, "club.db", null, 1);
		db = helper.getWritableDatabase();
		order = new SQLiteFunction(db, helper);
		
		allArray = new ArrayList<ClubData>();
		
		int versionNum = getBaseContext().getResources().getInteger(R.integer.VERSION_NUM);
		
		if(getPF() == 0){
			
			CopyFromExcel saveDB = new CopyFromExcel(getBaseContext(), db, helper);
			
			saveDB.copyFromExcel();
			savePF(versionNum);

		} else if (getPF() != versionNum){
			allArray = order.selectAll();
			
			for(int i = 0; i < allArray.size(); i++){
				order.deleteDB(i);
			}
			
			CopyFromExcel saveDB = new CopyFromExcel(getBaseContext(), db, helper);
			
			saveDB.copyFromExcel();
			savePF(versionNum);
		}
		
		pager = (ViewPager)findViewById(R.id.Pager);
		pA = new pagerAdapter(getSupportFragmentManager());
		pager.setAdapter(pA);
		pager.setCurrentItem(FRAGMENT_HC);
		
		hcBtn = (Button)findViewById(R.id.HCbtn);
		mcBtn = (Button)findViewById(R.id.MCbtn);
		dev = (Button)findViewById(R.id.Dev);
		
		dev.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent devInt = new Intent(MainActivity.this, DevActivity.class);
				startActivity(devInt);
			}
		});
		
		hcBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				pager.setCurrentItem(FRAGMENT_HC);
			}
		});
		
		mcBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				pager.setCurrentItem(FRAGMENT_MC);
				pA.notifyDataSetChanged();
			}
		});
		
		pager.setOnPageChangeListener(new OnPageChangeListener(){

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageSelected(int position) {
				hcBtn.setSelected(false);
				mcBtn.setSelected(false);
				
				switch(position){
				case 0:
					hcBtn.setSelected(true);
					break;
				case 1:
					mcBtn.setSelected(true);
					pA.notifyDataSetChanged();
					break;
				}
			}
			
		});
		
		hcBtn.setSelected(true);
	}
	
	private class pagerAdapter extends FragmentPagerAdapter{
		
		public pagerAdapter(android.support.v4.app.FragmentManager fm) {
			super(fm);
		}
		
		@Override
		public Fragment getItem(int position) {
			switch(position){
			case 0:
				return new Fragment_HYCLUB();
			case 1:
				return new Fragment_MYCLUB();
			default:
				return null;
			}
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return NUM_PAGES;
		}
		
		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return POSITION_NONE;
		}
	}
	
	private int getPF(){ // 이건 저장된 값 불러오는 메소드
		SharedPreferences pref = getSharedPreferences("verPref", MODE_PRIVATE);
		int SC = pref.getInt("ver", 0); 
		
		return SC;
	}
	
	private void savePF(int value){ // 이건 저장 or 수정 하는 메서드
		SharedPreferences pref = getSharedPreferences("verPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("ver", value);
        editor.commit();
	}
	
	@Override 
	public void onBackPressed() {
	    long tempTime        = System.currentTimeMillis();
	    long intervalTime    = tempTime - backPressedTime;
	
	    if ( 0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime ) {
	       //super.onBackPressed();
	       moveTaskToBack(true); 
	       finish(); 
	       android.os.Process.killProcess(android.os.Process.myPid());
	    } 
	    else { 
	       backPressedTime = tempTime; 
	       Toast.makeText(getApplicationContext(),"'뒤로'버튼을 한번 더 누르시면 종료됩니다.",Toast.LENGTH_SHORT).show(); 
	    } 
	 }

}
