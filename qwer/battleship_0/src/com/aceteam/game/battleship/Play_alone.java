package com.aceteam.game.battleship;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class Play_alone extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_alone);
       
        final Intent play_alone_playing = new Intent(this,Play_alone.class);
        
        
        findViewById(R.id.previous_button).setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {	
        	finish();
    	    }
    	});
        
        findViewById(R.id.refresh_button).setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {	
        	
    			
    			//refresh 에 걸맞는 메소드
    	    }
    	});
        
        findViewById(R.id.auto_button).setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {	
    			//auto에 걸맞는 버튼 
    	    }
    	});
        findViewById(R.id.next_button).setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {	
    		
    		
    			//if( 게임을 시작할 준비가 다 되었을 때!!
    			startActivity(play_alone_playing);
    			
    			//else 준비가 다 도지않았습니다. 토스트 띄어줌

    		
    		}
    	});

    }
        

}
