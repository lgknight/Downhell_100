package com.example.downhell_100;

import java.io.FileOutputStream;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
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
		// ȡ������
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ��ֹ��Ļ����
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		// ȫ��Ļ
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.main);
		
		mAgileBuddyView = (AgileBuddyView) findViewById(R.id.agile_buddy);
		
		mLeft = (Button) findViewById(R.id.left);
		mRight = (Button) findViewById(R.id.right);
		
		
		View.OnTouchListener buttonListener=new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == event.ACTION_DOWN)
				{
				switch(v.getId()){
				case R.id.left:
					mAgileBuddyView.handleMoving(-1);
					break;
				case R.id.right:
					mAgileBuddyView.handleMoving(1);
					break;
					}	
				} else if(event.getAction() == event.ACTION_UP) 
					mAgileBuddyView.handleMoving(0);
				
				return true;
			}
		}; 
		mLeft.setOnTouchListener(buttonListener);
		mRight.setOnTouchListener(buttonListener);
	} 
}
