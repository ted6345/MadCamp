package com.beginagain.hyclub00;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class DevActivity extends Activity implements OnClickListener{
	private Button hsMail, jyMail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.activity_dev);
		
		hsMail = (Button)findViewById(R.id.hsMail);
		jyMail = (Button)findViewById(R.id.jyMail);
		
		hsMail.setOnClickListener(this);
		jyMail.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent mail = new Intent(Intent.ACTION_SENDTO);
		
		switch(v.getId()){
		case R.id.hsMail:
			mail.setData(Uri.parse("mailto:ahs1992@nate.com"));
			break;
		case R.id.jyMail:
			mail.setData(Uri.parse("mailto:violentjy@hanyang.ac.kr"));
			break;
		}
		
		startActivity(mail);
	}
}
