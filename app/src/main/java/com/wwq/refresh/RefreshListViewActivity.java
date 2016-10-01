package com.wwq.refresh;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wwq.darkhorseday53.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 魏文强 on 2016/9/21.
 */
public class RefreshListViewActivity extends Activity {
    private RefreshListView refreshListView;
    private List<String> list = new ArrayList<String>();
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day54_refresh_list);
        initView();
        initDate();
    }

    private void initView(){
        refreshListView = (RefreshListView) findViewById(R.id.refreshListView);
    }

    private void initDate(){
        for(int i=0; i<15; i++){
            list.add("list原来的数据-"+i);
        }
        /*final View headerView = View.inflate(this, R.layout.view_header, null);
        headerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                headerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int headerViewHeight = headerView.getHeight();
                headerView.setPadding(0, -headerViewHeight, 0, 0);
                refreshListView.addHeaderView(headerView);//必须在setAdapter之前调用

            }
        });*/
        /*headerView.measure(0, 0);//主动通知系统去测量
        int headerViewHeight = headerView.getMeasuredHeight();
        headerView.setPadding(0, -headerViewHeight, 0, 0);
        refreshListView.addHeaderView(headerView);//必须在setAdapter之前调用*/

        adapter = new MyAdapter();
        refreshListView.setAdapter(adapter);

        refreshListView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onPullRefresh() {
                //需要联网请求服务器的数据，然后更新UI
                requestDataFromServer(false);
            }

            @Override
            public void onLoadingMore() {
                requestDataFromServer(true);
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter.notifyDataSetChanged();
            refreshListView.completeRefresh();
        }
    };

    private void requestDataFromServer(final boolean isLoadingMore){
        new Thread(){
            @Override
            public void run() {
                SystemClock.sleep(3000);
                if(isLoadingMore){
                    list.add("上拉加载更多的数据--1");
                    list.add("上拉加载更多的数据--2");
                    list.add("上拉加载更多的数据--3");
                }else{
                    list.add(0, "下拉刷新的数据");
                }
                //更新UI
                handler.sendEmptyMessage(0);
            }
        }.start();
    }


    class MyAdapter extends BaseAdapter{

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
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(RefreshListViewActivity.this);
            textView.setPadding(20,20,20,20);
            textView.setTextSize(18);
            textView.setText(list.get(position));
            return textView;
        }
    }
}
