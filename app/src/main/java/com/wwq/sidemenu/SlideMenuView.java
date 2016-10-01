package com.wwq.sidemenu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * Created by 魏文强 on 2016/9/21.
 */
public class SlideMenuView extends FrameLayout {
    private View menuView, mainView;
    private int downX;
    private int menuWidth = 0;
    private Scroller scroller;//模拟一个执行流程
    private ScrollAnimation scrollAnimation;

    public SlideMenuView(Context context) {
        super(context);
        init();
    }

    public SlideMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        int measureSpec = MeasureSpec.makeMeasureSpec(menuWidth, MeasureSpec.EXACTLY);
//
//        //测量所有子View的宽高
//        //通过getLayoutParams（）获取布局文件中的宽高
//        menuView.measure(measureSpec, heightMeasureSpec);
////        menuView.measure(menuView.getLayoutParams().width, heightMeasureSpec);
//        //都是充满父窗体，直接使用
//        mainView.measure(widthMeasureSpec, heightMeasureSpec);
//    }

    /**
     * l: 当前子view的左边在父view的坐标系中的x坐标
     * t: 当前子view的顶边在父view的坐标系中的y坐标
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        menuView.layout(-menuWidth, 0, 0, menuView.getMeasuredHeight());
        mainView.layout(0, 0, r, b);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getX();
                int deltaX = moveX - downX;
                int newScrollX = getScrollX() - deltaX;
                Log.e("滑动监听", "deltaX:" + deltaX + "     newScrollX:" + newScrollX);

                if (newScrollX < -menuWidth) {
                    newScrollX = -menuWidth;
                }
                if (newScrollX > 0) {
                    newScrollX = 0;
                }
                scrollTo(newScrollX, 0);
                downX = moveX;
                break;
            case MotionEvent.ACTION_UP:
                //1.使用自定义动画
                //2.使用scroller
//                ScrollAnimation scrollAnimation;
                if (getScrollX() > -menuWidth / 2) {//关闭菜单
                    closeMenu();
                } else {//打开菜单
                    openMenu();
                }
//                startAnimation(scrollAnimation);
                break;
        }
        return true;
    }

    //Scroller不主动去调用这个方法
    //而 invalidate 可以调用这个方法 invalidate->draw->computeScroll
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {//返回true，表示动画没结束
            scrollTo(scroller.getCurrX(), 0);
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = (int) (ev.getX() - downX);
                if (Math.abs(deltaX) > 8) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void init() {
        scroller = new Scroller(getContext());
    }

    //当直接（1级）子View全部加载完调用，可以用初始化的子View的引用
    //这里无法获取子View的宽高
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        menuView = getChildAt(0);
        mainView = getChildAt(1);
        menuWidth = menuView.getLayoutParams().width;
    }

    public void switchMenu() {
        //切换菜单的开和关
        if (getScrollX() == 0) {//需要打开
            openMenu();
        } else {//需要关闭
            closeMenu();
        }
    }

    private void closeMenu() {
//      scrollTo(0, 0);//瞬间回去
//      scrollAnimation = new ScrollAnimation(this, 0);
        scroller.startScroll(getScrollX(), 0, 0 - getScrollX(), 0, 400);
        invalidate();

    }

    private void openMenu() {
//      scrollTo(-menuWidth, 0);//瞬间回去
//      scrollAnimation = new ScrollAnimation(this, -menuWidth);
        scroller.startScroll(getScrollX(), 0, -menuWidth - getScrollX(), 0, 400);
        invalidate();
    }

}
