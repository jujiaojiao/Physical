package com.physical.app.presenter;

import android.content.Context;

import com.physical.app.callback.IMemberManageCallback;
import com.physical.app.common.api.ProgressSubscriber;
import com.physical.app.common.base.BasePresenter;

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
     * @param idCard   身份证
     * @param idxStr   姓名或手机号或身份证
     * @param mobile   手机号
     * @param page     当前默认页
     * @param pageSize 每页大小
     * @param userName 姓名
     * @return
     */
    public void queryMemberList(String sessionId,
                                String idxStr,
                                String page,
                                String pageSize) {
        mRequestClient.queryMemberList( sessionId,idxStr, page, pageSize).subscribe(new ProgressSubscriber<Object>(mContext) {
            @Override
            public void onNext(Object bean) {
                callback.onMemberListSuccess();
            }

        });
    }
}
