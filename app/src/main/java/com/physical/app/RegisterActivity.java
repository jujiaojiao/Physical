package com.physical.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.physical.app.common.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {
    @Bind(R.id.iv_title)
    ImageView ivTitle;
    @Bind(R.id.iv_circle)
    ImageView ivCircle;
    @Bind(R.id.iv_bottom)
    ImageView ivBottom;
    @Bind(R.id.et_user)
    EditText etUser;//姓名
    @Bind(R.id.ll_user)
    LinearLayout llUser;//主人
    @Bind(R.id.et_phone)
    EditText etPhone;//手机号
    @Bind(R.id.ll_phone)
    LinearLayout llPhone;
    @Bind(R.id.et_pwd)
    EditText etPwd;//密码
    @Bind(R.id.ll_pwd)
    LinearLayout llPwd;
    @Bind(R.id.et_repwd)
    EditText etRepwd;//新密码
    @Bind(R.id.ll_repwd)
    LinearLayout llRepwd;//重复输入密码
    @Bind(R.id.et_code)
    EditText etCode;//验证码
    @Bind(R.id.ll_code)
    LinearLayout llCode;//
    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Bind(R.id.tv_register)
    TextView tvRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.tv_login,R.id.tv_register})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.tv_login:

                break;
            case R.id.tv_register:

                break;
        }
    }

}
