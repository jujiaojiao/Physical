package com.physical.app.setting;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.physical.app.LoginActivity;
import com.physical.app.MainActivity;
import com.physical.app.R;
import com.physical.app.adapter.WifiAdapter;
import com.physical.app.common.base.BaseActivity;
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
 * @author jjj
 * 版本：1.0
 * 创建日期：2019/6/16
 * 描述：
 */
public class WifiActivity extends BaseActivity {
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.iv_title)
    ImageView ivTitle;
    @Bind(R.id.rlTop)
    RelativeLayout rlTop;
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
    private ProgressDialog scanDialog;
    private boolean wifi;

    public static void start(Context context) {
        Intent intent = new Intent(context, WifiActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        instance = WifiUtils.getInstance(this);
        ButterKnife.bind(this);
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
                        MainActivity.start(WifiActivity.this);
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
        ivTitle.setImageResource(R.mipmap.word_wifi);
        if (ischeck) {
            lvData.setVisibility(View.VISIBLE);
        } else {
            lvData.setVisibility(View.GONE);
        }
        if (WifiUtils.getInstance(this).isWifienabled()) {
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

    @OnClick({R.id.iv_switch, R.id.ivBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_switch:
                if (!ischeck) {
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
                new WifiConnector(WifiActivity.this).connectWifi(data.SSID, code, WifiUtil.TYPE_WPA, new WifiConnector.WifiConnectCallBack() {
                    @Override
                    public void onConnectSucess() {
                        progressDialog.dismiss();
                        showToast("连接成功！！");
                        if (null!=getUser()){
                            MainActivity.start(WifiActivity.this);
                        }else{
                            LoginActivity.start(WifiActivity.this);
                        }
                        finish();
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

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    scanDialog.dismiss();
                    datas.addAll(WifiUtils.getInstance(mContext).getScanResults());
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };
}
