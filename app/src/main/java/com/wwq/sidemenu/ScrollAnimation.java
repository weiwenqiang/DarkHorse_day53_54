package com.wwq.sidemenu;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by 魏文强 on 2016/9/21.
 */
class ScrollAnimation extends Animation {
    private View view;
    private int targetScrollX;
    private int startScrollX;
    private int totalValue;

    public ScrollAnimation(View view, int targetScrollX){
        super();
        this.view = view;
        this.targetScrollX = targetScrollX;

        startScrollX = view.getScrollX();
        totalValue = this.targetScrollX - startScrollX;

        int time = Math.abs(totalValue);
        setDuration(time);
    }


    //在指定的时间内，一直执行该方法，直到动画结束
    //interpolatedTime： 0~1 标识动画执行的进度或者百分比
    //time : 0 - 0.5 - 1
    //value: 10 - 60 - 110
    // 当前值 = 起始值 + 总的差值
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        int currentScrollX = (int) (startScrollX + totalValue * interpolatedTime);
        view.scrollTo(currentScrollX, 0);
    }
}
