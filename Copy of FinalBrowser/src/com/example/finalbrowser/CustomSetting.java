package com.example.finalbrowser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class CustomSetting extends Activity {
	TextView textsize, textpreview;
	SeekBar seekbar;
	Context mContext;
	int size;

	protected void onCreate(Bundle savedInstanceState) {
		mContext = this;
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_setting);
		textsize = (TextView) findViewById(R.id.TextSize);
		textpreview = (TextView) findViewById(R.id.TextPreview);
		seekbar = (SeekBar) findViewById(R.id.SeekBar);

		SharedPreferences pref = ((MainActivity) MainActivity.mContext)
				.getPreferences(0);
		final SharedPreferences.Editor mEdit1 = pref.edit();

		size = pref.getInt("TextSize", 70);
		System.out.println("prefTextSize" + size);
		seekbar.setMax(170);
		seekbar.setProgress(size);
		textpreview.setTextSize(15);
		textsize.setText("" + (size + 30) + "%");

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
				textpreview.setTextSize((15 * (seekbar.getProgress() + 30)) / 100);
				textsize.setText("" + (seekbar.getProgress() + 30) + "%");
				((MainActivity) MainActivity.mContext).getWeb().getSettings()
						.setTextZoom((seekbar.getProgress() + 30));
				((MainActivity) MainActivity.mContext).settextsize((seekbar
						.getProgress() + 30));
				mEdit1.putInt("TextSize", seekbar.getProgress());
				mEdit1.commit();
			}
		});

	}
}
