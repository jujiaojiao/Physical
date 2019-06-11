package com.physical.app.common.api;


import com.physical.app.MyApplication;
import com.physical.app.bean.MedicalHistory;
import com.physical.app.bean.MemberManageBean;
import com.physical.app.bean.MemberVo;
import com.physical.app.bean.RecommendBean;
import com.physical.app.bean.SeedlingBean;
import com.physical.app.bean.UpdateBean;
import com.physical.app.common.mine.bean.User;

import java.util.List;
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
    public Observable<List<SeedlingBean>> seedling(String sessionId) {
        return mServerApi.seedling(sessionId)
                .map(new HttpResultFuc<List<SeedlingBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取注册验证码
     *
     * @param mobile
     * @return
     */
    public Observable<Object> sendMessage(String mobile,String type) {
        return mServerApi.sendMessage(mobile,type)
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
                                     String times,
                                     String sessionId) {
        return mServerApi.charge(machineCode, memberId, money, times, sessionId)
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
    public Observable<MemberVo> queryDetailById(String memberId, String sessionId) {
        return mServerApi.queryDetailById(memberId, sessionId)
                .map(new HttpResultFuc<MemberVo>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 分页查询会员列表
     *
     * @param idxStr   姓名或手机号或身份证
     * @param page     当前默认页
     * @param pageSize 每页大小
     * @return
     */
    public Observable<List<MemberVo>> queryMemberList(String sessionId,
                                                      String idxStr,
                                                      String page,
                                                      String pageSize) {
        return mServerApi.queryMemberList(sessionId, idxStr, page, pageSize)
                .map(new HttpResultFuc<List<MemberVo>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 查询处方
     *
     * @param hisIds
     * @return
     */
    public Observable<List<RecommendBean>> queryRecipeList(String hisIds, String sessionId) {
        return mServerApi.queryRecipeList(hisIds, sessionId)
                .map(new HttpResultFuc<List<RecommendBean>>())
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
    public Observable<Object> updatePwd(String newPassword, String userId, String verifyCode, String sessionId) {
        return mServerApi.updatePwd(newPassword, userId, verifyCode, sessionId)
                .map(new HttpResultFuc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 新增会员
     *
     * @param param
     * @return
     */
    public Observable<Object> saveOrUpdate(String param, String sessionId) {
        return mServerApi.saveOrUpdate(param, sessionId)
                .map(new HttpResultFuc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 病史列表
     *
     * @return
     */
    public Observable<List<MedicalHistory>> disease(String sessionId) {
        return mServerApi.disease(sessionId)
                .map(new HttpResultFuc<List<MedicalHistory>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 上传机器问题报告
     *
     * @param title       标题
     * @param descript    描述
     * @param machineCode 机器码
     * @param sessionId
     * @return
     */
    public Observable<Object> problemReport(String title,
                                            String descript,
                                            String machineCode,
                                            String sessionId) {
        return mServerApi.problemReport(title, descript, machineCode, sessionId)
                .map(new HttpResultFuc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 查询最新的版本号
     *
     * @param sessionId
     * @return
     */
    public Observable<UpdateBean> queryLatestVersion(String sessionId) {
        return mServerApi.queryLatestVersion(sessionId)
                .map(new HttpResultFuc<UpdateBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 查询广告
     *
     * @param sessionId
     * @return
     */
    public Observable<Object> query(String sessionId) {
        return mServerApi.query(sessionId)
                .map(new HttpResultFuc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 下载app
     *
     * @param sessionId
     * @return
     */
    public Observable<Object> downLoadNewVersion(String sessionId) {
        return mServerApi.downLoadNewVersion(sessionId)
                .map(new HttpResultFuc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 新增疗养
     * @param param
     * @param sessionId
     * @return
     */
    public Observable<Object> save(String param,String sessionId) {
        return mServerApi.save(param, sessionId)
                .map(new HttpResultFuc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     *  疗养纪录
     * @param startTime 起始时间
     * @param endTime 结束时间
     * @param machineCode 机器码
     * @param page 当前页
     * @param pageSize 每页大小
     * @param sessionId 用户id
     * @return
     */
    public Observable<Object> queryList(String startTime,
                                            String endTime,
                                            String machineCode,
                                            String page,
                                            String pageSize,
                                            String sessionId) {
        return mServerApi.queryList(startTime, endTime, machineCode, page, pageSize, sessionId)
                .map(new HttpResultFuc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
