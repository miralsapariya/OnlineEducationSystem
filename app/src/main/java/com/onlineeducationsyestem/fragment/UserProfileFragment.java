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

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.EditUserProfileActivity;
import com.onlineeducationsyestem.LoginActivity;
import com.onlineeducationsyestem.R;
import com.onlineeducationsyestem.adapter.UserProfileAboutUsAdapter;
import com.onlineeducationsyestem.adapter.UserProfileAdapter;
import com.onlineeducationsyestem.interfaces.OnItemClick;
import com.onlineeducationsyestem.util.AppConstant;
import com.onlineeducationsyestem.util.AppSharedPreference;

import java.util.ArrayList;
import java.util.Locale;

public class UserProfileFragment extends BaseFragment implements OnItemClick {
    private RecyclerView rvBasicSetting,rvAboutUs;
    private UserProfileAdapter userProfileAdapter;
    private UserProfileAboutUsAdapter userProfileAboutUsAdapter;
    private LinearLayout llWithLogin;
    private TextView tvSignIn,tvSignOut;
    private View view;
    private Configuration config;

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
                Intent intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
            }
        });

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
