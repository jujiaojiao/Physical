package com.physical.app.setting.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.physical.app.R;
import com.physical.app.adapter.TimesAdapter;
import com.physical.app.bean.TimeBean;
import com.physical.app.callback.ITimeCallback;
import com.physical.app.common.base.BaseFragment;
import com.physical.app.presenter.TimesPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jjj
 * 时间:  2019/5/20
 * 邮箱: jujiaojiao@gemdale.com
 * 描述: 理疗次数
 */

public class TimesFragment extends BaseFragment implements ITimeCallback {
    @Bind(R.id.tv_start_time)
    TextView tvStartTime;
    @Bind(R.id.tv_end_time)
    TextView tvEndTime;
    @Bind(R.id.lv_data)
    ListView lvData;
    @Bind(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    private TimesAdapter adapter;
    private List<TimeBean> list;
    private TimesPresenter timesPresenter;
    private int pageNum = 1;
    private int pageSize = 10;

    private String startTime;
    private String endTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_times, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }


    private void initData() {
        ClassicsFooter footer = new ClassicsFooter(getContext());
        footer.setFinishDuration(0);
        ClassicsHeader header = new ClassicsHeader(getContext());
        header.setFinishDuration(0);
        refreshlayout.setRefreshHeader(header);
        refreshlayout.setRefreshFooter(footer);
//        refreshlayout.setDisableContentWhenRefresh(true);
//        refreshlayout.setDisableContentWhenLoading(true);
        refreshlayout.setEnableRefresh(false);
//        refreshlayout.setEnableLoadmore(false);

        list = new ArrayList<>();
        adapter = new TimesAdapter(mContext, list);
        lvData.setAdapter(adapter);

        timesPresenter = new TimesPresenter(getContext(), this);
        addListener();
        request();
    }

    private void addListener(){
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


    private void request(){
        timesPresenter.queryList(startTime,endTime,getWifiMac(),""+pageNum,""+pageSize,getUserId());
    }

    private void showTimeStart(){

    }

    private void showTimeEnd(){

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onQuerySuccess(List<TimeBean> bean) {
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
}
