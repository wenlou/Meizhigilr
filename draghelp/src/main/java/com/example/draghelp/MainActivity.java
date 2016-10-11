package com.example.draghelp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    CharAvatarView mAvatarView,mAvatarView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url = "https://avatars2.githubusercontent.com/u/11666634?v=3&s=466";
        mAvatarView = (CharAvatarView) findViewById(R.id.avatar);
        mAvatarView1= (CharAvatarView) findViewById(R.id.avatar1);
        mAvatarView.setText("沈晓健");
        mAvatarView1.setText("谢搜索");
        //Glide.with(this).load(url).into(mAvatarView);
    }


}
