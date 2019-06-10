package com.physical.app.callback;

import com.physical.app.bean.MedicalHistory;
import com.physical.app.bean.MemberVo;

import java.util.List;

/**
 * @author jjj
 * 版本：1.0
 * 创建日期：2019/6/1
 * 描述： 新增会员
 */
public interface IAddMemberCallback  {
    void onSaveSuccess();

    void onDiseaseSuccess(List<MedicalHistory> bean);


    void onQueryDetailSuccess(MemberVo bean);

    void onchargeSuccess();

}
