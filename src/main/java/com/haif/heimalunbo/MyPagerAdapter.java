package com.haif.heimalunbo;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by haif on 2017/10/24.
 */

public class MyPagerAdapter extends PagerAdapter {

    ArrayList<ImageView> imageList;

    public MyPagerAdapter(ArrayList<ImageView> imageList) {
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
//        return imageList.size();
        return Integer.MAX_VALUE;
    }

    //当滑到新的条目，又返回来，view是否可以被复用。返回判断规则。（固定写法）
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.i("Adapter", "instantiate: " + position);
//        ImageView imageView = imageList.get(position);
        int newPostion = position % imageList.size();
        ImageView imageView = imageList.get(newPostion);

        container.addView(imageView);
        return imageView;   //必须返回，否则报错
    }

    //固定写法
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.i("Adapter", "destroy: " + position);
        container.removeView((View) object);
    }
}
