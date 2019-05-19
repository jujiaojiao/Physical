package com.physical.app.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.BaseAdapter;

import com.physical.app.R;
import com.physical.app.common.base.BaseActivity;

/**
 * @author jjj
 * 版本：1.0
 * 创建日期：2019/5/19
 * 描述： 系统设置
 */
public class SettingActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }
}
