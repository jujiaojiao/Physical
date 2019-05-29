package com.physical.app.setting.fragment;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.physical.app.R;
import com.physical.app.adapter.WifiAdapter;
import com.physical.app.common.base.BaseFragment;
import com.physical.app.common.utils.WifiUtils;
import com.physical.app.common.widget.InputPwdDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jjj
 * 时间:  2019/5/20
 * 邮箱: jujiaojiao@gemdale.com
 * 描述: WIFI
 */

public class WifiFragment extends BaseFragment {
    @Bind(R.id.iv_switch)
    ImageView ivSwitch;
    @Bind(R.id.lv_data)
    ListView lvData;
    private WifiAdapter adapter;
    private List<ScanResult> datas;
    private InputPwdDialog inputPwdDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wifi, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        addListener();
    }

    private void addListener() {
        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ScanResult data = datas.get(position);
                if (data.capabilities.contains("WEP")||data.capabilities.contains("PSK")||data.capabilities.contains("EAP")){
                    showInputPwdDialog();
                }else{
                    WifiUtils.getInstance(mContext).connectNoPassWordWifi(data);
                }
            }
        });
    }

    private void initData() {
        WifiUtils.getInstance(mContext).openWifi();
        datas = WifiUtils.getInstance(mContext).getScanResults();
//        datas = new ArrayList<>();
        adapter = new WifiAdapter(mContext, datas);
        lvData.setAdapter(adapter);
    }

    @OnClick({R.id.iv_switch})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.iv_switch:

                break;
        }
    }


    private void showInputPwdDialog(){
        inputPwdDialog = new InputPwdDialog(mContext, new InputPwdDialog.Callback() {
            @Override
            public void onConfirm(String code) {

            }

            @Override
            public void onCancel() {

            }
        });
        inputPwdDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
