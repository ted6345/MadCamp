package com.aceteam.game.battleship;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
import com.google.android.gms.games.multiplayer.turnbased.OnTurnBasedMatchUpdateReceivedListener;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.plus.Plus;
import com.google.example.games.basegameutils.BaseGameUtils;

public class GameActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, 
OnInvitationReceivedListener, OnTurnBasedMatchUpdateReceivedListener{

	static GoogleApiClient mGoogleApiClient;
	
    final static int TOAST_DELAY = Toast.LENGTH_SHORT;
	
	final static int RC_SIGN_IN = 9001;
    final static int RC_SELECT_PLAYERS = 10000;

	static boolean mResolvingConnectionFailure = false;
	static boolean mAutoStartSignInflow = true;
	static boolean mSignInClicked = false;


	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        MainActivity.array.add(this);
        
        overridePendingTransition(R.anim.left, R.anim.right);
        
        
     // Create the Google Api Client with access to Plus and Games
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                // add other APIs and scopes here as needed
                .build();          

        
        final ImageButton play_alone = (ImageButton) findViewById(R.id.play_alone); 
        final ImageButton play_online = (ImageButton) findViewById(R.id.play_online);
        final ImageButton sign_out = (ImageButton) findViewById(R.id.sign_out_button);
        final ImageButton previous = (ImageButton) findViewById(R.id.previous);
        
        play_alone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				Intent play_alone_intent = new Intent(GameActivity.this, Play_alone.class);
				startActivity(play_alone_intent);	
			
		    }
		});
        
        play_online.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				Intent match = new Intent(GameActivity.this, Match.class);
				startActivity(match);
		    }
		});
        
        findViewById(R.id.sign_in_button).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mSignInClicked = true;
				mGoogleApiClient.connect();	
		    }
		});
        
        sign_out.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
		        findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
		        findViewById(R.id.sign_out_button).setVisibility(View.GONE);
				mSignInClicked = false;
		        Games.signOut(mGoogleApiClient);
		        mGoogleApiClient.disconnect();
		        Toast.makeText(GameActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
		    }
		});

        previous.setOnClickListener(new OnClickListener() { //
			
			@Override
			public void onClick(View arg0) {
				onBackPressed();
			}
		});
        
        play_alone.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch(arg1.getAction()){
					case MotionEvent.ACTION_DOWN:
						play_alone.setImageResource(R.drawable.alone_on);
						break;
					case MotionEvent.ACTION_UP:
						play_alone.setImageResource(R.drawable.alone);
						break;
				}
				return false;
			}
		});
        
        play_online.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch(arg1.getAction()){
					case MotionEvent.ACTION_DOWN:
						play_online.setImageResource(R.drawable.online_on);
						break;
					case MotionEvent.ACTION_UP:
						play_online.setImageResource(R.drawable.online);
						break;
				}
				return false;
			}
		});
        
        sign_out.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch(arg1.getAction()){
					case MotionEvent.ACTION_DOWN:
						sign_out.setImageResource(R.drawable.sinout_on);
						break;
					case MotionEvent.ACTION_UP:
						sign_out.setImageResource(R.drawable.signout);
						break;
				}
				return false;
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
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (mResolvingConnectionFailure) {
            // already resolving
            return;
        }

        // if the sign-in button was clicked or if auto sign-in is enabled,
        // launch the sign-in flow
        if (mSignInClicked || mAutoStartSignInflow) {
            mAutoStartSignInflow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = true;

            // Attempt to resolve the connection failure using BaseGameUtils.
            // The R.string.signin_other_error value should reference a generic
            // error string in your strings.xml file, such as "There was
            // an issue with sign-in, please try again later."
            if (!BaseGameUtils.resolveConnectionFailure(this,
                    mGoogleApiClient, connectionResult,
                    RC_SIGN_IN, "signin_other_error")) {
                mResolvingConnectionFailure = false;
            }
        }

        // Put code here to display the sign-in button
    }

	@Override
	public void onConnected(Bundle connectionHint) {
		findViewById(R.id.sign_in_button).setVisibility(View.GONE);
	    findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionSuspended(int i) {
	    // Attempt to reconnect
	    mGoogleApiClient.connect();
	}

    
	@Override
	protected void onStart() {
	    super.onStart();
	   mGoogleApiClient.connect();
	}

	@Override
	protected void onStop() {
	    super.onStop();
	    if (mGoogleApiClient.isConnected()) {
	      //  mGoogleApiClient.disconnect();
	    }
	}
	
	@Override
	   protected void onPause() {
	      // TODO Auto-generated method stub
	      super.onPause();
	     // Start.soundpool.pause(Start.gullId);
	    Start.soundpool.pause();
	   }
	   
	   protected void onResume() {
	      // TODO Auto-generated method stub
	      super.onPause();
	    //  Start.soundpool.resume(Start.gullId);
	      Start.soundpool.start();
	   }
	
	protected void onActivityResult(int requestCode, int resultCode,
	        Intent intent) {
	    if (requestCode == RC_SIGN_IN) {
	        mSignInClicked = false;
	        mResolvingConnectionFailure = false;
	        if (resultCode == RESULT_OK) {
	            mGoogleApiClient.connect();
	        } else {
	            // Bring up an error dialog to alert the user that sign-in
	            // failed. The R.string.signin_failure should reference an error
	            // string in your strings.xml file that tells the user they
	            // could not be signed in, such as "Unable to sign in."
	            BaseGameUtils.showActivityResultError(this,
	                requestCode, resultCode, 1);
	        }
	    }
	}

	@Override
    public void onInvitationReceived(Invitation invitation) {
        Toast.makeText(
                this,
                "An invitation has arrived from "
                        + invitation.getInviter().getDisplayName(), TOAST_DELAY)
                .show();
    }

    @Override
    public void onInvitationRemoved(String invitationId) {
        Toast.makeText(this, "An invitation was removed.", TOAST_DELAY).show();
    }

    @Override
    public void onTurnBasedMatchReceived(TurnBasedMatch match) {
        Toast.makeText(this, "A match was updated.", TOAST_DELAY).show();
    }

    @Override
    public void onTurnBasedMatchRemoved(String matchId) {
        Toast.makeText(this, "A match was removed.", TOAST_DELAY).show();

    }
   
}