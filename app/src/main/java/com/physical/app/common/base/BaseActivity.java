package com.physical.app.common.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


import com.physical.app.AppData;
import com.physical.app.R;
import com.physical.app.common.utils.ToastUtil;
import com.physical.app.mine.bean.User;
import com.physical.app.mine.view.LoginActivity;

import butterknife.ButterKnife;

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
        mContext=this;
    }

    public void showToast(String msg){
        ToastUtil.show(msg);
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void startTo(Class c){
        startActivity(new Intent(this, c));
    }

    public String getUserId() {
        return AppData.getInstance().getUserId();
    }

    public boolean userIsLogin(boolean startToLogin) {
        User user = AppData.getInstance().getUser();
        if(null == user){
            if(startToLogin){
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
    public void initTitleAndBack(String title, String menuName){
        ButterKnife.bind(this);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvBack = findViewById(R.id.tvBack);
        TextView tvMenu = findViewById(R.id.tvMenu);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setText(title);
        tvMenu.setText(menuName);
    }
    public void initTitleAndBack(String title){
        ButterKnife.bind(this);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvBack = findViewById(R.id.tvBack);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setText(title);
    }


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

}
