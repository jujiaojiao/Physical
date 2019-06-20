package com.physical.app;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.physical.app.bean.MemberVo;
import com.physical.app.bean.RecommendBean;
import com.physical.app.bean.SeedlingBean;
import com.physical.app.callback.IMainCallback;
import com.physical.app.callback.ISeedlingCallback;
import com.physical.app.common.base.BaseActivity;
import com.physical.app.common.constains.Constains;
import com.physical.app.common.utils.NetworkUtils;
import com.physical.app.common.utils.Preferences;
import com.physical.app.common.widget.ComDialog;
import com.physical.app.common.widget.NetDialog;
import com.physical.app.common.widget.SelectNetorNoneDialog;
import com.physical.app.common.widget.SystemSetDialog;
import com.physical.app.member.MemberManageActivity;
import com.physical.app.music.LocalMucicActivity;
import com.physical.app.physical.PhysicalActivity;
import com.physical.app.presenter.MainPresenter;
import com.physical.app.presenter.SeedlingPresenter;
import com.physical.app.setting.SettingActivity;
import com.physical.app.setting.WifiActivity;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements ISeedlingCallback, IMainCallback {

    @Bind(R.id.iv_head)
    ImageView ivHead;//头像
    @Bind(R.id.tv_username)
    TextView tvUsername;//用户名
    @Bind(R.id.ll_local_music)
    LinearLayout llLocalMusic;//本地音乐
    @Bind(R.id.ll_phsical)
    LinearLayout llPhsical;//理疗
    @Bind(R.id.ll_vip)
    LinearLayout llVip;//会员
    @Bind(R.id.ll_set)
    LinearLayout llSet;//设置
    @Bind(R.id.ll_qq_music)
    LinearLayout llQqMusic;//qq音乐
    @Bind(R.id.iv_menu)
    ImageView ivMenu;//菜单
    @Bind(R.id.iv_switch)
    ImageView ivSwitch;//门磁开关
    private SystemSetDialog systemSetDialog;


    private int door = 0;//0 关  1开
    private NetDialog netDialog;
    private SeedlingPresenter seedlingPresenter;
    private SelectNetorNoneDialog comDialog;
    private MainPresenter mainPresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        getWindow().setEnterTransition(new Slide().setDuration(2000));
//        getWindow().setExitTransition(new Slide().setDuration(2000));
        Log.i("jjj", "onCreate: ");
        if (null != getUser()) {
            tvUsername.setText(getUser().userName);
        }
        mainPresenter = new MainPresenter(this, this);
//        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        if (NetworkUtils.isNetworkAvailable(this)) {
            seedlingPresenter = new SeedlingPresenter(this, this);
            Log.i("jjj", "initView: " + getWifiMac());
            seedlingPresenter.seedling(getUserId());
//            List<MemberVo> nonet = Preferences.getList(this, "nonet");
//            if (nonet.size() > 0) {
//                mainPresenter.upload(toJson(nonet), getUserId());
//            }
//            有网调用上传进度接口，更新进度 ，点击开始理疗时保存进度  保存成列表 每次都添加，在上传进度接口调用成功后，清除记录
        } else {
            String timeCode = Preferences.getString(Constains.TIMECODE);
            if (null == timeCode || !timeCode.equals(getCurrentTime())) {
                showDialog();
            }
        }
    }

    private String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }


    @OnClick({R.id.ll_local_music, R.id.ll_phsical, R.id.ll_vip, R.id.ll_set, R.id.ll_qq_music, R.id.iv_menu, R.id.iv_switch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_local_music:
//                LocalMucicActivity.start(this);
                Intent musicIntent = new Intent("android.intent.action.MUSIC_PLAYER");
                startActivity(musicIntent);
                break;
            case R.id.ll_phsical:
                PhysicalActivity.start(this);
                break;
            case R.id.ll_vip:
                MemberManageActivity.start(this, "");
                break;
            case R.id.ll_set:
                SettingActivity.start(this);
                break;
            case R.id.ll_qq_music:
//                Intent intent = new Intent();
//                //包名 包名+类名（全路径）
//                intent.setClassName("com.demo.surfaceviewdemo", "com.demo.surfaceviewdemo.JumpTestActivity");
//                startActivity(intent);
                if (checkPackInfo("com.tencent.qqmusic")) {
                    openPackage(this, "com.tencent.qqmusic");
                } else {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse("https://sj.qq.com/myapp/detail.htm?apkName=com.tencent.qqmusic");
                    intent.setData(content_url);
                    startActivity(intent);
                }
                break;
            case R.id.iv_menu://菜单
                showSystemDilaog();
//                showNetDilaog();
                break;
            case R.id.iv_switch://门磁开关
                if (door == 0) {
//                    ivSwitch.setImageResource();//开的图标
                    door = 1;
                } else {
//                    ivSwitch.setImageResource();//关的图标
                    door = 0;
                }
                break;
        }
    }

    private void showSystemDilaog() {
        systemSetDialog = new SystemSetDialog(this, new SystemSetDialog.Callback() {
            @Override
            public void onConfirm() {
                systemSetDialog.dismiss();
            }

            @Override
            public void onCancel() {
                systemSetDialog.dismiss();
            }
        });
        systemSetDialog.show();
    }

    private void showDialog() {
        comDialog = new SelectNetorNoneDialog(this, "温馨提示", "网络连接已断开，请选择连接Wifi或使用断网模式", new ComDialog.Callback() {
            @Override
            public void callback(int param) {
                if (param == 0) {
                    WifiActivity.start(MainActivity.this);
                } else {
                    showNetDilaog();
                }
//                MainActivity.this.finish();
            }
        });
        //设置点击屏幕不消失
        comDialog.setCanceledOnTouchOutside(false);
        //设置点击返回键不消失
        comDialog.setCancelable(false);
        comDialog.show();
    }

    private void showNetDilaog() {
        netDialog = new NetDialog(this, new NetDialog.Callback() {
            @Override
            public void onConfirm(String code) {
                String string = Preferences.getString(Constains.CODE);
                if (null != string && string.equals(code)) {
                    netDialog.dismiss();
//                    MainActivity.start(MainActivity.this);
                } else {
                    showToast("激活码有误请重新输入");
                }
            }

            @Override
            public void onCancel() {
//                netDialog.dismiss();
            }
        });
        //设置点击屏幕不消失
        netDialog.setCanceledOnTouchOutside(false);
        //设置点击返回键不消失
        netDialog.setCancelable(false);
        netDialog.show();
    }

    /**
     * 检查包是否存在
     *
     * @param packname
     * @return
     */
    private boolean checkPackInfo(String packname) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(packname, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }


    public static Intent getAppOpenIntentByPackageName(Context context, String packageName) {
        //Activity完整名
        String mainAct = null;
        //根据包名寻找
        PackageManager pkgMag = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_NEW_TASK);

        @SuppressLint("WrongConstant") List<ResolveInfo> list = pkgMag.queryIntentActivities(intent,
                PackageManager.GET_ACTIVITIES);
        for (int i = 0; i < list.size(); i++) {
            ResolveInfo info = list.get(i);
            if (info.activityInfo.packageName.equals(packageName)) {
                mainAct = info.activityInfo.name;
                break;
            }
        }
        if (TextUtils.isEmpty(mainAct)) {
            return null;
        }
        intent.setComponent(new ComponentName(packageName, mainAct));
        return intent;
    }

    public static Context getPackageContext(Context context, String packageName) {
        Context pkgContext = null;
        if (context.getPackageName().equals(packageName)) {
            pkgContext = context;
        } else {
            // 创建第三方应用的上下文环境
            try {
                pkgContext = context.createPackageContext(packageName,
                        Context.CONTEXT_IGNORE_SECURITY
                                | Context.CONTEXT_INCLUDE_CODE);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return pkgContext;
    }

    public static boolean openPackage(Context context, String packageName) {
        Context pkgContext = getPackageContext(context, packageName);
        Intent intent = getAppOpenIntentByPackageName(context, packageName);
        if (pkgContext != null && intent != null) {
            pkgContext.startActivity(intent);
            return true;
        }
        return false;
    }

    /**
     * 幼苗
     *
     * @param beans
     */
    @Override
    public void onSeedlingSuccess(List<SeedlingBean> beans) {
        Preferences.putList(mContext, "seedling", beans);
    }

    @Override
    public void onRecipeSuccess(List<RecommendBean> beans) {

    }

    /**
     * 上传进度接口
     *
     * @param bean
     */
    @Override
    public void onUploadSuccess(Object bean) {
        Preferences.putList(mContext, "nonet", null);
    }
}
