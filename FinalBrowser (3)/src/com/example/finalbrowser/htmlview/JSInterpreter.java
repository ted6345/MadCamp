package com.example.finalbrowser.htmlview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalbrowser.MainActivity;
import com.example.finalbrowser.R;

public class JSInterpreter extends Activity{
	public TextView log;
	EditText input;
	ImageView confirm;
	WebView web;
	public static Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jsinterpreter);
		
		mContext = this;
		log = (TextView) findViewById(R.id.tv_jslog);
		input = (EditText) findViewById(R.id.et_jsinput);
		confirm = (ImageView) findViewById(R.id.bt_ok);
		web = ((MainActivity)MainActivity.mContext).getWeb();
		((MainActivity)MainActivity.mContext).inJS = true;
				
		confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.out.println(((MainActivity)MainActivity.mContext).inJS);
				String temp = input.getText().toString();
				if(temp.equals("")) return;
				log.append("\n"+temp);
				input.setText("");
				web.loadUrl("javascript:" + temp);
				temp = "";
				
			}
		});
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		((MainActivity)MainActivity.mContext).inJS = false;
		super.onPause();
	}

}
