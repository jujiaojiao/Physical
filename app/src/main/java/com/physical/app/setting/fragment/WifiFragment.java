package com.physical.app.setting.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.jflavio1.wificonnector.WifiConnector;
import com.jflavio1.wificonnector.interfaces.ConnectionResultListener;
import com.jflavio1.wificonnector.interfaces.RemoveWifiListener;
import com.jflavio1.wificonnector.interfaces.ShowWifiListener;
import com.jflavio1.wificonnector.interfaces.WifiConnectorModel;
import com.jflavio1.wificonnector.interfaces.WifiStateListener;
import com.physical.app.R;
import com.physical.app.adapter.WifiAdapter;
import com.physical.app.common.base.BaseFragment;
import com.physical.app.common.utils.WifiUtils;
import com.physical.app.common.widget.InputPwdDialog;

import org.json.JSONArray;

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

    private boolean ischeck = false;


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
                if (data.capabilities.contains("WEP") || data.capabilities.contains("PSK") || data.capabilities.contains("EAP")) {
                    showInputPwdDialog();
                } else {
                    WifiUtils.getInstance(mContext).connectNoPassWordWifi(data);
                }
            }
        });
    }


    private void initData() {
        datas  = new ArrayList<ScanResult>();
        adapter = new WifiAdapter(mContext, datas);
        lvData.setAdapter(adapter);

        if (ischeck) {
            lvData.setVisibility(View.VISIBLE);
        } else {
            lvData.setVisibility(View.GONE);
        }

    }

    @OnClick({R.id.iv_switch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_switch:
                if (!ischeck) {
                    ivSwitch.setImageResource(R.mipmap.button_on);
                    ischeck = true;
                    datas.clear();
                    WifiUtils.getInstance(mContext).openWifi();
                    datas.addAll( WifiUtils.getInstance(mContext).getScanResults()) ;
                    adapter.notifyDataSetChanged();
                    lvData.setVisibility(View.VISIBLE);
                } else {
                    ivSwitch.setImageResource(R.mipmap.button_off);
                    ischeck = false;
                    lvData.setVisibility(View.GONE);
                }
                break;
        }
    }


    private void showInputPwdDialog() {
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