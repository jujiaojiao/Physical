package com.physical.app.common.api;

import android.content.Context;


import com.physical.app.LoginActivity;
import com.physical.app.common.utils.NetworkUtils;
import com.physical.app.common.utils.ToastUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

public abstract class ProgressSubscriber<T> extends Subscriber<T> implements ProgressDialogListener {

    /**
     * 上下文
     */
    private Context context;

    private ProgressDialogHandler mProgressDialogHandler;

    private boolean showDialog = true;


    public ProgressSubscriber(Context context) {
        this.context = context;
        this.mProgressDialogHandler = new ProgressDialogHandler(false, this, context);
    }

    public ProgressSubscriber(Context context, boolean showDialog) {
        this.context = context;
        this.mProgressDialogHandler = new ProgressDialogHandler(false, this, context);
        this.showDialog = showDialog;
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }


    @Override
    public void onStart() {

        if (!NetworkUtils.isNetworkAvailable(context)) {
            ToastUtil.show("网络中断，请检查您的网络状态");
            if (!this.isUnsubscribed()) {
                this.unsubscribe();
            }
            return;
        }
        if (showDialog) {
            showProgressDialog();
        }
    }


    @Override
    public void onCompleted() {
        onFinish();
        dismissProgressDialog();
    }
    public void onFinish() {
    }


    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof SocketTimeoutException) {
            ToastUtil.show("网络中断，请检查您的网络状态");
        } else if (e instanceof ConnectException) {
            ToastUtil.show("网络中断，请检查您的网络状态");
        } else if (e instanceof ApiException) {
            ApiException ae = (ApiException) e;
            String type = ae.type;
            if ("-200".equals(type)) {
                //弹出互踢对话框，另外处理
                LoginActivity.start(context);
//                DropOutActivity.startDropOutActivity(e.getMessage(), context);
            } else {
                ToastUtil.show(e.getMessage());
            }
        } else {
            ToastUtil.show("服务器异常！！！");
        }
        dismissProgressDialog();
        onFinish();
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

}
