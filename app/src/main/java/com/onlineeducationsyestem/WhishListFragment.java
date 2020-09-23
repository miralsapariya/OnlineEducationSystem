package com.onlineeducationsyestem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.adapter.WhishListAdapter;
import com.onlineeducationsyestem.interfaces.DeleteWhishList;
import com.onlineeducationsyestem.interfaces.NetworkListener;
import com.onlineeducationsyestem.interfaces.OnItemClick;
import com.onlineeducationsyestem.model.BaseBean;
import com.onlineeducationsyestem.model.MyWhishList;
import com.onlineeducationsyestem.network.ApiCall;
import com.onlineeducationsyestem.network.ApiInterface;
import com.onlineeducationsyestem.network.RestApi;
import com.onlineeducationsyestem.network.ServerConstents;
import com.onlineeducationsyestem.util.AppConstant;
import com.onlineeducationsyestem.util.AppSharedPreference;
import com.onlineeducationsyestem.util.AppUtils;

import java.util.HashMap;

import retrofit2.Call;


public class WhishListFragment extends BaseActivity implements OnItemClick, DeleteWhishList, NetworkListener {

    private RecyclerView recyclerView;
    private WhishListAdapter whishListAdapter;
    private ImageView imgBack;
    MyWhishList data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whish_list);
        init();
    }

    @Override
    public void onGridClick(int pos) {
        Intent intent = new Intent(WhishListFragment.this, SubCategoryActivity.class);
        startActivity(intent);
    }

    @Override
    public void deleteWishList(int pos) {
        String lang = "";
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("course_id", data.getData().get(0).getCourseslist().get(pos).getId());

        if (AppSharedPreference.getInstance().getString(WhishListFragment.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(WhishListFragment.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        } else {
            lang = AppConstant.ARABIC_LANG;
        }
        Call<BaseBean> call = apiInterface.addWhishList(lang,
                AppSharedPreference.getInstance().
                        getString(WhishListFragment.this, AppSharedPreference.ACCESS_TOKEN),

                params);

        ApiCall.getInstance().hitService(WhishListFragment.this, call, this, ServerConstents.DEFUALT_CAT);


    }

    private void init() {

        recyclerView = findViewById(R.id.recyclerView);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (AppUtils.isInternetAvailable(WhishListFragment.this)) {
            getWhishList();
        }

    }

    private void getWhishList() {
        String lang = "";
        AppUtils.showDialog(WhishListFragment.this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        if (AppSharedPreference.getInstance().getString(WhishListFragment.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(WhishListFragment.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        } else {
            lang = AppConstant.ARABIC_LANG;
        }
        Call<MyWhishList> call = apiInterface.getWhishList(lang, AppSharedPreference.getInstance().
                getString(WhishListFragment.this, AppSharedPreference.ACCESS_TOKEN));
        ApiCall.getInstance().hitService(WhishListFragment.this,
                call, this, ServerConstents.WHISH_LIST);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {

        if (requestCode == ServerConstents.WHISH_LIST) {
            data = (MyWhishList) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                whishListAdapter = new WhishListAdapter(WhishListFragment.this, data.getData().get(0).getCourseslist(), this, this);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                LinearLayoutManager manager = new LinearLayoutManager(WhishListFragment.this);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(whishListAdapter);
            }
        }else if (requestCode == ServerConstents.DEFUALT_CAT) {
                BaseBean data = (BaseBean) response;
                if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                    getWhishList();
                    Toast.makeText(WhishListFragment.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                }
        }

    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {
        if (requestCode == ServerConstents.DEFUALT_CAT) {
            if (AppUtils.isInternetAvailable(WhishListFragment.this)) {
                getWhishList();
            }
        }
        Toast.makeText(WhishListFragment.this, response, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFailure() {

    }
}
