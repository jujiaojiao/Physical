package com.physical.app.common.api;

import rx.functions.Func1;

public class HttpResultFuc<T> implements Func1<HttpResult<T>,T> {
    @Override
    public T call(HttpResult<T> httpResult) {
        if(!"1".equals(httpResult.type)){

            //非正常返回结构处理
            throw new ApiException(httpResult.type, httpResult.msg);
        }
        return httpResult.returnMap;
    }
}
