package com.physical.app.presenter;

import android.content.Context;

import com.physical.app.callback.IRegisterCallback;
import com.physical.app.common.api.ProgressSubscriber;
import com.physical.app.common.base.BasePresenter;
import com.physical.app.common.utils.ToastUtil;

/**
 * Created by jjj
 * 时间:  2019/5/29
 * 邮箱: jujiaojiao@gemdale.com
 * 描述: 注册
 */

public class RegisterPresenter extends BasePresenter {

    private IRegisterCallback callback;

    public RegisterPresenter(Context context,IRegisterCallback callback) {
        super(context);
        this.callback = callback;
    }

    /**
     * 获取注册验证码
     *
     * @param mobile
     * @return
     */
    public void sendMessage(String mobile) {
        mRequestClient.sendMessage(mobile).subscribe(new ProgressSubscriber<Object>(mContext) {
            @Override
            public void onNext(Object bean) {
                callback.onCodeFinish();
            }

        });
    }


    /**
     * 注册
     * @param countryCode
     * @param machineCode
     * @param mobile
     * @param password
     * @param rePassword
     * @param userName
     * @param verifyCode
     */
    public void register( String countryCode,
                          String machineCode,
                          String mobile,
                          String  password,
                          String rePassword,
                          String  userName,
                          String  verifyCode) {
        mRequestClient.register(countryCode, machineCode, mobile, password, rePassword, userName, verifyCode).subscribe(new ProgressSubscriber<Object>(mContext) {
            @Override
            public void onNext(Object bean) {
                callback.onRegisterSuccess();
            }

        });
    }

}
