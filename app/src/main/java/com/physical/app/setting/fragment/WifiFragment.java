package com.physical.app.setting.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.physical.app.R;
import com.physical.app.adapter.WifiAdapter;
import com.physical.app.common.base.BaseFragment;
import com.physical.app.common.constains.Constains;
import com.physical.app.common.utils.Preferences;
import com.physical.app.common.utils.ToastUtil;
import com.physical.app.common.utils.WifiUtils;
import com.physical.app.common.widget.InputPwdDialog;
import com.tj24.easywifi.wifi.WifiConnector;
import com.tj24.easywifi.wifi.WifiUtil;


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
    private WifiUtils instance;
    private ProgressDialog progressDialog;
    private boolean wifi;
    private ProgressDialog scanDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wifi, null);
        instance = WifiUtils.getInstance(getContext());
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
                    if (instance.isConnected(data)) {
                        ToastUtil.show("此WIFI已连接！");
                    } else {
                        showInputPwdDialog(data);
                    }
                } else {
                    instance.connectNoPassWordWifi(data);
                }
            }
        });
    }


    private void initData() {
        datas = new ArrayList<ScanResult>();
        adapter = new WifiAdapter(mContext, datas);
        lvData.setAdapter(adapter);

        if (ischeck) {
            lvData.setVisibility(View.VISIBLE);
        } else {
            lvData.setVisibility(View.GONE);
        }
        wifi = Preferences.getBoolean(Constains.WIFISTATUS, false);
        if (WifiUtils.getInstance(getContext()).isWifienabled()) {
            scanProgressDialog();
            handler.sendEmptyMessageDelayed(100, 3000);
            ivSwitch.setImageResource(R.mipmap.button_on);
            ischeck = true;
            datas.clear();
            WifiUtils.getInstance(mContext).openWifi();
            lvData.setVisibility(View.VISIBLE);
        } else {
            ivSwitch.setImageResource(R.mipmap.button_off);
            ischeck = false;
            WifiUtils.getInstance(mContext).closeWifi();
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
                    handler.sendEmptyMessageDelayed(100, 3000);
                    lvData.setVisibility(View.VISIBLE);
                    Preferences.putBoolean(Constains.WIFISTATUS, true);
                } else {
                    ivSwitch.setImageResource(R.mipmap.button_off);
                    ischeck = false;
                    WifiUtils.getInstance(mContext).closeWifi();
                    lvData.setVisibility(View.GONE);
                    Preferences.putBoolean(Constains.WIFISTATUS, false);

                }
                break;
        }
    }


    private void showInputPwdDialog(final ScanResult data) {
        inputPwdDialog = new InputPwdDialog(mContext, new InputPwdDialog.Callback() {
            @Override
            public void onConfirm(String code) {
                inputPwdDialog.cancel();
                buildProgressDialog();
                Log.i("dyy", code + "===code===" + data.SSID + "===data");
                new WifiConnector(getContext()).connectWifi(data.SSID, code, WifiUtil.TYPE_WPA, new WifiConnector.WifiConnectCallBack() {
                    @Override
                    public void onConnectSucess() {
                        progressDialog.dismiss();
                        showToast("连接成功！！");
                    }

                    @Override
                    public void onConnectFail(String msg) {
                        progressDialog.dismiss();
                        showToast(msg);
                        inputPwdDialog.cancel();
                    }
                });
            }

            @Override
            public void onCancel() {
                inputPwdDialog.cancel();
            }
        });
        inputPwdDialog.show();

    }

    public void buildProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.setMessage("连接中");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    public void scanProgressDialog() {
        if (scanDialog == null) {
            scanDialog = new ProgressDialog(mContext);
            scanDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        scanDialog.setMessage("WiFi扫描中...");
        scanDialog.setCancelable(true);
        scanDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    scanDialog.dismiss();
                    List<ScanResult> scanResults = WifiUtils.getInstance(mContext).getScanResults();
                    Log.i("jjj", "handleMessage: " + scanResults.size());
                    datas.addAll(WifiUtils.getInstance(mContext).getScanResults());
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };
}