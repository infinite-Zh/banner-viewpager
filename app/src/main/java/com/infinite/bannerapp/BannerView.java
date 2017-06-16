package com.infinite.bannerapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 19082 on 2017/6/14.
 */

public class BannerView extends LinearLayout{

    private static final int DEFAULT_OFFSET=30;
    private static final int DEFAULT_TIME_INTERVAL=3;
    private boolean mAutoScroll=true;

    private float mLeftOffset=DEFAULT_OFFSET;
    private float mRightOffset=DEFAULT_OFFSET;
    private long mTimeInterval=DEFAULT_TIME_INTERVAL;
    public static final String TAG="BannerView";
    private ViewPager mViewPager;
    private BannerAdapter mAdapter;
    private List<View> mViews=new ArrayList<>();

    private ScrollHandler mHandler;

    private Timer mTimer;
    public BannerView(Context context) {
        this(context,null);
    }

    public BannerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public BannerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.BannerView);
        mAutoScroll=array.getBoolean(R.styleable.BannerView_banner_autoScroll,true);
        mLeftOffset=  array.getDimension(R.styleable.BannerView_banner_leftOffset,DEFAULT_OFFSET);
        mRightOffset=  array.getDimension(R.styleable.BannerView_banner_rightOffset,DEFAULT_OFFSET);
        mTimeInterval=array.getInt(R.styleable.BannerView_banner_scrollTimeInterval,DEFAULT_TIME_INTERVAL)*1000;
        array.recycle();
        initViews();
    }

    private void initViews(){
        ((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_banner,this,true);
        mViewPager= (ViewPager) this.findViewById(R.id.bannerPager);
        mViewPager.setPageTransformer(false,new ScaleTransform());
        mViewPager.setOffscreenPageLimit(3);
        LayoutParams lp= (LayoutParams) mViewPager.getLayoutParams();
        lp.leftMargin= (int) mLeftOffset;
        lp.rightMargin= (int) mRightOffset;
        if (mAutoScroll){
            mHandler=new ScrollHandler(this);
            mTimer = new Timer();
            mTimer.schedule(new ScrollTimerTask(),mTimeInterval,mTimeInterval);
        }

    }



    public void setViews(List<View> views){
        mViews.clear();
        if (views!=null){
            mViews.addAll(views);
        }
        setUpViewPager();
    }

    private void setUpViewPager(){
        if (mViewPager==null){
            initViews();
        }
        mAdapter=new BannerAdapter(mViews);
        mViewPager.setAdapter(mAdapter);
    }

    private class ScrollTimerTask extends TimerTask{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
        }
    }

    private int mCurrentIndex=0;
    private static class  ScrollHandler extends Handler{
        private SoftReference<BannerView> sr;
        public ScrollHandler(BannerView view){
            sr=new SoftReference<>(view);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e(TAG,sr.get().mShouldPause+"");
            if (sr.get().mShouldPause){
                return;
            }
            sr.get().mCurrentIndex++;
            if (sr.get().mCurrentIndex>sr.get().getViewSize()-1){
                sr.get().mCurrentIndex=0;
            }
            sr.get().mViewPager.setCurrentItem(sr.get().mCurrentIndex);
        }
    }

    private int getViewSize(){
        return mAdapter.getCount();
    }

    private boolean mShouldPause;
    public void onPause(){
        mShouldPause =true;
    }
    public void onResume(){
        mShouldPause =false;

    }
}
