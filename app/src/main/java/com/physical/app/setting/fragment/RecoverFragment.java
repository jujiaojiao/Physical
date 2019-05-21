package com.physical.app.setting.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.physical.app.R;
import com.physical.app.common.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jjj
 * 时间:  2019/5/20
 * 邮箱: jujiaojiao@gemdale.com
 * 描述: 恢复出厂设置
 */

public class RecoverFragment extends BaseFragment {
    @Bind(R.id.tv_accout)
    TextView tvAccout;
    @Bind(R.id.iv_check)
    ImageView ivCheck;
    @Bind(R.id.ll_recover_crash)
    LinearLayout llRecoverCrash;
    @Bind(R.id.tv_reset)
    TextView tvReset;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recover, null);
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
        llRecoverCrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initData() {


        tvAccout.setText("• 您的 *** 账户\n" +
                "\n" +
                "• 系统及应用数据和设置\n" +
                "\n" +
                "• 已下载的应用\"");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
