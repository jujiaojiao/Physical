package com.physical.app.presenter;

import android.content.Context;

import com.physical.app.callback.IPhysicalCallback;
import com.physical.app.common.api.ProgressSubscriber;
import com.physical.app.common.base.BasePresenter;

/**
 * @author jjj
 * 版本：1.0
 * 创建日期：2019/6/11
 * 描述：
 */
public class PhysicalPresenter extends BasePresenter {

    private IPhysicalCallback callback;

    public PhysicalPresenter(Context context,IPhysicalCallback callback) {
        super(context);
        this.callback = callback;
    }
    /**
     * 新增疗养
     * @param param
     * @param sessionId
     * @return
     */
    public void save(String param,String sessionId){
        mRequestClient.save(param, sessionId).subscribe(new ProgressSubscriber<Object>(mContext) {
            @Override
            public void onNext(Object bean) {
                callback.onSaveSuccess();
            }

        });
    }

    /**
     * 开始疗养
     * @param sessionId
     * @return
     */
    public void start(String id,String beginTime,String sessionId){
        mRequestClient.start(id, beginTime, sessionId).subscribe(new ProgressSubscriber<Object>(mContext) {
            @Override
            public void onNext(Object bean) {
                callback.onStartSuccess();
            }

        });
    }

    /**
     * 结束疗养
     * @param sessionId
     * @return
     */
    public void finish(String id,String endTime,String sessionId){
        mRequestClient.finish(id, endTime, sessionId).subscribe(new ProgressSubscriber<Object>(mContext) {
            @Override
            public void onNext(Object bean) {
                callback.onFinishSuccess();
            }

        });
    }


    /**
     * 评价
     * @param sessionId
     * @return
     */
    public void comment(String id,String commentType,String comment,String sessionId){
        mRequestClient.comment(id, commentType, comment, sessionId).subscribe(new ProgressSubscriber<Object>(mContext) {
            @Override
            public void onNext(Object bean) {
                callback.onCommentSuccess();
            }

        });
    }

}
