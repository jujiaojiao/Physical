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
import com.physical.app.callback.IUpdatePwdCallback;
import com.physical.app.common.base.BaseFragment;
import com.physical.app.common.utils.WifiUtils;
import com.physical.app.common.widget.ChangePwdDialog;
import com.physical.app.presenter.UpdatePwdPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jjj
 * 时间:  2019/5/20
 * 邮箱: jujiaojiao@gemdale.com
 * 描述: 管理员信息
 */

public class MangeFragment extends BaseFragment implements IUpdatePwdCallback {
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.iv_switch)
    ImageView ivSwitch;
    @Bind(R.id.ll_changePwd)
    LinearLayout llChangePwd;
    private ChangePwdDialog changePwdDialog;
    private UpdatePwdPresenter updatePwdPresenter;

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
        tvName.setText(getUser().userName);
        tvPhone.setText(getUser().mobile);
        updatePwdPresenter = new UpdatePwdPresenter(mContext, this);
    }


    private void showChangePwdDailog() {

        changePwdDialog = new ChangePwdDialog(mContext, getUser().mobile, new ChangePwdDialog.Callback() {
            @Override
            public void onConfirm(String pwd, String code) {
                updatePwdPresenter.updatePwd(pwd, getUserId(), code, getUserId());
                changePwdDialog.dismiss();
            }

            @Override
            public void onCancel() {
                changePwdDialog.dismiss();
            }
        });
        changePwdDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 更新密码回调
     *
     * @param bean
     */
    @Override
    public void onUpdateSuccess(Object bean) {

    }
}
