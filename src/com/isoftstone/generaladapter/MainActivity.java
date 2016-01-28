
package com.isoftstone.generaladapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv = (ListView) findViewById(R.id.lv);
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            data.add(getString(R.string.hello_world) + i);
        }
        GeneralAdapter<String> adapter = new GeneralAdapter<String>(this, R.layout.item_view, data) {
            @Override
            public void convert(ViewHolder holder, String item, int position) {
                Button btn = (Button) holder.getView(R.id.btn);
                TextView tv = (TextView) holder.getView(R.id.tv);
                btn.setText(item);
                btn.setBackgroundColor(Color.DKGRAY);
                tv.setText(item);
            }
        };
        lv.setAdapter(adapter);

        String[] data1 = new String[10];
        for (int i = 0; i < data1.length; i++) {
            data1[i] = getString(R.string.hello_world) + (i + 80);
        }
        adapter.addAll(data1);
    }

}
