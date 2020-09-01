package com.onlineeducationsyestem.network;


import com.onlineeducationsyestem.model.BaseBean;
import com.onlineeducationsyestem.model.CartList;
import com.onlineeducationsyestem.model.Category;
import com.onlineeducationsyestem.model.CourseDetail;
import com.onlineeducationsyestem.model.CourseList;
import com.onlineeducationsyestem.model.DefaultCategory;
import com.onlineeducationsyestem.model.ForgotPwd;
import com.onlineeducationsyestem.model.GetProfile;
import com.onlineeducationsyestem.model.GlobalSearch;
import com.onlineeducationsyestem.model.Home;
import com.onlineeducationsyestem.model.MyCourseList;
import com.onlineeducationsyestem.model.MyWhishList;
import com.onlineeducationsyestem.model.SectionCourse;
import com.onlineeducationsyestem.model.SubCategory;
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
    Call<User> login(@Header("language") String lang,@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("register")
    Call<User> register(@Header("language") String lang,@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("verify")
    Call<User> otp(@Header("language") String lang,@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("resend")
    Call<BaseBean> resend(@Header("language") String lang,@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("change_password")
    Call<BaseBean> changePwd(@Header("language") String lang,@Header("Authorization")String auth,@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("forgotpassword")
    Call<ForgotPwd> forgotPWD(@Header("language") String lang,@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("reset_password")
    Call<BaseBean> resetPwd(@Header("language") String lang,@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("get_profile")
    Call<GetProfile> getProfile(@Header("language") String lang
            ,@Header("Authorization")String auth,
                                @FieldMap HashMap<String, String> map);

    @Multipart
    @POST("edit_profile")
    Call<GetProfile> editProfile(@Header("language") String lang,
                                 @Header("Authorization")String auth,
                                 @Part("user_id") RequestBody userId,
                                 @Part("name") RequestBody name,
                                 @Part("email") RequestBody email,
                                 @Part("phone_no") RequestBody phone,
                                 @Part MultipartBody.Part file);

    @Multipart
    @POST("edit_profile")
    Call<GetProfile> editProfile(@Header("language") String lang,
                                 @Header("Authorization")String auth,
                                 @Part("user_id") RequestBody userId,
                                 @Part("name") RequestBody name,
                                 @Part("email") RequestBody email,
                                 @Part("phone_no") RequestBody phone
                                );


    @FormUrlEncoded
    @POST("categorieslist")
    Call<Category> categoryList(@Header("language") String lang,@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("homescreen")
    Call<Home> getHome(@Header("language") String lang,@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("courselist")
    Call<CourseList> getCourseList(@Header("language") String lang,@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("categories_details")
    Call<SubCategory> getSubCat(@Header("language") String lang,@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("addwishlist")
    Call<BaseBean> addWhishList(@Header("language") String lang,@Header("Authorization")String auth,@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("coursedetails")
    Call<CourseDetail> getCourseDetail(@Header("language") String lang,@FieldMap HashMap<String, String> map);

    @POST("globalsearch")
    Call<DefaultCategory> getDefaultCategory(@Header("language") String lang);

    @FormUrlEncoded
    @POST("globalsearch")
    Call<GlobalSearch> getDefaultCategory(@Header("language") String lang,@FieldMap HashMap<String, String> map);

    @POST("mywishlist")
    Call<MyWhishList> getWhishList(@Header("language") String lang,@Header("Authorization")String auth);

    @POST("mycartlist")
    Call<CartList> getCartList(@Header("language") String lang,@Header("Authorization")String auth);

    @FormUrlEncoded
    @POST("addtocart")
    Call<BaseBean> addToCart(@Header("language") String lang,@Header("Authorization")String auth,@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("deletecart")
    Call<BaseBean> deleteFromCart(@Header("language") String lang,@Header("Authorization")String auth,@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("applypromocode")
    Call<BaseBean> applyPromo(@Header("language") String lang,@Header("Authorization")String auth,@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("proceedcheckout")
    Call<BaseBean> doCheckOut(@Header("language") String lang,@Header("Authorization")String auth,@FieldMap HashMap<String, String> map);

    @POST("mycourselist")
    Call<MyCourseList> myCourseList(@Header("language") String lang, @Header("Authorization")String auth);

    @FormUrlEncoded
    @POST("startcourse")
    Call<SectionCourse> startCourse(@Header("language") String lang, @Header("Authorization")String auth, @FieldMap HashMap<String, String> map);

}