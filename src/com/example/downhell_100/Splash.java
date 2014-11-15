package com.example.downhell_100;

//import java.util.HashMap;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
//import android.media.SoundPool;
//import android.media.AudioManager;


public class Splash extends Activity implements OnClickListener {
	
//	private SoundPool soundPool;
//	private HashMap< integer , integer> soundPoolMap;
//	private int SOUND_TOP = 1;
//	RelativeLayout mAdContainer1;
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
	
//		soundPool = new SoundPool( 5, AudioManager.STREAM_MUSIC, 0);
//		soundPoolMap = new HashMap<integer ,integer>();
//		soundPoolMap.put(SOUND_TOP, soundPool.load(this, R.raw.top, 1));
//		playSound();
		
		Button startButton = (Button) findViewById(R.id.start_game);
		startButton.setOnClickListener(this);

		Button storeButton = (Button) findViewById(R.id.store);
		storeButton.setOnClickListener(this);
		
		Button exitButton = (Button) findViewById(R.id.exit);
		exitButton.setOnClickListener(this);
		
	}
	
//	private void playSound(){
//		try{
//			AudioManager mgr = (AudioManager) this.
//					getSystemService(Context.AUDIO_SERVICE);
//			
//			float streamVolumeCurrent = mgr
//					.getStreamVolume(AudioManager.STREAM_RING);
//			float streamVolumeMax = mgr
//					.getStreamMaxVolume(AudioManager.STREAM_RING);
//			float volume = streamVolumeCurrent / streamVolumeMax;
//			soundPool.play(R.raw.top, volume, volume, 1, 0,
//					1f);
//			
//		} catch(Exception e) {
//			Log.d("playsound", e.toString());
//		}
//	}
	
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
