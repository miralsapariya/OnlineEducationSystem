package com.onlineeducationsyestem.util;

import com.onlineeducationsyestem.model.User;

/**
 * Created  on 25/10/16.
 */
public class AppConstant {
    public static String APP_IMAGE_FOLDER = android.os.Environment.getExternalStorageDirectory() + "/";

    public static final String DEVICE = "android";
    public static final String ENG_LANG = "en";
    public static final String ARABIC_LANG = "ar";

    public static final String PRIVACY_POLICY="http://1.22.161.26:9875/online_education_system/public/privacy_policy";
    public static final String TERMS_CONDITION="http://1.22.161.26:9875/online_education_system/public/terms_of_use";

    public static String SUB_FOLDER = "OnlineEducationSystem";

    //Permission Constant
    public static final int CAMERA = 1;
    public static User registerData=new User();

    public static String COURSE_STATUS_RESUME="2";
    public static String COURSE_STATUS_START="1";


}
