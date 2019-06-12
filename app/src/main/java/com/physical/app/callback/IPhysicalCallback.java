package com.physical.app.callback;

import com.physical.app.bean.AddPhysicalBean;

/**
 * @author jjj
 * 版本：1.0
 * 创建日期：2019/6/11
 * 描述：
 */
public interface IPhysicalCallback {
    void onSaveSuccess(AddPhysicalBean bean);

    void onFinishSuccess();

    void onStartSuccess();

    void onCommentSuccess();

}
