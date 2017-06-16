package com.infinite.bannerapp;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by 19082 on 2017/6/15.
 */

public class ScaleTransform implements ViewPager.PageTransformer {
    private static final float SCALE_RATIO=0.8F;
    private float mScaleRatio=SCALE_RATIO;
    @Override
    public void transformPage(View view, float v) {
        if (v<-1){
            view.setScaleY(mScaleRatio);
        }else if (v<=1){
            float scale=Math.max(SCALE_RATIO,1-Math.abs(v));
            view.setScaleY(scale);
        }else {
            view.setScaleY(SCALE_RATIO);
        }


    }

}
