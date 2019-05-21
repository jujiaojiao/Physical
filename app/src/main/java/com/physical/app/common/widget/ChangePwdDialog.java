package com.physical.app.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telecom.Call;
import android.view.Gravity;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.physical.app.R;
import com.physical.app.common.utils.RegularUtils;
import com.physical.app.common.utils.StringUtil;
import com.physical.app.common.utils.ToastUtil;

import java.util.List;

/**
 * Created by jjj
 * 时间:  2019/5/21
 * 邮箱: jujiaojiao@gemdale.com
 * 描述: 修改密码弹窗
 */

public class ChangePwdDialog  extends Dialog implements View.OnClickListener {
    private Context context;
    private EditText etCode;
    private TextView tvCancel;
    private TextView tvConfirm;

    private Callback callback;
    private EditText etPhone;
    private EditText
            etPwd;
    private TextView tvGetcode;

    public ChangePwdDialog(@NonNull Context context,Callback callback) {
        super(context, R.style.dialog);
        this.context = context;
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_getcode:


                break;
            case R.id.tv_cancel:
                callback.onCancel();
                break;
            case R.id.tv_confirm:

                String phone = etPhone.getText().toString();
                String pwd = etPwd.getText().toString();
                String code = etCode.getText().toString();
                if (StringUtil.isEmpty(phone)) {
                    ToastUtil.show("请输入手机号码");
                    return;
                }
                if (!RegularUtils.isMobilePhone(phone)) {
                    ToastUtil.show("请输入正确的手机号码");
                    return;
                }
                if (StringUtil.isEmpty(pwd)) {
                    ToastUtil.show("请输入密码");
                    return;
                }
                if (StringUtil.isEmpty(code)) {
                    ToastUtil.show("请输入验证码");
                    return;
                }
                callback.onConfirm(phone,pwd,code);
                break;
        }
    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, @Nullable Menu menu, int deviceId) {

    }


    public interface Callback {
        void onConfirm(String phone,String pwd,String code);

        void onCancel();
    }
}
