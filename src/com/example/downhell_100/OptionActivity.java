package com.example.downhell_100;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import agilebuddy.util.Global_data;

public class OptionActivity extends Activity implements OnClickListener {
		public void onCreate(Bundle onSaveInstanceState) {
			super.onCreate(onSaveInstanceState);

			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
			setContentView(R.layout.option);

			Button easy = (Button) findViewById(R.id.easy);
			easy.setOnClickListener(this);

			Button normal = (Button) findViewById(R.id.normal);
			normal.setOnClickListener(this);
			
			Button hard = (Button) findViewById(R.id.hard);
			hard.setOnClickListener(this);

		}
		public void onClick(View v) {
			Intent intent = null;
			switch (v.getId()) {
			case R.id.easy:
				Global_data.model = 1;
				new AlertDialog.Builder(this).setTitle("Model")
				.setMessage("model has been chosen").create()
				.show();
				break;
			case R.id.normal:
				Global_data.model = 2;
				new AlertDialog.Builder(this).setTitle("Model")
				.setMessage("model has been chosen").create()
				.show();
				break;
			case R.id.hard:
				Global_data.model = 3;
				new AlertDialog.Builder(this).setTitle("Model")
				.setMessage("model has been chosen").create()
				.show();
				return;
			}
		}
}
