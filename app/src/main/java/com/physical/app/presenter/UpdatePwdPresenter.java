package com.physical.app.presenter;

import android.content.Context;

import com.physical.app.callback.IUpdatePwdCallback;
import com.physical.app.common.api.ProgressSubscriber;
import com.physical.app.common.base.BasePresenter;

/**
 * Created by jjj
 * 时间:  2019/6/10
 * 邮箱: jujiaojiao@gemdale.com
 * 描述:
 */

public class UpdatePwdPresenter extends BasePresenter {

    private IUpdatePwdCallback callback;

    public UpdatePwdPresenter(Context context,IUpdatePwdCallback callback) {
        super(context);
        this.callback =callback;
    }


    /**
     * 修改密码
     *
     * @param newPassword
     * @param userId
     * @param verifyCode
     * @return
     */
    public void updatePwd(String newPassword, String userId, String verifyCode, String sessionId) {
        mRequestClient.updatePwd(newPassword, userId, verifyCode, sessionId).subscribe(new ProgressSubscriber<Object>(mContext) {
            @Override
            public void onNext(Object bean) {
                callback.onUpdateSuccess(bean);
            }

        });
    }

}
