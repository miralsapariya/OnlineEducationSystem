package com.onlineeducationsyestem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.onlineeducationsyestem.adapter.SearchResultAdapter;
import com.onlineeducationsyestem.interfaces.AddItemInCart;
import com.onlineeducationsyestem.interfaces.NetworkListener;
import com.onlineeducationsyestem.interfaces.OnItemClick;
import com.onlineeducationsyestem.model.BaseBean;
import com.onlineeducationsyestem.model.GlobalSearch;
import com.onlineeducationsyestem.network.ApiCall;
import com.onlineeducationsyestem.network.ApiInterface;
import com.onlineeducationsyestem.network.RestApi;
import com.onlineeducationsyestem.network.ServerConstents;
import com.onlineeducationsyestem.util.AppConstant;
import com.onlineeducationsyestem.util.AppSharedPreference;
import com.onlineeducationsyestem.util.AppUtils;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;

public class SearchResultActivity extends AppCompatActivity
        implements NetworkListener, OnItemClick , AddItemInCart, SwipeRefreshLayout.OnRefreshListener{

    String searchKeyword="",cat_id="";
    private ImageView imgBack;
    private RecyclerView rvSearch;
    private TextView tvNoData;
    private SearchResultAdapter searchResultAdapter;
    private ArrayList<GlobalSearch.Courseslist> list;
    private SwipeRefreshLayout swipeRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();
    }

    private void initUI()
    {
        searchKeyword= getIntent().getStringExtra("searchKeyword");
        cat_id =getIntent().getStringExtra("cat_id");

        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rvSearch=findViewById(R.id.rvSearch);
        tvNoData =findViewById(R.id.tvNoData);


        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        rvSearch.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(SearchResultActivity.this);
        rvSearch.setLayoutManager(mLayoutManager);
        searchResultAdapter = new SearchResultAdapter(SearchResultActivity.this
                , new ArrayList<GlobalSearch.Courseslist> () , this,this);
        rvSearch.setAdapter(searchResultAdapter);

        if (AppUtils.isInternetAvailable(SearchResultActivity.this)) {
            hintGetSearchResult();
        }

    }

    private void hintGetSearchResult()
    {
        String lang="";
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);

        if (AppSharedPreference.getInstance().getString(SearchResultActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(SearchResultActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else {
            lang= AppConstant.ARABIC_LANG;
        }
        final HashMap params = new HashMap<>();
        if(searchKeyword == null ||  searchKeyword.equals(""))
        {

        }else {
            params.put("search_keyword", searchKeyword);
        }

        if(cat_id == null || cat_id.equals(""))
        {

        }else
        {
            params.put("cat_id", cat_id);
        }
      //  page_no
      //  page_limit
        Call<GlobalSearch> call = apiInterface.getDefaultCategory(lang,params);
        ApiCall.getInstance().hitService(SearchResultActivity.this, call, this, ServerConstents.DEFUALT_CAT);

    }

    @Override
    public void onRefresh() {

    }



    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if(requestCode == ServerConstents.CART){

            BaseBean baseBean =(BaseBean) response;
            Toast.makeText(SearchResultActivity.this, baseBean.getMessage().toString(),Toast.LENGTH_SHORT).show();

        }else {


            GlobalSearch data = (GlobalSearch) response;
            list = new ArrayList<>();
            list.addAll(data.getData().get(0).getCourseslist());

            if (data.getData().get(0).getCourseslist().size() > 0) {
                rvSearch.setVisibility(View.VISIBLE);
                tvNoData.setVisibility(View.GONE);

                searchResultAdapter = new SearchResultAdapter
                        (SearchResultActivity.this, data.getData().get(0).getCourseslist(), this, this);
                rvSearch.setItemAnimator(new DefaultItemAnimator());
                LinearLayoutManager manager = new LinearLayoutManager(SearchResultActivity.this);
                rvSearch.setLayoutManager(manager);
                rvSearch.setAdapter(searchResultAdapter);
            } else {
                rvSearch.setVisibility(View.GONE);
                tvNoData.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onGridClick(int pos) {

        Intent intent =new Intent(SearchResultActivity.this,CourseDetailActivity.class);
        intent.putExtra("course_id", list.get(pos).getId()+"");
        startActivity(intent);
    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {

        if(requestCode == ServerConstents.CART){
            Toast.makeText(SearchResultActivity.this, response, Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(SearchResultActivity.this, response, Toast.LENGTH_SHORT).show();
            rvSearch.setVisibility(View.GONE);
            tvNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFailure() {
        Log.d("in failure ", "========== ");

    }
    private void hintAddToCart(int pos)
    {
        String lang="";
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();

        params.put("course_id",list.get(pos).getId()+"");
        if (AppSharedPreference.getInstance().getString(SearchResultActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(SearchResultActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }

        Call<BaseBean> call = apiInterface.addToCart(lang,AppSharedPreference.getInstance().
                getString(SearchResultActivity.this, AppSharedPreference.ACCESS_TOKEN),params);
        ApiCall.getInstance().hitService(SearchResultActivity.this, call, this, ServerConstents.CART);

    }
    @Override
    public void addToCart(int pos) {

        if (AppUtils.isInternetAvailable(SearchResultActivity.this)) {
            if (AppSharedPreference.getInstance().getString(SearchResultActivity.this, AppSharedPreference.USERID) == null) {
                AppUtils.loginAlert(SearchResultActivity.this);
            }else {
                hintAddToCart(pos);
            }
        }

    }
}