package com.example.downhell_100;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class AgileBuddyActivity extends Activity {
	private AgileBuddyView mAgileBuddyView;

	private Button mLeft;
	private Button mRight;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 禁止屏幕休眠
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		// 全屏幕
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
		mAgileBuddyView = (AgileBuddyView) findViewById(R.id.agile_buddy);
		mLeft = (Button)findViewById(R.id.left);
		mRight =(Button)findViewById(R.id.right);
		
		
		
		View.OnClickListener buttonListener=new View.OnClickListener() {
			
			@Override
			public void onClick(View arg) {
				// TODO Auto-generated method stub
				switch(arg.getId()){
					case R.id.left:
						mAgileBuddyView.handleMoving(-1);
						break;
					case R.id.right:
						mAgileBuddyView.handleMoving(1);
						break;
						default:
						mAgileBuddyView.handleMoving(0);
						break;
				}
				}
		};	
		mLeft.setOnClickListener(buttonListener);
		mRight.setOnClickListener(buttonListener);
	  
}
}
