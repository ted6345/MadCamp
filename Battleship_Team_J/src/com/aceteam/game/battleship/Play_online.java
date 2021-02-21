package com.aceteam.game.battleship;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Play_online extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_online);
        MainActivity.array.add(this);
    }
    
    
    public void nextClick(View v)
    {
    	Intent intent = new Intent(this,Play_alone.class);
    	startActivity(intent);

    }
    public void previousClick(View v)
    {
    	finish();
    }

    public void refreshClick(View v)
    {
    	//refresh에 맞는 메소드
    	
    	
    }
    public void autoClick(View v)
    {
    	//auto에 맞는 메소드
    	
    	
    }
}
