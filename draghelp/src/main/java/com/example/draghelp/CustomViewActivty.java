package com.example.draghelp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by sxj52 on 2016/10/9.
 */

public class CustomViewActivty extends AppCompatActivity {
    private CustomView mCustomView;
    private int mradiu;
//   private Handler mHandler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            mCustomView.setraid(mradiu);
//        }
//    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customviewactivty);
        mCustomView= (CustomView) findViewById(R.id.customview);
        new Thread(mCustomView).start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(true) {
//                    if (mradiu <= 200) {
//                        mradiu += 10;
//                        mHandler.obtainMessage().sendToTarget();
//                    } else {
//                        mradiu = 0;
//                    }
//                    try {
//                        Thread.sleep(400);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
    }

}
