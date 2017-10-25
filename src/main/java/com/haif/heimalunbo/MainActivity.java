package com.haif.heimalunbo;

import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    ViewPager vp;
    ArrayList<ImageView> imageList;
    //小圆点所在布局
    LinearLayout ll_point;
    TextView tv_title;

    //小圆点集合
    ArrayList<Point> pointList = new ArrayList<>();
    String[] titleArr = null;

    android.os.Handler handler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int curItem = vp.getCurrentItem();
            vp.setCurrentItem(++curItem);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //模仿mvc模式

        //初始化布局（View 视图）
        initView();

        //Model 数据
        initData();

        //Controller 控制器
        initAdapter();
    }

    private void initView() {
        vp = (ViewPager) findViewById(R.id.main_vp);
        ll_point = (LinearLayout) findViewById(R.id.main_ll_cycle);
        tv_title = (TextView) findViewById(R.id.main_tv_title);
    }

    private void initData() {
        //初始化要显示的数据

        //图片资源
        int[] imageResIds = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e};

        //文字资源
        titleArr = new String[]{"title0", "title1", "title2", "title3", "title4"};

        //初始化要显示的ImageView
        ImageView pagerIv;
        ImageView pointIv;
        LinearLayout.LayoutParams params;
        imageList = new ArrayList<>();
        for (int i = 0; i < imageResIds.length; i++) {
            //图片资源
            pagerIv = new ImageView(this);
            pagerIv.setBackgroundResource(imageResIds[i]);
            imageList.add(pagerIv);

            //小圆点资源（指示器），动态添加，因为要依据轮播图个数
            pointIv = new ImageView(this);
            pointIv.setBackgroundResource(R.drawable.points_selector);
            //圆点间设置些间隔
            params = new LinearLayout.LayoutParams(10, 10);
            if (i != 0) {
                params.leftMargin = 20;
            }
            pointIv.setSelected(false);    //默认都是没有选中
            ll_point.addView(pointIv, params);

        }


    }

    private void initAdapter() {
        ll_point.getChildAt(0).setSelected(true);
        tv_title.setText(titleArr[0]);  //文字默认显示第一张

        //设置适配器
        vp.setAdapter(new MyPagerAdapter(imageList));
        //设置ViewPager更新的监听
        vp.setOnPageChangeListener(this);

        //为了实现向左也无限滑动，可以设置初始position为一个很大的值(MAX_VALUE/2，再保证是第一张图片)
        //int pos = Integer.MAX_VALUE/2 - Integer.MAX_VALUE/2%imageList.size();
        //vp.setCurrentItem(pos);
        //但上面设置的值很大，ViewPager会有小bug，这里直接设置个值
        int pos = 5000000 - 500000 % imageList.size();
        vp.setCurrentItem(pos);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //handler.sendEmptyMessage(0);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int curItem = vp.getCurrentItem();
                        vp.setCurrentItem(++curItem);
                    }
                });
            }
        },5000, 5000);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        int newPosition = position % imageList.size();

        tv_title.setText(titleArr[newPosition]);
        for (int i = 0; i < ll_point.getChildCount(); i++) {
            ll_point.getChildAt(i).setSelected(i == newPosition);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
