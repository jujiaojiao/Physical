package com.physical.app.common.api;


import com.physical.app.bean.MedicalHistory;
import com.physical.app.bean.MemberManageBean;
import com.physical.app.bean.MemberVo;
import com.physical.app.bean.RecommendBean;
import com.physical.app.bean.SeedlingBean;
import com.physical.app.bean.UpdateBean;
import com.physical.app.common.mine.bean.User;

import java.util.List;

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
     * 幼苗列表
     *
     * @param sessionId
     * @return
     */
    @POST("app/data/seedling.json")
    @FormUrlEncoded
    Observable<HttpResult<List<SeedlingBean>>> seedling(@Field("sessionId") String sessionId);

    /**
     * 获取注册验证码
     *
     * @param mobile
     * @return
     */
    @POST("app/message/sendMessage.json")
    @FormUrlEncoded
    Observable<HttpResult<Object>> sendMessage(@Field("mobile") String mobile,@Field("type")String type);

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
                                          @Field("times") String times,
                                          @Field("sessionId") String sessionId);

    /**
     * 根据会员id查询会员详情
     *
     * @param memberId
     * @return
     */
    @POST("app/member/queryDetailById.json")
    @FormUrlEncoded
    Observable<HttpResult<MemberVo>> queryDetailById(@Field("memberId") String memberId, @Field("sessionId") String sessionId);


    /**
     * 分页查询会员列表
     *
     * @param idxStr   姓名或手机号或身份证
     * @param page     当前默认页
     * @param pageSize 每页大小
     * @return
     */
    @POST("app/member/queryMemberList.json")
    @FormUrlEncoded
    Observable<HttpResult<List<MemberVo>>> queryMemberList(@Field("sessionId") String sessionId,
                                                           @Field("idxStr") String idxStr,
                                                           @Field("page") String page,
                                                           @Field("pageSize") String pageSize);
//    ,
//    @Field("idCard") String idCard,
//    @Field("mobile") String mobile,
//    @Field("userName") String userName

    /**
     * 查询处方
     *
     * @param hisIds
     * @return
     */
    @POST("app/member/queryRecipeList.json")
    @FormUrlEncoded
    Observable<HttpResult<List<RecommendBean>>> queryRecipeList(@Field("hisIds") String hisIds, @Field("sessionId") String sessionId);


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
                                             @Field("verifyCode") String verifyCode,
                                             @Field("sessionId") String sessionId);

    /**
     * 新增会员
     *
     * @param param
     * @return
     */
    @POST("app/member/saveOrUpdate.json")
    @FormUrlEncoded
    Observable<HttpResult<Object>> saveOrUpdate(@Field("param") String param, @Field("sessionId") String sessionId);

    /**
     * 病史列表
     *
     * @param sessionId
     * @return
     */
    @POST("app/data/disease.json")
    @FormUrlEncoded
    Observable<HttpResult<List<MedicalHistory>>> disease(@Field("sessionId") String sessionId);


    /**
     * 上传机器问题报告
     *
     * @param title       标题
     * @param descript    描述
     * @param machineCode 机器码
     * @param sessionId
     * @return
     */
    @POST("app/machine/problemReport.json")
    @FormUrlEncoded
    Observable<HttpResult<Object>> problemReport(@Field("title") String title,
                                                 @Field("descript") String descript,
                                                 @Field("machineCode") String machineCode,
                                                 @Field("sessionId") String sessionId);

    /**
     * 查询最新的版本号
     * @param sessionId
     * @return
     */
    @POST("app/advertisement/queryLatestVersion.json")
    @FormUrlEncoded
    Observable<HttpResult<UpdateBean>> queryLatestVersion(@Field("sessionId") String sessionId);

    /**
     * 查询广告
     * @param sessionId
     * @return
     */
    @POST("app/advertisement/query.json")
    @FormUrlEncoded
    Observable<HttpResult<Object>> query(@Field("sessionId") String sessionId);

    /**
     * 下载app
     * @param sessionId
     * @return
     */
    @POST("app/advertisement/downLoadNewVersion.json")
    @FormUrlEncoded
    Observable<HttpResult<Object>> downLoadNewVersion(@Field("sessionId") String sessionId);


    /**
     * 新增疗养
     * @param param
     * @param sessionId
     * @return
     */
    @POST("app/case/save.json")
    @FormUrlEncoded
    Observable<HttpResult<Object>> save(@Field("param") String param,@Field("sessionId") String sessionId);



    /**
     * 开始疗养
     * @param sessionId
     * @return
     */
    @POST("app/case/start.json")
    @FormUrlEncoded
    Observable<HttpResult<Object>> start(@Field("Id") String Id,@Field("beginTime") String beginTime, @Field("sessionId") String sessionId);


    /**
     * 结束疗养
     * @param sessionId
     * @return
     */
    @POST("app/case/finish.json")
    @FormUrlEncoded
    Observable<HttpResult<Object>> finish(@Field("Id") String Id,@Field("endTime") String endTime, @Field("sessionId") String sessionId);


    /**
     * 评价
     * @param sessionId
     * @return
     */
    @POST("app/case/comment.json")
    @FormUrlEncoded
    Observable<HttpResult<Object>> comment(@Field("Id") String Id,@Field("commentType") String commentType,@Field("comment") String comment, @Field("sessionId") String sessionId);


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
    @POST("app/case/queryList.json")
    @FormUrlEncoded
    Observable<HttpResult<Object>> queryList(@Field("startTime ") String startTime ,
                                             @Field("endTime ") String endTime,
                                             @Field("machineCode  ") String machineCode,
                                             @Field("page") String page,
                                             @Field("pageSize") String pageSize,
                                             @Field("sessionId") String sessionId);


}
