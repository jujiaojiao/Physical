package com.physical.app.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
 *         版本：1.0
 *         创建日期：2019/5/19
 *         描述： 系统设置
 */
public class SettingActivity extends BaseActivity {
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.tvTitle)
    TextView tvTitle;

    @Bind(R.id.tv_wifi)
    TextView tvWifi;//wifi
    @Bind(R.id.tv_manage)
    TextView tvManage;//管理员信息
    @Bind(R.id.tv_recover)
    TextView tvRecover;//恢复出厂设置
    @Bind(R.id.tv_update)
    TextView tvUpdate;//版本更新
    @Bind(R.id.tv_repair)
    TextView tvRepair;//设备报修
    @Bind(R.id.tv_times)
    TextView tvTimes;//理疗次数
    @Bind(R.id.framlayout)
    FrameLayout framlayout;
    private WifiFragment wifiFragment;
    private MangeFragment mangeFragment;
    private RecoverFragment recoverFragment;
    private UpdateFragment updateFragment;
    private RepairFragment repairFragment;
    private TimesFragment timesFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        wifiFragment = new WifiFragment();
        mangeFragment = new MangeFragment();
        recoverFragment = new RecoverFragment();
        updateFragment = new UpdateFragment();
        repairFragment = new RepairFragment();
        timesFragment = new TimesFragment();
    }


    @OnClick({R.id.tv_wifi, R.id.tv_manage, R.id.tv_recover, R.id.tv_update, R.id.tv_repair, R.id.tv_times})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_wifi:
                selectFragment(0);
                break;
            case R.id.tv_manage:
                selectFragment(1);
                break;
            case R.id.tv_recover:
                selectFragment(2);
                break;
            case R.id.tv_update:
                selectFragment(3);
                break;
            case R.id.tv_repair:
                selectFragment(4);
                break;
            case R.id.tv_times:
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
                break;
            case 1:
                if (mangeFragment == null) {
                    mangeFragment = new MangeFragment();
                }
                ft.replace(R.id.framlayout, mangeFragment);
                break;
            case 2:
                if (recoverFragment == null) {
                    recoverFragment = new RecoverFragment();
                }
                ft.replace(R.id.framlayout, recoverFragment);
                break;
            case 3:
                if (updateFragment == null) {
                    updateFragment = new UpdateFragment();
                }
                ft.replace(R.id.framlayout, updateFragment);
                break;
            case 4:
                if (repairFragment == null) {
                    repairFragment = new RepairFragment();
                }
                ft.replace(R.id.framlayout, repairFragment);
                break;
            case 5:
                if (timesFragment == null) {
                    timesFragment = new TimesFragment();
                }
                ft.replace(R.id.framlayout, timesFragment);
                break;
            default:
                break;
        }
        ft.commit();
    }

}
