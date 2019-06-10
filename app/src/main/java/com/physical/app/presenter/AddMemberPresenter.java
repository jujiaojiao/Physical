package com.physical.app.presenter;

import android.content.Context;

import com.physical.app.bean.MedicalHistory;
import com.physical.app.callback.IAddMemberCallback;
import com.physical.app.common.api.ProgressSubscriber;
import com.physical.app.common.base.BasePresenter;

import java.util.List;

/**
 * @author jjj
 * 版本：1.0
 * 创建日期：2019/6/1
 * 描述：新增会员
 */
public class AddMemberPresenter extends BasePresenter {

    private IAddMemberCallback callback;

    public AddMemberPresenter(Context context,IAddMemberCallback callback) {
        super(context);
        this.callback = callback;
    }


    /**
     * 新增会员
     *
     * @param param
     * @return
     */
    public void save(String param,String sessionId) {
        mRequestClient.save(param,sessionId).subscribe(new ProgressSubscriber<Object>(mContext) {
            @Override
            public void onNext(Object bean) {
                callback.onSaveSuccess();
            }

        });
    }

    /**
     *  病史列表
     * @return
     */
    public void disease(String sessionId) {
        mRequestClient.disease(sessionId).subscribe(new ProgressSubscriber<List<MedicalHistory>>(mContext) {
            @Override
            public void onNext(List<MedicalHistory> bean) {
                callback.onDiseaseSuccess(bean);
            }
        });
    }
    /**
     * 会员充值
     *
     * @param machineCode
     * @param memberId
     * @param money
     * @param times
     * @return
     */
    public void charge(String machineCode,
                       String memberId,
                       String money,
                       String times,
                       String sessionId) {
        mRequestClient.charge(machineCode, memberId, money, times, sessionId).subscribe(new ProgressSubscriber<Object>(mContext) {
            @Override
            public void onNext(Object bean) {
                callback.onchargeSuccess();
            }
        });
    }
}
