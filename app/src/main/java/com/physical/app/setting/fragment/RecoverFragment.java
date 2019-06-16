package com.physical.app.setting.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.storage.StorageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.physical.app.AppData;
import com.physical.app.LoginActivity;
import com.physical.app.R;
import com.physical.app.common.base.BaseFragment;
import com.physical.app.common.utils.CacheUtil;
import com.physical.app.common.widget.ComDialog;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jjj
 * 时间:  2019/5/20
 * 邮箱: jujiaojiao@gemdale.com
 * 描述: 恢复出厂设置
 */

public class RecoverFragment extends BaseFragment {
    @Bind(R.id.tv_accout)
    TextView tvAccout;
    @Bind(R.id.iv_check)
    ImageView ivCheck;
    @Bind(R.id.ll_recover_crash)
    LinearLayout llRecoverCrash;
    @Bind(R.id.tv_reset)
    TextView tvReset;

    private boolean ischeck = false;
    private ComDialog comDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recover, null);
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
        llRecoverCrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ischeck) {
                    ivCheck.setImageResource(R.mipmap.choose_have);//选中
                    ischeck = true;
                } else {
                    ivCheck.setImageResource(R.mipmap.choose_none);//未选中
                    ischeck = false;
                }
            }
        });

    }


    @OnClick({R.id.tv_reset})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.tv_reset:
                showDialog();
                break;
        }
    }


    private void showDialog(){
        comDialog = new ComDialog(getContext(), "温馨提示", "确认要对设备进行清理吗？", new ComDialog.Callback() {
            @Override
            public void callback(int param) {

                if (ischeck){
                    CacheUtil.clearAllCache(getContext());
                }
                try {
                    startFactoryDefault(getContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                AppData.getInstance().setUser(null);
                getActivity().finish();
                LoginActivity.start(getContext());
            }
        });
        comDialog.show();
    }


    private void initData() {


        tvAccout.setText("• 您的 *** 账户\n" +
                "\n" +
                "• 系统及应用数据和设置\n" +
                "\n" +
                "• 已下载的应用\"");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    /**
     * 恢复出厂设置
     *
     * @param context
     * @throws Exception
     */
    public static void startFactoryDefault(Context context) throws Exception {
        if (Build.VERSION.SDK_INT < 26) {
            context.sendBroadcast(new Intent("android.intent.action.MASTER_CLEAR"));
        } else {
            Intent intent = new Intent("android.intent.action.FACTORY_RESET");
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
            intent.setPackage("android");
            context.sendBroadcast(intent);
        }
    }


}
