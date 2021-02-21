package com.aceteam.game.battleship;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;

public class Loading extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.loading);
				
		overridePendingTransition(R.anim.left, R.anim.right);

		progressThread task = new progressThread();
		task.execute();
	}
	
	@Override
	public void onBackPressed() {
		
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
			
			Intent loading = new Intent(getApplicationContext(), GameActivity.class);
			startActivity(loading);
			
			return null;
		}
		
	}
}