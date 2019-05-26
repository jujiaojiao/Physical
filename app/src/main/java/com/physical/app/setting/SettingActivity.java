package com.physical.app.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.physical.app.R;
import com.physical.app.common.base.BaseActivity;
import com.physical.app.setting.fragment.MangeFragment;
import com.physical.app.setting.fragment.RecoverFragment;
import com.physical.app.setting.fragment.RepairFragment;
import com.physical.app.setting.fragment.TimesFragment;
import com.physical.app.setting.fragment.UpdateFragment;
import com.physical.app.setting.fragment.WifiFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jjj
 * 版本：1.0
 * 创建日期：2019/5/19
 * 描述： 系统设置
 */
public class SettingActivity extends BaseActivity {
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.framlayout)
    FrameLayout framlayout;
    @Bind(R.id.rlTop)
    RelativeLayout rlTop;
    @Bind(R.id.ll_wifi)
    LinearLayout llWifi;//wifi
    @Bind(R.id.ll_manage)
    LinearLayout llManage;//管理员信息
    @Bind(R.id.ll_recover)
    LinearLayout llRecover;//恢复出厂设置
    @Bind(R.id.ll_update)
    LinearLayout llUpdate;//版本更新
    @Bind(R.id.ll_repair)
    LinearLayout llRepair;//设备报修
    @Bind(R.id.ll_times)
    LinearLayout llTimes;//理疗次数
    @Bind(R.id.iv_title)
    ImageView ivTitle;
    @Bind(R.id.tv_wifi)
    TextView tvWifi;
    private WifiFragment wifiFragment;
    private MangeFragment mangeFragment;
    private RecoverFragment recoverFragment;
    private UpdateFragment updateFragment;
    private RepairFragment repairFragment;
    private TimesFragment timesFragment;

    public static void start(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivTitle.setImageResource(R.mipmap.word_sys);
        wifiFragment = new WifiFragment();
        mangeFragment = new MangeFragment();
        recoverFragment = new RecoverFragment();
        updateFragment = new UpdateFragment();
        repairFragment = new RepairFragment();
        timesFragment = new TimesFragment();
        selectFragment(0);
    }


    @OnClick({R.id.ivBack, R.id.ll_wifi, R.id.ll_manage, R.id.ll_recover, R.id.ll_update, R.id.ll_repair, R.id.ll_times})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ll_wifi:
                selectFragment(0);
                break;
            case R.id.ll_manage:
                selectFragment(1);
                break;
            case R.id.ll_recover:
                selectFragment(2);
                break;
            case R.id.ll_update:
                selectFragment(3);
                break;
            case R.id.ll_repair:
                selectFragment(4);
                break;
            case R.id.ll_times:
                selectFragment(5);
                break;
        }
    }

    public void selectFragment(int position) {//设置传入第几值显示第几个fragment
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        switch (position) {
            case 0:
                if (wifiFragment == null) {
                    wifiFragment = new WifiFragment();
                }
                //将原来的Fragment替换掉---此处R.id.fragmen指的是FrameLayout
                ft.replace(R.id.framlayout, wifiFragment);
                llWifi.setBackgroundResource(R.mipmap.menu_orange);
                llManage.setBackgroundResource(R.mipmap.menu_blue);
                llRecover.setBackgroundResource(R.mipmap.menu_blue);
                llUpdate.setBackgroundResource(R.mipmap.menu_blue);
                llRepair.setBackgroundResource(R.mipmap.menu_blue);
                llTimes.setBackgroundResource(R.mipmap.menu_blue);
                break;
            case 1:
                if (mangeFragment == null) {
                    mangeFragment = new MangeFragment();
                }
                ft.replace(R.id.framlayout, mangeFragment);
                llWifi.setBackgroundResource(R.mipmap.menu_blue);
                llManage.setBackgroundResource(R.mipmap.menu_orange);
                llRecover.setBackgroundResource(R.mipmap.menu_blue);
                llUpdate.setBackgroundResource(R.mipmap.menu_blue);
                llRepair.setBackgroundResource(R.mipmap.menu_blue);
                llTimes.setBackgroundResource(R.mipmap.menu_blue);
                break;
            case 2:
                if (recoverFragment == null) {
                    recoverFragment = new RecoverFragment();
                }
                ft.replace(R.id.framlayout, recoverFragment);
                llWifi.setBackgroundResource(R.mipmap.menu_blue);
                llManage.setBackgroundResource(R.mipmap.menu_blue);
                llRecover.setBackgroundResource(R.mipmap.menu_orange);
                llUpdate.setBackgroundResource(R.mipmap.menu_blue);
                llRepair.setBackgroundResource(R.mipmap.menu_blue);
                llTimes.setBackgroundResource(R.mipmap.menu_blue);
                break;
            case 3:
                if (updateFragment == null) {
                    updateFragment = new UpdateFragment();
                }
                ft.replace(R.id.framlayout, updateFragment);
                llWifi.setBackgroundResource(R.mipmap.menu_blue);
                llManage.setBackgroundResource(R.mipmap.menu_blue);
                llRecover.setBackgroundResource(R.mipmap.menu_blue);
                llUpdate.setBackgroundResource(R.mipmap.menu_orange);
                llRepair.setBackgroundResource(R.mipmap.menu_blue);
                llTimes.setBackgroundResource(R.mipmap.menu_blue);
                break;
            case 4:
                if (repairFragment == null) {
                    repairFragment = new RepairFragment();
                }
                ft.replace(R.id.framlayout, repairFragment);
                llWifi.setBackgroundResource(R.mipmap.menu_blue);
                llManage.setBackgroundResource(R.mipmap.menu_blue);
                llRecover.setBackgroundResource(R.mipmap.menu_blue);
                llUpdate.setBackgroundResource(R.mipmap.menu_blue);
                llRepair.setBackgroundResource(R.mipmap.menu_orange);
                llTimes.setBackgroundResource(R.mipmap.menu_blue);
                break;
            case 5:
                if (timesFragment == null) {
                    timesFragment = new TimesFragment();
                }
                ft.replace(R.id.framlayout, timesFragment);
                llWifi.setBackgroundResource(R.mipmap.menu_blue);
                llManage.setBackgroundResource(R.mipmap.menu_blue);
                llRecover.setBackgroundResource(R.mipmap.menu_blue);
                llUpdate.setBackgroundResource(R.mipmap.menu_blue);
                llRepair.setBackgroundResource(R.mipmap.menu_blue);
                llTimes.setBackgroundResource(R.mipmap.menu_orange);
                break;
            default:
                break;
        }
        ft.commit();
    }

}
