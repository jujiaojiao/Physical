package com.physical.app.setting.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.physical.app.R;
import com.physical.app.common.base.BaseFragment;
import com.physical.app.common.widget.ComDialog;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jjj
 * 时间:  2019/5/20
 * 邮箱: jujiaojiao@gemdale.com
 * 描述: 版本更新
 */

public class UpdateFragment extends BaseFragment {
    @Bind(R.id.tv_version_name)
    TextView tvVersionName;
    @Bind(R.id.tv_check_version)
    TextView tvCheckVersion;
    private ComDialog comDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        addListener();
    }

    private void addListener() {
        tvCheckVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDialog();
            }
        });
    }

    private void initData() {

    }

    private void showUpdateDialog(){
        comDialog = new ComDialog(mContext, "检测新版本","是否更新到最新版本？", new ComDialog.Callback() {
            @Override
            public void callback(int param) {

            }
        });
        comDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
