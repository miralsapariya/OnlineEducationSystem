package com.onlineeducationsyestem.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.onlineeducationsyestem.ChangePwdActivity;
import com.onlineeducationsyestem.EditUserProfileActivity;
import com.onlineeducationsyestem.LoginActivity;
import com.onlineeducationsyestem.R;
import com.onlineeducationsyestem.adapter.UserProfileAboutUsAdapter;
import com.onlineeducationsyestem.adapter.UserProfileAdapter;
import com.onlineeducationsyestem.interfaces.NetworkListener;
import com.onlineeducationsyestem.interfaces.OnItemClick;
import com.onlineeducationsyestem.model.GetProfile;
import com.onlineeducationsyestem.network.ApiCall;
import com.onlineeducationsyestem.network.ApiInterface;
import com.onlineeducationsyestem.network.RestApi;
import com.onlineeducationsyestem.network.ServerConstents;
import com.onlineeducationsyestem.util.AppConstant;
import com.onlineeducationsyestem.util.AppSharedPreference;
import com.onlineeducationsyestem.util.AppUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;

public class UserProfileFragment extends BaseFragment implements OnItemClick , NetworkListener {
    private RecyclerView rvBasicSetting,rvAboutUs;
    private UserProfileAdapter userProfileAdapter;
    private UserProfileAboutUsAdapter userProfileAboutUsAdapter;
    private LinearLayout llWithLogin;
    private TextView tvSignIn,tvSignOut,tvDate,tvCountry,tvCourse,tvName;
    private View view;
    private Configuration config;
    private CircularImageView imgUser;

    @Override
    public View onCreateView
            (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.content_user_profile, container, false);
        initUI();
        return view;
    }


    private void initUI(){

        ArrayList<String> list=new ArrayList<>();
        list.add(getString(R.string.prefered_lang));
       // list.add(getString(R.string.dasahboard));
        list.add(getString(R.string.my_profile));
        list.add(getString(R.string.change_pwd_));
       // list.add(getString(R.string.whishlist_));
      //  list.add(getString(R.string.notification));

        tvDate =view.findViewById(R.id.tvDate);
        tvCountry =view.findViewById(R.id.tvCountry);
        tvCourse =view.findViewById(R.id.tvCourse);
        tvName =view.findViewById(R.id.tvName);
        rvBasicSetting =view.findViewById(R.id.rvBasicSetting);
        userProfileAdapter= new UserProfileAdapter(activity, list,this);
        rvBasicSetting.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        rvBasicSetting.setLayoutManager(manager);
        rvBasicSetting.setAdapter(userProfileAdapter);

        ArrayList<String> listAboutUs=new ArrayList<>();
        listAboutUs.add(getString(R.string.about));
        listAboutUs.add(getString(R.string.faq));
        listAboutUs.add(getString(R.string.privacy_policy));
        listAboutUs.add(getString(R.string.terms_n_condition));

        rvAboutUs =view.findViewById(R.id.rvAboutUs);
        userProfileAboutUsAdapter = new UserProfileAboutUsAdapter(activity,listAboutUs , this);
        rvAboutUs.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager manager1 = new LinearLayoutManager(activity);
        rvAboutUs.setLayoutManager(manager1);
        rvAboutUs.setAdapter(userProfileAboutUsAdapter);

        llWithLogin =view.findViewById(R.id.llWithLogin);
        tvSignIn =view.findViewById(R.id.tvSignIn);
        tvSignOut= view.findViewById(R.id.tvSignOut);

        if (AppSharedPreference.getInstance().getString(activity, AppSharedPreference.USERID) == null) {
            llWithLogin.setVisibility(View.GONE);
            tvSignIn.setVisibility(View.VISIBLE);
        }else
        {
            llWithLogin.setVisibility(View.VISIBLE);
            tvSignIn.setVisibility(View.GONE);
        }


        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
            }
        });
        tvSignOut =view.findViewById(R.id.tvSignOut);
        tvSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppSharedPreference.getInstance().clearAllPrefs(activity);
                llWithLogin.setVisibility(View.GONE);
                tvSignIn.setVisibility(View.VISIBLE);
                Intent intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
            }
        });


        imgUser =view.findViewById(R.id.imgUser);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (AppUtils.isInternetAvailable(activity)) {
            getProfile();
        }
    }

    private void getProfile()
    {
        String lang="";
        AppUtils.showDialog(activity, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("user_id", AppSharedPreference.getInstance().getString(activity, AppSharedPreference.USERID));
        if (AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<GetProfile> call = apiInterface.getProfile(lang,
                AppSharedPreference.getInstance().
                        getString(activity, AppSharedPreference.ACCESS_TOKEN),
                params);

        ApiCall.getInstance().hitService(activity, call, this, ServerConstents.GET_PROFILE);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {

        if(requestCode == ServerConstents.GET_PROFILE) {
            GetProfile data = (GetProfile) response;

            AppUtils.loadImageWithPicasso(data.getData().get(0).getProfilePicture() , imgUser, activity, 0, 0);

           // Picasso.with(activity).load(data.getData().get(0).getProfilePicture()).into(imgUser);
            tvDate.setText(data.getData().get(0).getJoinDate());
            tvCountry.setText(data.getData().get(0).getCountryName());
            tvCourse.setText(data.getData().get(0).getCourse()+"");
            tvName.setText(data.getData().get(0).getName());
        }

    }

    @Override
    public void onError(String response, int requestCode) {
        Toast.makeText(activity, response, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onGridClick(int pos) {

        if(pos == 0)
        {
            Log.d("click in pref lang", "=========>");
            showBottomSheet();

        }else if(pos == 1)
        {
            Intent intent =new Intent(activity, EditUserProfileActivity.class);
            startActivity(intent);
        }else if(pos == 2)
        {
            Intent intent =new Intent(activity, ChangePwdActivity.class);
            startActivity(intent);
        }
    }

    private void showBottomSheet()
    {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.bottom_sheet_language);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        TextView tvEnglish =dialog.findViewById(R.id.tvEnglish);
        TextView tvArabic =dialog.findViewById(R.id.tvArabic);
        tvEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("English..................", "Locale");
                String languageToLoad = "en"; // your language
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                activity.getBaseContext().getResources().updateConfiguration(config, activity.getBaseContext().getResources().getDisplayMetrics());

                AppSharedPreference.getInstance().putString(activity, AppSharedPreference.LANGUAGE_SELECTED, AppConstant.ENG_LANG);

               /* Fragment currentFragment = getFragmentManager().findFragmentById(R.id.nav_host_fragment);
                FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
                fragTransaction.detach(currentFragment);
                fragTransaction.attach(currentFragment);
                fragTransaction.commit();
*/
               refereshActivity();
                dialog.dismiss();

            }
        });
        tvArabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Arabic..................", "Locale");
                String languageToLoad = "ar"; // your language
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                activity.getBaseContext().getResources().updateConfiguration(config, activity.getBaseContext().getResources().getDisplayMetrics());

                AppSharedPreference.getInstance().putString(activity, AppSharedPreference.LANGUAGE_SELECTED, AppConstant.ARABIC_LANG);

                Log.d("arabic :: ", AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED));
                /*Fragment currentFragment = getFragmentManager().findFragmentById(R.id.nav_host_fragment);
                FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
                fragTransaction.detach(currentFragment);
                fragTransaction.attach(currentFragment);
                fragTransaction.commit();*/

                refereshActivity();
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    private void refereshActivity()
    {
        new Handler().post(new Runnable() {

            @Override
            public void run()
            {
                Intent intent = getActivity().getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                getActivity().overridePendingTransition(0, 0);
                getActivity().finish();

                getActivity().overridePendingTransition(0, 0);
                startActivity(intent);
            }
        });
    }


}
