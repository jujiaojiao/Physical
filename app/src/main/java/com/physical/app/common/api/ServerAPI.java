package com.physical.app.common.api;


import com.physical.app.common.mine.bean.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;


public interface ServerAPI {

    //    @POST("http://192.168.4.25:8082/app/fileManager/uploadFile.do")
    @POST("http://123.56.199.235:9999/api/AppUpdate/UploadUserInfoImg")
    @Multipart
    Observable<Object> test2(@Part("userid") RequestBody userid, @Part("mobile") RequestBody mobile, @Part MultipartBody.Part file);

    /**
     * 获取验证码
     *
     * @param phone
     * @param type
     * @return
     */
    @POST("appApi/getVerificationCode")
    @FormUrlEncoded
    Observable<HttpResult<Object>> getVerificationCode(@Field("phone") String phone, @Field("type") String type);

    /**
     * 手机号注册
     *
     * @param phone
     * @param validateKey
     * @param password
     * @return
     */
    @POST("appApi/mobileRegisiter")
    @FormUrlEncoded
    Observable<HttpResult<Object>> mobileRegisiter(@Field("phone") String phone, @Field("validateKey") String validateKey, @Field("password") String password);

    /**
     * 幼苗
     *
     * @param phone
     * @return
     */
    @POST("app/data/seedling")
    @FormUrlEncoded
    Observable<HttpResult<Object>> seedling(@Field("phone") String phone);

    /**
     * 获取注册验证码
     *
     * @param mobile
     * @return
     */
    @POST("app/message/sendMessage.json")
    @FormUrlEncoded
    Observable<HttpResult<Object>> sendMessage(@Field("mobile") String mobile);

    /**
     * 注册
     *
     * @param mobile
     * @return
     */
    @POST("app/member/register.json")
    @FormUrlEncoded
    Observable<HttpResult<Object>> register(@Field("countryCode") String countryCode,
                                            @Field("machineCode") String machineCode,
                                            @Field("mobile") String mobile,
                                            @Field("password") String password,
                                            @Field("rePassword") String rePassword,
                                            @Field("userName") String userName,
                                            @Field("verifyCode") String verifyCode);

    /**
     * 登录
     *
     * @param machineCode 机器码
     * @param userName    用户名
     * @param password    密码
     * @return
     */
    @POST("app/member/login.json")
    @FormUrlEncoded
    Observable<HttpResult<User>> login(@Field("machineCode") String machineCode,
                                       @Field("userName") String userName,
                                       @Field("password") String password);


    /**
     * 会员充值
     *
     * @param machineCode
     * @param memberId
     * @param money
     * @param times
     * @return
     */
    @POST("app/member/charge.json")
    @FormUrlEncoded
    Observable<HttpResult<Object>> charge(@Field("machineCode") String machineCode,
                                          @Field("memberId") String memberId,
                                          @Field("money") String money,
                                          @Field("times") String times);

    /**
     * 根据会员id查询会员详情
     *
     * @param memberId
     * @return
     */
    @POST("app/member/queryDetailById.json")
    @FormUrlEncoded
    Observable<HttpResult<Object>> queryDetailById(@Field("memberId") String memberId);


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
    @POST("app/member/queryMemberList.json")
    @FormUrlEncoded
    Observable<HttpResult<Object>> queryMemberList(@Field("idCard") String idCard,
                                                   @Field("idxStr") String idxStr,
                                                   @Field("mobile") String mobile,
                                                   @Field("page") String page,
                                                   @Field("pageIndex") String pageIndex,
                                                   @Field("pageSize") String pageSize,
                                                   @Field("userName") String userName);

    /**
     * 查询处方
     *
     * @param hisIds
     * @return
     */
    @POST("app/member/queryRecipeList.json")
    @FormUrlEncoded
    Observable<HttpResult<Object>> queryRecipeList(@Field("hisIds") String hisIds);


    /**
     * 修改密码
     *
     * @param newPassword
     * @param userId
     * @param verifyCode
     * @return
     */
    @POST("app/member/updatePwd.json")
    @FormUrlEncoded
    Observable<HttpResult<Object>> updatePwd(@Field("newPassword") String newPassword,
                                             @Field("userId") String userId,
                                             @Field("verifyCode") String verifyCode);

}
