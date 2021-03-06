package com.wwq.refresh;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wwq.darkhorseday53.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 魏文强 on 2016/9/21.
 */
public class RefreshListView extends ListView implements AbsListView.OnScrollListener {
    private View headerView;
    private View footerView;
    private ImageView iv_arrow;
    private ProgressBar pb_rotate;
    private TextView tv_state, tv_time;

    private RotateAnimation upAnimation;
    private RotateAnimation dwonAnimation;

    private int headerViewHeight;
    private int footerViewHeight;
    private int downY;//按下时Y坐标
    private boolean isLoadingMore = false;//当前是否正在处于加载更多

    private final int PULL_REFRESH = 0;//下拉刷新状态
    private final int REFRESE_REFRESH = 1;//松开刷新状态
    private final int REFRESHING = 2;//正在刷新状态
    private int currentState = PULL_REFRESH;

    public RefreshListView(Context context) {
        super(context);
        init();
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        setOnScrollListener(this);
        initHeaderView();
        initFooterView();
        initRotateAnimation();
    }

    private void initHeaderView(){
        headerView = View.inflate(getContext(), R.layout.view_header, null);
        iv_arrow = (ImageView) headerView.findViewById(R.id.iv_arrow);
        pb_rotate = (ProgressBar) headerView.findViewById(R.id.pb_rotate);
        tv_state = (TextView) headerView.findViewById(R.id.tv_state);
        tv_time = (TextView) headerView.findViewById(R.id.tv_time);

        headerView.measure(0, 0);//主动通知系统去测量
        headerViewHeight = headerView.getMeasuredHeight();
        headerView.setPadding(0, -headerViewHeight, 0, 0);
        addHeaderView(headerView);
    }

    private void initFooterView(){
        footerView = View.inflate(getContext(), R.layout.view_footer, null);

        footerView.measure(0, 0);//主动通知系统去测量
        footerViewHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0, -footerViewHeight, 0, 0);

        addFooterView(footerView);
    }

    private void initRotateAnimation(){
        upAnimation = new RotateAnimation(0,-180,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,
                RotateAnimation.RELATIVE_TO_SELF,0.5f);
        upAnimation.setDuration(300);
        upAnimation.setFillAfter(true);

                dwonAnimation = new RotateAnimation(-180,-360,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,
                RotateAnimation.RELATIVE_TO_SELF,0.5f);
        dwonAnimation.setDuration(300);
        dwonAnimation.setFillAfter(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(currentState == REFRESHING){
                    break;
                }
                int deltaY = (int) (ev.getY() - downY);
                int paddingTop = -headerViewHeight + deltaY;
                if(paddingTop>-headerViewHeight && getFirstVisiblePosition()==0){
                    headerView.setPadding(0, paddingTop, 0, 0);

                    if(paddingTop>=0 && currentState == PULL_REFRESH){//进入松开刷新状态
                        currentState = REFRESE_REFRESH;
                        refreshHeaderView();
                    }else if(paddingTop<0 && currentState == REFRESE_REFRESH){
                        currentState = PULL_REFRESH;
                        refreshHeaderView();
                    }
                    return true;//拦截TouchMOVE,不让listview处理该次move,会造成listview不能滑动
                }
                break;
            case MotionEvent.ACTION_UP:
                if(currentState ==PULL_REFRESH){
                    headerView.setPadding(0,-headerViewHeight,0,0);
                }else if(currentState ==REFRESE_REFRESH){
                    headerView.setPadding(0,0,0,0);
                    currentState = REFRESHING;
                    refreshHeaderView();

                    //延时
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            completeRefresh();
//                        }
//                    }, 3000);

                    if(listener !=null){
                        listener.onPullRefresh();
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
    //根据currentState来更新headerView
    private void refreshHeaderView(){
        switch(currentState){
            case PULL_REFRESH:
                tv_state.setText("下拉刷新");
                iv_arrow.startAnimation(dwonAnimation);
                break;
            case REFRESE_REFRESH:
                tv_state.setText("松开刷新");
                iv_arrow.startAnimation(upAnimation);
                break;
            case REFRESHING:
                iv_arrow.clearAnimation();//因为向上的旋转动画没有执行完
                iv_arrow.setVisibility(View.INVISIBLE);
                pb_rotate.setVisibility(View.VISIBLE);
                tv_state.setText("正在刷新。。。");

                break;
        }
    }

    //完成刷新操作，重置状态,在获取完数据并更新完Adapter之后，在UI线程调用该方法
    public void completeRefresh(){
        if(isLoadingMore){
            footerView.setPadding(0,0,0,0);
            isLoadingMore = false;
        }else{
            headerView.setPadding(0,-headerViewHeight,0,0);
            currentState = PULL_REFRESH;
            iv_arrow.setVisibility(View.VISIBLE);
            pb_rotate.setVisibility(View.INVISIBLE);
            tv_state.setText("下拉刷新");
            tv_time.setText("最后刷新时间："+getCurrentTime());
        }
    }

    private String getCurrentTime(){
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    private OnRefreshListener listener;
    public void setOnRefreshListener(OnRefreshListener listener){
        this.listener = listener;
    }
    //滚动状态改变回调，
    // SCROLL_STATE_IDLE：闲置状态，就是手指松开；
    // SCROLL_STATE_TOUCH_SCROLL：手指触摸滑动，就是按着来滑动
    // SCROLL_STATE_FLING：快速滑动时松开
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState == OnScrollListener.SCROLL_STATE_IDLE && getLastVisiblePosition()==getCount()-1 && !isLoadingMore){
            isLoadingMore = true;
            footerView.setPadding(0,0,0,0);
            setSelection(getCount());
            if(listener!=null){
                listener.onLoadingMore();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    public interface OnRefreshListener{
        void onPullRefresh();
        void onLoadingMore();
    }
}
