package com.aceteam.game.battleship;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.turnbased.OnTurnBasedMatchUpdateReceivedListener;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;

public class Match extends Activity implements OnInvitationReceivedListener,
		OnTouchListener, OnTurnBasedMatchUpdateReceivedListener {

	final static int RC_SELECT_PLAYERS = 10000;
	final static int RC_LOOK_AT_MATCHES = 10001;

	static int[][] info = new int[10][10];
	static int[][] opponent_info = new int[10][10];
	int my_array[] = { 4, 3, 3, 2, 2, 2, 1, 1, 1, 1 }, op_array[] = { 4, 3, 3, 2, 2, 2, 1, 1, 1, 1 };
	int my_count = 10, op_count = 10;

	public TextView mXCoordinate;
	public TextView mYCoordinate;
	int x_touch;
	int y_touch;

	static int count = 0;

	ImageView ship1_1, ship1_2, ship1_3, ship1_4, ship2_1, ship2_2, ship2_3, ship3_1, ship3_2, ship4_1;
	String tag;
	View drag_view;
	Vibrator vibrator;

	public static final String TAG = "BattleShip";
	final static int TOAST_DELAY = Toast.LENGTH_SHORT;

	public boolean isDoingTurn = false;
	public boolean match_started = false;
	public boolean received_opponent = false;
	public boolean first_turn = true;
	public boolean invitated = false;
	public boolean waiting_opponent = false;
	public boolean mine = false;
	public boolean gamestart = false;
	public int counter = 0;

	private TurnBasedMatch mTurnBasedMatch;
	public TurnBasedMatch mMatch;

	public SkeletonTurn mTurnData;

	private AlertDialog mAlertDialog;

	public TextView mDataView;

	public ProgressDialog dialog;
	RelativeLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.play_online);

		Games.Invitations.registerInvitationListener(
				GameActivity.mGoogleApiClient, this);
		Games.TurnBasedMultiplayer.registerMatchUpdateListener(
				GameActivity.mGoogleApiClient, this);

		mDataView = ((TextView) findViewById(R.id.o_data_view));

		MainActivity.array.add(this);

		overridePendingTransition(R.anim.left, R.anim.right);
		layout = (RelativeLayout) findViewById(R.id.o_setting);
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

		// ////////////status initialize//////////////
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++)
				info[i][j] = 0;
		}

		count = 0;
		// ///////////////////////////////////////////

		final ImageButton previous = (ImageButton) findViewById(R.id.o_previous_button);
		final ImageButton auto = (ImageButton) findViewById(R.id.o_auto_button);
		final ImageButton next = (ImageButton) findViewById(R.id.o_next_button);
		final ImageButton myturn = (ImageButton) findViewById(R.id.o_myturn);
		final ImageView yourturn = (ImageView) findViewById(R.id.o_yourturn);
		final ImageButton invitation = (ImageButton) findViewById(R.id.o_findOpponentButton);
		final ImageButton receive = (ImageButton) findViewById(R.id.o_receiveInvitation);
		final ImageButton rotate = (ImageButton) findViewById(R.id.o_rotate_button);
		final TableLayout tbl_board = (TableLayout) findViewById(R.id.o_tblBoard);
		final TableLayout tbl_board2 = (TableLayout) findViewById(R.id.o_tblBoard2);

		ship4_1 = (ImageView) findViewById(R.id.o_ship4_1);
		ship3_1 = (ImageView) findViewById(R.id.o_ship3_1);
		ship3_2 = (ImageView) findViewById(R.id.o_ship3_2);
		ship2_1 = (ImageView) findViewById(R.id.o_ship2_1);
		ship2_2 = (ImageView) findViewById(R.id.o_ship2_2);
		ship2_3 = (ImageView) findViewById(R.id.o_ship2_3);
		ship1_1 = (ImageView) findViewById(R.id.o_ship1_1);
		ship1_2 = (ImageView) findViewById(R.id.o_ship1_2);
		ship1_3 = (ImageView) findViewById(R.id.o_ship1_3);
		ship1_4 = (ImageView) findViewById(R.id.o_ship1_4);

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

				if (tag == null)
					return;
				else if (tag.endsWith("v")) {
					id = Integer.parseInt(tag.substring(0, tag.length() - 1));
					direction = true;
				} else {
					id = Integer.parseInt(tag.substring(0, tag.length()));
					direction = false;
				}

				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						if (info[i][j] == id) {
							flag = check_space(i, j, id, direction, info, false);
							i = 10;
							break;
						}
					}
				}

				if (flag) {
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
				layout.removeView(ship1_1);
				layout.removeView(ship2_2);
				layout.removeView(ship1_2);
				layout.removeView(ship2_3);
				layout.removeView(ship1_3);
				layout.removeView(ship3_1);
				layout.removeView(ship1_4);
				layout.removeView(ship3_2);
				layout.removeView(ship2_1);
				layout.removeView(ship4_1);

				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++)
						info[i][j] = 0;
				}

				int sample1[][] = new int[][] {
						{ 0, 7, 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 6, 0, 0, 0, 0, 0 },
						{ 0, 0, 8, 0, 6, 0, 0, 0, 2, 0 },
						{ 9, 0, 0, 0, 0, 0, 0, 0, 2, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 2, 0 },
						{ 0, 0, 5, 5, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 1, 0, 0, 3 },
						{ 0, 4, 0, 0, 0, 0, 1, 0, 0, 3 },
						{ 0, 4, 0, 0, 0, 0, 1, 0, 0, 3 },
						{ 0, 0, 0, 10, 0, 0, 1, 0, 0, 0 } };

				int sample2[][] = new int[][] {
						{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 2, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 2, 0 },
						{ 7, 0, 0, 0, 3, 3, 3, 0, 2, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 8, 0, 0, 0, 0, 1, 1, 1, 1 },
						{ 0, 0, 0, 0, 9, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 4, 4, 0, 10, 0, 5, 5, 0, 6, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 6, 0 } };

				int sample3[][] = new int[][] {
						{ 0, 0, 4, 4, 0, 0, 0, 2, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 2, 0, 7 },
						{ 0, 0, 5, 5, 0, 8, 0, 2, 0, 0 },
						{ 9, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 6, 6, 0, 0, 0, 0 },
						{ 0, 1, 0, 0, 0, 0, 0, 0, 3, 0 },
						{ 0, 1, 0, 0, 0, 0, 0, 0, 3, 0 },
						{ 0, 1, 0, 0, 0, 0, 0, 0, 3, 0 },
						{ 0, 1, 0, 0, 0, 10, 0, 0, 0, 0 } };

				int sample4[][] = new int[][] {
						{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 7 },
						{ 2, 2, 2, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
						{ 0, 0, 10, 0, 3, 3, 3, 0, 0, 1 },
						{ 9, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
						{ 0, 0, 0, 0, 0, 4, 0, 0, 0, 0 },
						{ 0, 6, 0, 0, 0, 4, 0, 0, 0, 0 },
						{ 0, 6, 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 5, 5, 0, 0, 8, 0 } };

				int sample5[][] = new int[][] {
						{ 7, 0, 0, 0, 0, 0, 9, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 8, 0, 0, 0, 0, 0, 0, 4, 4, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 2, 2, 2, 0, 0, 0, 0, 0, 5, 5 },
						{ 0, 0, 0, 0, 10, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 1, 1, 1, 1, 0, 0, 0, 6, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 6, 0 },
						{ 0, 0, 0, 3, 3, 3, 0, 0, 0, 0 } };

				int sample6[][] = new int[][] {
						{ 0, 7, 0, 0, 0, 0, 0, 0, 6, 0 },
						{ 0, 0, 0, 2, 2, 2, 0, 0, 6, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 4, 0, 0, 5, 0, 0, 0, 0, 0, 8 },
						{ 4, 0, 0, 5, 0, 10, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 },
						{ 9, 0, 0, 0, 0, 0, 0, 0, 1, 0 },
						{ 0, 0, 0, 3, 3, 3, 0, 0, 1, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 } };

				int sample7[][] = new int[][] {
						{ 0, 0, 0, 0, 0, 0, 0, 0, 10, 0 },
						{ 0, 7, 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 2, 2, 2, 0, 0 },
						{ 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 1, 0, 0, 0, 0, 6, 6, 0 },
						{ 0, 0, 1, 0, 0, 5, 0, 0, 0, 0 },
						{ 0, 0, 1, 0, 0, 5, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 9, 0 },
						{ 0, 4, 4, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 8, 0, 0, 0, 3, 3, 3 } };

				int sample8[][] = new int[][] {
						{ 0, 0, 0, 0, 0, 0, 0, 0, 7, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 1, 1, 1, 1, 0, 0, 2, 2, 2 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 10, 0, 0, 0, 4, 4, 0, 0, 5 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 5 },
						{ 0, 3, 3, 3, 0, 0, 8, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 6, 6, 0, 9 } };

				int sample9[][] = new int[][] {
						{ 10, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 2, 0, 0, 8 },
						{ 0, 9, 0, 0, 0, 0, 2, 0, 0, 0 },
						{ 0, 0, 0, 6, 0, 0, 2, 0, 7, 0 },
						{ 0, 3, 0, 6, 0, 0, 0, 0, 0, 0 },
						{ 0, 3, 0, 0, 0, 0, 0, 0, 1, 0 },
						{ 0, 3, 0, 0, 0, 4, 4, 0, 1, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 },
						{ 0, 0, 0, 0, 5, 5, 0, 0, 1, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

				int sample10[][] = new int[][] {
						{ 0, 4, 0, 7, 0, 0, 0, 0, 0, 0 },
						{ 0, 4, 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 3, 0, 0, 2, 0 },
						{ 0, 0, 0, 0, 0, 3, 0, 0, 2, 0 },
						{ 0, 0, 0, 0, 0, 3, 0, 0, 2, 0 },
						{ 0, 0, 5, 5, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 9, 0, 8, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 6, 0, 0 },
						{ 1, 1, 1, 1, 0, 0, 0, 6, 0, 10 } };

				switch ((int) (Math.random() * 10)) {
					case 1:
						info = sample1;
						break;
					case 2:
						info = sample2;
						break;
					case 3:
						info = sample3;
						break;
					case 4:
						info = sample4;
						break;
					case 5:
						info = sample5;
						break;
					case 6:
						info = sample6;
						break;
					case 7:
						info = sample7;
						break;
					case 8:
						info = sample8;
						break;
					case 9:
						info = sample9;
						break;
					default:
						info = sample10;
				}

				count = 10;
			
				setInfoToView(info, true);
				setShipListener();
			}
		});

		previous.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch (arg1.getAction()) {
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
				switch (arg1.getAction()) {
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

		next.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch (arg1.getAction()) {
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

		invitation.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch (arg1.getAction()) {
				case MotionEvent.ACTION_DOWN:
					invitation.setImageResource(R.drawable.invitation_on);
					break;
				case MotionEvent.ACTION_UP:
					invitation.setImageResource(R.drawable.invitation);
					break;
				}
				return false;
			}
		});

		receive.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch (arg1.getAction()) {
				case MotionEvent.ACTION_DOWN:
					receive.setImageResource(R.drawable.receive_on);
					break;
				case MotionEvent.ACTION_UP:
					receive.setImageResource(R.drawable.receive);
					break;
				}
				return false;
			}
		});

		ship4_1.setTag("1");
		ship3_1.setTag("2");
		ship3_2.setTag("3");
		ship2_1.setTag("4");
		ship2_2.setTag("5");
		ship2_3.setTag("6");
		ship1_1.setTag("7");
		ship1_2.setTag("8");
		ship1_3.setTag("9");
		ship1_4.setTag("10");
		
		setShipListener();

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
					int x,
					y;
					boolean flag;

					if (event.getX() > 0 && event.getY() > 0 && (event.getX() + drag_view.getWidth()) < 1000 && (event.getY() + drag_view.getHeight()) < 1000) {

						/////////////////////////////calibration////////////////////////////////						
						x = Math.round(event.getX()/65)-1;
						y = Math.round(event.getY()/65)-1;					
	
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

						if (tag.endsWith("v"))
							flag = check_space(y, x, Integer.parseInt(tag.substring(0, tag.length() - 1)), false, info, true);
						else
							flag = check_space(y, x, Integer.parseInt(tag.substring(0, tag.length())), true, info, true);

						if (flag) {
							RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) drag_view.getLayoutParams();

							param.leftMargin = (int) (64 * (x + 1) + 55);
							param.topMargin = (int) (64 * (y + 1) + 225);
							
							layout.removeView(drag_view);
							layout.addView(drag_view, param);
						} else {
							return false;
						}
					}

					break;
				case DragEvent.ACTION_DRAG_ENDED:
					break;
				default:
					break;
				}

				for (int i = 0; i < 10; i++) {
					Log.e("test", info[i][0] + " " + info[i][1] + " "
							+ info[i][2] + " " + info[i][3] + " " + info[i][4]
							+ " " + info[i][5] + " " + info[i][6] + " "
							+ info[i][7] + " " + info[i][8] + " " + info[i][9]);
				}

				return true;
			}
		});

		tbl_board2.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();

				switch (action) {
				case MotionEvent.ACTION_UP:
					int x,
					y;

					if (true) {
						x = Math.round(event.getX() / 65) - 1;
						y = Math.round(event.getY() / 65) - 1;

						if (x < 0)
							x = 0;
						else if (x > 9)
							x = 9;
						else if (y < 0)
							y = 0;
						else if (y > 9)
							y = 9;
						
						x_touch = x;
						y_touch = y;
					}

					if (count == 10 && match_started && mine == true) {
						String nextParticipantId = getNextParticipantId();
						boolean flag = false;

						// mTurnData.x_coordinate =
						// Integer.parseInt(mXCoordinate.getText().toString());
						// mTurnData.y_coordinate =
						// Integer.parseInt(mYCoordinate.getText().toString());

						if (opponent_info[y_touch][x_touch] > 10) { 
							Toast.makeText(getApplicationContext(), "이미 선택된 필드입니다", Toast.LENGTH_SHORT).show();
							return false;
						}

						else if (opponent_info[y_touch][x_touch] > 0) {
							vibrator.vibrate(500);
							
							switch (opponent_info[y_touch][x_touch]) {
								case 1:
									if (--op_array[0] == 0) {
										setShipBoundary(1);
										ship4_1.setVisibility(View.VISIBLE);
										op_count--;
									}
									break;
								case 2:
									if (--op_array[1] == 0) {
										setShipBoundary(2);
										ship3_1.setVisibility(View.VISIBLE);
										op_count--;
									}
									break;
								case 3:
									if (--op_array[2] == 0) {
										setShipBoundary(3);
										ship3_2.setVisibility(View.VISIBLE);
										op_count--;
									}
									break;
								case 4:
									if (--op_array[3] == 0) {
										setShipBoundary(4);
										ship2_1.setVisibility(View.VISIBLE);
										op_count--;
									}
									break;
								case 5:
									if (--op_array[4] == 0) {
										setShipBoundary(5);
										ship2_2.setVisibility(View.VISIBLE);
										op_count--;
									}
									break;
								case 6:
									if (--op_array[5] == 0) {
										setShipBoundary(6);
										ship2_3.setVisibility(View.VISIBLE);
										op_count--;
									}
									break;
								case 7:
									if (--op_array[6] == 0) {
										setShipBoundary(7);
										ship1_1.setVisibility(View.VISIBLE);
										op_count--;
									}
									break;
								case 8:
									if (--op_array[7] == 0) {
										setShipBoundary(8);
										ship1_2.setVisibility(View.VISIBLE);
										op_count--;
									}
									break;
								case 9:
									if (--op_array[8] == 0) {
										setShipBoundary(9);
										ship1_3.setVisibility(View.VISIBLE);
										op_count--;
									}
									break;
								case 10:
									if (--op_array[9] == 0) {
										setShipBoundary(10);
										ship1_4.setVisibility(View.VISIBLE);
										op_count--;
									}
									break;
								default:
							}
							
							opponent_info[y_touch][x_touch] = 11;
							setInfoToView(opponent_info, false);
						} else {
							opponent_info[y_touch][x_touch] = 12;
							flag = true;
						}

						if (op_count == 0) {
							Intent victory = new Intent(Match.this,Victory.class);
		                    startActivity(victory);
						}
						
						setInfoToView(opponent_info, false);
						
						mTurnData.x_coordinate[counter] = x_touch; 
						mTurnData.y_coordinate[counter] = y_touch;
						mTurnData.count = counter;
						counter++;
						
						if(flag){
							counter = 0;
							// Create the next turn
							for (int i = 0; i < 10; i++)
								for (int j = 0; j < 10; j++)
									mTurnData.boarddata[i][j] = info[i][j];
	
							mTurnData.data = mDataView.getText().toString();
	
							findViewById(R.id.o_myturn).setVisibility(View.GONE);
							findViewById(R.id.o_yourturn).setVisibility(View.VISIBLE);
							mine = false;
							
							Games.TurnBasedMultiplayer
									.takeTurn(GameActivity.mGoogleApiClient,
											mMatch.getMatchId(),
											mTurnData.persist(), nextParticipantId)
									.setResultCallback(
											new ResultCallback<TurnBasedMultiplayer.UpdateMatchResult>() {
												@Override
												public void onResult(
														TurnBasedMultiplayer.UpdateMatchResult result) {
													processResult(result);
												}
											});
	
							mTurnData = null;
						}

						// Intent play_online_intent = new Intent(Match.this,
						// Play_online.class);
						// startActivity(play_online_intent);
					} else
						Toast.makeText(getApplicationContext(), "니 차례가 아닙니다",
								Toast.LENGTH_SHORT).show();

					break;

				default:
					break;
				}

				return true;
			}
		});

		invitation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = Games.TurnBasedMultiplayer
						.getSelectOpponentsIntent(
								GameActivity.mGoogleApiClient, 1, 1, true);
				startActivityForResult(intent, RC_SELECT_PLAYERS);

			}
		});

		receive.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				findViewById(R.id.o_myturn).setVisibility(View.GONE);
				findViewById(R.id.o_yourturn).setVisibility(View.VISIBLE);
				mine = false;
				Intent intent = Games.TurnBasedMultiplayer
						.getInboxIntent(GameActivity.mGoogleApiClient);
				startActivityForResult(intent, RC_LOOK_AT_MATCHES);

			}
		});

		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (count == 10 && match_started) {
					unsetShipListener();
					String nextParticipantId = getNextParticipantId();

					// mTurnData.x_coordinate =
					// Integer.parseInt(mXCoordinate.getText().toString());
					// mTurnData.y_coordinate =
					// Integer.parseInt(mYCoordinate.getText().toString());

					mTurnData.x_coordinate[0] = x_touch;
					mTurnData.y_coordinate[0] = y_touch;

					// Create the next turn
					for (int i = 0; i < 10; i++)
						for (int j = 0; j < 10; j++)
							mTurnData.boarddata[i][j] = info[i][j];

					mTurnData.data = mDataView.getText().toString();
					gamestart = true;
					setInfoToView(opponent_info, false);

					Games.TurnBasedMultiplayer
							.takeTurn(GameActivity.mGoogleApiClient,
									mMatch.getMatchId(), mTurnData.persist(),
									nextParticipantId)
							.setResultCallback(
									new ResultCallback<TurnBasedMultiplayer.UpdateMatchResult>() {
										@Override
										public void onResult(
												TurnBasedMultiplayer.UpdateMatchResult result) {
											processResult(result);
										}
									});

					mTurnData = null;

					// Intent play_online_intent = new Intent(Match.this,
					// Play_online.class);
					// startActivity(play_online_intent);
				} else
					Toast.makeText(getApplicationContext(),
							"먼저 게임을 시작 한 후 배 10개를 모두 배치하세요", Toast.LENGTH_SHORT)
							.show();
			}
		});

		myturn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (count == 10
						&& match_started
						&& mMatch.getTurnStatus() == TurnBasedMatch.MATCH_TURN_STATUS_MY_TURN) {
					String nextParticipantId = getNextParticipantId();

					mTurnData.x_coordinate[0] = x_touch;
					mTurnData.y_coordinate[0] = y_touch;

					// Create the next turn
					for (int i = 0; i < 10; i++)
						for (int j = 0; j < 10; j++)
							mTurnData.boarddata[i][j] = info[i][j];

					mTurnData.data = mDataView.getText().toString();

					findViewById(R.id.o_myturn).setVisibility(View.GONE);
					findViewById(R.id.o_yourturn).setVisibility(View.VISIBLE);
					mine = false;

					Games.TurnBasedMultiplayer
							.takeTurn(GameActivity.mGoogleApiClient,
									mMatch.getMatchId(), mTurnData.persist(),
									nextParticipantId)
							.setResultCallback(
									new ResultCallback<TurnBasedMultiplayer.UpdateMatchResult>() {
										@Override
										public void onResult(
												TurnBasedMultiplayer.UpdateMatchResult result) {
											processResult(result);
										}
									});

					mTurnData = null;

					// Intent play_online_intent = new Intent(Match.this,
					// Play_online.class);
					// startActivity(play_online_intent);
				} else
					Toast.makeText(getApplicationContext(),
							"먼저 게임을 시작 한 후 배 10개를 모두 배치하세요", Toast.LENGTH_SHORT)
							.show();
			}
		});
	}
	
	@Override
	   public void onBackPressed() {
	      // TODO Auto-generated method stub
	      super.onBackPressed();
	      for (int i = 0; i < 10; i++) {
	         for (int j = 0; j < 10; j++){
	            info[i][j] = 0;
	            opponent_info[i][j] = 0;
	         }
	      }
	      finish();
	   }

	public void setShipListener() {
		ship4_1.setOnTouchListener(this);
		ship3_1.setOnTouchListener(this);
		ship3_2.setOnTouchListener(this);
		ship2_1.setOnTouchListener(this);
		ship2_2.setOnTouchListener(this);
		ship2_3.setOnTouchListener(this);
		ship1_1.setOnTouchListener(this);
		ship1_2.setOnTouchListener(this);
		ship1_3.setOnTouchListener(this);
		ship1_4.setOnTouchListener(this);
	}
	
	public void unsetShipListener() {
		ship4_1.setOnTouchListener(null);
		ship3_1.setOnTouchListener(null);
		ship3_2.setOnTouchListener(null);
		ship2_1.setOnTouchListener(null);
		ship2_2.setOnTouchListener(null);
		ship2_3.setOnTouchListener(null);
		ship1_1.setOnTouchListener(null);
		ship1_2.setOnTouchListener(null);
		ship1_3.setOnTouchListener(null);
		ship1_4.setOnTouchListener(null);
	}
	
	public void setInfoToView(int[][] info, boolean o) {
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.o_setting);
		boolean set[] = { true, true, true, true, true, true, true, true, true, true, true, true, true };
		int id;

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (info[i][j] != 0 && set[info[i][j] - 1]) {
					ImageView view = new ImageView(getApplicationContext());
					RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					id = info[i][j];

					if (id > 10) {						
						switch (id) {
						case 11:
							view.setImageResource(R.drawable.tile_11);
							break;
						case 12:
							view.setImageResource(R.drawable.tile_12);
							break;
						}
					} else if (i < 9 && info[i][j] == info[i + 1][j]) {
						
						while(i>0 && info[i-1][j]==11)
							i--;
						
						switch (id) {
							case 1:
								view.setImageResource(R.drawable.ship_4v);
								ship4_1 = view;
								ship4_1.setTag("1v");
								break;
							case 2:
								view.setImageResource(R.drawable.ship_3v);
								ship3_1 = view;
								ship3_1.setTag("2v");
								break;
							case 3:
								view.setImageResource(R.drawable.ship_3v);
								ship3_2 = view;
								ship3_2.setTag("3v");
								break;
							case 4:
								view.setImageResource(R.drawable.ship_2v);
								ship2_1 = view;
								ship2_1.setTag("4v");
								break;
							case 5:
								view.setImageResource(R.drawable.ship_2v);
								ship2_2 = view;
								ship2_2.setTag("5v");
								break;
							case 6:
								view.setImageResource(R.drawable.ship_2v);
								ship2_3 = view;
								ship2_3.setTag("6v");
								break;
						}
					} else if (j < 9 && info[i][j] == info[i][j + 1]) {
						
						while(j>0 && info[i][j-1]==11)
							j--;
												
						switch (id) {
							case 1:
								view.setImageResource(R.drawable.ship_4);
								ship4_1 = view;
								ship4_1.setTag("1");
								break;
							case 2:
								view.setImageResource(R.drawable.ship_3);
								ship3_1 = view;
								ship3_1.setTag("2");
								break;
							case 3:
								view.setImageResource(R.drawable.ship_3);
								ship3_2 = view;
								ship3_2.setTag("3");
								break;
							case 4:
								view.setImageResource(R.drawable.ship_2);
								ship2_1 = view;
								ship2_1.setTag("4");
								break;
							case 5:
								view.setImageResource(R.drawable.ship_2);
								ship2_2 = view;
								ship2_2.setTag("5");
								break;
							case 6:
								view.setImageResource(R.drawable.ship_2);
								ship2_3 = view;
								ship2_3.setTag("6");
								break;
						}
					} else {
						switch (id) {
							case 7:
								view.setImageResource(R.drawable.ship_1);
								ship1_1 = view;
								ship1_1.setTag("7");
								break;
							case 8:
								view.setImageResource(R.drawable.ship_1);
								ship1_2 = view;
								ship1_2.setTag("8");
								break;
							case 9:
								view.setImageResource(R.drawable.ship_1);
								ship1_3 = view;
								ship1_3.setTag("9");
								break;
							case 10:
								view.setImageResource(R.drawable.ship_1);
								ship1_4 = view;
								ship1_4.setTag("10");
								break;
						}
					}
					
					if (o) {
						param.leftMargin = (int) (64 * (j + 1) + 55);
						param.topMargin = (int) (64 * (i + 1) + 225);
					} else {
						param.leftMargin = (int) (64 * (j + 1) + 986);
						param.topMargin = (int) (64 * (i + 1) + 225);
								
						if(id > 10 || op_array[id-1] == 0){
							view.setVisibility(View.VISIBLE);
						}
						else{
							view.setVisibility(View.INVISIBLE);
						}
					}

					view.setLayoutParams(param);
					layout.addView(view);

					if (id < 11)
						set[id - 1] = false;
				}
			}
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		tag = v.getTag().toString();
		drag_view = v;
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

	@Override
	public void onInvitationReceived(Invitation invitation) {
		Toast.makeText(
				this,
				"An invitation has arrived from "
						+ invitation.getInviter().getDisplayName(), TOAST_DELAY)
				.show();
		Log.e("test", "Tset");
	}

	@Override
	public void onInvitationRemoved(String invitationId) {
		Toast.makeText(this, "An invitation was removed.", TOAST_DELAY).show();
	}

	@Override
	public void onTurnBasedMatchReceived(TurnBasedMatch match) {
		Log.e("match", "match");

		if (waiting_opponent && match.getTurnStatus() == TurnBasedMatch.MATCH_TURN_STATUS_MY_TURN) {
			waiting_opponent = false;
			dialog.dismiss();
			gamestart = true;
			findViewById(R.id.o_auto_button).setVisibility(View.GONE);
			findViewById(R.id.o_findOpponentButton).setVisibility(View.GONE);
			findViewById(R.id.o_rotate_button).setVisibility(View.GONE);
			findViewById(R.id.o_receiveInvitation).setVisibility(View.GONE);
			findViewById(R.id.o_previous_button).setVisibility(View.GONE);
			findViewById(R.id.o_next_button).setVisibility(View.GONE);
			findViewById(R.id.o_tblBoard2).setVisibility(View.VISIBLE);
			
			
			// findViewById(R.id.o_myturn).setVisibility(View.VISIBLE);

			// Intent play_online_intent = new Intent(Match.this,
			// Play_online.class);
			// startActivity(play_online_intent);
			// //
			// findViewById(R.id.findOpponentButton).setVisibility(View.GONE);
		}
		if (match != null
				&& match.getTurnStatus() == TurnBasedMatch.MATCH_TURN_STATUS_MY_TURN) {
			Log.e("asdf", "asdf");
			updateMatch(match);
		}
	}

	@Override
	public void onTurnBasedMatchRemoved(String matchId) {
		Toast.makeText(this, "A match was removed.", TOAST_DELAY).show();

	}

	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		if (request == RC_LOOK_AT_MATCHES) {
			// Returning from the 'Select Match' dialog

			if (response != Activity.RESULT_OK) {
				// user canceled
				return;
			}

			invitated = true;

			TurnBasedMatch match = data
					.getParcelableExtra(Multiplayer.EXTRA_TURN_BASED_MATCH);

			if (match != null) {
				Log.e("qwer", "qwer");
				match_started = true;
				updateMatch(match);
			}

			// Log.d(TAG, "Match = " + match);
		} else if (request == RC_SELECT_PLAYERS) {
			// Returned from 'Select players to Invite' dialog

			Log.e("please", "please");

			if (response != Activity.RESULT_OK) {
				// user canceled
				return;
			}

			// get the invitee list
			final ArrayList<String> invitees = data
					.getStringArrayListExtra(Games.EXTRA_PLAYER_IDS);

			// get automatch criteria
			Bundle autoMatchCriteria = null;

			int minAutoMatchPlayers = data.getIntExtra(
					Multiplayer.EXTRA_MIN_AUTOMATCH_PLAYERS, 0);
			int maxAutoMatchPlayers = data.getIntExtra(
					Multiplayer.EXTRA_MAX_AUTOMATCH_PLAYERS, 0);

			if (minAutoMatchPlayers > 0) {
				autoMatchCriteria = RoomConfig.createAutoMatchCriteria(
						minAutoMatchPlayers, maxAutoMatchPlayers, 0);
			} else {
				autoMatchCriteria = null;
			}

			TurnBasedMatchConfig tbmc = TurnBasedMatchConfig.builder()
					.addInvitedPlayers(invitees)
					.setAutoMatchCriteria(autoMatchCriteria).build();

			Log.e("vkdkdk", "vkvkvk");
			match_started = true;

			// Start the match
			Games.TurnBasedMultiplayer
					.createMatch(GameActivity.mGoogleApiClient, tbmc)
					.setResultCallback(
							new ResultCallback<TurnBasedMultiplayer.InitiateMatchResult>() {
								@Override
								public void onResult(
										TurnBasedMultiplayer.InitiateMatchResult result) {
									processResult(result);
								}
							});
			// showSpinner();
		}
	}

	public String getNextParticipantId() {

		String playerId = Games.Players
				.getCurrentPlayerId(GameActivity.mGoogleApiClient);
		String myParticipantId = mMatch.getParticipantId(playerId);

		ArrayList<String> participantIds = mMatch.getParticipantIds();

		int desiredIndex = -1;

		for (int i = 0; i < participantIds.size(); i++) {
			if (participantIds.get(i).equals(myParticipantId)) {
				desiredIndex = i + 1;
			}
		}

		if (desiredIndex < participantIds.size()) {
			return participantIds.get(desiredIndex);
		}

		if (mMatch.getAvailableAutoMatchSlots() <= 0) {
			// You've run out of automatch slots, so we start over.
			return participantIds.get(0);
		} else {
			// You have not yet fully automatched, so null will find a new
			// person to play against.
			return null;
		}
	}

	private void processResult(TurnBasedMultiplayer.InitiateMatchResult result) {
		TurnBasedMatch match = result.getMatch();

		if (!checkStatusCode(match, result.getStatus().getStatusCode())) {
			return;
		}

		if (match.getData() != null) {
			// This is a game that has already started, so I'll just start
			Log.e("zxcv", "zxcv");
			updateMatch(match);
			return;
		}
		Log.e("222", "2222");
		startMatch(match);
	}

	public void processResult(TurnBasedMultiplayer.UpdateMatchResult result) {
		TurnBasedMatch match = result.getMatch();
		if (!checkStatusCode(match, result.getStatus().getStatusCode())) {
			return;
		}
		if (match.canRematch()) {
			// askForRematch();
		}

		isDoingTurn = (match.getTurnStatus() == TurnBasedMatch.MATCH_TURN_STATUS_MY_TURN);
		Log.e("cvn", "cvn");

		if (!invitated && !received_opponent && !first_turn) {
			waiting_opponent = true;
			dialog = new ProgressDialog(this);
			dialog.setMessage("상대방을 기다리는 중입니다");
			dialog.setCancelable(false);
			dialog.show();
		}

		if (isDoingTurn) {
			Log.e("yuio", "yuio");
			updateMatch(match);
			return;
		}

		findViewById(R.id.o_auto_button).setVisibility(View.GONE);
		findViewById(R.id.o_findOpponentButton).setVisibility(View.GONE);
		findViewById(R.id.o_rotate_button).setVisibility(View.GONE);
		findViewById(R.id.o_receiveInvitation).setVisibility(View.GONE);
		findViewById(R.id.o_previous_button).setVisibility(View.GONE);
		findViewById(R.id.o_next_button).setVisibility(View.GONE);
		findViewById(R.id.o_tblBoard2).setVisibility(View.VISIBLE);
		// findViewById(R.id.o_myturn).setVisibility(View.VISIBLE);
	}

	// startMatch() happens in response to the createTurnBasedMatch()
	// above. This is only called on success, so we should have a
	// valid match object. We're taking this opportunity to setup the
	// game, saving our initial state. Calling takeTurn() will
	// callback to OnTurnBasedMatchUpdated(), which will show the game
	// UI.
	public void startMatch(TurnBasedMatch match) {
		mTurnData = new SkeletonTurn();
		// Some basic turn data
		mTurnData.data = "First turn";
		Log.d("works", "works");
		mMatch = match;

		String playerId = Games.Players
				.getCurrentPlayerId(GameActivity.mGoogleApiClient);
		String myParticipantId = mMatch.getParticipantId(playerId);

		Games.TurnBasedMultiplayer
				.takeTurn(GameActivity.mGoogleApiClient, match.getMatchId(),
						mTurnData.persist(), myParticipantId)
				.setResultCallback(
						new ResultCallback<TurnBasedMultiplayer.UpdateMatchResult>() {
							@Override
							public void onResult(
									TurnBasedMultiplayer.UpdateMatchResult result) {
								processResult(result);
							}
						});
	}

	public void updateMatch(TurnBasedMatch match) {
		mMatch = match;

		int status = match.getStatus();
		int turnStatus = match.getTurnStatus();

		switch (status) {
		case TurnBasedMatch.MATCH_STATUS_CANCELED:
			showWarning("Canceled!", "This game was canceled!");
			return;
		case TurnBasedMatch.MATCH_STATUS_EXPIRED:
			showWarning("Expired!", "This game is expired.  So sad!");
			return;
		case TurnBasedMatch.MATCH_STATUS_AUTO_MATCHING:
			showWarning("Waiting for auto-match...",
					"We're still waiting for an automatch partner.");
			return;
		case TurnBasedMatch.MATCH_STATUS_COMPLETE:
			if (turnStatus == TurnBasedMatch.MATCH_TURN_STATUS_COMPLETE) {
				showWarning(
						"Complete!",
						"This game is over; someone finished it, and so did you!  There is nothing to be done.");
				break;
			}

			// Note that in this state, you must still call "Finish" yourself,
			// so we allow this to continue.
			showWarning("Complete!",
					"This game is over; someone finished it!  You can only finish it now.");
		}

		// OK, it's active. Check on turn status.
		switch (turnStatus) {
		case TurnBasedMatch.MATCH_TURN_STATUS_MY_TURN:
			int[] get_x_array = new int[10]; 
			int[] get_y_array = new int[10];
			int get_x,get_y;
			int number;
			
			mTurnData = SkeletonTurn.unpersist(mMatch.getData());
			mDataView.setText(mTurnData.data);
			get_x_array = mTurnData.x_coordinate;
			get_y_array = mTurnData.y_coordinate;
			number = mTurnData.count;
						
			////////////////////////////////////////////////////////////////
		
			if ((gamestart && invitated) || (gamestart && !invitated && received_opponent)){
				
				for(int i=number; i >= 0; i-- ){
					get_x = get_x_array[i];
					get_y = get_y_array[i];
					
					Log.e("number",number + " " + get_x + " " + get_y + " " + info[get_x][get_y]);
					
					if (info[get_y][get_x] > 0) {
						vibrator.vibrate(500);
						
						switch (info[get_y][get_x]) {
							case 1:
								if (--my_array[0] == 0) {
									setShipBoundary(1);
									my_count--;
								}
								break;
							case 2:
								if (--my_array[1] == 0) {
									setShipBoundary(2);
									my_count--;
								}
								break;
							case 3:
								if (--my_array[2] == 0) {
									setShipBoundary(3);
									my_count--;
								}
								break;
							case 4:
								if (--my_array[3] == 0) {
									setShipBoundary(4);
									my_count--;
								}
								break;
							case 5:
								if (--my_array[4] == 0) {
									setShipBoundary(5);
									my_count--;
								}
								break;
							case 6:
								if (--my_array[5] == 0) {
									setShipBoundary(6);
									my_count--;
								}
								break;
							case 7:
								if (--my_array[6] == 0) {
									setShipBoundary(7);
									my_count--;
								}
								break;
							case 8:
								if (--my_array[7] == 0) {
									setShipBoundary(8);
									my_count--;
								}
								break;
							case 9:
								if (--my_array[8] == 0) {
									setShipBoundary(9);
									my_count--;
								}
								break;
							case 10:
								if (--my_array[9] == 0) {
									setShipBoundary(10);
									my_count--;
								}
								break;
							default:
						}
						
						info[get_y][get_x] = 11;
					} else {
						
							info[get_y][get_x] = 12;
						}
					}
			}
			if (my_count == 0) 
				Toast.makeText(getApplicationContext(), "패배", Toast.LENGTH_SHORT).show();
			
			
			setInfoToView(info, true);
			
			/////////////////////////////////////////////////////////////
			
			
			if (!invitated) {
				findViewById(R.id.o_myturn).setVisibility(View.VISIBLE);
				findViewById(R.id.o_yourturn).setVisibility(View.GONE);
				mine = true;
			}
			if (invitated) {
				if (!first_turn) {
					findViewById(R.id.o_myturn).setVisibility(View.VISIBLE);
					findViewById(R.id.o_yourturn).setVisibility(View.GONE);
					mine = true;
				}
			}
			if (first_turn) {
				first_turn = false;
			} else if (!received_opponent && !first_turn) {
				Log.e("recievingdata", "receive");
				
				for(int i = 0; i<10 ; i ++)
					for(int j = 0; j<10;j++)
						opponent_info[i][j] = mTurnData.boarddata[i][j];

				setInfoToView(opponent_info, false);
		            
				received_opponent = true;
			}

			if (!received_opponent && invitated) {
				for(int i = 0; i<10 ; i ++)
					for(int j = 0; j<10;j++)
						opponent_info[i][j] = mTurnData.boarddata[i][j];
				
				received_opponent = true;
			}
			Toast.makeText(this, "myturn " + mTurnData.boarddata[5][5],
					Toast.LENGTH_SHORT).show();
			return;
		case TurnBasedMatch.MATCH_TURN_STATUS_THEIR_TURN:
			// Should return results.
			showWarning("Alas...", "It's not your turn.");
			break;
		case TurnBasedMatch.MATCH_TURN_STATUS_INVITED:
			showWarning("Good inititative!",
					"Still waiting for invitations.\n\nBe patient!");
		}

		mTurnData = null;
	}

	public void showErrorMessage(TurnBasedMatch match, int statusCode,
			String string) {

		showWarning("Warning", string);
	}

	public void showWarning(String title, String message) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// set title
		alertDialogBuilder.setTitle(title).setMessage(message);

		// set dialog message
		alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						// if this button is clicked, close
						// current activity
					}
				});

		// create alert dialog
		mAlertDialog = alertDialogBuilder.create();

		// show it
		mAlertDialog.show();
	}

	private boolean checkStatusCode(TurnBasedMatch match, int statusCode) {
		switch (statusCode) {
		case GamesStatusCodes.STATUS_OK:
			return true;
		case GamesStatusCodes.STATUS_NETWORK_ERROR_OPERATION_DEFERRED:
			// This is OK; the action is stored by Google Play Services and will
			// be dealt with later.
			Toast.makeText(
					this,
					"Stored action for later.  (Please remove this toast before release.)",
					TOAST_DELAY).show();
			// NOTE: This toast is for informative reasons only; please remove
			// it from your final application.
			return true;
		case GamesStatusCodes.STATUS_MULTIPLAYER_ERROR_NOT_TRUSTED_TESTER:
			showErrorMessage(match, statusCode,
					"status_multiplayer_error_not_trusted_tester");
			break;
		case GamesStatusCodes.STATUS_MATCH_ERROR_ALREADY_REMATCHED:
			showErrorMessage(match, statusCode, "match_error_already_rematched");
			break;
		case GamesStatusCodes.STATUS_NETWORK_ERROR_OPERATION_FAILED:
			showErrorMessage(match, statusCode,
					"network_error_operation_failed");
			break;
		case GamesStatusCodes.STATUS_CLIENT_RECONNECT_REQUIRED:
			showErrorMessage(match, statusCode, "client_reconnect_required");
			break;
		case GamesStatusCodes.STATUS_INTERNAL_ERROR:
			showErrorMessage(match, statusCode, "internal_error");
			break;
		case GamesStatusCodes.STATUS_MATCH_ERROR_INACTIVE_MATCH:
			showErrorMessage(match, statusCode, "match_error_inactive_match");
			break;
		case GamesStatusCodes.STATUS_MATCH_ERROR_LOCALLY_MODIFIED:
			showErrorMessage(match, statusCode, "match_error_locally_modified");
			break;
		default:
			showErrorMessage(match, statusCode, "unexpected_status");
			Log.d(TAG, "Did not have warning or string to deal with: "
					+ statusCode);
		}

		return false;
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

	public boolean check_space(int x, int y, int id, boolean flag,
			int info[][], boolean count_flag) {
		switch (id) {
		case 1:
			if (flag) {
				for (int i = -1; i < 5; i++) {
					for (int j = -1; j < 2; j++) {
						if (x + j < 0 || y + i < 0 || x + j > 9 || y + i > 9) {
							if (j == 0 && i >= 0 && i <= 3)
								return false;
							else
								continue;
						} else if (info[x + j][y + i] != 0
								&& info[x + j][y + i] != id)
							return false;
					}
				}
			} else {
				for (int i = -1; i < 2; i++) {
					for (int j = -1; j < 5; j++) {
						if (x + j < 0 || y + i < 0 || x + j > 9 || y + i > 9) {
							if (i == 0 && j >= 0 && j <= 3)
								return false;
							else
								continue;
						} else if (info[x + j][y + i] != 0
								&& info[x + j][y + i] != id)
							return false;
					}
				}
			}

			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					if (info[i][j] == id) {
						info[i][j] = 0;
						count_flag = false;
					}
				}
			}

			for (int i = 0; i < 4; i++) {
				if (flag)
					info[x][y + i] = id;
				else
					info[x + i][y] = id;
			}

			break;

		case 2:
		case 3:
			if (flag) {
				for (int i = -1; i < 4; i++) {
					for (int j = -1; j < 2; j++) {
						if (x + j < 0 || y + i < 0 || x + j > 9 || y + i > 9) {
							if (j == 0 && i >= 0 && i <= 2)
								return false;
							else
								continue;
						} else if (info[x + j][y + i] != 0
								&& info[x + j][y + i] != id)
							return false;
					}
				}
			} else {
				for (int i = -1; i < 2; i++) {
					for (int j = -1; j < 4; j++) {
						if (x + j < 0 || y + i < 0 || x + j > 9 || y + i > 9) {
							if (i == 0 && j >= 0 && j <= 2)
								return false;
							else
								continue;
						} else if (info[x + j][y + i] != 0
								&& info[x + j][y + i] != id)
							return false;
					}
				}
			}

			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					if (info[i][j] == id) {
						info[i][j] = 0;
						count_flag = false;
					}
				}
			}

			for (int i = 0; i < 3; i++) {
				if (flag)
					info[x][y + i] = id;
				else
					info[x + i][y] = id;
			}

			break;

		case 4:
		case 5:
		case 6:
			if (flag) {
				for (int i = -1; i < 3; i++) {
					for (int j = -1; j < 2; j++) {
						if (x + j < 0 || y + i < 0 || x + j > 9 || y + i > 9) {
							if (j == 0 && i >= 0 && i <= 1)
								return false;
							else
								continue;
						} else if (info[x + j][y + i] != 0
								&& info[x + j][y + i] != id)
							return false;
					}
				}
			} else {
				for (int i = -1; i < 2; i++) {
					for (int j = -1; j < 3; j++) {
						if (x + j < 0 || y + i < 0 || x + j > 9 || y + i > 9) {
							if (i == 0 && j >= 0 && j <= 1)
								return false;
							else
								continue;
						} else if (info[x + j][y + i] != 0
								&& info[x + j][y + i] != id)
							return false;
					}
				}
			}

			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					if (info[i][j] == id) {
						info[i][j] = 0;
						count_flag = false;
					}
				}
			}

			for (int i = 0; i < 2; i++) {
				if (flag)
					info[x][y + i] = id;
				else
					info[x + i][y] = id;
			}

			break;

		case 7:
		case 8:
		case 9:
		case 10:
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					if (x + j < 0 || y + i < 0 || x + j > 9 || y + i > 9)
						continue;
					else if (info[x + j][y + i] != 0 && info[x + j][y + i] != id)
						return false;
				}
			}

			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					if (info[i][j] == id) {
						info[i][j] = 0;
						count_flag = false;
					}
				}
			}

			info[x][y] = id;

			break;

		default:
			return false;
		}

		if (count_flag)
			count++;

		return true;
	}
	
	public void setShipBoundary(int id){
	}
}