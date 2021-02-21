package com.aceteam.game.battleship;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

public class Bon_angels extends Activity {
	Animation anim;
	Context context;
	boolean flag = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bon_angels);
		MainActivity.array.add(this);
		   
		context = getApplicationContext();
		
		anim = AnimationUtils.loadAnimation(this, R.anim.fade_in_out);
	    		
		new Thread(){
			public void run() {
				try {
					findViewById(R.id.bon).startAnimation(anim);
					sleep(5000);
					if(flag){
						Intent loading = new Intent(context, Loading.class);
						startActivity(loading);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

	@Override
	public void onBackPressed() {
		
	}
	
	@Override
	protected void onStop() {
		flag = false;
		super.onStop();
	}
	
	@Override
	protected void onStart() {
		if(!flag){
			Intent loading = new Intent(context, MainActivity.class);
			startActivity(loading);
		}
		else
			super.onStart();
	}
}