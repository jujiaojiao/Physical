package com.physical.app.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.physical.app.R;

/**
 * Created by jjj
 * 时间:  2019/5/21
 * 邮箱: jujiaojiao@gemdale.com
 * 描述: 系统设置弹窗
 */

public class SystemSetDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private SeekBar voice;
    private SeekBar light;
    private TextView tvCancel;
    private TextView tvConfirm;
    private Callback callback;

    public SystemSetDialog(@NonNull Context context, Callback callback) {
        super(context, R.style.dialog);
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_system_set);
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

        voice = ((SeekBar) findViewById(R.id.progressBar_voice));
        light = ((SeekBar) findViewById(R.id.progressBar_light));
        tvCancel = ((TextView) findViewById(R.id.tv_cancel));
        tvConfirm = ((TextView) findViewById(R.id.tv_confirm));

        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);

        addListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                callback.onCancel();
                break;
            case R.id.tv_confirm:
                callback.onConfirm();
                break;
        }
    }


    private void addListener() {
        voice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        light.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                txt_cur.setText("当前进度值:" + progress + "  / 100 ");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
//                Toast.makeText(mContext, "触碰SeekBar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                Toast.makeText(mContext, "放开SeekBar", Toast.LENGTH_SHORT).show();
            }

        });
    }


    public interface Callback {
        void onConfirm();

        void onCancel();
    }
}
