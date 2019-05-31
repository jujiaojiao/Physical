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
//        builder.sslSocketFactory(SSLSocketFactoryUtils.createSSLSocketFactory(MyApplication.context));
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
     * 登录
     *
     * @param machineCode 机器码
     * @param user_name   用户名
     * @param password    密码
     * @return
     */
    public Observable<User> login(String machineCode, String user_name, String password) {
        return mServerApi.login(machineCode, user_name, password)
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
     *
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


    /**
     * 幼苗
     *
     * @return
     */
    public Observable<Object> seedling() {
        return mServerApi.seedling("")
                .map(new HttpResultFuc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取注册验证码
     *
     * @param mobile
     * @return
     */
    public Observable<Object> sendMessage(String mobile) {
        return mServerApi.sendMessage(mobile)
                .map(new HttpResultFuc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 注册
     *
     * @param mobile
     * @return
     */
    public Observable<Object> register(String countryCode,
                                       String machineCode,
                                       String mobile,
                                       String password,
                                       String rePassword,
                                       String userName,
                                       String verifyCode) {
        return mServerApi.register(countryCode, machineCode, mobile, password, rePassword, userName, verifyCode)
                .map(new HttpResultFuc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 会员充值
     *
     * @param machineCode
     * @param memberId
     * @param money
     * @param times
     * @return
     */
    public Observable<Object> charge(String machineCode,
                                     String memberId,
                                     String money,
                                     String times) {
        return mServerApi.charge(machineCode, memberId, money, times)
                .map(new HttpResultFuc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 根据会员id查询会员详情
     *
     * @param memberId
     * @return
     */
    public Observable<Object> queryDetailById(String memberId) {
        return mServerApi.queryDetailById(memberId)
                .map(new HttpResultFuc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 分页查询会员列表
     *
     * @param idCard    身份证
     * @param idxStr    姓名或手机号或身份证
     * @param mobile    手机号
     * @param page      当前默认页
     * @param pageIndex
     * @param pageSize  每页大小
     * @param userName  姓名
     * @return
     */
    public Observable<Object> queryMemberList(String idCard,
                                              String idxStr,
                                              String mobile,
                                              String page,
                                              String pageIndex,
                                              String pageSize,
                                              String userName) {
        return mServerApi.queryMemberList(idCard, idxStr, mobile, page, pageIndex, pageSize, userName)
                .map(new HttpResultFuc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 查询处方
     *
     * @param hisIds
     * @return
     */
    public Observable<Object> queryRecipeList(String hisIds) {
        return mServerApi.queryRecipeList(hisIds)
                .map(new HttpResultFuc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 修改密码
     *
     * @param newPassword
     * @param userId
     * @param verifyCode
     * @return
     */
    public Observable<Object> updatePwd(String newPassword,String userId,String verifyCode) {
        return mServerApi.updatePwd(newPassword, userId, verifyCode)
                .map(new HttpResultFuc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
