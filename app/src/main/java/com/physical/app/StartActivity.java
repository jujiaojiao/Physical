package com.physical.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.physical.app.bean.AdvertisementBean;
import com.physical.app.callback.IStartCallback;
import com.physical.app.common.base.BaseActivity;
import com.physical.app.common.utils.ImageManager;
import com.physical.app.common.utils.NetworkUtils;
import com.physical.app.common.widget.ComDialog;
import com.physical.app.common.widget.NetDialog;
import com.physical.app.common.widget.SelectNetorNoneDialog;
import com.physical.app.presenter.StartPresenter;
import com.physical.app.setting.WifiActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jjj
 * 时间:  2019/5/16
 * 邮箱: jujiaojiao@gemdale.com
 * 描述:
 */

public class StartActivity extends BaseActivity implements IStartCallback {


    @Bind(R.id.iv_ad)
    ImageView ivAd;
    private StartPresenter startPresenter;
    private SelectNetorNoneDialog comDialog;
    private NetDialog netDialog;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //全屏

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);


        initData();

    }


    private void initData() {
        if (NetworkUtils.isNetworkAvailable(this)) {//有网
            //请求广告//有广告展示广告，没广告进行用户登录判断
            startPresenter = new StartPresenter(this, this);
            startPresenter.query(getUserId());
        } else {
            if (null != getUser()) {
                MainActivity.start(this);
            } else {
                LoginActivity.start(this);
            }

            //弹窗判断是打开WiFi 还是断网模式
            //打开连接WiFi页
//           showDialog();
        }


    }


    private void showDialog() {
        comDialog = new SelectNetorNoneDialog(this, "温馨提示", "网络连接已断开，请选择连接Wifi或使用断网模式", new ComDialog.Callback() {
            @Override
            public void callback(int param) {
                if (param == 0) {
                    WifiActivity.start(StartActivity.this);
                } else {
                    showNetDilaog();
                }
            }
        });
        //设置点击屏幕不消失
        comDialog.setCanceledOnTouchOutside(false);
        //设置点击返回键不消失
        comDialog.setCancelable(false);
        comDialog.show();
    }


    @SuppressLint("HandlerLeak")

    private Handler handler = new Handler() {

        @Override

        public void handleMessage(Message msg) {

            super.handleMessage(msg);

            switch (msg.what) {

                case 100:
                    if (null != getUser()) {
                        MainActivity.start(mContext);
                    } else {
                        startTo(LoginActivity.class);
                    }
                    finish();

                    break;

                default:

                    break;

            }

        }

    };

    private void showNetDilaog() {
        netDialog = new NetDialog(this, new NetDialog.Callback() {
            @Override
            public void onConfirm(String code) {
                MainActivity.start(StartActivity.this);
            }

            @Override
            public void onCancel() {

            }
        });
        //设置点击屏幕不消失
        netDialog.setCanceledOnTouchOutside(false);
        //设置点击返回键不消失
        netDialog.setCancelable(false);
        netDialog.show();
    }

    @Override
    public void onQuerySuccess(AdvertisementBean bean) {
        if (null != bean && bean.imgPathList.size() > 0) {
            ivAd.setVisibility(View.VISIBLE);
            ImageManager.Load("http://120.79.18.122:8080/" + bean.imgPathList.get(0), ivAd);
            handler.sendEmptyMessageDelayed(100, 2000);
        } else {
            if (null != getUser()) {
                MainActivity.start(this);
            } else {
                LoginActivity.start(this);
            }
        }
    }
}
