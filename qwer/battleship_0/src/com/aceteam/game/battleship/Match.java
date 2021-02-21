package com.aceteam.game.battleship;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.android.gms.games.Games;

public class Match extends Activity {

    final static int RC_SELECT_PLAYERS = 10000;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match);
		
		findViewById(R.id.startMatchButton).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {	
	    	
				Intent play_online_intent = new Intent(Match.this, Play_online.class);
				startActivity(play_online_intent);	
			
		    }
		});
		
		findViewById(R.id.findOpponentButton).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				Intent intent = Games.TurnBasedMultiplayer.getSelectOpponentsIntent(GameActivity.mGoogleApiClient,
			             1, 7, true);
			     startActivityForResult(intent, RC_SELECT_PLAYERS);	
			
		    }
		});
	}
	
	 
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.match, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
