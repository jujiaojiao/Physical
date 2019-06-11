package com.physical.app.presenter;

import android.content.Context;

import com.physical.app.bean.SeedlingBean;
import com.physical.app.bean.UpdateBean;
import com.physical.app.callback.IUpdateCallback;
import com.physical.app.common.api.ProgressSubscriber;
import com.physical.app.common.base.BasePresenter;

import java.util.List;

/**
 * Created by jjj
 * 时间:  2019/6/11
 * 邮箱: jujiaojiao@gemdale.com
 * 描述:
 */

public class UpdatePresenter extends BasePresenter {

    private IUpdateCallback callback;

    public UpdatePresenter(Context context,IUpdateCallback callback) {
        super(context);
        this.callback =callback;
    }


    /**
     * 查询最新的版本号
     *
     * @param sessionId
     * @return
     */
    public void queryLatestVersion(String sessionId) {
        mRequestClient.queryLatestVersion(sessionId).subscribe(new ProgressSubscriber<UpdateBean>(mContext) {
            @Override
            public void onNext(UpdateBean bean) {
                callback.onUpdateSuccess(bean);
            }

        });
    }


}
