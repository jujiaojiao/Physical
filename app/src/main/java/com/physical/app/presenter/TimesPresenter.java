package com.physical.app.presenter;

import android.content.Context;

import com.physical.app.bean.AddPhysicalBean;
import com.physical.app.bean.TimeBean;
import com.physical.app.callback.ITimeCallback;
import com.physical.app.common.api.ProgressSubscriber;
import com.physical.app.common.base.BasePresenter;

import java.util.List;

/**
 * @author jjj
 * 版本：1.0
 * 创建日期：2019/6/12
 * 描述：
 */
public class TimesPresenter extends BasePresenter {

    private ITimeCallback callback;

    public TimesPresenter(Context context,ITimeCallback callback) {
        super(context);
        this.callback =callback;
    }

    public void queryList(String startTime,
                     String endTime,
                     String machineCode,
                     String page,
                     String pageSize,
                     String sessionId){
        mRequestClient.queryList(startTime, endTime, machineCode, page, pageSize, sessionId).subscribe(new ProgressSubscriber<List<TimeBean>>(mContext) {
            @Override
            public void onNext(List<TimeBean> bean) {
                callback.onQuerySuccess(bean);
            }

        });
    }
}
