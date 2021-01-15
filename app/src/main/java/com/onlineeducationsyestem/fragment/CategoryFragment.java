package com.onlineeducationsyestem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.R;
import com.onlineeducationsyestem.SubCategoryActivity;
import com.onlineeducationsyestem.adapter.CategoryAdapter;
import com.onlineeducationsyestem.adapter.GridTopCategoryAdapter;
import com.onlineeducationsyestem.interfaces.NetworkListener;
import com.onlineeducationsyestem.interfaces.OnItemClick;
import com.onlineeducationsyestem.interfaces.OnSubItemClick;
import com.onlineeducationsyestem.model.Category;
import com.onlineeducationsyestem.model.User;
import com.onlineeducationsyestem.network.ApiCall;
import com.onlineeducationsyestem.network.ApiInterface;
import com.onlineeducationsyestem.network.RestApi;
import com.onlineeducationsyestem.network.ServerConstents;
import com.onlineeducationsyestem.util.AppConstant;
import com.onlineeducationsyestem.util.AppSharedPreference;
import com.onlineeducationsyestem.util.AppUtils;

import java.util.HashMap;

import retrofit2.Call;


public class CategoryFragment extends BaseFragment implements OnItemClick, NetworkListener {

    private View view;
    private RecyclerView recyclerView,rvSubCat;
    private TextView tvNameCategory;
    Category data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_category, container, false);

        init();

        return  view;
    }

    @Override
    public void onGridClick(int pos) {

        Intent intent = new Intent(activity, SubCategoryActivity.class);
        intent.putExtra("cat_id", data.getData().get(0).getAllCategoriesList().get(pos).getId()+"");
        intent.putExtra("cat_name", data.getData().get(0).getAllCategoriesList().get(pos).getCategoryName());
        startActivity(intent);
    }

    private void init()
    {

        if (AppUtils.isInternetAvailable(activity)) {
            getCategory();
        }else {
            AppUtils.showAlertDialog(activity,activity.getString(R.string.no_internet),activity.getString(R.string.alter_net));
        }


        recyclerView =view.findViewById(R.id.recyclerView);
        rvSubCat =view.findViewById(R.id.rvSubCat);
        tvNameCategory =view.findViewById(R.id.tvNameCategory);

    }

    private void getCategory()
    {
        String lang="";
        AppUtils.showDialog(activity, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        if (AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<User> call = apiInterface.categoryList(lang,params);
        ApiCall.getInstance().hitService(activity, call, this, ServerConstents.CATEGORY);
    }



    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if(requestCode == ServerConstents.CATEGORY) {
            data= (Category) response;
            tvNameCategory.setText(data.getData().get(0).getCategoryLabel());
            //top cat
            int numberOfColumns = 3;
            recyclerView.setLayoutManager(new GridLayoutManager(activity, numberOfColumns));
            GridTopCategoryAdapter adapter = new GridTopCategoryAdapter(activity, data.getData().get(0).getTopCategoriesList(), this);
            recyclerView.setAdapter(adapter);

            //lower cat
            CategoryAdapter homeAdapter =
                    new CategoryAdapter(activity, data.getData().get(0).getAllCategoriesList(), new OnSubItemClick() {
                        @Override
                        public void onSubGridClick(int pos) {

                        }
                    });

            RecyclerView.LayoutManager mLayoutManager =
                    new LinearLayoutManager(activity);
            rvSubCat.setLayoutManager(mLayoutManager);
            rvSubCat.setItemAnimator(new DefaultItemAnimator());
            rvSubCat.setHasFixedSize(true);
            rvSubCat.setAdapter(homeAdapter);
        }

    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {
        Toast.makeText(activity, response, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFailure() {

    }
}
