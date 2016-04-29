
package com.isoftstone.generaladapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

    /** 显示左边item */
    static final int TYPE_LEFT = 0;

    /** 显示右边item */
    static final int TYPE_RIGHT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(new MultItemAdapter(this, getTestData()));
    }

    /**
     * 生成测试列表数据
     * 
     * @return
     * @author hubing
     */
    ArrayList<ItemData> getTestData() {
        ArrayList<ItemData> data = new ArrayList<>();

        ItemData itemData;
        for (int i = 0; i < 50; i++) {
            itemData = new ItemData();
            itemData.itemType = Math.round(Math.random()) % 2 == 0 ? TYPE_LEFT : TYPE_RIGHT;
            if (itemData.itemType == TYPE_LEFT) {
                itemData.title = "我是左边标题:" + i;
                itemData.subtitle = "我是左边子标题:" + i;
            } else {
                itemData.title = "我是右边标题:" + i;
                itemData.subtitle = "我是右边子标题:" + i;
            }
            data.add(itemData);
        }

        return data;
    }

    /**
     * 多item布局Adapter
     * 
     * @author hubing
     * @version [1.0.0.0, 2016-4-29]
     */
    class MultItemAdapter extends GeneralAdapter<ItemData> {

        public MultItemAdapter(Context ctx, List<ItemData> data) {
            super(ctx, data);
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            return getItem(position).itemType;
        }

        @Override
        public int getItemLayoutId(int itemViewType) {
            // 根据不同的item类型，使用不同的item布局
            if (itemViewType == TYPE_LEFT) {
                return R.layout.item_left;
            } else if (itemViewType == TYPE_RIGHT) {
                return R.layout.item_right;
            }
            return 0;
        }

        @Override
        public void convert(ViewHolder holder, ItemData item, int position) {
            int itemType = item.itemType;
            if (itemType == TYPE_LEFT) {
                TextView titleTv = holder.getView(R.id.tv_left_title);
                titleTv.setText(item.title);
                TextView subtitleTv = holder.getView(R.id.tv_left_subtitle);
                subtitleTv.setText(item.subtitle);
            } else if (itemType == TYPE_RIGHT) {
                TextView titleTv = holder.getView(R.id.tv_right_title);
                titleTv.setText(item.title);
                TextView subtitleTv = holder.getView(R.id.tv_right_subtitle);
                subtitleTv.setText(item.subtitle);
            }
        }

    }

}
