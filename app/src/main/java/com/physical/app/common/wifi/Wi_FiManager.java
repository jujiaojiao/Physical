package com.physical.app.common.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import com.cvte.network.wifi.common.apiAdapter.WifiConfigurationAdapter;
import com.cvte.network.wifi.common.apiAdapter.WifiManagerAdapter;
import com.cvte.network.wifi.common.base.BasePoint;
import com.physical.app.MyApplication;
import com.physical.app.common.utils.ToastUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Roy on 2018/6/6.
 */

public class Wi_FiManager {

    private static final String TAG = Wi_FiManager.class.getSimpleName();

    private volatile static Wi_FiManager sInstance;// 声明成 volatile
    private static WifiManager mWifiManager;
    private static Context mContext;
    private static Handler mHandler;

    public static final int WIFI_OPEN = 0;
    public static final int WIFI_CLOSE = 1;
    public static final int WIFI_AP_OPEN = 2;
    public static final int WIFI_AP_CLOSE = 3;
    public static final int WIFI_SCAN_RESULTS = 4;

    //监听wifi状态变化
    public static final String WIFI_STATE_CHANGED_ACTION = "android.net.wifi.WIFI_STATE_CHANGED";
    //监听wifi热点的状态变化
    public static final String WIFI_AP_STATE_CHANGED_ACTION = "android.net.wifi.WIFI_AP_STATE_CHANGED";
    //WIFI扫描完成
    public static final String SCAN_RESULTS_AVAILABLE_ACTION = "android.net.wifi.SCAN_RESULTS";


    //监听wifi热点的状态值
    private static final int WIFI_AP_STATE_DISABLING = 10;// WiFi正要关闭的状态, 是 Disabled 和 Enabled 的临界状态;
    private static final int WIFI_AP_STATE_DISABLED = 11;// WiFi已经完全关闭的状态;
    private static final int WIFI_AP_STATE_ENABLING = 12;// WiFi正要开启的状态, 是 Enabled 和 Disabled 的临界状态
    private static final int WIFI_AP_STATE_ENABLED = 13;// WiFi已经完全开启的状态
    private static final int WIFI_AP_STATE_FAILED = 14;
    // 默认Wi_Fi热点秘密
    private static final String DEFAULT_AP_PASSWORD = "12345678";

    public enum WifiSecurityType {
        WIFICIPHER_NOPASS, WIFICIPHER_WPA, WIFICIPHER_WEP, WIFICIPHER_INVALID, WIFICIPHER_WPA2
    }

    // 监听Wi_Fi/热点状态变化
    private BroadcastReceiver mWifiStateBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){

                // WIFI状态变化
                case WIFI_STATE_CHANGED_ACTION:
                    int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED);
                    switch (wifiState) {
                        case WifiManager.WIFI_STATE_DISABLING:
                            if (MyApplication.DEBUG) Log.i(TAG, "正在关闭Wi_Fi...");
                            break;
                        case WifiManager.WIFI_STATE_ENABLING:
                            if (MyApplication.DEBUG) Log.i(TAG, "正在开启Wi_Fi...");
                            break;
                        case WifiManager.WIFI_STATE_DISABLED:
                        case WifiManager.WIFI_STATE_UNKNOWN:
                            startScanWifi();
                            if(mHandler != null) mHandler.sendEmptyMessage(WIFI_CLOSE);
                            if (MyApplication.DEBUG) Log.i(TAG, "Wi_Fi已关闭");
                            break;

                        case WifiManager.WIFI_STATE_ENABLED:
                            startScanWifi();
                            if(mHandler != null) mHandler.sendEmptyMessageDelayed(WIFI_OPEN,1000l);
                            if (MyApplication.DEBUG) Log.i(TAG, "Wi_Fi已开启");
                            break;
                    }

                    break;

                // WIFI热点的状态变化
                case WIFI_AP_STATE_CHANGED_ACTION:
                    int apState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED);
                    switch (apState) {
                        case WIFI_AP_STATE_DISABLING:
                            if (MyApplication.DEBUG) Log.i(TAG, "正在关闭Wi_Fi热点...");
                            break;
                        case WIFI_AP_STATE_ENABLING:
                            if (MyApplication.DEBUG) Log.i(TAG, "正在开启Wi_Fi热点...");
                            break;
                        case WIFI_AP_STATE_DISABLED:
                        case WIFI_AP_STATE_FAILED:
                            if(mHandler != null) mHandler.sendEmptyMessage(WIFI_AP_CLOSE);
                            if (MyApplication.DEBUG) Log.i(TAG, "Wi_Fi热点已关闭");
                            break;

                        case WIFI_AP_STATE_ENABLED:
                            if(mHandler != null) mHandler.sendEmptyMessage(WIFI_AP_OPEN);
                            if (MyApplication.DEBUG) Log.i(TAG, "Wi_Fi热点已开启");
                            break;
                    }
                    break;

                // WIFI扫描完成
                case  SCAN_RESULTS_AVAILABLE_ACTION:
                    if(mHandler != null) mHandler.sendEmptyMessage(WIFI_SCAN_RESULTS);
                    if (MyApplication.DEBUG) Log.i(TAG, "Wi_Fi扫描成功！");
                    break;

                // 当前网络信号强度
                case  WifiManager.RSSI_CHANGED_ACTION:
//                    if(mHandler != null) mHandler.sendEmptyMessage(WIFI_SCAN_RESULTS);
                    if (MyApplication.DEBUG) Log.i(TAG, "当前网络信号强度：" + getCurrentNetworkRssi(intent));

                    break;
            }
        }
    };

    //私有化构造方法
    private Wi_FiManager(Context context) {
        mContext = context;
        mWifiManager = getWifiManager(context);
        // 注册Wi_Fi/热点的状态变化
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WIFI_AP_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        filter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        context.registerReceiver(mWifiStateBroadcastReceiver, filter);
    }

    /**
     * 获取实例（静态工厂方法）
     * @param c
     * @return
     */
    public static Wi_FiManager getInstance(Context c) {
        if (sInstance == null) {
            synchronized (Wi_FiManager.class) {
                if (sInstance == null) {
                    sInstance = new Wi_FiManager(c);
                }
            }
        }
        return sInstance;
    }

    /**
     * 注册Handler
     * @param handler
     */
    public void regitsterHandler(Handler handler){
        mHandler = handler;
    }

    public void unregitsterHandler(){
        mHandler = null;
    }

    /**
     * 获取 WifiManager 实例.
     * @param context
     * @return
     */
    private WifiManager getWifiManager(Context context) {
        return context == null ? null : (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * 开启/关闭 WIFI.
     * @param enabled
     * @return
     */
    public boolean setWifiEnabled( boolean enabled) {
        return mWifiManager != null && mWifiManager.setWifiEnabled(enabled);
    }

    /**
     *
     *  获取 WIFI 的状态.
     *
     *  注意: * WiFi 的状态目前有五种, 分别是:
     *  WifiManager.WIFI_STATE_ENABLING: WiFi正要开启的状态, 是 Enabled 和 Disabled 的临界状态;
     *  WifiManager.WIFI_STATE_ENABLED: WiFi已经完全开启的状态;
     *  WifiManager.WIFI_STATE_DISABLING: WiFi正要关闭的状态, 是 Disabled 和 Enabled 的临界状态;
     *  WifiManager.WIFI_STATE_DISABLED: WiFi已经完全关闭的状态;
     *  WifiManager.WIFI_STATE_UNKNOWN: WiFi未知的状态, WiFi开启, 关闭过程中出现异常, 或是厂家未配备WiFi外挂模块会出现的情况;
     *
     *  @return
     */
    public int getWifiState() {
        return mWifiManager == null ? WifiManager.WIFI_STATE_UNKNOWN : mWifiManager.getWifiState();
    }

    /**
     * 开始扫描 WIFI.
     */
    public void startScanWifi() {
        if (mWifiManager != null) {
            mWifiManager.startScan();
        }
    }

    /**
     * 获取扫描 WIFI 的热点:
     * @return
     */
    public List<ScanResult> getScanResult() {
        return mWifiManager == null ? null : mWifiManager.getScanResults();
    }

    /**
     * 获取已经保存过的/配置好的 WIFI 热点
     *
     * 注意: * Android 的 WiFi 连接, 大概可以分为如下两种情况:
     * a. 无密码的, 可直接连接, 连接过程中, 此热点一直有, 不管最后是否需要其他方式进行验证操作, 但凡连接成功,即刻进行了对此热点的配置进行保存;
     * b. 有密码的, 暂且不论何种加密手段, 只要用户输入密码, 点击连接, 如果连接途中, 此热点一直有, 不论连接成功还 * 是失败, 都即刻对此热点的配置进行了保存操作; 使用上述的方式获取到的WiFi的配置, 就是上面进行操作保存的WiFi配置
     * c. 连接多个WiFi成功之后, 然后关闭WiFi, 下次开启WiFi的时候, 驱动会主动帮你连接这其中配置好的其中一个WiFi;
     *
     * @return
     */
    public static List<WifiConfiguration> getConfiguredNetworks() {
        return mWifiManager == null ? null : mWifiManager.getConfiguredNetworks();
    }

    /**
     * 使用 WifiConfiguration 连接.
     * @param config
     */
    public void connectByConfig( WifiConfiguration config) {
        if (mWifiManager == null) {
            return;
        }
        try {
            Method connect = mWifiManager.getClass().getDeclaredMethod("connect", WifiConfiguration.class, Class.forName("android.net.wifi.WifiManager$ActionListener"));
            if (connect != null) {
                connect.setAccessible(true);
                connect.invoke(mWifiManager, config, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用 networkId 连接
     * @param networkId
     */
    public void connectByNetworkId(int networkId) {
        if (mWifiManager == null) {
            return;
        }
        try {
            Method connect = mWifiManager.getClass().getDeclaredMethod("connect", int.class, Class.forName("android.net.wifi.WifiManager$ActionListener"));
            if (connect != null) {
                connect.setAccessible(true);
                connect.invoke(mWifiManager, networkId, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存网络.
     * @param config
     */
    public void saveNetworkByConfig( WifiConfiguration config) {
        if (mWifiManager == null) {
            return;
        }
        try {
            Method save = mWifiManager.getClass().getDeclaredMethod("save", WifiConfiguration.class, Class.forName("android.net.wifi.WifiManager$ActionListener"));
            if (save != null) {
                save.setAccessible(true);
                save.invoke(mWifiManager, config, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加网络.
     * @param config
     * @return
     */
    public int addNetwork( WifiConfiguration config) {
        if (mWifiManager != null) {
            return mWifiManager.addNetwork(config);
        }
        return -1;
    }

    /**
     * 忘记网络.
     * @param networkId
     */
    public void forgetNetwork(int networkId) {
        if (mWifiManager == null) {
            return;
        }
        try {
            Method forget = mWifiManager.getClass().getDeclaredMethod("forget", int.class, Class.forName("android.net.wifi.WifiManager$ActionListener"));
            if (forget != null) {
                forget.setAccessible(true);
                forget.invoke(mWifiManager, networkId, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 禁用网络.
     *
     * @param netId
     */
    public void disableNetwork( int netId) {
        if (mWifiManager == null) {
            return;
        }
        try {
            Method disable = mWifiManager.getClass().getDeclaredMethod("disable", int.class, Class.forName("android.net.wifi.WifiManager$ActionListener"));
            if (disable != null) {
                disable.setAccessible(true);
                disable.invoke(mWifiManager, netId, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 断开连接.
     * @return
     */
    public boolean disconnectNetwork() {
        return mWifiManager != null && mWifiManager.disconnect();
    }

    /**
     * 禁用短暂网络.
     * @param SSID
     */
    public void disableEphemeralNetwork(String SSID) {
        if (mWifiManager == null || TextUtils.isEmpty(SSID)) return;
        try {
            Method disableEphemeralNetwork = mWifiManager.getClass().getDeclaredMethod("disableEphemeralNetwork", String.class);
            if (disableEphemeralNetwork != null) {
                disableEphemeralNetwork.setAccessible(true);
                disableEphemeralNetwork.invoke(mWifiManager, SSID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接网络
     * @param ssid
     * @param paw
     */
    public static void connectNetwork(String ssid, String paw){
        BasePoint point = new BasePoint(mContext) {
            @Override
            public boolean isActive() {
                return false;
            }

            @Override
            public int compareTo(@NonNull Object o) {
                return 0;
            }
        };
        point.setNetworkId(WifiConfigurationAdapter.INVALID_NETWORK_ID);
        point.setSsid(ssid);
        point.setSecurity(BasePoint.SECURITY_PSK);

        if(Wi_FiManager.getInstance(mContext).addNetwork(WifiConfigurationAdapter.getConfig(point, paw)) == -1){
            ToastUtil.show("。。。WIFI添加失败！。。。");
            return;
        }
        ToastUtil.show("。。。WIFI添加成功！。。。");
        WifiManagerAdapter.connect(WifiConfigurationAdapter.getConfig(point,paw), Wi_FiManager.mWifiManager);

    }

//=================================================WIFI热点===================================================

    protected void finalize() {
        if(MyApplication.DEBUG) Log.d(TAG,"finalize");
        mContext.unregisterReceiver(mWifiStateBroadcastReceiver);

    }

    /**
     * 创建wifi热点
     * @param ssid
     * @param password
     * @param Type
     * @return
     */
    public boolean turnOnWifiAp(String ssid, String password, WifiSecurityType Type) {
        //配置热点信息。
        WifiConfiguration wcfg = new WifiConfiguration();
        wcfg.SSID = new String(ssid);
        wcfg.networkId = 1;
        wcfg.allowedAuthAlgorithms.clear();
        wcfg.allowedGroupCiphers.clear();
        wcfg.allowedKeyManagement.clear();
        wcfg.allowedPairwiseCiphers.clear();
        wcfg.allowedProtocols.clear();

        if(Type == WifiSecurityType.WIFICIPHER_NOPASS) {
            if(MyApplication.DEBUG) Log.d(TAG, "wifi ap----no password");
            wcfg.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN, true);
            wcfg.wepKeys[0] = "";
            wcfg.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            wcfg.wepTxKeyIndex = 0;
        } else if(Type == WifiSecurityType.WIFICIPHER_WPA) {
            if(MyApplication.DEBUG) Log.d(TAG, "wifi ap----wpa");
            //密码至少8位，否则使用默认密码
            if(null != password && password.length() >= 8){
                wcfg.preSharedKey = password;
            } else {
                wcfg.preSharedKey = DEFAULT_AP_PASSWORD;
            }
            wcfg.hiddenSSID = false;
            wcfg.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            wcfg.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            wcfg.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            //wcfg.allowedKeyManagement.set(4);
            wcfg.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            wcfg.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            wcfg.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            wcfg.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        } else if(Type == WifiSecurityType.WIFICIPHER_WPA2) {
            if(MyApplication.DEBUG) Log.d(TAG, "wifi ap---- wpa2");
            //密码至少8位，否则使用默认密码
            if(null != password && password.length() >= 8){
                wcfg.preSharedKey = password;
            } else {
                wcfg.preSharedKey = DEFAULT_AP_PASSWORD;
            }
            wcfg.hiddenSSID = true;
            wcfg.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            wcfg.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            wcfg.allowedKeyManagement.set(4);
            wcfg.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            wcfg.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            wcfg.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            wcfg.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        }
        try {
            Method method = mWifiManager.getClass().getMethod("setWifiApConfiguration", wcfg.getClass());
            Boolean rt = (Boolean)method.invoke(mWifiManager, wcfg);
            if(MyApplication.DEBUG) Log.d(TAG, " rt = " + rt);
        } catch (NoSuchMethodException e) {
            Log.e(TAG, e.getMessage());
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage());
        } catch (IllegalAccessException e) {
            Log.e(TAG, e.getMessage());
        } catch (InvocationTargetException e) {
            Log.e(TAG, e.getMessage());
        }
        return setWifiApEnabled();
    }

    /**
     * 获取热点状态
     * @return
     */
    public int getWifiAPState() {
        int state = -1;
        try {
            Method method2 = mWifiManager.getClass().getMethod("getWifiApState");
            state = (Integer) method2.invoke(mWifiManager);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        if(MyApplication.DEBUG) Log.i("WifiAP", "getWifiAPState.state " + state);
        return state;
    }

    private boolean setWifiApEnabled() {
        //开启wifi热点需要关闭wifi
        while(mWifiManager.getWifiState() != WifiManager.WIFI_STATE_DISABLED){
            mWifiManager.setWifiEnabled(false);
            try {
                Thread.sleep(200);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                return false;
            }
        }
        // 确保wifi 热点关闭。
        while(getWifiAPState() != WIFI_AP_STATE_DISABLED){
            try {
                Method method1 = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
                method1.invoke(mWifiManager, null, false);

                Thread.sleep(200);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                return false;
            }
        }

        //开启wifi热点
        try {
            Method method1 = mWifiManager.getClass().getMethod("setWifiApEnabled",
                    WifiConfiguration.class, boolean.class);
            method1.invoke(mWifiManager, null, true);
            Thread.sleep(200);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 关闭WiFi热点
     */
    public void closeWifiAp() {
        if (getWifiAPState() != WIFI_AP_STATE_DISABLED) {
            try {
                Method method = mWifiManager.getClass().getMethod("getWifiApConfiguration");
                method.setAccessible(true);
                WifiConfiguration config = (WifiConfiguration) method.invoke(mWifiManager);
                Method method2 = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
                method2.invoke(mWifiManager, config, false);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取热点ssid
     * @return
     */
    public String getValidApSsid() {
        try {
            Method method = mWifiManager.getClass().getMethod("getWifiApConfiguration");
            WifiConfiguration configuration = (WifiConfiguration)method.invoke(mWifiManager);
            return configuration.SSID;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    /**
     * 获取热点密码
     * @return
     */
    public String getValidPassword(){
        try {
            Method method = mWifiManager.getClass().getMethod("getWifiApConfiguration");
            WifiConfiguration configuration = (WifiConfiguration)method.invoke(mWifiManager);
            return configuration.preSharedKey;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }

    }

    /**
     * 获取热点安全类型
     * @return
     */
    public int getValidSecurity(){
        WifiConfiguration configuration;
        try {
            Method method = mWifiManager.getClass().getMethod("getWifiApConfiguration");
            configuration = (WifiConfiguration)method.invoke(mWifiManager);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return Wi_FiManager.WifiSecurityType.WIFICIPHER_INVALID.ordinal();
        }

        if(MyApplication.DEBUG) Log.i(TAG,"getSecurity security="+configuration.allowedKeyManagement);
        if(configuration.allowedKeyManagement.get(WifiConfiguration.KeyMgmt.NONE)) {
            return Wi_FiManager.WifiSecurityType.WIFICIPHER_NOPASS.ordinal();
        }else if(configuration.allowedKeyManagement.get(WifiConfiguration.KeyMgmt.WPA_PSK)) {
            return Wi_FiManager.WifiSecurityType.WIFICIPHER_WPA.ordinal();
        }else if(configuration.allowedKeyManagement.get(4)) { //4 means WPA2_PSK
            return Wi_FiManager.WifiSecurityType.WIFICIPHER_WPA2.ordinal();
        }
        return Wi_FiManager.WifiSecurityType.WIFICIPHER_INVALID.ordinal();
    }

    /**
     * 获取当前热点信息
     * @return
     */
    public WifiConfiguration getValidWifiApConfig() {
        try {
            Method method = mWifiManager.getClass().getMethod("getWifiApConfiguration");
            return (WifiConfiguration)method.invoke(mWifiManager);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    /**
     * 获取当前热点最新的信号强度
     */
    public static int getCurrentNetworkRssi(Intent intent) {
        return intent.getIntExtra(WifiManager.EXTRA_NEW_RSSI, -1000);
    }

    /**
     *  获取当前网络
     */
    public static NetworkInfo getCurrentNetworkInfo(Intent intent) {
        return intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
    }

    /**
     *  获取当前网路状态.
     */
    public static NetworkInfo.State getCurrentNetworkState(NetworkInfo info) {
        return info != null ? info.getState() : null;
    }

    /**
     * 获取当前网路BSSID.
     */

    public static String getCurrentNetworkBssid(Intent intent) {
        return intent.getStringExtra(WifiManager.EXTRA_BSSID);
    }

    /**
     *  获取当前网路的WifiInfo. wifi 连接成功有效.
     */
    public static WifiInfo getCurrentWifiInfo(Intent intent) {
        return intent.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO);
    }

}
