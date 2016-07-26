package com.example.sxj52.meizhigilr.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sxj52.meizhigilr.BaseApplication;
import com.example.sxj52.meizhigilr.R;
import com.example.sxj52.meizhigilr.model.GanHuo;
import com.example.sxj52.meizhigilr.retrofit.GankRetrofit;
import com.example.sxj52.meizhigilr.retrofit.GankService;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sxj52 on 2016/5/27..
 */
public class SplashActivity extends AppCompatActivity {
    private ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcome_layout);

        init();
    }

    private void init() {
        image = (ImageView) findViewById(R.id.welcome_image);

        GankRetrofit.getRetrofit(getApplicationContext())
                .create(GankService.class)
                .getGanHuo("福利",1,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GanHuo>() {
                    @Override
                    public void onCompleted() {
                        Log.e("666","onCompleted");
                        animateImage();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("666","onError");
                        Glide.with(SplashActivity.this)
                                .load(R.drawable.wall_picture)
                                .into(image);
                        animateImage();
                    }

                    @Override
                    public void onNext(GanHuo ganHuo) {
                        BaseApplication.currentGirl=ganHuo.getResults().get(0).getUrl();
                        Log.e("666","onNext");
                        Glide.with(SplashActivity.this)
                                .load(BaseApplication.currentGirl)
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .into(image);

                    }
                });

    }

    private void animateImage() {
        //缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f,1.1f,1.0f,1.1f,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(2500);
        image.startAnimation(scaleAnimation);

        //缩放动画监听
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                startActivity(new Intent(SplashActivity.this,MainActivity.class));

                overridePendingTransition(0,0);

                SplashActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
