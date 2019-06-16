package com.physical.app.common.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.physical.app.R;

/**
 * @author jjj
 * 版本：1.0
 * 创建日期：2019/6/16
 * 描述：
 */
public class SelectNetorNoneDialog  extends AlertDialog {
    private Context context;
    private LayoutInflater inflater;
    private String title ,desp;

    private TextView tvDesp;

    private ComDialog.Callback callback;
    private TextView tvTitle;
    private View tv_wifi;
    private View tv_no_net;

    public SelectNetorNoneDialog(Context context, String title,String desp, ComDialog.Callback callback) {
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
        View view = inflater.inflate(R.layout.dialog_select_net, null);
        tvTitle =view.findViewById(R.id.tv_title);
        tvDesp=view.findViewById(R.id.tvDesp);
        tv_wifi =view.findViewById(R.id.tv_wifi);
        tv_no_net =view.findViewById(R.id.tv_no_net);
        setContentView(view);
        setCanceledOnTouchOutside(false);
    }

    private void initData() {
        tvDesp.setText(desp);
        tvTitle.setText(title);
    }

    private void addListener() {
        tv_wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                callback.callback(0);
            }
        });
        tv_no_net.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                callback.callback(1);
            }
        });
    }

    public interface Callback {
        void callback(int param);
    }
}
