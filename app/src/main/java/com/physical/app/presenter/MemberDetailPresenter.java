package com.physical.app.presenter;

import android.content.Context;

import com.physical.app.bean.MemberDetailBean;
import com.physical.app.bean.MemberVo;
import com.physical.app.callback.IMemberDetailCallback;
import com.physical.app.common.api.ProgressSubscriber;
import com.physical.app.common.base.BasePresenter;

import java.util.List;

/**
 * Created by jjj
 * 时间:  2019/6/10
 * 邮箱: jujiaojiao@gemdale.com
 * 描述: 会员详情
 */

public class MemberDetailPresenter extends BasePresenter {

    private IMemberDetailCallback callback;
    public MemberDetailPresenter(Context context,IMemberDetailCallback callback) {
        super(context);
        this.callback = callback;
    }

    /**
     * 根据会员id查询会员详情
     *
     * @param memberId
     * @return
     */
    public void queryDetailById(String memberId,String sessionId) {
        mRequestClient.queryDetailById(memberId,sessionId).subscribe(new ProgressSubscriber<MemberDetailBean>(mContext) {
            @Override
            public void onNext(MemberDetailBean bean) {
                callback.onQueryDetailSuccess(bean);
            }

        });
    }

}
