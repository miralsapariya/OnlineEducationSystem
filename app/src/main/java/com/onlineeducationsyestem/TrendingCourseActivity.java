package com.onlineeducationsyestem;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.adapter.TrendingCourseAdapter;
import com.onlineeducationsyestem.interfaces.OnItemClick;

import java.util.ArrayList;

public class TrendingCourseActivity extends AppCompatActivity implements OnItemClick {


    private TrendingCourseAdapter trendingCourseAdapter;
    private RecyclerView rvTrendingCourse;


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
    }

    @Override
    public void onGridClick(int pos) {

        Intent intent =new Intent(TrendingCourseActivity.this,CourseDetailActivity.class);
        startActivity(intent);

    }

    private void initUI()
    {
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
}
