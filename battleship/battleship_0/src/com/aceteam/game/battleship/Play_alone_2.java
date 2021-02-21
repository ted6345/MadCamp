package com.aceteam.game.battleship;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.RelativeLayout.LayoutParams;

public class Play_alone_2 extends Activity {

	static int[][] board_setting =
		{{0,0,0,0,0,0,0,0,0,0},
	    {0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0}};
	int[][] tmp  =
			{{0,0,0,0,0,0,0,0,0,0},
		    {0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0}};
	int[][] tmp2  =
		{{0,0,0,0,0,0,0,0,0,0},
	    {0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0}};
	static int[][] board_setting2 = 
		{{0,0,0,0,0,0,0,0,0,0},
	    {0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0}};
	ImageView ship10, ship9, ship8, ship7, ship6, ship5, ship4, ship3, ship2, ship1,ship11,ship12;
	TableLayout tbl_board,tbl_board2;
	int x = 0, y = 0;
	boolean myturn_flag = false;
	boolean still_playing = true;
	boolean hitting = true;
	int turn = 0;
	int[] coordinate = new int[2];
	board player1 = new board();
	AIboard player2 = new AIboard();
	int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play_alone_2);
		final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

		tbl_board=(TableLayout) findViewById(R.id.tblBoard);
		tbl_board2 = (TableLayout) findViewById(R.id.tblBoard2);
		ship1 = (ImageView) findViewById(R.id.ship1);
	    ship2 = (ImageView) findViewById(R.id.ship2);
	    ship3 = (ImageView) findViewById(R.id.ship3);
	    ship4 = (ImageView) findViewById(R.id.ship4);
	    ship5 = (ImageView) findViewById(R.id.ship5);
	    ship6 = (ImageView) findViewById(R.id.ship6);
	    ship7 = (ImageView) findViewById(R.id.ship7);
	    ship8 = (ImageView) findViewById(R.id.ship8);
	    ship9 = (ImageView) findViewById(R.id.ship9);
	    ship10 = (ImageView) findViewById(R.id.ship10);
	    ship11 = (ImageView) findViewById(R.id.ship11);
	    ship12 = (ImageView) findViewById(R.id.ship12);
	    
		for(int i=0;i<10;i++)
			for(int j=0;j<10;j++)
					board_setting[i][j]=Play_alone.info[i][j];
		player1.setting(board_setting);
		player2.setting(board_setting2);
		setInfoToView(Play_alone.info, tmp,tmp2);
		
		tbl_board2.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
//				tbl_board2.getTouchables(true);
			  int action = event.getAction();
				switch (action) {
				case MotionEvent.ACTION_UP:
					count++;
					if (true) {
						if (count == 1) {
							x = Math.round(event.getX() / 70) - 1;
							y = Math.round(event.getY() / 70) - 1;
							if (x < 0)
								x = 0;
							else if (x > 9)
								x = 9;
							else if (y < 0)
								y = 0;
							else if (y > 9)
								y = 9;
							myturn_flag = false;
							if (board_setting2[y][x] < 10) {
								coordinate[0] = x;coordinate[1] = y;
								player2.opponent_turn(coordinate);
								still_playing = player2.getstill_board();
								hitting = player2.gethit();
								for(int i=0;i<10;i++)
									for(int j=0;j<10;j++)
										if(board_setting2[i][j]>10)
											tmp[i][j]=board_setting2[i][j];
								setInfoToView(Play_alone.info,tmp,tmp2);								
								if(hitting) vibrator.vibrate(500);
								
								else{
								hitting=true;				
								while (hitting && still_playing) {		
								coordinate = player2.turn(board_setting);
								player1.opponent_turn(coordinate);
								still_playing = player1.getstill_board();
								hitting = player1.gethit();
								for(int i=0;i<10;i++)
									for(int j=0;j<10;j++)
										if(board_setting[i][j]>10)
											tmp2[i][j]=board_setting[i][j];
								setInfoToView(Play_alone.info,tmp,tmp2);
								if(hitting) vibrator.vibrate(500);
								}
								}
								
							}
							count = 0;
							}
						}
						
					
					break;
					
				default:			break;
				}
				return true;
			}
		});

	}
	
	
	
	public void setInfoToView(int[][] myinfo, int[][] yourinfo,int[][] mydamage) {
		boolean set[] = { true, true, true, true, true, true, true, true, true,true, true, true, true };
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.setting2);
		layout.removeAllViews();
		layout.addView(tbl_board);
		layout.addView(tbl_board2);
		
		
		for (int i = 0; i < 10; i++) {
	         for (int j = 0; j < 10; j++) {
	            if (myinfo[i][j] != 0 && set[myinfo[i][j] - 1]) {
	               ImageView view = new ImageView(getApplicationContext());
	               RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	               int id = myinfo[i][j];

	               if (id > 10) {                  
	                  switch (id) {
	                  case 11:
	                     view.setImageResource(R.drawable.tile_11);
	                     ship11 = view;
	                     break;
	                  case 12:
	                     view.setImageResource(R.drawable.tile_12);
	                     ship12 = view;
	                     break;
	                  }
	               } else if (i < 9 && myinfo[i][j] == myinfo[i + 1][j]) {
	                  
	                  while(i>0 && myinfo[i-1][j]==11)
	                     i--;
	                  
	                  switch (id) {
	                     case 1:
	                        view.setImageResource(R.drawable.ship_4v);
	                        ship10 = view;
	                        break;
	                     case 2:
	                        view.setImageResource(R.drawable.ship_3v);
	                        ship9 = view;
	                        break;
	                     case 3:
	                        view.setImageResource(R.drawable.ship_3v);
	                        ship8 = view;
	                        break;
	                     case 4:
	                        view.setImageResource(R.drawable.ship_2v);
	                        ship7 = view;
	                        break;
	                     case 5:
	                        view.setImageResource(R.drawable.ship_2v);
	                        ship6 = view;
	                        break;
	                     case 6:
	                        view.setImageResource(R.drawable.ship_2v);
	                        ship5 = view;
	                       break;
	                  }
	               } else if (j < 9 && myinfo[i][j] == myinfo[i][j + 1]) {
	                  
	                  while(j>0 && myinfo[i][j-1]==11)
	                     j--;
	                                    
	                  switch (id) {
	                     case 1:
	                        view.setImageResource(R.drawable.ship_4);
	                        ship1 = view;
	                        break;
	                     case 2:
	                        view.setImageResource(R.drawable.ship_3);
	                        ship2 = view;
	                        break;
	                     case 3:
	                        view.setImageResource(R.drawable.ship_3);
	                        ship3 = view;
	                        break;
	                     case 4:
	                        view.setImageResource(R.drawable.ship_2);
	                        ship4 = view;
	                        break;
	                     case 5:
	                        view.setImageResource(R.drawable.ship_2);
	                        ship5 = view;
	                        break;
	                     case 6:
	                        view.setImageResource(R.drawable.ship_2);
	                        ship6 = view;
	                        break;
	                  }
	               } else {
	                  switch (id) {
	                     case 7:
	                        view.setImageResource(R.drawable.ship_1);
	                        ship7 = view;
	                        break;
	                     case 8:
	                        view.setImageResource(R.drawable.ship_1);
	                        ship8 = view;
	                        break;
	                     case 9:
	                        view.setImageResource(R.drawable.ship_1);
	                        ship9 = view;
	                        break;
	                     case 10:
	                        view.setImageResource(R.drawable.ship_1);
	                        ship10 = view;
	                        break;
	                  }
	               }
	              
	                  param.leftMargin = (int) (64 * (j + 1) + 55);
	                  param.topMargin = (int) (64 * (i + 1) + 225);
	              

	               view.setLayoutParams(param);
	               
	               layout.addView(view);
	               if (id < 11) set[id - 1] = false;
	            }
			}
		}
		
		for (int i = 0; i < 10; i++) {
	         for (int j = 0; j < 10; j++) {
	            if (mydamage[i][j] != 0 && set[mydamage[i][j] - 1]) {
	               ImageView view = new ImageView(getApplicationContext());
	               RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	               int id = mydamage[i][j];

	               if (id > 10) {                  
	                  switch (id) {
	                  case 11:
	                     view.setImageResource(R.drawable.tile_11);
	                     ship11 = view;
	                     break;
	                  case 12:
	                     view.setImageResource(R.drawable.tile_12);
	                     ship12 = view;
	                     break;
	                  }
	               } else if (i < 9 && mydamage[i][j] == mydamage[i + 1][j]) {
	                  
	                  while(i>0 && mydamage[i-1][j]==11)
	                     i--;
	                  
	                  switch (id) {
	                     case 1:
	                        view.setImageResource(R.drawable.ship_4v);
	                        ship10 = view;
	                        break;
	                     case 2:
	                        view.setImageResource(R.drawable.ship_3v);
	                        ship9 = view;
	                        break;
	                     case 3:
	                        view.setImageResource(R.drawable.ship_3v);
	                        ship8 = view;
	                        break;
	                     case 4:
	                        view.setImageResource(R.drawable.ship_2v);
	                        ship7 = view;
	                        break;
	                     case 5:
	                        view.setImageResource(R.drawable.ship_2v);
	                        ship6 = view;
	                        break;
	                     case 6:
	                        view.setImageResource(R.drawable.ship_2v);
	                        ship5 = view;
	                       break;
	                  }
	               } else if (j < 9 && mydamage[i][j] == mydamage[i][j + 1]) {
	                  
	                  while(j>0 && mydamage[i][j-1]==11)
	                     j--;
	                                    
	                  switch (id) {
	                     case 1:
	                        view.setImageResource(R.drawable.ship_4);
	                        ship1 = view;
	                        break;
	                     case 2:
	                        view.setImageResource(R.drawable.ship_3);
	                        ship2 = view;
	                        break;
	                     case 3:
	                        view.setImageResource(R.drawable.ship_3);
	                        ship3 = view;
	                        break;
	                     case 4:
	                        view.setImageResource(R.drawable.ship_2);
	                        ship4 = view;
	                        break;
	                     case 5:
	                        view.setImageResource(R.drawable.ship_2);
	                        ship5 = view;
	                        break;
	                     case 6:
	                        view.setImageResource(R.drawable.ship_2);
	                        ship6 = view;
	                        break;
	                  }
	               } else {
	                  switch (id) {
	                     case 7:
	                        view.setImageResource(R.drawable.ship_1);
	                        ship7 = view;
	                        break;
	                     case 8:
	                        view.setImageResource(R.drawable.ship_1);
	                        ship8 = view;
	                        break;
	                     case 9:
	                        view.setImageResource(R.drawable.ship_1);
	                        ship9 = view;
	                        break;
	                     case 10:
	                        view.setImageResource(R.drawable.ship_1);
	                        ship10 = view;
	                        break;
	                  }
	               }
	              
	                  param.leftMargin = (int) (64 * (j + 1) + 55);
	                  param.topMargin = (int) (64 * (i + 1) + 225);
	               view.setLayoutParams(param);
	               layout.addView(view);
	               if (id < 11) set[id - 1] = false;
	            }
			}
		}
		for (int i = 0; i < 10; i++) {
	         for (int j = 0; j < 10; j++) {
	            if (yourinfo[i][j] != 0 && set[yourinfo[i][j] - 1]) {
	               ImageView view = new ImageView(getApplicationContext());
	               RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	               int id = yourinfo[i][j];

	               if (id > 10) {                  
	                  switch (id) {
	                  case 11:
	                     view.setImageResource(R.drawable.tile_11);
	                     ship11 = view;
	                     break;
	                  case 12:
	                     view.setImageResource(R.drawable.tile_12);
	                     ship12 = view;
	                     break;
	                  }
	               } else if (i < 9 && yourinfo[i][j] == yourinfo[i + 1][j]) {
	                  
	                  while(i>0 && yourinfo[i-1][j]==11)
	                     i--;
	                  
	                  switch (id) {
	                     case 1:
	                        view.setImageResource(R.drawable.ship_4v);
	                        ship10 = view;
	                        break;
	                     case 2:
	                        view.setImageResource(R.drawable.ship_3v);
	                        ship9 = view;
	                        break;
	                     case 3:
	                        view.setImageResource(R.drawable.ship_3v);
	                        ship8 = view;
	                        break;
	                     case 4:
	                        view.setImageResource(R.drawable.ship_2v);
	                        ship7 = view;
	                        break;
	                     case 5:
	                        view.setImageResource(R.drawable.ship_2v);
	                        ship6 = view;
	                        break;
	                     case 6:
	                        view.setImageResource(R.drawable.ship_2v);
	                        ship5 = view;
	                       break;
	                  }
	               } else if (j < 9 && yourinfo[i][j] == yourinfo[i][j + 1]) {
	                  
	                  while(j>0 && yourinfo[i][j-1]==11)
	                     j--;
	                                    
	                  switch (id) {
	                     case 1:
	                        view.setImageResource(R.drawable.ship_4);
	                        ship1 = view;
	                        break;
	                     case 2:
	                        view.setImageResource(R.drawable.ship_3);
	                        ship2 = view;
	                        break;
	                     case 3:
	                        view.setImageResource(R.drawable.ship_3);
	                        ship3 = view;
	                        break;
	                     case 4:
	                        view.setImageResource(R.drawable.ship_2);
	                        ship4 = view;
	                        break;
	                     case 5:
	                        view.setImageResource(R.drawable.ship_2);
	                        ship5 = view;
	                        break;
	                     case 6:
	                        view.setImageResource(R.drawable.ship_2);
	                        ship6 = view;
	                        break;
	                  }
	               } else {
	                  switch (id) {
	                     case 7:
	                        view.setImageResource(R.drawable.ship_1);
	                        ship7 = view;
	                        break;
	                     case 8:
	                        view.setImageResource(R.drawable.ship_1);
	                        ship8 = view;
	                        break;
	                     case 9:
	                        view.setImageResource(R.drawable.ship_1);
	                        ship9 = view;
	                        break;
	                     case 10:
	                        view.setImageResource(R.drawable.ship_1);
	                        ship10 = view;
	                        break;
	                  }
	               }
	               
	               param.leftMargin = (int) (64 * (j + 1) + 986);
	               param.topMargin = (int) (64 * (i + 1) + 225);

	               view.setLayoutParams(param);
	               
	               layout.addView(view);
	               if (id < 11) set[id - 1] = false;
	            }
			}
		}	
		
		
		
	
		
	}
	
}


