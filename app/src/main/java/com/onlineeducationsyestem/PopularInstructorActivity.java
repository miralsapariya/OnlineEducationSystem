package com.onlineeducationsyestem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.adapter.PopularInstructorAdapter;
import com.onlineeducationsyestem.interfaces.NetworkListener;
import com.onlineeducationsyestem.interfaces.OnItemClick;
import com.onlineeducationsyestem.model.InstructorList;
import com.onlineeducationsyestem.network.ApiCall;
import com.onlineeducationsyestem.network.ApiInterface;
import com.onlineeducationsyestem.network.RestApi;
import com.onlineeducationsyestem.network.ServerConstents;
import com.onlineeducationsyestem.util.AppConstant;
import com.onlineeducationsyestem.util.AppSharedPreference;
import com.onlineeducationsyestem.util.AppUtils;

import java.util.HashMap;

import retrofit2.Call;

public class PopularInstructorActivity extends BaseActivity implements OnItemClick, NetworkListener {

    private RecyclerView rvInstructor;
    private PopularInstructorAdapter popularInstructorAdapter;
    private  InstructorList  data;
    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_instructor);

        initToolBar();
        initUI();
    }
    private void initToolBar()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imgBack =findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void initUI()
    {
        rvInstructor =findViewById(R.id.rvInstructor);
        if (AppUtils.isInternetAvailable(PopularInstructorActivity.this)) {
            getList();
        }else {
            AppUtils.showAlertDialog(PopularInstructorActivity.this,getString(R.string.no_internet),getString(R.string.alter_net));
        }
    }

    private void getList()
    {
        String lang = "";
        AppUtils.showDialog(PopularInstructorActivity.this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();

        if (AppSharedPreference.getInstance().getString(PopularInstructorActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(PopularInstructorActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        } else {
            lang = AppConstant.ARABIC_LANG;
        }
        Call<InstructorList> call = apiInterface.getInstructorList(lang, AppSharedPreference.getInstance().
                getString(PopularInstructorActivity.this, AppSharedPreference.ACCESS_TOKEN));
        ApiCall.getInstance().hitService(PopularInstructorActivity.this,
                call, this, ServerConstents.WHISH_LIST);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if (requestCode == ServerConstents.WHISH_LIST) {
            data= (InstructorList) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                popularInstructorAdapter = new PopularInstructorAdapter(PopularInstructorActivity.this, data.getData().get(0).getPopularInstructorList(), this);
                rvInstructor.setItemAnimator(new DefaultItemAnimator());
                LinearLayoutManager manager = new LinearLayoutManager(PopularInstructorActivity.this);
                rvInstructor.setLayoutManager(manager);
                rvInstructor.setAdapter(popularInstructorAdapter);
            }
        }
    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {

    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onGridClick(int pos) {

        Intent intent =new Intent(PopularInstructorActivity.this,InstructorProfileActivity.class);
        intent.putExtra("instructor_id", data.getData().get(0).getPopularInstructorList().get(pos).getId()+"");
        startActivity(intent);

    }
}
