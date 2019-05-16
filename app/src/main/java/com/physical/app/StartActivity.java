package com.physical.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.physical.app.common.base.BaseActivity;

/**
 * Created by jjj
 * 时间:  2019/5/16
 * 邮箱: jujiaojiao@gemdale.com
 * 描述:
 */

public class StartActivity extends BaseActivity {


    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //全屏

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_start);


        initData();

    }


    private void initData() {

        handler.sendEmptyMessageDelayed(100, 2000);

    }


    @SuppressLint("HandlerLeak")

    private Handler handler = new Handler() {

        @Override

        public void handleMessage(Message msg) {

            super.handleMessage(msg);

            switch (msg.what) {

                case 100:

                    startTo(MainActivity.class);

                    finish();

                    break;

                default:

                    break;

            }

        }

    };
}
