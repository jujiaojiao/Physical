package com.physical.app.common.wifi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


import com.physical.app.MyApplication;
import com.physical.app.R;
import com.physical.app.common.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Roy on 2018/8/29.
 */

public class WifiFragment extends BaseFragment {

    @Bind(R.id.open_wifi)
    Button openWifi;
    @Bind(R.id.scan_wifi)
    Button scanWifi;
    @Bind(R.id.close_wifi)
    Button closeWifi;
    @Bind(R.id.wifi_ap_name)
    TextView wifiApName;
    @Bind(R.id.wifi_list)
    ListView wifiList;

    private Wi_FiManager mWiFiManager;
//    private List<ScanResult> mResultList;
    // WIFI热点安全类型
    private Wi_FiManager.WifiSecurityType mType;
    // 接收message，做处理
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Wi_FiManager.WIFI_AP_OPEN:
                    showToast("WLAN热点开启成功！");
                    wifiApName.setText("便携式热点：”"+ mWiFiManager.getValidApSsid() +"”已激活");
                    wifiApName.setVisibility(View.VISIBLE);
                    break;
                case Wi_FiManager.WIFI_AP_CLOSE:
                    showToast("WLAN热点已关闭！");
                    wifiApName.setVisibility(View.GONE);
                    break;
                case Wi_FiManager.WIFI_OPEN:
                    showToast("Wi_Fi已开启！");
//                    initScanResult(mWiFiManager.getScanResult());
                    break;
                case Wi_FiManager.WIFI_CLOSE:
                    showToast("Wi_Fi已关闭！");
                    initScanResult(mWiFiManager.getScanResult());
                    break;
                case Wi_FiManager.WIFI_SCAN_RESULTS:
                    showToast("Wi_Fi扫描成功！");
                    initScanResult(mWiFiManager.getScanResult());
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    protected View initView() {
        /**=========返回一个View=========*/
        View view = View.inflate(getActivity(), R.layout.fragment_wifi2, null);
        ButterKnife.bind(this, view);
        mWiFiManager = Wi_FiManager.getInstance(MyApplication.getAppContext());
        mWiFiManager.regitsterHandler(mHandler);
//        initScanResult(mWiFiManager.getScanResult());
        if (mWiFiManager.getWifiState() == Wi_FiManager.WIFI_OPEN){
            initScanResult(mWiFiManager.getScanResult());
        }
        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mWiFiManager.unregitsterHandler();
    }

    @OnClick({R.id.open_wifi,R.id.scan_wifi,R.id.close_wifi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 开启Wi_Fi
            case R.id.open_wifi:
                mWiFiManager.setWifiEnabled(true);
                break;
            // 扫描Wi_Fi
            case R.id.scan_wifi:
                mWiFiManager.startScanWifi();
                break;
            // 关闭Wi_Fi
            case R.id.close_wifi:
                mWiFiManager.setWifiEnabled(false);
                break;
        }

    }

    private void initScanResult(final List<ScanResult> resultList){
        wifiList.setAdapter(new WifiAdapter(getActivity(),resultList));
        wifiList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ScanResult result = resultList.get(position);
//                ToastUtils.showLong(result.SSID.toString());

                showConnectWifiView(getActivity(),result.SSID.toString());
            }


        });
    }

    // 连接WIFI编辑框
    private void showConnectWifiView(final Context context, final String ssid) {
        View view = View.inflate(context, R.layout.connected_wifi, null);
        final TextView wifiSsid = view.findViewById(R.id.wifi_name);
        wifiSsid.setText(ssid);
        final EditText wifiPsw = view.findViewById(R.id.et_password);
        final CheckBox isCheck = view.findViewById(R.id.isCheck);
        isCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked){
                   wifiPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
               }else {
                   wifiPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());

               }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("连接", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Wi_FiManager.connectNetwork(ssid,wifiPsw.getText().toString());

                dialog.dismiss();
            }
        }); AlertDialog dialog = builder.create();
        dialog.show();
    }



    // 设置WLAN热点编辑框
    private void showSetWifiApView(final Context context) {
//        WifiConfiguration validWifiConfig = mWiFiManager.getValidWifiApConfig();
////        if (validWifiConfig != null){
////
////        }
        // 第一步：添加一个下拉列表项的list，这里添加的项就是下拉列表的菜单项
        List<String> list = new ArrayList<String>();
        list.add("无");
        list.add("WPA2 PSK");
        View view = View.inflate(context, R.layout.set_wifi_ap, null);
        final EditText apSsid = view.findViewById(R.id.ap_ssid);
        final EditText apPsw = view.findViewById(R.id.ap_psw);
        final LinearLayout llPsw = view.findViewById(R.id.ll_psw);
        Spinner spinner = view.findViewById(R.id.spinner);
        // 第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
        // 第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 第四步：将适配器添加到下拉列表上
        spinner.setAdapter(adapter);
        // 第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                String item = adapter.getItem(arg2);
                if (item.equals("WPA2 PSK")){
                    mType = Wi_FiManager.WifiSecurityType.WIFICIPHER_WPA2;
                    llPsw.setVisibility(View.VISIBLE);
                }
                if (item.equals("无")){
                    mType = Wi_FiManager.WifiSecurityType.WIFICIPHER_NOPASS;
                    llPsw.setVisibility(View.GONE);
                }
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO
            }
        });

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("设置WLAN热点")//设置对话框的标题
                .setView(view)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mWiFiManager.turnOnWifiAp(apSsid.getText().toString(), apPsw.getText().toString(),mType);

                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

}
