package com.physical.app.common.api;

public class HttpResult<T> {

    public String type;
    public String msg;
    public T returnMap;

    @Override
    public String toString() {
        return "HttpResult{" +
                "type='" + type + '\'' +
                ", msg='" + msg + '\'' +
                ", returnMap=" + returnMap +
                '}';
    }
}
