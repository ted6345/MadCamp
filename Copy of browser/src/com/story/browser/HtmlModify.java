package com.story.browser;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class HtmlModify extends Activity{
	TextView htmlmd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.htmlmodify);
		
		htmlmd = (TextView) findViewById(R.id.tv_htmlmd);
		//htmlmd.setMaxLines(100);
		//htmlmd.setVerticalScrollBarEnabled(true);
		//htmlmd.setMovementMethod(new ScrollingMovementMethod());
		htmlmd.setText(((MainActivity)MainActivity.mContext).html);
	}
}
