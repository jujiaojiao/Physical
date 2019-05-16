package com.physical.app.common.api;

/**
 * 异常类，当获取的数据不是我们需要时，抛出异常
 *
 * Created by liuyuanqi on 2017/7/19.
 */
public class NetworkException extends RuntimeException {

    public String type;

    /**
     * 异常信息
     * @param detailMessage
     */
    public NetworkException(String type, String detailMessage) {
        super(detailMessage);
        this.type = type;
    }
}
