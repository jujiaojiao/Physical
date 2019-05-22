package com.physical.app.setting.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.physical.app.R;
import com.physical.app.adapter.TimesAdapter;
import com.physical.app.bean.TimeBean;
import com.physical.app.common.base.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jjj
 * 时间:  2019/5/20
 * 邮箱: jujiaojiao@gemdale.com
 * 描述: 理疗次数
 */

public class TimesFragment extends BaseFragment {
    @Bind(R.id.tv_start_time)
    TextView tvStartTime;
    @Bind(R.id.tv_end_time)
    TextView tvEndTime;
    @Bind(R.id.lv_data)
    ListView lvData;
    @Bind(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    private TimesAdapter adapter;
    private ArrayList<TimeBean> list;

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
        list = new ArrayList<>();
        list.add(new TimeBean());
        list.add(new TimeBean());
        list.add(new TimeBean());
        list.add(new TimeBean());
        list.add(new TimeBean());
        list.add(new TimeBean());
        list.add(new TimeBean());
        list.add(new TimeBean());
        adapter = new TimesAdapter(mContext, list);
        lvData.setAdapter(adapter);
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
}
