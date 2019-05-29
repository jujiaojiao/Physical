package com.physical.app.common.api;

public class HttpResult<T> {

    public String status;
    public String statusText;
    public T data;

    @Override
    public String toString() {
        return "HttpResult{" +
                "type='" + status + '\'' +
                ", msg='" + statusText + '\'' +
                ", returnMap=" + data +
                '}';
    }
}
