package com.physical.app.physical;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.physical.app.R;
import com.physical.app.adapter.PhysicalAdapter;
import com.physical.app.common.base.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jjj
 * 时间:  2019/5/22
 * 邮箱: jujiaojiao@gemdale.com
 * 描述: 理疗详情
 */

public class PhysicalDetailActivity extends BaseActivity {
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.rlTop)
    RelativeLayout rlTop;
    @Bind(R.id.tv_user)
    TextView tvUser;
    @Bind(R.id.gv_data1)
    GridView gvData1;
    @Bind(R.id.iv_off)
    ImageView ivOff;
    @Bind(R.id.iv_on)
    ImageView ivOn;
    @Bind(R.id.tv_time)
    TextView tvTime;
    private PhysicalAdapter adapter;
    private ArrayList<String> list;

    public static void start(Context context){
        Intent intent = new Intent(context,PhysicalDetailActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_detail);
        ButterKnife.bind(this);
        initView();
    }


    private void initView(){
        list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        adapter = new PhysicalAdapter(this, list);
        gvData1.setAdapter(adapter);

    }
}
