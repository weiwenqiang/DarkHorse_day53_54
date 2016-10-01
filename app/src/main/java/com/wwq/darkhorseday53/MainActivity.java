package com.wwq.darkhorseday53;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wwq.downselect.DownSelectActivity;
import com.wwq.refresh.RefreshListViewActivity;
import com.wwq.sidemenu.SideSlideMenuActivity;
import com.wwq.toggle.GlideToggleActivity;
import com.wwq.viewpager.ViewPagerPlayActivity;
import com.wwq.youkumenu.YoukuMenuActivity;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button btn_day53_b1;
    private Button btn_day53_b2;
    private Button btn_day53_b3;
    private Button btn_day53_b4;
    private Button btn_day54_b5;
    private Button btn_day54_b6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getControlReference();
        initComponent();
        getControlEvents();
    }


    private void getControlReference() {
        btn_day53_b1 = (Button) findViewById(R.id.btn_day53_b1);
        btn_day53_b2 = (Button) findViewById(R.id.btn_day53_b2);
        btn_day53_b3 = (Button) findViewById(R.id.btn_day53_b3);
        btn_day53_b4 = (Button) findViewById(R.id.btn_day53_b4);
        btn_day54_b5 = (Button) findViewById(R.id.btn_day54_b5);
        btn_day54_b6 = (Button) findViewById(R.id.btn_day54_b6);
    }

    private void initComponent() {

    }

    private void getControlEvents() {
        btn_day53_b1.setOnClickListener(this);
        btn_day53_b2.setOnClickListener(this);
        btn_day53_b3.setOnClickListener(this);
        btn_day53_b4.setOnClickListener(this);
        btn_day54_b5.setOnClickListener(this);
        btn_day54_b6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_day53_b1:
                startActivity(new Intent(MainActivity.this, YoukuMenuActivity.class));
                break;
            case R.id.btn_day53_b2:
                startActivity(new Intent(MainActivity.this, ViewPagerPlayActivity.class));
                break;
            case R.id.btn_day53_b3:
                startActivity(new Intent(MainActivity.this, DownSelectActivity.class));
                break;
            case R.id.btn_day53_b4:
                startActivity(new Intent(MainActivity.this, GlideToggleActivity.class));
                break;
            case R.id.btn_day54_b5:
                startActivity(new Intent(MainActivity.this, RefreshListViewActivity.class));
                break;
            case R.id.btn_day54_b6:
                startActivity(new Intent(MainActivity.this, SideSlideMenuActivity.class));
                break;
        }
    }
}
