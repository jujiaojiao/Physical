package com.physical.app;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.transition.Fade;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.physical.app.callback.IRegisterCallback;
import com.physical.app.common.base.BaseActivity;
import com.physical.app.common.utils.RegularUtils;
import com.physical.app.common.utils.StringUtil;
import com.physical.app.presenter.RegisterPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements IRegisterCallback {
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
    RelativeLayout llCode;//
    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Bind(R.id.tv_register)
    TextView tvRegister;
    @Bind(R.id.tv_getcode)
    TextView tvGetCode;
    private String code;
    private AnimatorSet animatorSet;
    private RegisterPresenter registerPresenter;
    private String phone;

    public static void start(Context context){
        Intent intent = new Intent(context,RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){

        registerPresenter = new RegisterPresenter(this, this);
//        startAnimator();
//        getWindow().setEnterTransition(new Fade().setDuration(2000));
//        getWindow().setExitTransition(new Fade().setDuration(2000));
        addListener();
    }

    @OnClick({R.id.tv_login,R.id.tv_register,R.id.tv_getcode})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.tv_login:
//                MainActivity.start(this);
                login();
                break;
            case R.id.tv_register:
                //使用已有账号登录
//                LoginActivity.start(this);
                startActivity(new Intent(this, LoginActivity.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

                break;
            case R.id.tv_getcode:
                Log.i("jjj", "onClick: "+etPhone.getText().toString());
                registerPresenter.sendMessage(etPhone.getText().toString(),"0");
//                timer.start();
                break;
        }
    }

    private void addListener(){
        etUser.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 监听到回车键，会执行2次该方法。按下与松开
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        //松开事件
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(etUser.getWindowToken(), 0); //强制隐藏键盘
                    }
                }
                return false;

            }
        });
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
        etRepwd.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 监听到回车键，会执行2次该方法。按下与松开
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        //松开事件
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(etRepwd.getWindowToken(), 0); //强制隐藏键盘
                    }
                }
                return false;

            }
        });
        etCode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 监听到回车键，会执行2次该方法。按下与松开
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        //松开事件
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(etCode.getWindowToken(), 0); //强制隐藏键盘
                    }
                }
                return false;

            }
        });
    }
    CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            int time = (int) (millisUntilFinished / 1000);
            tvGetCode.setText(time + "秒后重发");
            tvGetCode.setEnabled(false);
//            tvGetmsg.setBackgroundResource(R.drawable.);
        }

        @Override
        public void onFinish() {
            tvGetCode.setText("重新发送");
            tvGetCode.setEnabled(true);
//            tvGetmsg.setBackgroundResource(R.drawable.bg_333_12);

        }
    };


    private void startAnimator(){
        ObjectAnimator alpha = ObjectAnimator.ofFloat(ivCircle,"alpha",1,0,1,0,1,1,0,1,0,1);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(ivCircle,"rotation",0,180,0,-180,0);
        ObjectAnimator scaleXcircle = ObjectAnimator.ofFloat(ivCircle, "scaleX", 1f, 1.5f, 1f,1.5f,1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(ivTitle, "scaleX", 1f, 1.5f, 1f,1.5f,1f);
        alpha.setRepeatMode(ValueAnimator.RESTART);
        rotation.setRepeatMode(ValueAnimator.RESTART);
        scaleXcircle.setRepeatMode(ValueAnimator.RESTART);
        scaleX.setRepeatMode(ValueAnimator.RESTART);
        alpha.setRepeatCount(Animation.INFINITE);
        rotation.setRepeatCount(Animation.INFINITE);
        scaleXcircle.setRepeatCount(Animation.INFINITE);
        scaleX.setRepeatCount(Animation.INFINITE);
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(alpha,rotation,scaleXcircle,scaleX);
        animatorSet.setDuration(3000);
        animatorSet.start();
    }

    private void stopAnimator(){
        if (null!= animatorSet){
            animatorSet.end();
        }
    }

    private void login(){
        phone = etPhone.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();
        String name = etUser.getText().toString().trim();
        String repwd = etRepwd.getText().toString().trim();
        code = etCode.getText().toString().trim();
        if (StringUtil.isEmpty(name)){
            showToast("请输入姓名");
            return;
        }
        if (StringUtil.isEmpty(phone)){
            showToast("请输入手机号码");
            return;
        }
        if (!RegularUtils.isMobilePhone(phone)){
            if (StringUtil.isEmpty(phone)){
                showToast("请输入正确的手机号码");
                return;
            }
        }
        if (StringUtil.isEmpty(pwd)){
            showToast("请输入密码");
            return;
        }
        if (StringUtil.isEmpty(repwd)){
            showToast("请再次输入密码");
            return;
        }
        if (StringUtil.isEmpty(code)){
            showToast("请输入验证码");
            return;
        }
        Log.i("jjj", "wifimac: "+getWifiMac());
        registerPresenter.register("+86",getWifiMac(), phone,pwd,repwd,name,code);

    }


    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }
        stopAnimator();
        super.onDestroy();
    }


    /**
     * 注册成功回调
     */
    @Override
    public void onRegisterSuccess() {
        showToast("注册成功");
        finish();
    }

    /**
     * 验证码回调
     */
    @Override
    public void onCodeFinish() {
        timer.start();
    }
}
