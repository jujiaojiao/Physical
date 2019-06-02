package com.physical.app.common.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


import com.google.gson.Gson;
import com.physical.app.AppData;
import com.physical.app.LoginActivity;
import com.physical.app.common.utils.ToastUtil;
import com.physical.app.common.mine.bean.User;

/**
 * 作者: liangzixun
 * 时间: 2017/9/5 16:28
 * 邮箱: liangzixun@eims.com.cn
 */
public class BaseActivity extends AppCompatActivity {
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        hideBottomUIMenu();
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    public void showToast(String msg) {
        ToastUtil.show(msg);
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void startTo(Class c) {
        startActivity(new Intent(this, c));
    }

    public String getUserId() {
        return AppData.getInstance().getUserId();
    }

    public boolean userIsLogin(boolean startToLogin) {
        User user = AppData.getInstance().getUser();
        if (null == user) {
            if (startToLogin) {
                startTo(LoginActivity.class);
            }
            return false;
        }
        return true;
    }


    public User getUser() {
        return AppData.getInstance().getUser();
    }

    protected void initStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void setStatusBarTextColor(Window window, boolean lightStatusBar) {
        if (window == null) return;
        View decor = window.getDecorView();
        int ui = decor.getSystemUiVisibility();
        if (lightStatusBar) {
            ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        } else {
            ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        decor.setSystemUiVisibility(ui);
    }
//    public void initTitleAndBack(String title, String menuName){
//        ButterKnife.bind(this);
//        TextView tvTitle = findViewById(R.id.tvTitle);
//        TextView tvBack = findViewById(R.id.tvBack);
//        TextView tvMenu = findViewById(R.id.tvMenu);
//        tvBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        tvTitle.setText(title);
//        tvMenu.setText(menuName);
//    }
//    public void initTitleAndBack(String title){
//        ButterKnife.bind(this);
//        TextView tvTitle = findViewById(R.id.tvTitle);
//        TextView tvBack = findViewById(R.id.tvBack);
//        tvBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        tvTitle.setText(title);
//    }


    protected void initWebviewSettings(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    /**
     * 判断apk是否安装
     *
     * @param context
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 通过包名 在应用商店打开应用
     *
     * @param context     上下文
     * @param packageName 包名
     * @param url         下载App的官方链接
     */
    public void openApplicationMarket(Context context, String packageName, String url) {
        try {
            String str = "market://details?id=" + packageName;
            Intent localIntent = new Intent(Intent.ACTION_VIEW);
            localIntent.setData(Uri.parse(str));
            context.startActivity(localIntent);
        } catch (Exception e) {
            e.printStackTrace();
            // 调用系统浏览器进行下载
            Toast.makeText(context, "打开应用商店失败", Toast.LENGTH_SHORT).show();
            openLinkBySystem(context, url);
        }
    }

    /**
     * 调用系统浏览器打开网页或下载链接
     *
     * @param url 地址
     */

    public void openLinkBySystem(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }



    public static <T> String toJson(T data) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(data);
        return jsonStr;
    }

    /**
     * 隐藏虚拟按键，并且设置成全屏
     */
    @SuppressLint("ObsoleteSdkInt")
    protected void hideBottomUIMenu() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE;
        getWindow().setAttributes(params);

    }


}
