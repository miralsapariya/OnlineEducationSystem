package com.onlineeducationsyestem;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.adapter.SubCategoryAdapter;
import com.onlineeducationsyestem.interfaces.NetworkListener;
import com.onlineeducationsyestem.interfaces.OnItemClick;
import com.onlineeducationsyestem.model.SubCategory;
import com.onlineeducationsyestem.network.ApiCall;
import com.onlineeducationsyestem.network.ApiInterface;
import com.onlineeducationsyestem.network.RestApi;
import com.onlineeducationsyestem.network.ServerConstents;
import com.onlineeducationsyestem.util.AppConstant;
import com.onlineeducationsyestem.util.AppSharedPreference;
import com.onlineeducationsyestem.util.AppUtils;

import java.util.HashMap;

import retrofit2.Call;

public class SubCategoryActivity extends BaseActivity implements OnItemClick, NetworkListener {

    private RecyclerView recyclerViewSubCat;
    private SubCategoryAdapter subCategoryAdapter;
    private ImageView imgBack;
    private  String catId,cat_name;
    private TextView tvTitle,tvNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        initToolbar();
        initUI();
    }

    private void initUI()
    {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            catId = extras.getString("cat_id");
            cat_name  =extras.getString("cat_name");
            // and get whatever type user account id is
        }

        recyclerViewSubCat =findViewById(R.id.recyclerViewSubCat);
        tvTitle =findViewById(R.id.tvTitle);
        tvNoData =findViewById(R.id.tvNoData);
        tvTitle.setText(cat_name+"");

        imgBack =findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (AppUtils.isInternetAvailable(SubCategoryActivity.this)) {
                hintSubCat();
        }else {
            AppUtils.showAlertDialog(SubCategoryActivity.this,getString(R.string.no_internet),getString(R.string.alter_net));
        }
    }

    private void hintSubCat()
    {
        String lang="";
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        if (AppSharedPreference.getInstance().getString(SubCategoryActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(SubCategoryActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }
        params.put("category_id", catId);
        Call<SubCategory> call = apiInterface.getSubCat(lang,params);
        ApiCall.getInstance().hitService(SubCategoryActivity.this, call, this, ServerConstents.LOGIN);
    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        SubCategory data=(SubCategory) response;
//        Log.d("size:: ", data.getData().get(0).getCategories().get(0).getSubCategories().size()+"");

        if(data.getData().get(0).getCategories().size() ==0)
        {
            recyclerViewSubCat.setVisibility(View.GONE);
            tvNoData.setVisibility(View.VISIBLE);
            return;
        }
        if(data.getData().get(0).getCategories().get(0).getSubCategories().size() > 0) {

            recyclerViewSubCat.setVisibility(View.VISIBLE);
            tvNoData.setVisibility(View.GONE);
            subCategoryAdapter =
                    new SubCategoryAdapter(SubCategoryActivity.this, data.getData().get(0).getCategories().get(0).getSubCategories(), this);

            recyclerViewSubCat.setItemAnimator(new DefaultItemAnimator());
            LinearLayoutManager manager = new LinearLayoutManager(SubCategoryActivity.this);
            recyclerViewSubCat.setLayoutManager(manager);
            recyclerViewSubCat.setAdapter(subCategoryAdapter);
        }else
        {
            recyclerViewSubCat.setVisibility(View.GONE);
            tvNoData.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {
        Toast.makeText(SubCategoryActivity.this, response, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onGridClick(int pos) {

    }

    private void initToolbar()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
