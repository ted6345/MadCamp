package com.example.finalbrowser;

import com.example.finalbrowser.normal.History;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class CustomSetting extends Activity {
	TextView textsize, textpreview;
	SeekBar seekbar;
	Context mContext;
	CheckBox checkbox;
	Button mClearHistory;
	Button mClearCache;
	int size,tmp;
	Boolean checked ;
	
	protected void onCreate(Bundle savedInstanceState) {
		mContext = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_setting);
		textsize = (TextView) findViewById(R.id.TextSize);
		textpreview = (TextView) findViewById(R.id.TextPreview);
		mClearHistory = (Button) findViewById(R.id.clear_history);
		mClearCache = (Button) findViewById(R.id.clear_cache);
		seekbar = (SeekBar) findViewById(R.id.SeekBar);
		checkbox = (CheckBox) findViewById(R.id.FullScreen);

		SharedPreferences pref = ((MainActivity) MainActivity.mContext)
				.getPreferences(0);
		final SharedPreferences.Editor mEdit1 = pref.edit();
		checked = pref.getBoolean("FullScreen", false);
		checkbox.setChecked(checked);

		size = pref.getInt("TextSize", 70);
		System.out.println("prefTextSize" + size);
		seekbar.setMax(130);
		seekbar.setProgress(size-30);
		textpreview.setTextSize(20*(size)/100);
		textsize.setText("" + (size) + "%");
	
		if(pref.getBoolean("FullScreen", false))
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		else
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		
		mClearHistory.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				History.list.clear();
				Toast.makeText(mContext, "히스토리가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
			}
		});
		
		mClearCache.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((MainActivity) MainActivity.mContext).getWeb().clearCache(false);
				Toast.makeText(mContext, "이 탭의 캐시가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
			}
		});
		
		
		
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
			}

			@SuppressLint("NewApi")
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				tmp=seekbar.getProgress() + 30;
				if(tmp%5>2) tmp+= (5-tmp%5);
				else tmp-= tmp%5;
				seekbar.setProgress(tmp-30);
				textpreview.setTextSize((20 * tmp) / 100);
				textsize.setText("" + tmp  + "%");
				((MainActivity) MainActivity.mContext).getWeb().getSettings().setTextZoom(tmp);
				((MainActivity) MainActivity.mContext).settextsize(tmp);
				mEdit1.putInt("TextSize", tmp);
				mEdit1.commit();
			}
		});
		
		// 체크박스
		checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				((MainActivity) MainActivity.mContext).setwindowsize(isChecked);
				if(isChecked)
					getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
				else
					getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
				
				mEdit1.putBoolean("FullScreen", isChecked);
				mEdit1.commit();
			}});

	}
}
