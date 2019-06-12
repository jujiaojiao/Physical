package com.physical.app.callback;

import com.physical.app.bean.TimeBean;

import java.util.List;

/**
 * @author jjj
 * 版本：1.0
 * 创建日期：2019/6/12
 * 描述：
 */
public interface ITimeCallback {
    void onQuerySuccess(List<TimeBean> bean);
}
