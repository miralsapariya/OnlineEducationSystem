package com.onlineeducationsyestem.network;


import com.onlineeducationsyestem.model.BaseBean;
import com.onlineeducationsyestem.model.User;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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
    Call<BaseBean> register(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("verify")
    Call<User> otp(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("resend")
    Call<BaseBean> resend(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("change_password")
    Call<BaseBean> changePwd(@FieldMap HashMap<String, String> map);
}