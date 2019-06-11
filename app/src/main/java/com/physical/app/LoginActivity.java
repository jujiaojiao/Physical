package com.physical.app;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Explode;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.physical.app.callback.ILoginCallback;
import com.physical.app.callback.ISeedlingCallback;
import com.physical.app.common.base.BaseActivity;
import com.physical.app.common.mine.bean.User;
import com.physical.app.common.utils.GenerateValueFiles;
import com.physical.app.common.utils.RegularUtils;
import com.physical.app.common.utils.StringUtil;
import com.physical.app.presenter.LoginPresenter;
import com.physical.app.presenter.SeedlingPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity implements ILoginCallback {
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

    private AnimatorSet animatorSet;
    private LoginPresenter loginPresenter;
    private String phone;
    private String pwd;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getWindow().setEnterTransition(new Explode().setDuration(1000));
        getWindow().setExitTransition(new Explode().setDuration(1000));
//        GenerateValueFiles.main();
//        startAnimator();
        addListener();
    }

    private void addListener(){
        etPhone.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 监听到回车键，会执行2次该方法。按下与松开
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        //松开事件
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(etPhone.getWindowToken(), 0); //强制隐藏键盘
                    }
                }
                return false;

            }
        });
        etPwd.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 监听到回车键，会执行2次该方法。按下与松开
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        //松开事件
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(etPwd.getWindowToken(), 0); //强制隐藏键盘
                    }
                }
                return false;

            }
        });
    }


    private void startAnimator() {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(ivCircle, "alpha", 1, 0, 1, 0, 1, 1, 0, 1, 0, 1);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(ivCircle, "rotation", 0, 180, 0, -180, 0);
        ObjectAnimator scaleXcircle = ObjectAnimator.ofFloat(ivCircle, "scaleX", 1f, 1.5f, 1f, 1.5f, 1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(ivTitle, "scaleX", 1f, 1.5f, 1f, 1.5f, 1f);
        alpha.setRepeatMode(ValueAnimator.RESTART);
        rotation.setRepeatMode(ValueAnimator.RESTART);
        scaleXcircle.setRepeatMode(ValueAnimator.RESTART);
        scaleX.setRepeatMode(ValueAnimator.RESTART);
        alpha.setRepeatCount(Animation.INFINITE);
        rotation.setRepeatCount(Animation.INFINITE);
        scaleXcircle.setRepeatCount(Animation.INFINITE);
        scaleX.setRepeatCount(Animation.INFINITE);
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(alpha, rotation, scaleXcircle, scaleX);
        animatorSet.setDuration(3000);
        animatorSet.start();

    }

    private void stopAnimator() {
        if (null != animatorSet) {
            animatorSet.end();
        }
    }


    @OnClick({R.id.tv_login, R.id.tv_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                login();
                break;
            case R.id.tv_register:
//                RegisterActivity.start(this);
                startActivity(new Intent(this, RegisterActivity.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
        }
    }


    private void request() {
        loginPresenter = new LoginPresenter(this, this);
        loginPresenter.login(getWifiMac(),phone,pwd);
    }


    private void login() {
        phone = etPhone.getText().toString().trim();
        pwd = etPwd.getText().toString().trim();
        if (StringUtil.isEmpty(phone)) {
            showToast("请输入手机号码");
            return;
        }
        if (StringUtil.isEmpty(pwd)) {
            showToast("请输入密码");
            return;
        }
        request();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAnimator();
    }


    @Override
    public void onLoginSuccess(User user) {
        AppData.getInstance().setUser(user);
        startActivity(new Intent(this, MainActivity.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        finish();
    }

    //取消返回键的监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
    
}


