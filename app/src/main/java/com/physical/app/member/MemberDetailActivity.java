package com.physical.app.member;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.physical.app.R;
import com.physical.app.adapter.MemberDetailAdapter;
import com.physical.app.adapter.MemberDetailRecordAdapter;
import com.physical.app.bean.MemberDetailRecordBean;
import com.physical.app.common.base.BaseActivity;
import com.physical.app.common.widget.MyGridView;
import com.physical.app.common.widget.MyListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jjj
 * 版本：1.0
 * 创建日期：2019/5/25
 * 描述： 会员详情
 */
public class MemberDetailActivity extends BaseActivity {

    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.rlTop)
    RelativeLayout rlTop;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_iden)
    TextView tvIden;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.tv_birthday)
    TextView tvBirthday;
    @Bind(R.id.tv_out)
    TextView tvOut;
    @Bind(R.id.tv_high)
    TextView tvHigh;
    @Bind(R.id.tv_weight)
    TextView tvWeight;
    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.gv_data)
    MyGridView gvData;
    @Bind(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    @Bind(R.id.lv_data)
    MyListView lvData;
    @Bind(R.id.iv_title)
    ImageView ivTitle;
    private MemberDetailAdapter memberDetailAdapter;
    private ArrayList<String> datas;
    private MemberDetailRecordAdapter memberDetailRecordAdapter;
    private ArrayList<MemberDetailRecordBean> records;

    public static void start(Context context) {
        Intent intent = new Intent(context, MemberDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivTitle.setImageResource(R.mipmap.word_details);
        datas = new ArrayList<>();
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        memberDetailAdapter = new MemberDetailAdapter(this, datas);
        gvData.setAdapter(memberDetailAdapter);

        records = new ArrayList<>();
        records.add(new MemberDetailRecordBean());
        records.add(new MemberDetailRecordBean());
        records.add(new MemberDetailRecordBean());
        records.add(new MemberDetailRecordBean());
        records.add(new MemberDetailRecordBean());
        memberDetailRecordAdapter = new MemberDetailRecordAdapter(this, records);
        lvData.setAdapter(memberDetailRecordAdapter);
    }

    @OnClick({R.id.ivBack})
    public  void onClick(View view){
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
        }
    }
}
