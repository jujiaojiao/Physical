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
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.util.ConvertUtils;

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

    private void addListener() {
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


    @OnClick({R.id.tv_end_time,R.id.tv_start_time})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.tv_end_time:
                showTimeEndDay();
                break;
            case R.id.tv_start_time:
                showTimeStartDay();
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


    private void request() {
        endTime = tvEndTime.getText().toString();
        startTime = tvStartTime.getText().toString();
        timesPresenter.queryList(startTime, endTime, getWifiMac(), "" + pageNum, "" + pageSize, getUserId());
    }

    /**
     * 开始
     */
    public void showTimeStartDay() {
        final cn.qqtheme.framework.picker.DatePicker picker = new cn.qqtheme.framework.picker.DatePicker(getActivity());
        Calendar c = Calendar.getInstance();
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(getContext(), 10));
        picker.setRangeEnd(2100, 1, 11);
        picker.setRangeStart(1900, 1, 1);
        picker.setSelectedItem(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new cn.qqtheme.framework.picker.DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                showToast(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new cn.qqtheme.framework.picker.DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                tvStartTime.setText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                tvStartTime.setText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                tvStartTime.setText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }

    /**
     * 结束
     */
    public void showTimeEndDay() {
        final cn.qqtheme.framework.picker.DatePicker picker = new cn.qqtheme.framework.picker.DatePicker(getActivity());
        Calendar c = Calendar.getInstance();
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(getContext(), 10));
        picker.setRangeEnd(2100, 1, 11);
        picker.setRangeStart(1900, 1, 1);
        picker.setSelectedItem(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new cn.qqtheme.framework.picker.DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                showToast(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new cn.qqtheme.framework.picker.DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                tvEndTime.setText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                tvEndTime.setText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                tvEndTime.setText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onQuerySuccess(List<TimeBean> bean) {
        if (null != bean && bean.size() > 0) {
            if (bean.size() < pageSize) {
                refreshlayout.setEnableLoadmore(false);
            } else {
                refreshlayout.setEnableLoadmore(true);
            }
            list.addAll(bean);
            adapter.notifyDataSetChanged();
        } else {
            refreshlayout.setEnableLoadmore(false);
        }
    }
}
