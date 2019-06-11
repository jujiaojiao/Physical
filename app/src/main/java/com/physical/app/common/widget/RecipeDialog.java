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
import com.physical.app.bean.RecommendBean;
import com.physical.app.bean.SeedlingBean;

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
    private List<SeedlingBean> datas;
    private Callback callback;
    private TextView tvName;
    private List<RecommendBean>  name;

    public RecipeDialog(@NonNull Context context, List<RecommendBean> name, List<SeedlingBean> datas, Callback callback) {
        super(context, R.style.dialog);
        mContext = context;
        this.datas = datas;
        this.name = name;
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
        tvName = ((TextView) findViewById(R.id.tv_name));
        StringBuffer recipe = new StringBuffer();
        if (name.size()>0){
            for (RecommendBean recommendBean : name) {
                recipe.append(recommendBean.hisName+"  ");
            }
            tvName.setText(recipe.toString());
        }

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
