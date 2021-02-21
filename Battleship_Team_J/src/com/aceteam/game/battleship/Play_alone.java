package com.aceteam.game.battleship;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.Toast;

public class Play_alone extends Activity implements OnTouchListener {
	static int[][] info = new int[10][10];
	static int count =0;
	
	ImageView ship1_1, ship1_2, ship1_3, ship1_4, ship2_1, ship2_2, ship2_3, ship3_1, ship3_2, ship4_1;
	String tag;
	ImageView drag_view;
	RelativeLayout layout;
	Context context;
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_alone);
        MainActivity.array.add(this);
       
        layout = (RelativeLayout) findViewById(R.id.setting);
        overridePendingTransition(R.anim.left, R.anim.right);
        context = getApplicationContext();

        //////////////status initialize//////////////
        for(int i=0; i<10;i++){
        	for(int j=0; j<10;j++)
        		info[i][j] = 0;
        }
        
        count = 0;
        /////////////////////////////////////////////
        
        final ImageButton previous = (ImageButton) findViewById(R.id.previous_button);
        final ImageButton auto = (ImageButton) findViewById(R.id.auto_button);
        final ImageButton next = (ImageButton) findViewById(R.id.next_button);
        final ImageButton rotate = (ImageButton) findViewById(R.id.rotate_button);
        final TableLayout tbl_board = (TableLayout) findViewById(R.id.tblBoard);
        
        ship4_1 = (ImageView) findViewById(R.id.ship4_1);
        ship3_1 = (ImageView) findViewById(R.id.ship3_1);
        ship3_2 = (ImageView) findViewById(R.id.ship3_2);
        ship2_1 = (ImageView) findViewById(R.id.ship2_1);
        ship2_2 = (ImageView) findViewById(R.id.ship2_2);
        ship2_3 = (ImageView) findViewById(R.id.ship2_3);
        ship1_1 = (ImageView) findViewById(R.id.ship1_1);
        ship1_2 = (ImageView) findViewById(R.id.ship1_2);
        ship1_3 = (ImageView) findViewById(R.id.ship1_3);
        ship1_4 = (ImageView) findViewById(R.id.ship1_4);
           
        previous.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {	
    			onBackPressed();
    	    }
    	});
        
        rotate.setOnClickListener(new OnClickListener() {
        	
    		@Override
    		public void onClick(View v) {
    			boolean flag = false, direction;
    			int id;
    			
    			if(tag == null)
    				return;
    			else if(tag.endsWith("v")){
    				id = Integer.parseInt(tag.substring(0, tag.length()-1));
    				direction = true;
    			}
    			else{
    				id = Integer.parseInt(tag.substring(0, tag.length()));
    				direction = false;
    			}
    			
    			for(int i=0; i<10; i++){
    				for(int j=0; j<10; j++){
    					if(info[i][j] == id){
    						flag = check_space(i, j, id, direction, info, false);
    						i = 10;
    						break;
    					}
    				}	
    			}
    			
    			if(flag){
					switch (tag) {
						case "1":
							ship4_1.setImageResource(R.drawable.ship_4v);
							ship4_1.setTag("1v");
							tag = "1v";
							break;
							
						case "1v":
							ship4_1.setImageResource(R.drawable.ship_4);
							ship4_1.setTag("1");
							tag = "1";
							break;
		
						case "2":
							ship3_1.setImageResource(R.drawable.ship_3v);
							ship3_1.setTag("2v");
							tag = "2v";
							break;
							
						case "2v":
							ship3_1.setImageResource(R.drawable.ship_3);
							ship3_1.setTag("2");
							tag = "2";
							break;
		
						case "3":
							ship3_2.setImageResource(R.drawable.ship_3v);
							ship3_2.setTag("3v");
							tag = "3v";
							break;
							
						case "3v":
							ship3_2.setImageResource(R.drawable.ship_3);
							ship3_2.setTag("3");
							tag = "3";
							break;
		
						case "4":
							ship2_1.setImageResource(R.drawable.ship_2v);
							ship2_1.setTag("4v");
							tag = "4v";
							break;
							
						case "4v":
							ship2_1.setImageResource(R.drawable.ship_2);
							ship2_1.setTag("4v");
							tag = "4";
							break;
		
						case "5":
							ship2_2.setImageResource(R.drawable.ship_2v);
							ship2_2.setTag("5v");
							tag = "5";
							break;
							
						case "5v":
							ship2_2.setImageResource(R.drawable.ship_2);
							ship2_2.setTag("5");
							tag = "5";
							break;
		
						case "6":
							ship2_3.setImageResource(R.drawable.ship_2v);
							ship2_3.setTag("6v");
							tag = "6v";
							break;
							
						case "6v":
							ship2_3.setImageResource(R.drawable.ship_2);
							ship2_3.setTag("6");
							tag = "6";
							break;
					}
    			}
    	    }
        });
        
        auto.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			layout.removeView(ship1_1);	layout.removeView(ship2_2);
    			layout.removeView(ship1_2);	layout.removeView(ship2_3);
    			layout.removeView(ship1_3);	layout.removeView(ship3_1);
    			layout.removeView(ship1_4);	layout.removeView(ship3_2);
    			layout.removeView(ship2_1);	layout.removeView(ship4_1);
    			
    			for(int i=0;i<10;i++){
    				for(int j=0;j<10;j++)
    					info[i][j] = 0;
    			}
    			
    			int sample1[][] = new int[][]
    					{{0,7,0,0,0,0,0,0,0,0},{0,0,0,0,6,0,0,0,0,0},{0,0,8,0,6,0,0,0,2,0},{9,0,0,0,0,0,0,0,2,0},{0,0,0,0,0,0,0,0,2,0},{0,0,5,5,0,0,0,0,0,0},{0,0,0,0,0,0,1,0,0,3},{0,4,0,0,0,0,1,0,0,3},{0,4,0,0,0,0,1,0,0,3},{0,0,0,10,0,0,1,0,0,0}};
    			
    			int sample2[][] = new int[][]
    					{{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,2,0},{0,0,0,0,0,0,0,0,2,0},{7,0,0,0,3,3,3,0,2,0},{0,0,0,0,0,0,0,0,0,0},{0,8,0,0,0,0,1,1,1,1},{0,0,0,0,9,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{4,4,0,10,0,5,5,0,6,0},{0,0,0,0,0,0,0,0,6,0}};
    			
    			int sample3[][] = new int[][]
    					{{0,0,4,4,0,0,0,2,0,0},{0,0,0,0,0,0,0,2,0,7},{0,0,5,5,0,8,0,2,0,0},{9,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,6,6,0,0,0,0},{0,1,0,0,0,0,0,0,3,0},{0,1,0,0,0,0,0,0,3,0},{0,1,0,0,0,0,0,0,3,0},{0,1,0,0,0,10,0,0,0,0}};
    			
    			int sample4[][] = new int[][]
    					{{0,0,0,0,0,0,0,0,0,7},{2,2,2,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,1},{0,0,0,0,0,0,0,0,0,1},{0,0,10,0,3,3,3,0,0,1},{9,0,0,0,0,0,0,0,0,1},{0,0,0,0,0,4,0,0,0,0},{0,6,0,0,0,4,0,0,0,0},{0,6,0,0,0,0,0,0,0,0},{0,0,0,0,5,5,0,0,8,0}};
    			
    			int sample5[][] = new int[][]
    					{{7,0,0,0,0,0,9,0,0,0},{0,0,0,0,0,0,0,0,0,0},{8,0,0,0,0,0,0,4,4,0},{0,0,0,0,0,0,0,0,0,0},{2,2,2,0,0,0,0,0,5,5},{0,0,0,0,10,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,1,1,1,1,0,0,0,6,0},{0,0,0,0,0,0,0,0,6,0},{0,0,0,3,3,3,0,0,0,0}};
    			
    			int sample6[][] = new int[][]
    					{{0,7,0,0,0,0,0,0,6,0},{0,0,0,2,2,2,0,0,6,0},{0,0,0,0,0,0,0,0,0,0},{4,0,0,5,0,0,0,0,0,8},{4,0,0,5,0,10,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,1,0},{9,0,0,0,0,0,0,0,1,0},{0,0,0,3,3,3,0,0,1,0},{0,0,0,0,0,0,0,0,1,0}};
    			
    			int sample7[][] = new int[][]
    					{{0,0,0,0,0,0,0,0,10,0},{0,7,0,0,0,0,0,0,0,0},{0,0,0,0,0,2,2,2,0,0},{0,0,1,0,0,0,0,0,0,0},{0,0,1,0,0,0,0,6,6,0},{0,0,1,0,0,5,0,0,0,0},{0,0,1,0,0,5,0,0,0,0},{0,0,0,0,0,0,0,0,9,0},{0,4,4,0,0,0,0,0,0,0},{0,0,0,8,0,0,0,3,3,3}};
    			
    			int sample8[][] = new int[][]
    					{{0,0,0,0,0,0,0,0,7,0},{0,0,0,0,0,0,0,0,0,0},{0,1,1,1,1,0,0,2,2,2},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,10,0,0,0,4,4,0,0,5},{0,0,0,0,0,0,0,0,0,5},{0,3,3,3,0,0,8,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,6,6,0,9}};
    			
    			int sample9[][] = new int[][]
    					{{10,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,2,0,0,8},{0,9,0,0,0,0,2,0,0,0},{0,0,0,6,0,0,2,0,7,0},{0,3,0,6,0,0,0,0,0,0},{0,3,0,0,0,0,0,0,1,0},{0,3,0,0,0,4,4,0,1,0},{0,0,0,0,0,0,0,0,1,0},{0,0,0,0,5,5,0,0,1,0},{0,0,0,0,0,0,0,0,0,0}};
    			
    			int sample10[][] = new int[][]
    					{{0,4,0,7,0,0,0,0,0,0},{0,4,0,0,0,0,0,0,0,0},{0,0,0,0,0,3,0,0,2,0},{0,0,0,0,0,3,0,0,2,0},{0,0,0,0,0,3,0,0,2,0},{0,0,5,5,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{9,0,8,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,6,0,0},{1,1,1,1,0,0,0,6,0,10}};
    			
    			switch((int)(Math.random()*10)){
    				case 1:	info = sample1;	break;
    				case 2:	info = sample2;	break;
    				case 3:	info = sample3;	break;
    				case 4:	info = sample4;	break;
    				case 5:	info = sample5;	break;
    				case 6:	info = sample6;	break;
    				case 7:	info = sample7;	break;
    				case 8:	info = sample8;	break;
    				case 9:	info = sample9;	break;
    				default:info = sample10;
        		}
    			
    			count = 10;

    			setInfoToView(info);
    	    }
    	});
        
        next.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {	
    			if(count==10){
    				Intent loading = new Intent(context, Play_alone_2.class);
					startActivity(loading);
    			}
    		}
    	});
        
        previous.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch(arg1.getAction()){
					case MotionEvent.ACTION_DOWN:
						previous.setImageResource(R.drawable.previous_on);
						break;
					case MotionEvent.ACTION_UP:
						previous.setImageResource(R.drawable.previous);
						break;
				}
				return false;
			}
		});
        
        auto.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch(arg1.getAction()){
					case MotionEvent.ACTION_DOWN:
						auto.setImageResource(R.drawable.auto_on);
						break;
					case MotionEvent.ACTION_UP:
						auto.setImageResource(R.drawable.auto);
						break;
				}
				return false;
			}
		});
        
        rotate.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch(arg1.getAction()){
					case MotionEvent.ACTION_DOWN:
						rotate.setImageResource(R.drawable.rotate_on);
						break;
					case MotionEvent.ACTION_UP:
						rotate.setImageResource(R.drawable.rotate);
						break;
				}
				return false;
			}
		});
        
        next.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch(arg1.getAction()){
					case MotionEvent.ACTION_DOWN:
						next.setImageResource(R.drawable.next_on);
						break;
					case MotionEvent.ACTION_UP:
						next.setImageResource(R.drawable.next);
						break;
				}
				return false;
			}
		});
                        
        ship4_1.setTag("1");	ship4_1.setOnTouchListener(this);
        ship3_1.setTag("2");	ship3_1.setOnTouchListener(this);
        ship3_2.setTag("3");	ship3_2.setOnTouchListener(this);
        ship2_1.setTag("4");	ship2_1.setOnTouchListener(this);
        ship2_2.setTag("5");	ship2_2.setOnTouchListener(this);
        ship2_3.setTag("6");	ship2_3.setOnTouchListener(this);
        ship1_1.setTag("7");	ship1_1.setOnTouchListener(this);
        ship1_2.setTag("8");	ship1_2.setOnTouchListener(this);
        ship1_3.setTag("9");	ship1_3.setOnTouchListener(this);
        ship1_4.setTag("10");	ship1_4.setOnTouchListener(this);
        
		tbl_board.setOnDragListener(new OnDragListener() {
			
			@Override
			public boolean onDrag(View v, DragEvent event) {
				int action = event.getAction();
								
				switch (action) {
				case DragEvent.ACTION_DRAG_STARTED:
					break;
				case DragEvent.ACTION_DRAG_ENTERED:
					break;
				case DragEvent.ACTION_DRAG_EXITED:
					break;
				case DragEvent.ACTION_DROP:
					int x, y;
					boolean flag;
					
					if(event.getX() > 0 && event.getY() > 0 && (event.getX() + drag_view.getWidth()) < 1000 && (event.getY() + drag_view.getHeight()) < 1000){						

						/////////////////////////////calibration////////////////////////////////						
						x = Math.round(event.getX()/70)-1;
						y = Math.round(event.getY()/70)-1;					

						if(x<0) x=0;
						else if(x>9) x=9;
						else if(y<0) y=0;
						else if(y>9) y=9;							
						
						if(tag.equals("1") && x-->6)	x=6;
						else if((tag.equals("2") || tag.equals("3")) && x-->7)	x=7;
						else if((tag.equals("4") || tag.equals("5") || tag.equals("6")) && x>8)	x=8;
						
						if(tag.equals("1v") && y-->6)	y=6;
						else if((tag.equals("2v") || tag.equals("3v")) && y-->7)	y=7;
						else if((tag.equals("4v") || tag.equals("5v") || tag.equals("6v")) && y>8)	y=8;
						//////////////////////////////////////////////////////////////////////////
						
						if(tag.endsWith("v"))
							flag = check_space(y, x, Integer.parseInt(tag.substring(0, tag.length()-1)), false, info, true);
						else
							flag = check_space(y, x, Integer.parseInt(tag.substring(0, tag.length())), true, info, true);
						
						if(flag){
							RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) drag_view.getLayoutParams();			
							
							param.leftMargin = (int) (64 * (x+1) + 55);
							param.topMargin = (int) (64 * (y+1) + 225);
							
							layout.removeView(drag_view);
							layout.addView(drag_view, param);
						}
						else{
							return false;
						}
					}
					
					break;
				case DragEvent.ACTION_DRAG_ENDED:
					break;
				default:
					break;
				}
				
				for(int i=0; i<10; i++){
					Log.e("test", info[i][0] + " " + info[i][1] + " " + info[i][2] + " " + info[i][3] + " " + info[i][4]
					+ " " + info[i][5] + " " + info[i][6] + " " + info[i][7] + " " + info[i][8] + " " + info[i][9]);
				}
				
				return true;
			}
		});
    }
       
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		tag = v.getTag().toString();
		drag_view = (ImageView) v;
		ClipData dragData = ClipData.newPlainText("TAG", tag);
		
		View.DragShadowBuilder myShadow;
		
		switch(tag){				
			case "1":
				myShadow = new View.DragShadowBuilder(ship4_1);
				ship4_1.setImageResource(R.drawable.ship_4);
				break;
				
			case "1v":
				myShadow = new View.DragShadowBuilder(ship4_1);
				ship4_1.setImageResource(R.drawable.ship_4v);
				break;
				
			case "2":
				myShadow = new View.DragShadowBuilder(ship3_1);
				ship3_1.setImageResource(R.drawable.ship_3);
				break;
				
			case "2v":
				myShadow = new View.DragShadowBuilder(ship3_1);
				ship3_1.setImageResource(R.drawable.ship_3v);
				break;
						
			case "3":
				myShadow = new View.DragShadowBuilder(ship3_2);
				ship3_2.setImageResource(R.drawable.ship_3);
				break;
				
			case "3v":
				myShadow = new View.DragShadowBuilder(ship3_2);
				ship3_2.setImageResource(R.drawable.ship_3v);
				break;
				
			case "4":
				myShadow = new View.DragShadowBuilder(ship2_1);
				ship2_1.setImageResource(R.drawable.ship_2);
				break;
				
			case "4v":
				myShadow = new View.DragShadowBuilder(ship2_1);
				ship2_1.setImageResource(R.drawable.ship_2v);
				break;
			
			case "5":
				myShadow = new View.DragShadowBuilder(ship2_2);
				ship2_2.setImageResource(R.drawable.ship_2);
				break;
				
			case "5v":
				myShadow = new View.DragShadowBuilder(ship2_2);
				ship2_2.setImageResource(R.drawable.ship_2v);
				break;
			
			case "6":
				myShadow = new View.DragShadowBuilder(ship2_3);
				ship2_3.setImageResource(R.drawable.ship_2);
				break;
				
			case "6v":
				myShadow = new View.DragShadowBuilder(ship2_3);
				ship2_3.setImageResource(R.drawable.ship_2v);
				break;
				
			case "7":
				myShadow = new View.DragShadowBuilder(ship1_1);
				ship1_1.setImageResource(R.drawable.ship_1);
				break;
				
			case "8":
				myShadow = new View.DragShadowBuilder(ship1_2);
				ship1_2.setImageResource(R.drawable.ship_1);
				break;
				
			case "9":
				myShadow = new View.DragShadowBuilder(ship1_3);
				ship1_3.setImageResource(R.drawable.ship_1);
				break;
			
			default:
				myShadow = new View.DragShadowBuilder(ship1_4);
				ship1_4.setImageResource(R.drawable.ship_1);
		}
		
		v.startDrag(dragData, myShadow, null, 0);
				
		return true;
	}
	
	public boolean check_space(int x, int y, int id, boolean flag, int info[][], boolean count_flag) {			
		switch(id){
			case 1:
				if(flag){
					for(int i=-1;i<5;i++){
						for(int j=-1;j<2;j++){
							if(x+j<0 || y+i<0 || x+j>9 || y+i>9){
								if(j==0 && i>=0 && i<=3)
									return false;
								else
									continue;
							}
							else if(info[x+j][y+i]!=0 && info[x+j][y+i]!=id)
								return false;
						}
					}
				}
				else{
					for(int i=-1;i<2;i++){
						for(int j=-1;j<5;j++){
							if(x+j<0 || y+i<0 || x+j>9 || y+i>9){
								if(i==0 && j>=0 && j<=3)
									return false;
								else
									continue;
							}
							else if(info[x+j][y+i]!=0 && info[x+j][y+i]!=id)
								return false;
						}
					}
				}
			
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						if (info[i][j] == id){
							info[i][j] = 0;
							count_flag = false;
						}
					}
				}
				
				for(int i=0;i<4;i++){
					if(flag)
						info[x][y+i]=id;
					else
						info[x+i][y]=id;
				}
					
				break;
				
			case 2:	case 3:
				if(flag){
					for(int i=-1;i<4;i++){
						for(int j=-1;j<2;j++){
							if(x+j<0 || y+i<0 || x+j>9 || y+i>9){
								if(j==0 && i>=0 && i<=2)
									return false;
								else
									continue;
							}
							else if(info[x+j][y+i]!=0 && info[x+j][y+i]!=id)
								return false;
						}
					}
				}
				else{
					for(int i=-1;i<2;i++){
						for(int j=-1;j<4;j++){
							if(x+j<0 || y+i<0 || x+j>9 || y+i>9){
								if(i==0 && j>=0 && j<=2)
									return false;
								else
									continue;
							}
							else if(info[x+j][y+i]!=0 && info[x+j][y+i]!=id)
								return false;
						}
					}
				}
				
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						if (info[i][j] == id){
							info[i][j] = 0;
							count_flag = false;
						}
					}	
				}
				
				for(int i=0;i<3;i++){
					if(flag)
						info[x][y+i]=id;
					else
						info[x+i][y]=id;
				}
				
				break;
				
			case 4:	case 5:	case 6:
				if(flag){
					for(int i=-1;i<3;i++){
						for(int j=-1;j<2;j++){
							if(x+j<0 || y+i<0 || x+j>9 || y+i>9){
								if(j==0 && i>=0 && i<=1)
									return false;
								else
									continue;
							}
							else if(info[x+j][y+i]!=0 && info[x+j][y+i]!=id)
								return false;
						}
					}
				}
				else{
					for(int i=-1;i<2;i++){
						for(int j=-1;j<3;j++){
							if(x+j<0 || y+i<0 || x+j>9 || y+i>9){
								if(i==0 && j>=0 && j<=1)
									return false;
								else
									continue;
							}
							else if(info[x+j][y+i]!=0 && info[x+j][y+i]!=id)
								return false;
						}
					}
				}
				
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						if (info[i][j] == id){
							info[i][j] = 0;
							count_flag = false;
						}
					}
				}
				
				for(int i=0;i<2;i++){
					if(flag)
						info[x][y+i]=id;
					else
						info[x+i][y]=id;
				}
				
				break;
				
			case 7:	case 8:	case 9:	case 10:
				for(int i=-1;i<2;i++){
					for(int j=-1;j<2;j++){
						if(x+j<0 || y+i<0 || x+j>9 || y+i>9)
							continue;
						else if(info[x+j][y+i]!=0 && info[x+j][y+i]!=id)
							return false;
					}
				}
				
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						if (info[i][j] == id){
							info[i][j] = 0;
							count_flag = false;
						}
					}
				}
				
				info[x][y]=id;
				
				break;
				
			default:
				return false;
		}
		
		if(count_flag)
			count++;
		
		return true;
	}
	
	public void setInfoToView(int[][] info) {
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.setting);
		boolean set[] = { true, true, true, true, true, true, true, true, true, true };
		
		for(int i=0; i<10; i++){
			for(int j=0; j<10; j++){
				if(info[i][j]!=0 && set[info[i][j]-1]){
					ImageView view = new ImageView(getApplicationContext());
					RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);			
					
					if(i<9 && info[i][j] == info[i+1][j]){
						switch(info[i][j]){
							case 1:
								view.setImageResource(R.drawable.ship_4v);
								ship4_1 = view;
								ship4_1.setTag("1v");	ship4_1.setOnTouchListener(this);
								break;
							case 2:
								view.setImageResource(R.drawable.ship_3v);
								ship3_1 = view;
								ship3_1.setTag("2v");	ship3_1.setOnTouchListener(this);
						        break;
							case 3:
								view.setImageResource(R.drawable.ship_3v);
								ship3_2 = view;
								ship3_2.setTag("3v");	ship3_2.setOnTouchListener(this);
								break;
							case 4:
								view.setImageResource(R.drawable.ship_2v);
								ship2_1 = view;
								ship2_1.setTag("4v");	ship2_1.setOnTouchListener(this);
								break;
							case 5:
								view.setImageResource(R.drawable.ship_2v);
								ship2_2 = view;
								ship2_2.setTag("5v");	ship2_2.setOnTouchListener(this);
								break;
							case 6:
								view.setImageResource(R.drawable.ship_2v);
								ship2_3 = view;
								ship2_3.setTag("6v");	ship2_3.setOnTouchListener(this);
								break;
						}
					}
					else if(j<9 && info[i][j] == info[i][j+1]){
						switch(info[i][j]){
							case 1:
								view.setImageResource(R.drawable.ship_4);
								ship4_1 = view;
								ship4_1.setTag("1");	ship4_1.setOnTouchListener(this);
								break;
							case 2:
								view.setImageResource(R.drawable.ship_3);
								ship3_1 = view;
								ship3_1.setTag("2");	ship3_1.setOnTouchListener(this);
								break;
							case 3:
								view.setImageResource(R.drawable.ship_3);
								ship3_2 = view;
								ship3_2.setTag("3");	ship3_2.setOnTouchListener(this);
								break;
							case 4:
								view.setImageResource(R.drawable.ship_2);
								ship2_1 = view;
								ship2_1.setTag("4");	ship2_1.setOnTouchListener(this);
								break;
							case 5:
								view.setImageResource(R.drawable.ship_2);
								ship2_2 = view;
								ship2_2.setTag("5");	ship2_2.setOnTouchListener(this);
								break;
							case 6:
								view.setImageResource(R.drawable.ship_2);
								ship2_3 = view;
								ship2_3.setTag("6");	ship2_3.setOnTouchListener(this);
								break;
						}
					}
					else{
						switch(info[i][j]){
							case 7:
								view.setImageResource(R.drawable.ship_1);
								ship1_1 = view;
								ship1_1.setTag("7");	ship1_1.setOnTouchListener(this);
								break;
							case 8:
								view.setImageResource(R.drawable.ship_1);
								ship1_2 = view;
								ship1_2.setTag("8");	ship1_2.setOnTouchListener(this);
						        break;
							case 9:
								view.setImageResource(R.drawable.ship_1);
								ship1_3 = view;
								ship1_3.setTag("9");	ship1_3.setOnTouchListener(this);
								break;
							case 10:
								view.setImageResource(R.drawable.ship_1);
								ship1_4 = view;
								ship1_4.setTag("10");	ship1_4.setOnTouchListener(this);
								break;
						}
					}
					
					param.leftMargin = (int) (64 * (j+1) + 55);
					param.topMargin = (int) (64 * (i+1) + 225);
					
					view.setLayoutParams(param);
					layout.addView(view);
					set[info[i][j]-1] = false;
				}
			}
		}
	}
}
