package com.aceteam.game.battleship;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MainActivity extends Activity {
	Animation anim;
	Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		   
		context = getApplicationContext();
		
		anim = AnimationUtils.loadAnimation(this, R.anim.fade_in_out);
	    		
		new Thread(){
			public void run() {
				try {
					findViewById(R.id.logo).startAnimation(anim);
					sleep(3000);
					Intent loading = new Intent(context, Bon_angels.class);
					startActivity(loading);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
}