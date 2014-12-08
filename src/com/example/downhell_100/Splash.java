package com.example.downhell_100;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Splash extends Activity implements OnClickListener {
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.splash);
		
		Button startButton = (Button) findViewById(R.id.start_game);
		startButton.setOnClickListener(this);

		Button storeButton = (Button) findViewById(R.id.store);
		storeButton.setOnClickListener(this);
		
		Button exitButton = (Button) findViewById(R.id.exit);
		exitButton.setOnClickListener(this);
		
	}
	
	
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.start_game:
			intent = new Intent(this, AgileBuddyActivity.class);
			if (intent != null) {
				startActivity(intent);
			}
			break;
		case R.id.store:
			intent = new Intent(this, StoreActivity.class);
			if(intent != null) {
				startActivity(intent);
			}
			break;
		case R.id.exit:
			super.finish();
			return;
		}
	}

}
