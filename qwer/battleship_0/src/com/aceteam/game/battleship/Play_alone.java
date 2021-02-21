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
        	
    			
    			//refresh �� �ɸ´� �޼ҵ�
    	    }
    	});
        
        findViewById(R.id.auto_button).setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {	
    			//auto�� �ɸ´� ��ư 
    	    }
    	});
        findViewById(R.id.next_button).setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {	
    		
    		
    			//if( ������ ������ �غ� �� �Ǿ��� ��!!
    			startActivity(play_alone_playing);
    			
    			//else �غ� �� �����ʾҽ��ϴ�. �佺Ʈ �����

    		
    		}
    	});

    }
        

}
