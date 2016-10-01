package com.wwq.toggle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 魏文强 on 2016/9/20.
 */
public class ToggleButton extends View {
    private ToggleState toggleState;
    private Bitmap slideBg;
    private Bitmap switchBg;
    private int currentX;
    private boolean isSliding;

    //测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(switchBg.getWidth(), switchBg.getHeight());
    }

    //绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(switchBg, 0, 0, null);

        if (isSliding) {
            int left = currentX - slideBg.getWidth() / 2;
            if (left < 0) {
                left = 0;
            }
            if (left > (switchBg.getWidth() - slideBg.getWidth())) {
                left = switchBg.getWidth() - slideBg.getWidth();
            }
            canvas.drawBitmap(slideBg, left, 0, null);
        } else {
            if (toggleState == ToggleState.Open) {
                canvas.drawBitmap(slideBg, switchBg.getWidth() - slideBg.getWidth(), 0, null);
            } else {
                canvas.drawBitmap(slideBg, 0, 0, null);
            }
        }
    }

    //触摸
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentX = (int) event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isSliding = true;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                isSliding = false;
                int centerX = switchBg.getWidth() / 2;
                if (currentX > centerX) {
                    if(toggleState!=ToggleState.Open){
                        toggleState = ToggleState.Open;
                        if(listener!=null){
                            listener.onToggleStateChange(toggleState);
                        }
                    }
                } else {
                    if(toggleState != ToggleState.Close){
                        toggleState = ToggleState.Close;
                        if(listener!=null){
                            listener.onToggleStateChange(toggleState);
                        }
                    }
                }
                break;
        }
        invalidate();
        return true;
    }

    //暴露接口
    public interface OnToggleStateChangeListener{
        void onToggleStateChange(ToggleState state);
    }

    private OnToggleStateChangeListener listener;

    public void setOnToggleStateChangListener(OnToggleStateChangeListener listener){
        this.listener = listener;
    }

    //枚举类
    public enum ToggleState {
        Open, Close
    }

    public ToggleButton(Context context) {
        super(context);
    }

    public ToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //设置开关的背景图片
    public void setSlideBackgroudResource(int slideBackgroudResource) {
        slideBg = BitmapFactory.decodeResource(getResources(), slideBackgroudResource);
    }

    //设置滑动块的背景图片
    public void setSwitchBackgroudResource(int switchBackgroudResource) {
        switchBg = BitmapFactory.decodeResource(getResources(), switchBackgroudResource);
    }

    //设置开关的状态
    public void setToggleState(ToggleState state) {
        toggleState = state;
    }
}
