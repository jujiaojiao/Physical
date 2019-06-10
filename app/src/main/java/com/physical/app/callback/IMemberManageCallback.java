package com.physical.app.callback;

import com.physical.app.bean.MemberManageBean;
import com.physical.app.bean.MemberVo;

import java.util.List;

/**
 * @author jjj
 * 版本：1.0
 * 创建日期：2019/6/1
 * 描述：会员列表
 */
public interface IMemberManageCallback {
    void onMemberListSuccess(List<MemberVo> bean);

    void onFinish();

    void onchargeSuccess();

}
