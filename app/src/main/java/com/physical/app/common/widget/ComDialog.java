package com.physical.app.common.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.physical.app.R;


/**
 * 作者：liangzixun
 * 时间：2018/10/8 09:59
 * 描述：
 */
public class ComDialog extends AlertDialog {
    private Context context;
    private LayoutInflater inflater;
    private String title ,desp;

    private TextView tvDesp;
    private TextView tvCancel;
    private TextView tvConfirm;

    private Callback callback;
    private TextView tvTitle;

    public ComDialog(Context context, String title,String desp, Callback callback) {
        super(context, R.style.dialog);
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.desp=desp;
        this.title = title;
        this.callback=callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        addListener();
    }

    private void initView() {
        View view = inflater.inflate(R.layout.dialog_common_desp, null);
        tvTitle =view.findViewById(R.id.tv_title);
        tvDesp=view.findViewById(R.id.tvDesp);
        tvCancel=view.findViewById(R.id.tv_cancel);
        tvConfirm=view.findViewById(R.id.tv_confirm);
        setContentView(view);
        setCanceledOnTouchOutside(false);
    }

    private void initData() {
        tvDesp.setText(desp);
        tvTitle.setText(title);
    }

    private void addListener() {
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                callback.callback(1);
            }
        });
    }

    public interface Callback {
        public void callback(int param);
    }
}
