package com.aceteam.game.battleship;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;

public class Loading extends Activity{
	Context context;
	progressThread task;
	boolean flag = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.loading);
		MainActivity.array.add(this);
		
		context = getApplicationContext();	
		
		overridePendingTransition(R.anim.left, R.anim.right);

		task = new progressThread();
		task.execute();
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
		
	class progressThread extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			ProgressBar pBar = (ProgressBar) findViewById(R.id.progressBar);
						
			for(int i=0; i<=100; i++){
				try {
					pBar.setProgress(i);
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if(flag){
				Intent loading = new Intent(getApplicationContext(), Start.class);
				startActivity(loading);
			}		
			
			return null;
		}
		
	}
}