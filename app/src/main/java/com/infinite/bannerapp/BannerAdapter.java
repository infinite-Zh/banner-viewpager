package com.infinite.bannerapp;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 19082 on 2017/6/14.
 */

public class BannerAdapter extends PagerAdapter{

    private List<View> mViews=new ArrayList<>();

    public BannerAdapter(List<View> views){
        mViews=views;
    }
    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {

        return view==o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=mViews.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view=mViews.get(position);
        container.removeView(view);
    }
}
