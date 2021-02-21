package com.aceteam.game.battleship;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageButton;


public class Start extends Activity{	
	
	static MediaPlayer soundpool;
//	static SoundPool soundpool;
	static int gullId = -1;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.start);	
		MainActivity.array.add(this);
		
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
     //   soundpool = new SoundPool(1,AudioManager.STREAM_MUSIC,0);
		soundpool = new MediaPlayer();
        try{
           AssetManager assetManager = getAssets();
           AssetFileDescriptor descriptor = assetManager.openFd("gulls.mp3");
//           gullId = soundpool.load(descriptor,1);
           soundpool.setDataSource(descriptor.getFileDescriptor(),descriptor.getStartOffset(),descriptor.getLength());
           soundpool.prepare();
           soundpool.setLooping(true);        
        }
        catch(IOException e){
           Log.e("Couldn't load sound effect from asset",e.getMessage());           
        }
        
        soundpool.start();
        
		overridePendingTransition(R.anim.left, R.anim.right);
		
		final ImageButton btn = (ImageButton) findViewById(R.id.start);
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent loading = new Intent(getApplicationContext(), GameActivity.class);
				startActivity(loading);				
			}
		});
		
		btn.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch(arg1.getAction()){
					case MotionEvent.ACTION_DOWN:
						btn.setImageResource(R.drawable.start_on);
						break;
					case MotionEvent.ACTION_UP:
						btn.setImageResource(R.drawable.start);
						break;
				}
				return false;
			}
		});				
	}

	@Override
	public void onBackPressed() {
		Intent loading = new Intent(getApplicationContext(), Exit.class);
		startActivity(loading);
	}
	
	@Override
	   protected void onPause() {
	      // TODO Auto-generated method stub
	      super.onPause();
	     // soundpool.pause(gullId);
	      soundpool.pause();
	   }
	   
	   @Override
	   protected void onDestroy() {
	      // TODO Auto-generated method stub
	      super.onDestroy();
	      soundpool.release();
	   }
	   
	   @Override
	   protected void onResume() {
	      // TODO Auto-generated method stub
	      super.onResume();
//	      soundpool.resume(gullId);
	      soundpool.start();
	   }
	
}