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
import android.widget.TextView;

public class StoreActivity extends Activity {
	
	private SimpleAdapter spAdapter;
	private ListView listView;
	private List<Map<String, Object>> list;
	
	@Override
	public void onCreate(Bundle onSaveInstanceState) {
		super.onCreate(onSaveInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ��ֹ��Ļ����
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		// ȫ��Ļ
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.store);
		TextView coinText=(TextView) findViewById(R.id.Coin);
		coinText.setText(String.valueOf(Global_data.money));
		
//		list = new ArrayList<Map<String,Object>>();
//		listView = new ListView(this);
//		
//		Map<String, Object> map = new HashMap<String ,Object>();
//		map.put("storerole", R.drawable.coin1);
//		map.put("rolename", "coin:" + Global_data.money);
//		map.put("cost", "");
//		map.put("buyid", "����");
//		list.add(map);
//		
//		map = new HashMap<String ,Object>();
//		map.put("storerole", R.drawable.storerole1);
//		map.put("rolename", "��ʸ");
//		map.put("cost", "000���");
//		map.put("buyid", "����");
//		list.add(map);
//		
//		map = new HashMap<String ,Object>();
//		map.put("storerole", R.drawable.storerole2);
//		map.put("rolename", "��˲");
//		map.put("cost", "100���");
//		map.put("buyid", "����");
//		list.add(map);
//
//		map = new HashMap<String ,Object>();
//		map.put("storerole", R.drawable.storerole3);
//		map.put("rolename", "����");
//		map.put("cost", "200���");
//		map.put("buyid", "����");
//		list.add(map);
//		
//		map = new HashMap<String ,Object>();
//		map.put("storerole", R.drawable.storerole4);
//		map.put("rolename", "����");
//		map.put("cost", "300���");
//		map.put("buyid", "����");
//		list.add(map);
//		
//		map = new HashMap<String ,Object>();
//		map.put("storerole", R.drawable.storerole5);
//		map.put("rolename", "һ��");
//		map.put("cost", "400���");
//		map.put("buyid", "����");
//		list.add(map);
//		
//		spAdapter= new SimpleAdapter(this, list, R.layout.store, new String[]{
//				"storerole", "rolename"  ,"buyid"}, new int[] {
//				R.id.storerole, R.id.rolename,R.id.buyid}) {
//			public View getView(int position, View convertView, ViewGroup parent) {
//				final int p = position;
//				final View view = super.getView(position, convertView, parent);
//				Button button=(Button) view.findViewById(R.id.buyid);
//				button.setOnClickListener(new OnClickListener() {
//					public void onClick(View v) {
//						if(Global_data.money < 100 * ( p - 1)) {
//							new AlertDialog.Builder(StoreActivity.this)
//							.setTitle("no enough coin").setMessage("��Ҳ���")
//							.create()
//							.show();
//						} else {
//							Global_data.tempRole = p;
//							Global_data.money -= 100 * (p - 1);
//							new AlertDialog.Builder(StoreActivity.this)
//							.setTitle("OK").setMessage("����ɹ�")
//							.create()
//							.show();
//						}
//					}
//				});
//				return view;
//			}
//		};
//		listView.setAdapter(spAdapter);
//		setContentView(listView);
		
	}
	
}
