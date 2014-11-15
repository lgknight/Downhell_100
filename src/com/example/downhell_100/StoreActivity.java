package com.example.downhell_100;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import agilebuddy.util.Global_data;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class StoreActivity extends Activity implements OnClickListener{
	
	private SimpleAdapter spAdapter;
	private ListView listView;
	private List<Map<String, Object>> list;
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	public void onCreate(Bundle onSaveInstanceState) {
		super.onCreate(onSaveInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ½ûÖ¹ÆÁÄ»ÐÝÃß
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		// È«ÆÁÄ»
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		list = new ArrayList<Map<String,Object>>();
		listView = new ListView(this);
		
		Map<String, Object> map = new HashMap<String ,Object>();
		map.put("storerole", R.drawable.storerole1);
		map.put("rolename", "ÐÇÊ¸");
		map.put("cost", "0½ð±Ò");
		map.put("buyid", "¹ºÂò");
		list.add(map);
		
		map = new HashMap<String ,Object>();
		map.put("storerole", R.drawable.storerole2);
		map.put("rolename", "·çË²");
		map.put("cost", "100½ð±Ò");
		map.put("buyid", "¹ºÂò");
		list.add(map);

		map = new HashMap<String ,Object>();
		map.put("storerole", R.drawable.storerole3);
		map.put("rolename", "×ÏÁú");
		map.put("cost", "150½ð±Ò");
		map.put("buyid", "¹ºÂò");
		list.add(map);
		
		map = new HashMap<String ,Object>();
		map.put("storerole", R.drawable.storerole4);
		map.put("rolename", "±ùºÓ");
		map.put("cost", "200½ð±Ò");
		map.put("buyid", "¹ºÂò");
		list.add(map);
		
		map = new HashMap<String ,Object>();
		map.put("storerole", R.drawable.storerole5);
		map.put("rolename", "Ò»»Ô");
		map.put("cost", "300½ð±Ò");
		map.put("buyid", "¹ºÂò");
		list.add(map);
		
		spAdapter= new SimpleAdapter(this, list, R.layout.store, new String[]{
				"storerole", "rolename" ,"cost" ,"buyid"}, new int[] {
				R.id.storerole, R.id.rolename, R.id.cost ,R.id.buyid}) {
			public View getView(int position, View convertView, ViewGroup parent) {
				final int p = position;
				final View view = super.getView(position, convertView, parent);
				Button button=(Button) view.findViewById(R.id.buyid);
				button.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						Global_data.tempRole = p + 1;
						Global_data.money -= 100 * (p + 1);
						new AlertDialog.Builder(StoreActivity.this)
						.setTitle("OK").setMessage("¹ºÂò³É¹¦")
						.create()
						.show();
					}
				});
				return view;
			}
		};
		listView.setAdapter(spAdapter);
		
		setContentView(listView);
		
	}
	
}
