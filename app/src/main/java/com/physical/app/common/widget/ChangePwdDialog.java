package com.physical.app.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telecom.Call;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.physical.app.R;
import com.physical.app.callback.IRegisterCallback;
import com.physical.app.common.utils.RegularUtils;
import com.physical.app.common.utils.StringUtil;
import com.physical.app.common.utils.ToastUtil;
import com.physical.app.presenter.RegisterPresenter;

import java.util.List;

/**
 * Created by jjj
 * 时间:  2019/5/21
 * 邮箱: jujiaojiao@gemdale.com
 * 描述: 修改密码弹窗
 */

public class ChangePwdDialog extends Dialog implements View.OnClickListener, IRegisterCallback {
    private Context context;
    private EditText etCode;
    private TextView tvCancel;
    private TextView tvConfirm;

    private Callback callback;
    private EditText etPhone;
    private EditText
            etPwd;
    private TextView tvGetcode;
    private RegisterPresenter registerPresenter;
    private String phone;

    public ChangePwdDialog(@NonNull Context context, String phone, Callback callback) {
        super(context, R.style.dialog);
        this.context = context;
        this.phone= phone;
        this.callback = callback;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_change_pwd);
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

        etPhone = ((EditText) findViewById(R.id.et_phone));
        etPwd = ((EditText) findViewById(R.id.et_pwd));
        etCode = ((EditText) findViewById(R.id.et_code));
        tvGetcode = ((TextView) findViewById(R.id.tv_getcode));
        tvCancel = ((TextView) findViewById(R.id.tv_cancel));
        tvConfirm = ((TextView) findViewById(R.id.tv_confirm));
        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        tvGetcode.setOnClickListener(this);

        registerPresenter = new RegisterPresenter(context, this);

        addlistener();
    }

    private void addlistener() {
        etPwd.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 监听到回车键，会执行2次该方法。按下与松开
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        //松开事件
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(etPwd.getWindowToken(), 0); //强制隐藏键盘
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
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
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
            tvGetcode.setText(time + "秒后重发");
            tvGetcode.setEnabled(false);
//            tvGetmsg.setBackgroundResource(R.drawable.);
        }

        @Override
        public void onFinish() {
            tvGetcode.setText("重新发送");
            tvGetcode.setEnabled(true);
//            tvGetmsg.setBackgroundResource(R.drawable.bg_333_12);

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_getcode:
                registerPresenter.sendMessage(phone,"1");
                break;
            case R.id.tv_cancel:
                callback.onCancel();
                break;
            case R.id.tv_confirm:

                String phone = etPhone.getText().toString();
                String pwd = etPwd.getText().toString();
                String code = etCode.getText().toString();
//                if (StringUtil.isEmpty(phone)) {
//                    ToastUtil.show("请输入手机号码");
//                    return;
//                }
//                if (!RegularUtils.isMobilePhone(phone)) {
//                    ToastUtil.show("请输入正确的手机号码");
//                    return;
//                }
                if (StringUtil.isEmpty(pwd)) {
                    ToastUtil.show("请输入密码");
                    return;
                }
                if (StringUtil.isEmpty(code)) {
                    ToastUtil.show("请输入验证码");
                    return;
                }
                callback.onConfirm( pwd, code);

                break;
        }
    }

    @Override
    public void onRegisterSuccess() {

    }

    @Override
    public void onCodeFinish() {
        timer.start();
    }


    public interface Callback {
        void onConfirm(String pwd, String code);

        void onCancel();
    }
}
