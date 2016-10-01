package com.wwq.utils;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

/**
 * Created by 魏文强 on 2016/7/30.
 */
public class AnimUtil {
    public static int animCount = 0;//记录当前执行的动画数量
    public static void closeMenu(RelativeLayout rl, int startOffset) {
        for(int i = 0; i< rl.getChildCount(); i++){
           rl.getChildAt(i).setEnabled(false);
        }
        RotateAnimation animation = new RotateAnimation(0, -180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 1);
        animation.setDuration(500);
        animation.setFillAfter(true);//动画结束后保持结束的状态
        animation.setStartOffset(startOffset);//延时执行

        animation.setAnimationListener(new MyAnimationListener());//监听动画运行数

        rl.startAnimation(animation);
    }

    public static void showMenu(RelativeLayout rl, int startOffset) {
        for(int i = 0; i< rl.getChildCount(); i++){
            rl.getChildAt(i).setEnabled(true);
        }
        RotateAnimation animation = new RotateAnimation(-180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 1);
        animation.setDuration(500);
        animation.setFillAfter(true);//动画结束后保持结束的状态
        animation.setStartOffset(startOffset);//延时执行

        animation.setAnimationListener(new MyAnimationListener());//监听动画运行数

        rl.startAnimation(animation);
    }

    static class MyAnimationListener implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {
            animCount++;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            animCount--;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
