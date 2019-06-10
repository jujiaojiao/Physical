package com.physical.app.presenter;

import android.content.Context;

import com.physical.app.bean.MemberManageBean;
import com.physical.app.bean.MemberVo;
import com.physical.app.callback.IMemberManageCallback;
import com.physical.app.common.api.ProgressSubscriber;
import com.physical.app.common.base.BasePresenter;

import java.util.List;

/**
 * @author jjj
 * 版本：1.0
 * 创建日期：2019/6/1
 * 描述： 会员列表
 */
public class MemberManagePresenter extends BasePresenter {

    private IMemberManageCallback callback;

    public MemberManagePresenter(Context context, IMemberManageCallback callback) {
        super(context);
        this.callback = callback;
    }


    /**
     * 分页查询会员列表
     *
     * @param idxStr   姓名或手机号或身份证
     * @param page     当前默认页
     * @param pageSize 每页大小
     * @return
     */
    public void queryMemberList(String sessionId,
                                String idxStr,
                                String page,
                                String pageSize) {
        mRequestClient.queryMemberList( sessionId,idxStr, page, pageSize).subscribe(new ProgressSubscriber<List<MemberVo>>(mContext) {
            @Override
            public void onNext(List<MemberVo>  bean) {
                callback.onMemberListSuccess(bean);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                callback.onFinish();
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
