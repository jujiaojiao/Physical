package com.physical.app.presenter;

import android.content.Context;

import com.physical.app.callback.ILoginCallback;
import com.physical.app.common.api.ProgressSubscriber;
import com.physical.app.common.base.BasePresenter;
import com.physical.app.common.mine.bean.User;

/**
 * Created by jjj
 * 时间:  2019/5/31
 * 邮箱: jujiaojiao@gemdale.com
 * 描述: 登录
 */

public class LoginPresenter extends BasePresenter {

    private ILoginCallback callback;

    public LoginPresenter(Context context,ILoginCallback callback) {
        super(context);
        this.callback = callback;
    }

    /**
     *  登录
     * @param machineCode 机器码
     * @param user_name 用户名
     * @param password 密码
     * @return
     */
    public void login(String machineCode,String user_name, String password) {
        mRequestClient.login(machineCode, user_name, password).subscribe(new ProgressSubscriber<User>(mContext) {
            @Override
            public void onNext(User user) {
                callback.onLoginSuccess(user);
            }
        });
    }
}
