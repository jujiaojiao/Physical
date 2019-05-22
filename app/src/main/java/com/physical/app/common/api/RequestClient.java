package com.physical.app.common.api;


import com.physical.app.MyApplication;
import com.physical.app.common.mine.bean.User;

import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者：liuyuanqi on 17/7/17 16:02
 * 邮箱：liuyuanqi@eims.com.cn
 */

public class RequestClient {

    /**
     * 超时时间(秒)
     */
    public static final int DEFAULT_TIMEOUT = 30;

    /**
     * 单例
     */
    private static RequestClient requestClient;

    private OkHttpClient okHttpClient;

    private Retrofit mRetrofit;

    private ServerAPI mServerApi;

    private RequestClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
//        builder.retryOnConnectionFailure(false);
        //添加https支持
//        builder.sslSocketFactory(SSLSocketFactoryUtils.createSSLSocketFactory());
        builder.sslSocketFactory(SSLSocketFactoryUtils.createSSLSocketFactory(MyApplication.context));
//        builder.sslSocketFactory(SSLSocketFactoryUtils.getSSLSocketFactory(MyApplication.context));
        //拦截器－添加公共字段
        builder.addInterceptor(new CommonInterceptor());
        builder.addNetworkInterceptor(new LoggingInterceptor());
        okHttpClient = builder.build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(URLs.ServerUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mServerApi = mRetrofit.create(ServerAPI.class);
    }

    public static RequestClient getInstance() {
        if (null == requestClient) {
            requestClient = new RequestClient();
        }
        return requestClient;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    /**
     * 文件上传
     *
     * @param userid
     * @param mobile
     * @param part
     * @return
     */
    public Observable<Object> test2(RequestBody userid, RequestBody mobile, MultipartBody.Part part) {
        return mServerApi.test2(userid, mobile, part)
//                .map(new HttpResultFuc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 登录接口
     *
     * @param user_name
     * @param password
     * @return
     */
    public Observable<User> login(String user_name, String password) {
        return mServerApi.login(user_name, password)
                .map(new HttpResultFuc<User>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取验证码
     *
     * @param phone
     * @param type
     * @return
     */
    public Observable<Object> getVerificationCode(String phone, String type) {
        return mServerApi.getVerificationCode(phone, type)
                .map(new HttpResultFuc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 手机号注册
     * @param phone
     * @param validateKey
     * @param password
     * @return
     */
    public Observable<Object> mobileRegisiter(String phone, String validateKey, String password) {
        return mServerApi.mobileRegisiter(phone, validateKey, password)
                .map(new HttpResultFuc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



}
