package com.onlineeducationsyestem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;

public class UserProfileFragment extends BaseFragment implements OnItemClick {
    private RecyclerView rvBasicSetting,rvAboutUs;
    private UserProfileAdapter userProfileAdapter;
    private UserProfileAboutUsAdapter userProfileAboutUsAdapter;
    private LinearLayout llWithLogin;
    private TextView tvSignIn,tvSignOut;
    private View view;

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
        list.add(getString(R.string.notification));

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
        //llWithLogin.setVisibility(View.GONE);

        tvSignIn =view.findViewById(R.id.tvSignIn);
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
                Intent intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onGridClick(int pos) {

        if(pos == 1)
        {
            Intent intent =new Intent(activity, EditUserProfileActivity.class);
            startActivity(intent);

        }
    }


}
