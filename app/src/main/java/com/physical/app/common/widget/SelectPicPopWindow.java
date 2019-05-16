package com.physical.app.common.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.physical.app.R;


/**
 * 打开相册相机popupwind
 * Created by Administrator on 2017/3/3 0003.
 */

public class SelectPicPopWindow extends BasePopWindow implements View.OnClickListener {
    private Context context;
    private View conentView;

    private TextView tvTake;
    private TextView tvAbuml;
    private TextView tvCancel;


    private Callback callback;

    public SelectPicPopWindow(Context context, Callback callback) {
        super(context);
        this.context = context;
        this.callback = callback;
        initView();
        initData();
        addListener();
    }

    private void addListener() {
        tvTake.setOnClickListener(this);
        tvAbuml.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    private void initData() {

    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.select_pic_popwindow, null);
        tvTake= (TextView) conentView.findViewById(R.id.tvTake);
        tvAbuml= (TextView) conentView.findViewById(R.id.tvAbuml);
        tvCancel= (TextView) conentView.findViewById(R.id.tvCancel);
        setContentView(conentView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        // 设置弹出窗体可点击
        this.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        this.setBackgroundDrawable(dw);
        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.pop_bot_style);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        switch (v.getId()){
            case R.id.tvTake:
                callback.onCallback(1);
                break;
            case R.id.tvAbuml:
                callback.onCallback(2);
                break;
        }
    }

    public interface Callback{
        public void onCallback(int param);
    }
}
