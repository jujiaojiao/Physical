package com.physical.app.setting.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.physical.app.R;
import com.physical.app.bean.UpdateBean;
import com.physical.app.callback.IUpdateCallback;
import com.physical.app.common.api.URLs;
import com.physical.app.common.base.BaseFragment;
import com.physical.app.common.utils.DeviceUtils;
import com.physical.app.common.widget.ComDialog;
import com.physical.app.presenter.UpdatePresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jjj
 * 时间:  2019/5/20
 * 邮箱: jujiaojiao@gemdale.com
 * 描述: 版本更新
 */

public class UpdateFragment extends BaseFragment implements IUpdateCallback {
    @Bind(R.id.tv_version_name)
    TextView tvVersionName;
    @Bind(R.id.tv_check_version)
    TextView tvCheckVersion;
    private ComDialog comDialog;
    private UpdatePresenter updatePresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update, null);
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
        tvCheckVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showUpdateDialog();
                updatePresenter.queryLatestVersion(getUserId());
            }
        });
    }


    private void initData() {
        updatePresenter = new UpdatePresenter(getContext(), this);
    }

    private void showUpdateDialog(){
        comDialog = new ComDialog(mContext, "检测新版本","是否更新到最新版本？", new ComDialog.Callback() {
            @Override
            public void callback(int param) {
                //打开系统浏览器，下载文件
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri url = Uri.parse(URLs.ServerUrl+"/app/advertisement/downLoadNewVersion.json");
                intent.setData(url);
                startActivity(intent);
            }
        });
        comDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onUpdateSuccess(UpdateBean bean) {
        if (bean.versionUpdate != null) {
//            mVersionUpdate = bean.versionUpdate;
            int curVCode = DeviceUtils.getVersionCode(getContext());
//            int newVCode = mVersionUpdate.versionNum;
            //是否有新版本，是否强制更新
//            boolean hasNewVersion = newVCode > curVCode;
//            boolean hasNewVersion = true;
//            if (hasNewVersion) {
//                boolean isStrongUpdate = mVersionUpdate.forceUpdate.equals("1");
//                if (hasNewVersion) {
                    //发现新版本
                    showUpdateDialog();
//                }
//            }
        }
    }
}
