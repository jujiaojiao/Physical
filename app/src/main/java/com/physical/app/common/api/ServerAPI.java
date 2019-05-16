package com.physical.app.common.api;


import com.physical.app.mine.bean.User;

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
     * 登录接口
     *
     * @param user_name
     * @param password
     * @return
     */
    @POST("appApi/login")
    @FormUrlEncoded
    Observable<HttpResult<User>> login(@Field("user_name") String user_name, @Field("password") String password);

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
     * @param phone
     * @param validateKey
     * @param password
     * @return
     */
    @POST("appApi/mobileRegisiter")
    @FormUrlEncoded
    Observable<HttpResult<Object>> mobileRegisiter(@Field("phone") String phone, @Field("validateKey") String validateKey, @Field("password") String password);

}
