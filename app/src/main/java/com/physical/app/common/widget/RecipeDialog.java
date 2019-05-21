package com.physical.app.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.TextView;

import com.physical.app.R;
import com.physical.app.adapter.RecipeAdapter;
import com.physical.app.bean.RecipeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jjj
 * 时间:  2019/5/21
 * 邮箱: jujiaojiao@gemdale.com
 * 描述: 处方详情弹窗
 */

public class RecipeDialog extends Dialog implements View.OnClickListener {

    private  Context mContext;
    private GridView gvData;
    private TextView tvConfirm;
    private RecipeAdapter adapter;
    private List<RecipeBean> datas;
    private Callback callback;

    public RecipeDialog(@NonNull Context context, List<RecipeBean> datas,Callback callback) {
        super(context, R.style.dialog);
        mContext = context;
        this.datas = datas;
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_recipe);
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

        gvData = ((GridView) findViewById(R.id.gv_data));
        tvConfirm = ((TextView) findViewById(R.id.tv_confirm));

        adapter = new RecipeAdapter(mContext, datas);
        gvData.setAdapter(adapter);


        tvConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                callback.onConfirm();
                break;
        }
    }

    public interface Callback{
        void onConfirm();
    }
}
