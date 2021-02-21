package com.aceteam.game.battleship;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.Window;
import android.widget.ImageButton;

public class Exit extends Activity{	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exit);	
		MainActivity.array.add(this);
		
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		findViewById(R.id.exit).startAnimation(anim);
		
		final ImageButton yes = (ImageButton) findViewById(R.id.yes);
		final ImageButton no = (ImageButton) findViewById(R.id.no);
		
		yes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				for (int i=0; i < MainActivity.array.size(); i++) {
					MainActivity.array.get(i).finish();
				}
			}
		});
		
		no.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				onBackPressed();
			}
		});
		
		yes.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch(arg1.getAction()){
					case MotionEvent.ACTION_DOWN:
						yes.setImageResource(R.drawable.yes_on);
						break;
					case MotionEvent.ACTION_UP:
						yes.setImageResource(R.drawable.yes);
						break;
				}
				return false;
			}
		});		

		no.setOnTouchListener(new OnTouchListener() {
	
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch(arg1.getAction()){
					case MotionEvent.ACTION_DOWN:
						no.setImageResource(R.drawable.no_on);
						break;
					case MotionEvent.ACTION_UP:
						no.setImageResource(R.drawable.no);
						break;
				}
				return false;
			}
		});			
	}	
}