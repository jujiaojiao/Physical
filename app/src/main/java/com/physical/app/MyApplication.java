package com.physical.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;

import com.physical.app.common.constains.Constains;
import com.physical.app.common.utils.Md5Util;
import com.physical.app.common.utils.Preferences;
import com.physical.app.common.utils.StringUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者: liangzixun
 * 时间: 2017/10/27 09:13
 * 邮箱: liangzixun@eims.com.cn
 */
public class MyApplication extends Application {
    public static String BASEPATH = Environment.getExternalStorageDirectory().getPath() + "/ttyyapp/";
    public static Context context;
    public final static boolean DEBUG = true;
    private String wifiMac;

    @Override
    public void onCreate() {
        super.onCreate();
        initData();
        initUmeng();
    }

    private void initUmeng() {
//        String umeng_appkey = "5b347affa40fa374a6000035";
//        String umeng_push_secret = "cb2c3bef600e616a8a0bd719538ba555";
//        UMConfigure.setLogEnabled(true);
//        UMConfigure.init(context, umeng_appkey, "", UMConfigure.DEVICE_TYPE_PHONE, umeng_push_secret);
//        PlatformConfig.setWeixin("wxd75f906c4ce6fdda", "9dcbec79eaa52a38e3c3a73bca12e9bf");
        //豆瓣RENREN平台目前只能在服务器端配置
//        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
//        PlatformConfig.setQQZone("1106891731", "nEijTyKeQgVsuF4i");
//        //友盟推送配置
//        PushAgent mPushAgent = PushAgent.getInstance(this);
//        //注册推送服务，每次调用register方法都会回调该接口
//        mPushAgent.register(new IUmengRegisterCallback() {
//
//            @Override
//            public void onSuccess(String deviceToken) {
//                LogUtil.d("umeng_push",deviceToken);
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//
//            }
//        });
//
//        UmengMessageHandler umengMessageHandler=new UmengMessageHandler(){
//
//            @Override
//            public void dealWithCustomMessage(Context context, UMessage uMessage) {
//                super.dealWithCustomMessage(context, uMessage);
//                Toast.makeText(context, uMessage.custom, Toast.LENGTH_LONG).show();
//                LogUtil.d("umeng_push",uMessage.custom);
//            }
//        };
//        mPushAgent.setMessageHandler(umengMessageHandler);

    }

    private void initData() {
//        LitePal.initialize(this);
        context = this;
        initPath();
        WifiManager wifi = (WifiManager)
                context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        wifiMac = info.getMacAddress();
        Preferences.putString(Constains.WIFIMAC, wifiMac);
        Preferences.putString(Constains.CODE,getCode());

//        CrashReport.initCrashReport(getApplicationContext(), "aa2f75bab1", false);
    }

    public static Context getAppContext() {
        return context;
    }

    private void initPath() {
        File file = new File(BASEPATH);
        if (!file.exists()) {
            file.mkdirs();
        }
    }


    private String  getCode() {
        String sign = wifiMac+getCurrentTime();
        if (!StringUtil.isEmpty(sign)){
            String encode = Md5Util.encode(sign);
            return encode.substring(0,10);
        }
        return "";
    }


    private String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

}
