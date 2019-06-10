package com.physical.app.member;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.physical.app.AppData;
import com.physical.app.R;
import com.physical.app.adapter.MemberManageAdapter;
import com.physical.app.bean.MemberManageBean;
import com.physical.app.bean.MemberVo;
import com.physical.app.callback.IMemberManageCallback;
import com.physical.app.common.base.BaseActivity;
import com.physical.app.common.utils.StringUtil;
import com.physical.app.common.widget.RechargeDialog;
import com.physical.app.presenter.MemberManagePresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jjj
 * 版本：1.0
 * 创建日期：2019/5/19
 * 描述：会员信息管理
 */
public class MemberManageActivity extends BaseActivity implements IMemberManageCallback {
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
    @Bind(R.id.tv_no_data)
    TextView tv_no_data;
    @Bind(R.id.lv_data)
    ListView lvData;
    @Bind(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    @Bind(R.id.rlTop)
    RelativeLayout rlTop;
    @Bind(R.id.iv_title)
    ImageView ivTitle;
    @Bind(R.id.tv_choose)
    TextView tvChoose;
    @Bind(R.id.iv_sure)
    ImageView iv_sure;
    private MemberManageAdapter adapter;
    private List<MemberVo> list;
    private MemberManagePresenter memberManagePresenter;
    private String keyword;
    private int  pageNum = 1;
    private int  pageSize = 20;
    private String type;
    private RechargeDialog rechargeDialog;
    private String totalmoney;
    private String vipTimes;

    public static void start(Context context,String type) {
        Intent intent = new Intent(context, MemberManageActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_manage);
        ButterKnife.bind(this);
        initView();
        addListener();
    }

    private void initView() {
        type = getIntent().getStringExtra("type");
        ClassicsFooter footer = new ClassicsFooter(mContext);
        footer.setFinishDuration(0);
        ClassicsHeader header = new ClassicsHeader(mContext);
        header.setFinishDuration(0);
        refreshlayout.setRefreshHeader(header);
        refreshlayout.setRefreshFooter(footer);
//        refreshlayout.setDisableContentWhenRefresh(true);
//        refreshlayout.setDisableContentWhenLoading(true);
        refreshlayout.setEnableRefresh(false);
//        refreshlayout.setEnableLoadmore(false);
        ivTitle.setImageResource(R.mipmap.word_vip);
        list = new ArrayList<>();
        if (StringUtil.isEmpty(type)){
            tvChoose.setVisibility(View.GONE);
            iv_sure.setVisibility(View.GONE);
        }else {
            tvChoose.setVisibility(View.VISIBLE);
            iv_sure.setVisibility(View.VISIBLE);

        }
        adapter = new MemberManageAdapter(this, list, type, new MemberManageAdapter.Callback() {
            @Override
            public void recharge(int position) {
                MemberVo data = list.get(position);
                showRechargeDialog(data.mobile);
            }

            @Override
            public void onchange(int position) {
                MemberVo data = list.get(position);
                AddMemberActivity.start(MemberManageActivity.this,data);
            }

            @Override
            public void onwatch(int position) {
                MemberVo data = list.get(position);
                MemberDetailActivity.start(MemberManageActivity.this);

            }
        });
        lvData.setAdapter(adapter);

        memberManagePresenter = new MemberManagePresenter(this,this);

    }
    private void showRechargeDialog(final String phone) {
        rechargeDialog = new RechargeDialog(this, new RechargeDialog.Callback() {

            @Override
            public void onConfirm(String money, String time) {
                totalmoney = money;
                vipTimes = time;
                if (StringUtil.isEmpty(phone)){
                    showToast("请输入手机号码");
                    return;
                }
                memberManagePresenter.charge(getWifiMac(),phone, money, time,getUserId());
                rechargeDialog.dismiss();
            }

            @Override
            public void onCancel() {
                rechargeDialog.dismiss();
            }
        });
        rechargeDialog.show();
    }



    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void addListener() {
//        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                AddMemberActivity.start(MemberManageActivity.this);
//                MemberDetailActivity.start(MemberManageActivity.this);
//            }
//        });

        refreshlayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshLayout) {
                loadMore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refresh();
            }
        });
    }


    private void request(){
        memberManagePresenter.queryMemberList(getUserId(), keyword,""+pageNum,""+ pageSize);
    }

    @OnClick({R.id.ivRight,R.id.ivBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivRight:
                AddMemberActivity.start(this,null);
                break;
            case R.id.ivBack:
                finish();
                break;
        }
    }

    private void loadMore() {
        pageNum++;
        request();
    }

    private void refresh() {
        pageNum = 1;
        list.clear();
        adapter.notifyDataSetChanged();
        request();
    }



    private void onComplete() {
        refreshlayout.finishRefresh();
        refreshlayout.finishLoadmore();
    }

    @Override
    public void onMemberListSuccess(List<MemberVo> bean) {
        if (null!=bean&&bean.size()>0){
            if (bean.size()<pageSize){
                refreshlayout.setEnableLoadmore(false);
            }else {
                refreshlayout.setEnableLoadmore(true);
            }
            list.addAll(bean);
            adapter.notifyDataSetChanged();
        }else {
            refreshlayout.setEnableLoadmore(false);
        }
    }

    @Override
    public void onFinish() {
        onComplete();
    }

    @Override
    public void onchargeSuccess() {
        showToast("充值成功");
    }
}
