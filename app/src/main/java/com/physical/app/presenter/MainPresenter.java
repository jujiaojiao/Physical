package com.physical.app.presenter;

import android.content.Context;

import com.physical.app.bean.MemberDetailBean;
import com.physical.app.callback.IMainCallback;
import com.physical.app.common.api.ProgressSubscriber;
import com.physical.app.common.base.BasePresenter;

/**
 * Created by jjj
 * 时间:  2019/6/20
 * 邮箱: jujiaojiao@gemdale.com
 * 描述:
 */

public class MainPresenter extends BasePresenter{
    private IMainCallback callback;

    public MainPresenter(Context context,IMainCallback callback) {
        super(context);
        this.callback = callback;
    }

    /**
     * 上传离线缓存
     *
     * @param param
     * @param sessionId
     * @return
     */
    public void upload(String param,
                                String sessionId) {
        mRequestClient.upload(param,sessionId).subscribe(new ProgressSubscriber<Object>(mContext) {
            @Override
            public void onNext(Object bean) {
                callback.onUploadSuccess(bean);
            }

        });
    }
}
