package com.physical.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.physical.app.adapter.GlideImageLoader;
import com.physical.app.bean.AdvertisementBean;
import com.physical.app.callback.IStartCallback;
import com.physical.app.common.base.BaseActivity;
import com.physical.app.common.constains.Constains;
import com.physical.app.common.utils.ImageManager;
import com.physical.app.common.utils.NetworkUtils;
import com.physical.app.common.utils.Preferences;
import com.physical.app.common.widget.ComDialog;
import com.physical.app.common.widget.NetDialog;
import com.physical.app.common.widget.SelectNetorNoneDialog;
import com.physical.app.presenter.StartPresenter;
import com.physical.app.setting.WifiActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jjj
 * 时间:  2019/5/16
 * 邮箱: jujiaojiao@gemdale.com
 * 描述:
 */

public class StartActivity extends BaseActivity implements IStartCallback, ViewPager.OnPageChangeListener {

    @Bind(R.id.banner)
    Banner banner;
    @Bind(R.id.iv_logo)
    ImageView iv_logo;
    private StartPresenter startPresenter;
    private SelectNetorNoneDialog comDialog;
    private NetDialog netDialog;
    private List<String> imgPathList;

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
            //弹窗判断是打开WiFi 还是断网模式
            //打开连接WiFi页
            showDialog();
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
                String string = Preferences.getString(Constains.CODE);
                if (null != string && string.equals(code)) {
                    MainActivity.start(StartActivity.this);
                } else {
                    showToast("无法进入断网模式");
                }
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
        imgPathList = bean.imgPathList;
        if (null != bean && bean.imgPathList.size() > 0) {
            banner.setVisibility(View.VISIBLE);
            iv_logo.setVisibility(View.GONE);
            initBanner();
        } else {
            handler.sendEmptyMessageDelayed(100, 2000);
        }
    }

    private void initBanner() {
        //设置banner样式(显示圆形指示器)
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置指示器位置（指示器居右）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
//        banner.setViewPagerIsScroll(false);
        //设置图片集合
        banner.setImages(imgPathList);
        //设置banner动画效果
//        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
//        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2000);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == (imgPathList.size()-1)) {
            banner.stopAutoPlay();
            if (null!=handler){
                handler.sendEmptyMessageDelayed(100, 1000);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != handler) {
            handler = null;
        }
    }
}
