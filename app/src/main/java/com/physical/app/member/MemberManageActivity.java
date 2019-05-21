package com.physical.app.member;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.physical.app.R;
import com.physical.app.adapter.MemberManageAdapter;
import com.physical.app.bean.MemberManageBean;
import com.physical.app.common.base.BaseActivity;
import com.physical.app.music.LocalMucicActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author jjj
 * 版本：1.0
 * 创建日期：2019/5/19
 * 描述：会员信息管理
 */
public class MemberManageActivity extends BaseActivity {
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.ivRight)
    ImageView ivRight;
    @Bind(R.id.tv_search)
    TextView tvSearch;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_certi)
    TextView tvCerti;
    @Bind(R.id.tv_times)
    TextView tvTimes;
    @Bind(R.id.tv_operate)
    TextView tvOperate;
    @Bind(R.id.lv_data)
    ListView lvData;
    @Bind(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    private MemberManageAdapter adapter;
    private ArrayList<MemberManageBean> list;

    public static void start(Context context){
        Intent intent = new Intent(context,MemberManageActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_manage);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        list = new ArrayList<>();
        list.add(new MemberManageBean());
        list.add(new MemberManageBean());
        list.add(new MemberManageBean());
        list.add(new MemberManageBean());
        list.add(new MemberManageBean());
        list.add(new MemberManageBean());
        list.add(new MemberManageBean());
        list.add(new MemberManageBean());
        list.add(new MemberManageBean());
        adapter = new MemberManageAdapter(this, list);
        lvData.setAdapter(adapter);

    }
}
