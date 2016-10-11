package com.example.draghelp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;

import static com.example.draghelp.R.id.seekBar;

/**
 * Created by sxj52 on 2016/10/9.
 */

public class WeavLodingActivty extends AppCompatActivity {
    private WaveLoadingView mWaveLoadingView;
    private SeekBar mSeekBar;
    private  int pre;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mWaveLoadingView.setPercent(pre);
        }
    };

    public WeavLodingActivty() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waveloding_activty);
        mWaveLoadingView= (WaveLoadingView) findViewById(R.id.waveLoading);
        mWaveLoadingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pre=0;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            if (pre < 100) {
                                pre += 10;
                                mHandler.obtainMessage().sendToTarget();
                            }
                            try {
                                Thread.sleep(400);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }).start();
            }
        });
        mSeekBar= (SeekBar) findViewById(seekBar);
         mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

             @Override
         public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
             mWaveLoadingView.setPercent(progress);
         }

         @Override
         public void onStartTrackingTouch(SeekBar seekBar) {

         }

         @Override
         public void onStopTrackingTouch(SeekBar seekBar) {

         }
     });
    }
}
