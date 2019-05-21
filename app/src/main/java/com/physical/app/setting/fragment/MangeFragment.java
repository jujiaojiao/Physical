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
import com.physical.app.common.widget.ChangePwdDialog;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jjj
 * 时间:  2019/5/20
 * 邮箱: jujiaojiao@gemdale.com
 * 描述: 管理员信息
 */

public class MangeFragment extends BaseFragment {
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.iv_switch)
    ImageView ivSwitch;
    @Bind(R.id.ll_changePwd)
    LinearLayout llChangePwd;
    private ChangePwdDialog changePwdDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage, null);
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
        llChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangePwdDailog();
            }
        });
    }

    private void initData() {

    }

    private void showChangePwdDailog(){
        changePwdDialog = new ChangePwdDialog(mContext, new ChangePwdDialog.Callback() {
            @Override
            public void onConfirm(String phone, String pwd, String code) {

            }

            @Override
            public void onCancel() {

            }
        });
        changePwdDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
