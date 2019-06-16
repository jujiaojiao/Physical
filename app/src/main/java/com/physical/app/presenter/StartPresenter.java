package com.physical.app.presenter;

import android.content.Context;

import com.physical.app.callback.IStartCallback;
import com.physical.app.common.api.ProgressSubscriber;
import com.physical.app.common.base.BasePresenter;

/**
 * @author jjj
 * 版本：1.0
 * 创建日期：2019/6/16
 * 描述：
 */
public class StartPresenter extends BasePresenter {

    private IStartCallback callback;

    public StartPresenter(Context context, IStartCallback callback) {
        super(context);
        this.callback = callback;
    }

    /**
     * 查询广告
     *
     */
    public void query(String sessionId) {
        mRequestClient.query(sessionId).subscribe(new ProgressSubscriber<Object>(mContext) {
            @Override
            public void onNext(Object bean) {
                callback.onQuerySuccess();
            }

        });
    }
}
