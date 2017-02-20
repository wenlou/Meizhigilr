package com.example.sxj52.meizhigilr.view.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.sxj52.meizhigilr.BaseApplication;
import com.example.sxj52.meizhigilr.R;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by oracleen on 2016/7/4.
 */
public class AboutActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
    }



    private void initView() {
       ImageView mBackdrop= (ImageView) findViewById(R.id.backdrop);
       Toolbar mAboutToolbar= (Toolbar) findViewById(R.id.about_toolbar);
        CollapsingToolbarLayout mToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        mAboutToolbar.setTitle("关于我");
        setSupportActionBar(mAboutToolbar);
        mAboutToolbar.setNavigationIcon(R.drawable.ic_arrow_white_24dp);
        mAboutToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //毛玻璃效果
        Glide.with(this)
                .load(BaseApplication.currentGirl)
                .bitmapTransform(new BlurTransformation(this, 15))
                .into(mBackdrop);
    }


}
