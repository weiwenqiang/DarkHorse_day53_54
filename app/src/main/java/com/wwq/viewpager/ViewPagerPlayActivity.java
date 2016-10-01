package com.wwq.viewpager;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wwq.darkhorseday53.R;
import com.wwq.entity.AdEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 魏文强 on 2016/7/30.
 */
public class ViewPagerPlayActivity extends Activity {
    private ViewPager viewPager;
    private TextView tv_intro;
    private LinearLayout dot_layout;

    private List<AdEntity> list = new ArrayList<AdEntity>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            handler.sendEmptyMessageDelayed(0, 4000);
        }
    };

    private void initView() {
        setContentView(R.layout.day53_img_play);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tv_intro = (TextView) findViewById(R.id.tv_intro);
        dot_layout = (LinearLayout) findViewById(R.id.dot_layout);
    }

    private void initListener() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //开始位置是0，但不调用，后续滑动将调用
                Log.e("position", "position:" + position);
                updateIntroAndDot();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        list.add(new AdEntity(R.drawable.a, "巩俐不低俗，我就不能低俗"));
        list.add(new AdEntity(R.drawable.b, "朴树又回来了，再唱经典老歌引百万人同唱"));
        list.add(new AdEntity(R.drawable.c, "揭秘北京电影如何升级"));
        list.add(new AdEntity(R.drawable.d, "乐视网TV版免费放送"));
        list.add(new AdEntity(R.drawable.e, "热血屌丝的反击"));

        initDots();

        viewPager.setAdapter(new MyPagerAdapter());

        int centerValue = Integer.MAX_VALUE / 2;
        int value = centerValue % list.size();
        viewPager.setCurrentItem(centerValue - value);

        handler.sendEmptyMessageDelayed(0, 4000);
    }

    private void initDots() {
        for (int i = 0; i < list.size(); i++) {
            View view = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(8, 8);
            if (i != 0) {
                params.leftMargin = 5;
            }
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.selector_dot);
            dot_layout.addView(view);
        }
    }

    private void updateIntroAndDot() {
        int currentPage = viewPager.getCurrentItem() % 5;
        tv_intro.setText(list.get(currentPage).getIntro());
        for (int i = 0; i < dot_layout.getChildCount(); i++) {
            dot_layout.getChildAt(i).setEnabled(i == currentPage);
        }
    }

    class MyPagerAdapter extends PagerAdapter {
        //返回多少page
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            //true:表示不去创建，使用缓存；false：重新创建
            return view == object;
        }

        //类似于BaseAdapter的getView方法,用来将数据设置给view
        //由于它最多就3个界面，所以不需要viewHolder
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(ViewPagerPlayActivity.this, R.layout.item_viewpager_adapter, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);


            AdEntity ad = list.get(position % 5);

            imageView.setImageResource(ad.getIconResId());

            container.addView(view);//一定不能少，将view加入到

            return view;
        }

        //销毁page， position：当前需要消耗第几个page object：当前需要消耗的page
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
        }
    }
}
