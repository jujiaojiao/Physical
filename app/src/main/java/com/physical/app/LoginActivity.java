package com.physical.app;

import android.content.Context;
import android.content.Intent;
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

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity {
    @Bind(R.id.iv_title)
    ImageView ivTitle;
    @Bind(R.id.iv_circle)
    ImageView ivCircle;
    @Bind(R.id.iv_bottom)
    ImageView ivBottom;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.ll_phone)
    LinearLayout llPhone;
    @Bind(R.id.et_pwd)
    EditText etPwd;
    @Bind(R.id.ll_pwd)
    LinearLayout llPwd;
    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Bind(R.id.tv_register)
    TextView tvRegister;

    public  static void start(Context context){
        Intent intent = new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
