package com.physical.app.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by reeman on 2018/7/27.
 */

public class WifiUtils {
    private Context mContext;
    public static WifiManager mWifiManager;
    private static WifiUtils instance;
    private static ConnectivityManager mConnectivityManager ;

    public static final int LINE_WIFI_ERROR = 2345;
    public static final int LINE_WIFI_SUCCESS = LINE_WIFI_ERROR + 1;

    public WifiUtils(Context context) {
        mContext = context;
    }

    /**
     * 获取WifiUtils 实例
     * 获取WifiManager实例
     * @param context
     * @return
     */
    public static synchronized WifiUtils getInstance(Context context){
        if (instance == null){
            instance = new WifiUtils(context.getApplicationContext());
        }
        if (mWifiManager == null){
            mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        }
        instance.openWifi();
        return instance;
    }

    public void openWifi(){
        if (!mWifiManager.isWifiEnabled()){
            mWifiManager.setWifiEnabled(true);
        }
    }
    public void closeWifi(){
        if (mWifiManager.isWifiEnabled()){
            mWifiManager.setWifiEnabled(false);
        }
    }


    public void startScanWifi(){
        Log.i("ggg","》》》》执行wifi扫描！");
        mWifiManager.startScan();
    }

    public List<ScanResult> getScanResults(){
        startScanWifi();
        return  mWifiManager.getScanResults();
    }

    public WifiInfo getConnectedInfo(){
        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
        return wifiInfo;
    }

    public List<WifiConfiguration> getConfigureNetworks(){
//        startScanWifi();
        return mWifiManager.getConfiguredNetworks();
    }

    /**
     * 获取网络状态
     * @return
     */
    public boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        }
        return false;
    }

    /**
     * Function:判断扫描结果是否连接上<br>
     *
     * @param result
     * @return<br>
     */
    public boolean isConnected(ScanResult result) {
        if (result == null) {
            return false;
        }
        String g2 = "\"" + result.SSID + "\"";
        if (mWifiManager.getConnectionInfo().getSSID() != null && mWifiManager.getConnectionInfo().getSSID().endsWith(g2)) {
            return true;
        }
        return false;
    }

    /**
     * 根据ssid 断开网络
     * @param ssid
     */
    public void disconnectWifi(String ssid) {
        WifiConfiguration wifiConfiguration = isConfigured(ssid);
        if (wifiConfiguration != null){
            mWifiManager.disableNetwork(wifiConfiguration.networkId);
            mWifiManager.disconnect();
        }
    }

    public boolean connectNoPassWordWifi(ScanResult scan) {
        List<WifiConfiguration> list = mWifiManager.getConfiguredNetworks();
        boolean networkInSupplicant = false;
        boolean connectResult = false;
        // 重新连接指定AP
        mWifiManager.disconnect();
        for (WifiConfiguration w : list) {
            // 将指定AP 名字转化
            // String str = convertToQuotedString(info.ssid);
            if (w.BSSID != null && w.BSSID.equals(scan.BSSID)) {
                connectResult = mWifiManager.enableNetwork(w.networkId, true);
                // mWifiManager.saveConfiguration();
                networkInSupplicant = true;
                break;
            }
        }
        if (!networkInSupplicant) {
            WifiConfiguration config = createWifiConfiguration(scan.SSID, "","");
            connectResult = addNetwork(config);
        }

        return connectResult;
    }

    //连接指定Id的WIFI
    public boolean connectSavedWifi(int wifiId){
        List<WifiConfiguration> configurations = getConfigureNetworks();
        for(int i = 0; i < configurations.size(); i++){
            WifiConfiguration wifi = configurations.get(i);
            if(wifi.networkId == wifiId){
                while(!(mWifiManager.enableNetwork(wifiId, true))){
                    Log.i("ConnectWifi",String.valueOf(configurations.get(wifiId).status));
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 添加到网络
     *
     * @param wcg
     * @author Xiho
     */
    public boolean addNetwork(WifiConfiguration wcg) {
        if (wcg == null) {
            return false;
        }
        int wcgID = mWifiManager.addNetwork(wcg);
        boolean b = mWifiManager.enableNetwork(wcgID, true);
        if (b){
            mWifiManager.saveConfiguration();
        }
        Log.i("ggg","连接新加wifi是否成功：" + b);
        return b;
    }

    // 然后是一个实际应用方法，只验证过没有密码的情况：
    public WifiConfiguration createWifiConfiguration(String ssid, String Password,String type) {
        WifiConfiguration config = new WifiConfiguration();
        config.hiddenSSID = false;
        config.status = WifiConfiguration.Status.ENABLED;

        if (type.contains("WEP")) {
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers
                    .set(WifiConfiguration.GroupCipher.WEP104);

            config.SSID = "\"" + ssid + "\"";

            config.wepTxKeyIndex = 0;
            config.wepKeys[0] = Password;
            // config.preSharedKey = "\"" + SHARED_KEY + "\"";
        } else if (type.contains("PSK")) {
            //
            config.SSID = "\"" + ssid + "\"";
            config.preSharedKey = "\"" + Password + "\"";
        } else if (type.contains("EAP")) {
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_EAP);
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.TKIP);
            config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.SSID = "\"" + ssid+ "\"";
            config.preSharedKey = "\"" + Password + "\"";
        } else {
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.SSID = "\"" + ssid + "\"";
            config.preSharedKey = null;
        }
        return config;
    }

    /**
     * 给外部提供一个接口，连接无线网络
     *
     * @param scanResult
     * @param Password
     * @return true:连接成功；false:连接失败<br>
     */
    public boolean connectUnSaveWifi(ScanResult scanResult, String Password) {
        // 状态变成WIFI_STATE_ENABLED的时候才能执行下面的语句
        while (mWifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
            try {
                // 为了避免程序一直while循环，让它睡个100毫秒在检测……
                Thread.currentThread();
                Thread.sleep(100);
            } catch (Exception ie) {
            }
        }
        if (scanResult.SSID == null || Password == null || scanResult.SSID.equals("")) {
            Log.e(this.getClass().getName(),
                    "addNetwork() ## nullpointer error!");
            return false;
        }
        WifiConfiguration wifiConfig = createWifiConfiguration(scanResult.SSID,Password,scanResult.capabilities);
        // wifi的配置信息
        if (wifiConfig == null) {
            return false;
        }
        // 查看以前是否也配置过这个网络
        WifiConfiguration tempConfig = isConfigured(scanResult.SSID);
        if (tempConfig != null) {
            mWifiManager.removeNetwork(tempConfig.networkId);
        }
        // 添加一个新的网络描述为一组配置的网络。
        int netID = mWifiManager.addNetwork(wifiConfig);
        Log.d("ggg", "wifi的netID为：" + netID);
        // 断开连接
        mWifiManager.disconnect();
        // 重新连接
        Log.d("ggg", "Wifi的重新连接netID为：" + netID);
        // 设置为true,使其他的连接断开
        boolean mConnectConfig = mWifiManager.enableNetwork(netID, true);
        mWifiManager.reconnect();
        Log.d("ggg", "Wifi的重新连接：" + mConnectConfig);
        return mConnectConfig;
    }

    //删除配置信息
    public boolean delWifiConfig(String ssid){
        boolean isDel = false;
        List<WifiConfiguration> wifiConfigList = getConfigureNetworks();
        for(int i = 0; i < wifiConfigList.size(); i++){
            if(wifiConfigList.get(i).SSID.equals("\""+ssid+"\"") || wifiConfigList.get(i).SSID.equals(ssid)){
                mWifiManager.removeNetwork(wifiConfigList.get(i).networkId);
                mWifiManager.saveConfiguration();
                isDel = true;
                Log.i("ggg","删除wifi:"+ wifiConfigList.get(i).SSID);
                return isDel;
            }
        }
        return isDel;
    }

    // 查看以前是否也配置过这个网络
    public WifiConfiguration isConfigured(String SSID) {
        List<WifiConfiguration> existingConfigs = getConfigureNetworks();
        for (WifiConfiguration existingConfig : existingConfigs) {
            if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
                return existingConfig;
            }
        }
        return null;
    }

    /**
     * 获取已连接的wifi
     * @return
     */
    public List<ScanResult> getConnectedWifiScanResult(){
        List<ScanResult> scanR = new ArrayList<>();
        List<ScanResult> scanResults = getScanResults();
        for (int i = 0;i<scanResults.size();i++){
            String wifiName = scanResults.get(i).SSID.trim();
            if (!TextUtils.isEmpty(wifiName) || wifiName.length() > 2){
                ScanResult scanResult = scanResults.get(i);
                if (isConfigured(scanResult.SSID) != null){
                    if (isConfigured(scanResult.SSID).networkId == getConnectedInfo().getNetworkId()){
                        scanR.add(scanResult);
                        break;
                    }
                }
            }
        }
        return scanR;
    }

    /**
     * 过滤重复，已连接的wifi，并根据强度进行排序
     * @return
     */
    public List<ScanResult> sortScanResult(List<ScanResult> scanResults){
        List<ScanResult> sResults = new ArrayList<>();

        //根据信号强弱排序
        Collections.sort(scanResults, new Comparator<ScanResult>() {
            @Override
            public int compare(ScanResult lhs, ScanResult rhs) {
                return rhs.level - lhs.level;
            }
        });

        //排除重复的
        for (int i=0;i<scanResults.size();i++){
            //排除已连接的
            if (isConfigured(scanResults.get(i).SSID) != null){
                if (isConfigured(scanResults.get(i).SSID).networkId == getConnectedInfo().getNetworkId()){
                    scanResults.remove(scanResults.get(i));
                    continue;
                }
            }

            for (int j=i+1;j<scanResults.size();j++){
                if (scanResults.get(i).SSID.equals(scanResults.get(j).SSID)){
                    scanResults.remove(scanResults.get(j));
                }
            }


        }

        for (int i=0;i<scanResults.size();i++){
            if (isConfigured(scanResults.get(i).SSID) != null){
                sResults.add(scanResults.get(i));
                scanResults.remove(scanResults.get(i));
            }
        }

        sResults.addAll(scanResults);
        return sResults;
    }

    /**
     * Function:信号强度转换为字符串<br>
     *
     * @param level <br>
     * @author ZYT DateTime 2014-5-14 下午2:14:42<br>
     */
    public String singLevToStr(int level) {
        String resuString = "无信号";
        if (Math.abs(level) < 51) { //极强
            resuString = "极强";
        } else if (Math.abs(level) > 50 && Math.abs(level) < 61) {
            resuString = "较强";
        } else if (Math.abs(level) > 60 && Math.abs(level) < 71) {
            resuString = "弱";
        } else if (Math.abs(level) > 70 && Math.abs(level) < 85) {
            resuString = "非常弱";
        } else if (Math.abs(level) > 84) {
            resuString = "极弱";
        } else {
            resuString = "无信号";
        }
        Log.e("ggg", "==当前信号强度==" + level + "//" + resuString);
        return resuString;
    }


    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ipInt
     * @return
     */
    private static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    /**
     * 获取当前ip地址
     *
     * @return
     */
    public static String getLocalIpAddress(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int i = wifiInfo.getIpAddress();
            return int2ip(i);
        } catch (Exception ex) {
            Log.e("ggg"," 获取IP出错:" + ex.getMessage());
        }
        return "0.0.0.0";
    }
}
