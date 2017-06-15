package com.infinite.bannerapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BannerView mBanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBanner= (BannerView) findViewById(R.id.banner);
        List<View> list=new ArrayList<>();
        for (int i=0;i<5;i++){
            LinearLayout ll= (LinearLayout) getLayoutInflater().inflate(R.layout.item_banner,null);
            ImageView img= (ImageView) ll.findViewById(R.id.img);
            img.setImageResource(R.mipmap.a+i);
            list.add(ll);
        }
        mBanner.setViews(list);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mBanner.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBanner.onPause();
    }
}
