package com.physical.app.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.physical.app.common.utils.StringUtil;
import com.physical.app.common.utils.ToastUtil;

import java.util.List;

/**
 * Created by jjj
 * 时间:  2019/5/21
 * 邮箱: jujiaojiao@gemdale.com
 * 描述: 充值弹窗
 */

public class RechargeDialog extends Dialog implements View.OnClickListener {
    private Context context;

    private TextView tvCancel;
    private TextView tvConfirm;
    private Callback callback;
    private EditText tvMoney;
    private EditText tvTime;

    public RechargeDialog(@NonNull Context context,Callback callback) {
        super(context, R.style.dialog);
        this.context = context;
        this.callback = callback;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_recharge);
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

        tvMoney = ((EditText) findViewById(R.id.et_money));
        tvTime = ((EditText) findViewById(R.id.et_time));
        tvCancel = ((TextView) findViewById(R.id.tv_cancel));
        tvConfirm = ((TextView) findViewById(R.id.tv_confirm));
        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        addListener();
    }

    private void addListener(){

        tvMoney.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 监听到回车键，会执行2次该方法。按下与松开
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        //松开事件
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(tvMoney.getWindowToken(), 0); //强制隐藏键盘
                    }
                }
                return false;

            }
        });

        tvTime.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 监听到回车键，会执行2次该方法。按下与松开
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        //松开事件
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(tvTime.getWindowToken(), 0); //强制隐藏键盘
                    }
                }
                return false;

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                callback.onCancel();
                break;
            case R.id.tv_confirm:
                String money  = tvMoney.getText().toString();
                String time  = tvTime.getText().toString();
                if (StringUtil.isEmpty(money)){
                    ToastUtil.show("请输入金额");
                    return;
                }
                if (StringUtil.isEmpty(time)){
                    ToastUtil.show("请输入充值次数");
                    return;
                }
                callback.onConfirm(money,time);
                break;
        }
    }
    public interface Callback {
        void onConfirm(String money,String time);

        void onCancel();
    }
}
