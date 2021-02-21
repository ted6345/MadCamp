package com.example.galleryexample;

import android.animation.AnimatorSet.Builder;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;




public class singletouchtest extends Activity implements OnTouchListener{
	StringBuilder builder = new StringBuilder();
	TextView textView;
	
	public void onCreatd(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		textView =new TextView(this);
		textView.setText("Touch and dreag(one finger only)");
		textView.setOnTouchListener(this);
		setContentView(textView);
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		builder.setLength(0);
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			builder.append("down,");
			break;
		case MotionEvent.ACTION_MOVE:
			builder.append("MOVE,");
			break;
		case MotionEvent.ACTION_CANCEL:
			builder.append("CANCLE,");
			break;
		case MotionEvent.ACTION_UP:
			builder.append("UP,");
			break;
		}
	
	
	builder.append(event.getX());
	builder.append(",");
	builder.append(event.getY());
	String text= builder.toString();
	Log.d("Thouchtest",text);
	textView.setText(text);
	return true;
	}
	
}





}