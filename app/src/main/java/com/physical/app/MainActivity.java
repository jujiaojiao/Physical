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

import com.physical.app.bean.RecommendBean;
import com.physical.app.bean.SeedlingBean;
import com.physical.app.callback.ISeedlingCallback;
import com.physical.app.common.base.BaseActivity;
import com.physical.app.common.utils.NetworkUtils;
import com.physical.app.common.utils.Preferences;
import com.physical.app.common.widget.NetDialog;
import com.physical.app.common.widget.SystemSetDialog;
import com.physical.app.member.MemberManageActivity;
import com.physical.app.music.LocalMucicActivity;
import com.physical.app.physical.PhysicalActivity;
import com.physical.app.presenter.SeedlingPresenter;
import com.physical.app.setting.SettingActivity;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements ISeedlingCallback {

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

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getWindow().setEnterTransition(new Slide().setDuration(2000));
        getWindow().setExitTransition(new Slide().setDuration(2000));
        Log.i("jjj", "onCreate: ");
        if (null!=getUser()){
            tvUsername.setText(getUser().userName);
        }
        initData();
    }


    private void initData() {
        if (NetworkUtils.isNetworkAvailable(this)) {
            seedlingPresenter = new SeedlingPresenter(this, this);
            Log.i("jjj", "initView: " + getWifiMac());
            seedlingPresenter.seedling(getUserId());
        }
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

    private void showNetDilaog() {
        netDialog = new NetDialog(this, new NetDialog.Callback() {
            @Override
            public void onConfirm(String code) {

            }

            @Override
            public void onCancel() {

            }
        });
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
}
