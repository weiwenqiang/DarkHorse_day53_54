package com.wwq.youkumenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wwq.darkhorseday53.R;
import com.wwq.utils.AnimUtil;

/**
 * Created by 魏文强 on 2016/7/30.
 */
public class YoukuMenuActivity extends Activity implements View.OnClickListener {

    private RelativeLayout level1, level2, level3;
    private ImageView iv_home;
    private ImageView iv_menu;

    private boolean isShowLevel2 = true;//是否显示二级菜单
    private boolean isShowLevel3 = true;//是否显示三级菜单

    private boolean isShowMenu = true;//是否显示整个菜单

    private final String TAG = "YoukuMenuActivity.class";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day53_youku_menu);

        getControlReference();
        initComponent();
        getControlEvents();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (isShowMenu) {
                //需要关闭所有菜单
                int startOffset = 0;
                if (isShowLevel3) {
                    AnimUtil.closeMenu(level3, startOffset);
                    isShowLevel3 = false;
                    startOffset += 200;
                }
                if (isShowLevel2) {
                    AnimUtil.closeMenu(level2, startOffset);
                    isShowLevel2 = false;
                    startOffset += 200;
                }
                AnimUtil.closeMenu(level1, startOffset);
            } else {
                //需要显示所有菜单
                AnimUtil.showMenu(level1, 0);
                AnimUtil.showMenu(level2, 200);
                isShowLevel2 = true;
                AnimUtil.showMenu(level3, 400);
                isShowLevel3 = true;
            }
            isShowMenu = !isShowMenu;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getControlReference() {
        level1 = (RelativeLayout) findViewById(R.id.level1);
        level2 = (RelativeLayout) findViewById(R.id.level2);
        level3 = (RelativeLayout) findViewById(R.id.level3);
        iv_home = (ImageView) findViewById(R.id.iv_home);
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
    }

    private void initComponent() {

    }

    private void getControlEvents() {
        iv_home.setOnClickListener(this);
        iv_menu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_home:
                if(AnimUtil.animCount!=0){
                    return;
                }
                if (isShowLevel2) {
                    int startOffset = 0;
                    if (isShowLevel3) {
                        startOffset += 200;
                        AnimUtil.closeMenu(level3, 0);//三级需要隐藏
                        isShowLevel3 = false;
                    }
                    AnimUtil.closeMenu(level2, startOffset);//需要隐藏
                } else {
//                    int startOffset = 0;
//                    if (!isShowLevel3) {
//                        startOffset += 200;
//                        AnimUtil.showMenu(level3, startOffset);//三级需要显示
//                        isShowLevel3 = true;
//                    }
                    AnimUtil.showMenu(level2, 0);//需要显示
                }
                isShowLevel2 = !isShowLevel2;
                break;
            case R.id.iv_menu:
                if(AnimUtil.animCount!=0){
                    return;
                }
                if (isShowLevel3) {
                    AnimUtil.closeMenu(level3, 0);//需要隐藏
                } else {
                    AnimUtil.showMenu(level3, 0);//需要显示
                }
                isShowLevel3 = !isShowLevel3;
                break;
        }
    }
}
