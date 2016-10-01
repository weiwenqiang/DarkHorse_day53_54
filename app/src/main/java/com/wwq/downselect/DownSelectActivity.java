package com.wwq.downselect;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.wwq.darkhorseday53.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 魏文强 on 2016/8/10.
 */
public class DownSelectActivity extends Activity implements View.OnClickListener {
    private EditText edt_text;
    private ImageView img_select;

    private PopupWindow popupWindow;
    private ListView listView;
    private MyAdapter adapter;
    private List<String> list = new ArrayList<String>();

    private int popupWindowHeight = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day53_down_select);
        getControlReference();
        initComponent();
        setControlEvents();
    }

    private void getControlReference() {
        edt_text = (EditText) findViewById(R.id.edt_text);
        img_select = (ImageView) findViewById(R.id.img_select);
    }

    private void initComponent() {
        for (int i = 0; i < 15; i++) {
            list.add(900000 + i + "");
        }

        listView = new ListView(this);

        listView.setBackgroundResource(R.drawable.edit_pressed);
        listView.setVerticalScrollBarEnabled(false);//隐藏ListView滚动条

        adapter = new MyAdapter();
        listView.setAdapter(adapter);
    }

    private void setControlEvents() {
        img_select.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DownSelectActivity.this, list.get(position), Toast.LENGTH_SHORT).show();
                edt_text.setText(list.get(position));
                popupWindow.dismiss();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_select:
                showNumberList();
                break;
        }
    }

    private void showNumberList() {
        if (popupWindow == null) {
            popupWindow = new PopupWindow(listView, edt_text.getWidth(), popupWindowHeight);
        }
        //下面3个属性要同时设置,不然获取不到焦点，或者使用配置android:descendantFocusability="blocksDescendants"
        popupWindow.setFocusable(true);//PopupWindow 获取焦点
        popupWindow.setBackgroundDrawable(new BitmapDrawable());//为了可以点击item
        popupWindow.setOutsideTouchable(true);//点边缘消失

        popupWindow.showAsDropDown(edt_text, 0, 0);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final View view = View.inflate(DownSelectActivity.this, R.layout.adapter_list, null);
            TextView txt_number = (TextView) view.findViewById(R.id.txt_number);
            ImageView img_delete = (ImageView) view.findViewById(R.id.img_delete);

            txt_number.setText(list.get(position));
            img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);
                    notifyDataSetChanged();

                    int listviewHeight = view.getHeight() * list.size();
                    popupWindow.update(edt_text.getWidth(), listviewHeight > popupWindowHeight ? popupWindowHeight : listviewHeight);

                    if (list.size() == 0) {
                        popupWindow.dismiss();
                    }
                }
            });

            return view;
        }
    }
}
