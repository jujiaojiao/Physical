package com.physical.app.presenter;

import android.content.Context;

import com.physical.app.callback.IRepairCallback;
import com.physical.app.common.api.ProgressSubscriber;
import com.physical.app.common.base.BasePresenter;

/**
 * Created by jjj
 * 时间:  2019/6/10
 * 邮箱: jujiaojiao@gemdale.com
 * 描述:
 */

public class RepairPresenter extends BasePresenter {

    private IRepairCallback callback;

    public RepairPresenter(Context context,IRepairCallback callback) {
        super(context);
        this.callback = callback;
    }


    /**
     * 上传机器问题报告
     *
     * @param title       标题
     * @param descript    描述
     * @param machineCode 机器码
     * @param sessionId
     * @return
     */
    public void problemReport(String title,
                              String descript,
                              String machineCode,
                              String sessionId) {
        mRequestClient.problemReport(title, descript, machineCode, sessionId).subscribe(new ProgressSubscriber<Object>(mContext) {
            @Override
            public void onNext(Object bean) {
                callback.onProblemSuccess(bean);
            }

        });
    }

}
