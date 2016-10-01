package com.wwq.sidemenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.wwq.darkhorseday53.R;

/**
 * Created by 魏文强 on 2016/9/21.
 */
public class SideSlideMenuActivity extends Activity {
    private ImageView btn_back;
    private SlideMenuView slideMenuView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.day54_side_menu);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        slideMenuView = (SlideMenuView) findViewById(R.id.slideMenu);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideMenuView.switchMenu();
            }
        });
    }
}