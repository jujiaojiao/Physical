package com.physical.app.presenter;

import android.content.Context;

import com.physical.app.callback.ISeedlingCallback;
import com.physical.app.common.api.ProgressSubscriber;
import com.physical.app.common.base.BasePresenter;
import com.physical.app.common.utils.ToastUtil;

/**
 * Created by jjj
 * 时间:  2019/5/29
 * 邮箱: jujiaojiao@gemdale.com
 * 描述:
 */

public class SeedlingPresenter extends BasePresenter {

    private ISeedlingCallback callback;

    public SeedlingPresenter(Context context,ISeedlingCallback callback) {
        super(context);
        this.callback = callback;
    }


    public void seedling() {
        mRequestClient.seedling().subscribe(new ProgressSubscriber<Object>(mContext) {
            @Override
            public void onNext(Object bean) {
                ToastUtil.show("网络请求成功");
            }

        });
    }


}
