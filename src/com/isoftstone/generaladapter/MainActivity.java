package com.isoftstone.generaladapter;

import java.util.ArrayList;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ListView lv = (ListView) findViewById(R.id.lv);
		ArrayList<String> data = new ArrayList<String>();
		for (int i = 0; i < 60; i++) {
			data.add(getString(R.string.hello_world) + i);
		}
		GeneralAdapter<String> adapter = new GeneralAdapter<String>(this,
				R.layout.item_view, data) {
			@Override
			public void convert(ViewHolder holder, String item, int position) {
				Button btn = (Button) holder.getView(R.id.btn);
				TextView tv = (TextView) holder.getView(R.id.tv);
				btn.setText(item);
				btn.setBackgroundColor(Color.BLUE);
				tv.setText(item);
			}
		};
		adapter.setData(data);
		lv.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
