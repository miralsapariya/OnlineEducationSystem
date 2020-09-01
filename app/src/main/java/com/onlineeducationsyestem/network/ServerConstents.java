package com.onlineeducationsyestem.network;

/**
 * Created by appinventiv on 27/3/18.
 */

public class ServerConstents {

    /***********************LIVE URL**********************************/
    public static final String API_URL = "http://1.22.161.26:9875/online_education_system/public/api/";
    //public static final String IMG_URL="http://tcodextechnolab.com/mlmerp/";

    public static String DEVICE_TYPE="2";

    //
    public static int CODE_SUCCESS=200;

    public static String HEADER_ACCEPT="application/json";
    //service code

    public static int LOGIN=1;
    public static int RESEND=2;
    public static int SEND_OTP=3;
    public static int OTP=4;
    public static int RESET=5;
    public static int CHANGE_PWD=6;
    public static int GET_PROFILE=7;
    public static int EDIT_PROFILE=8;
    public static int CATEGORY=9;
    public static int HOME =10;
    public static int COURSE_LIST=11;
    public static int WHISH_LIST=12;
    public static int DETAIL=13;
    public static int DEFUALT_CAT=14;
    public static int CART=15;
    public static int DELETE=16;
    public static int PROMO=17;
    public static int CHECKOUT=18;
    //home screen type
    public static String HOME_TYPE_COURSE="1";
    public static String HOME_TYPE_CATEGORY="2";
    public static String HOME_TYPE_POPULAR_INSTRUCTOR="3";


}
