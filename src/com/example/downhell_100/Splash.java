package com.example.downhell_100;

import agilebuddy.util.ConstantInfo;

import com.fogo.spot.SpotManager;



import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;


public class Splash extends Activity implements OnClickListener {

	private SharedPreferences mBaseSettings;
	RelativeLayout mAdContainer1;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		SpotManager spotManager = SpotManager.getInstance(this);  
        spotManager.setKey(Global.VerticalGridOffsetx);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash);
		Button startButton = (Button) findViewById(R.id.start_game);
		startButton.setOnClickListener(this);

//		Button scoreBoardButton = (Button) findViewById(R.id.score_board);
//		scoreBoardButton.setOnClickListener(this);
//
//		Button optionButton = (Button) findViewById(R.id.options);
//		optionButton.setOnClickListener(this);

		Button exitButton = (Button) findViewById(R.id.exit);
		exitButton.setOnClickListener(this);

		mBaseSettings = PreferenceManager.getDefaultSharedPreferences(this);
		
	}
	public void onClick(View v) {
		Intent i = null;
		switch (v.getId()) {
		case R.id.start_game:
			i = new Intent(this, AgileBuddyActivity.class);
			break;
		case R.id.exit:
			finish();
			return;
		}
		if (i != null) {
			startActivity(i);
		}
	}

	@Override
	public void finish() {
		this.unbindService(mConnection);
		super.finish();
	}

	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
		}

		public void onServiceDisconnected(ComponentName className) {
		}
	};
}
