package com.onlineeducationsyestem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.adapter.TrendingCourseAdapter;
import com.onlineeducationsyestem.interfaces.NetworkListener;
import com.onlineeducationsyestem.interfaces.OnItemClick;
import com.onlineeducationsyestem.model.CourseList;
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

public class TrendingCourseActivity extends BaseActivity implements OnItemClick , NetworkListener {


    private TrendingCourseAdapter trendingCourseAdapter;
    private RecyclerView rvTrendingCourse;
    private String title="",from="";
    private ImageView imgBack;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending_course);

        initToolBar();
        initUI();

    }

    private void initToolBar()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvTitle =findViewById(R.id.tvTitle);
    }

    @Override
    public void onGridClick(int pos) {

        Intent intent =new Intent(TrendingCourseActivity.this,CourseDetailActivity.class);
        startActivity(intent);

    }

    private void initUI()
    {
        title = getIntent().getExtras().getString("title");
        from =getIntent().getExtras().getString("from");

        tvTitle.setText(title);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (AppUtils.isInternetAvailable(TrendingCourseActivity.this)) {
                hintCourseList();
        }

        ArrayList<String> list=new ArrayList<>();
        list.add("ghjhjhjhj");
        list.add("kjkj");
        list.add("ghjhjhjhj");
        list.add("kjkj");

        rvTrendingCourse =findViewById(R.id.rvTrendingCourse);
        trendingCourseAdapter= new TrendingCourseAdapter(TrendingCourseActivity.this, list,this);
        rvTrendingCourse.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager manager = new LinearLayoutManager(TrendingCourseActivity.this);
        rvTrendingCourse.setLayoutManager(manager);
        rvTrendingCourse.setAdapter(trendingCourseAdapter);
    }

    private void hintCourseList()
    {
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();

        if (AppSharedPreference.getInstance().getString(this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(TrendingCourseActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            params.put("language", AppConstant.ENG_LANG);
        }else
        {
            params.put("language", AppConstant.ARABIC_LANG);
        }

        if(from.equals("new_courses"))
        {
            params.put("is_trending", "0");
        }else if(from.equals("trending_courses"))
        {
            params.put("is_trending", "1");
        }else
        {
            params.put("sub_category_id", "");
        }

        Call<CourseList> call = apiInterface.getCourseList(params);

        ApiCall.getInstance().hitService(TrendingCourseActivity.this, call, this, ServerConstents.COURSE_LIST);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        CourseList data=(CourseList) response;


    }

    @Override
    public void onError(String response, int requestCode) {

    }

    @Override
    public void onFailure() {

    }
}
