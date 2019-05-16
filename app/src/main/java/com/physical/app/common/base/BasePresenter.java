package com.physical.app.common.base;

import android.content.Context;

import com.physical.app.common.api.RequestClient;


/**
 * 作者: liangzixun
 * 时间: 2017/9/8 09:06
 * 邮箱: liangzixun@eims.com.cn
 */
public class BasePresenter {
    protected Context mContext;
    protected RequestClient mRequestClient;

    public BasePresenter(Context context){
        mContext = context;
        mRequestClient = RequestClient.getInstance();
    }
}
