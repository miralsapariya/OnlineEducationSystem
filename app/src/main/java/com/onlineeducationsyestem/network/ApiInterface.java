package com.onlineeducationsyestem.network;


import com.onlineeducationsyestem.model.BaseBean;
import com.onlineeducationsyestem.model.Category;
import com.onlineeducationsyestem.model.ForgotPwd;
import com.onlineeducationsyestem.model.GetProfile;
import com.onlineeducationsyestem.model.User;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * ApiInterface.java
 * This class act as an interface between Retrofit and Classes used using Retrofit in Application
 *
 * @author Appinvetiv
 * @version 1.0
 * @since 1.0
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login")
    Call<User> login(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("register")
    Call<User> register(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("verify")
    Call<User> otp(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("resend")
    Call<BaseBean> resend(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("change_password")
    Call<BaseBean> changePwd(@Header("Authorization")String auth,@Header("Accept") String headr,@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("forgotpassword")
    Call<ForgotPwd> forgotPWD(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("reset_password")
    Call<BaseBean> resetPwd(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("get_profile")
    Call<GetProfile> getProfile(@Header("Authorization")String auth,@Header("Accept") String headr,@FieldMap HashMap<String, String> map);

    @Multipart
    @POST("edit_profile")
    Call<GetProfile> editProfile(@Header("Authorization")String auth,
                                 @Header("Accept") String headr,
                                 @Part("user_id") RequestBody userId,
                                 @Part("name") RequestBody name,
                                 @Part("email") RequestBody email,
                                 @Part("phone_no") RequestBody phone,
                                 @Part("language") RequestBody lang,
                                 @Part MultipartBody.Part file);

    @Multipart
    @POST("edit_profile")
    Call<GetProfile> editProfile(@Header("Authorization")String auth,
                                 @Header("Accept") String headr,
                                 @Part("user_id") RequestBody userId,
                                 @Part("name") RequestBody name,
                                 @Part("email") RequestBody email,
                                 @Part("phone_no") RequestBody phone,
                                 @Part("language") RequestBody lang
                                );


    @FormUrlEncoded
    @POST("categorieslist")
    Call<Category> categoryList(@FieldMap HashMap<String, String> map);

}