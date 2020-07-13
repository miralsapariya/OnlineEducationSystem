package com.onlineeducationsyestem;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.adapter.PopularInstructorAdapter;
import com.onlineeducationsyestem.interfaces.OnItemClick;

import java.util.ArrayList;

public class PopularInstructorActivity extends AppCompatActivity implements OnItemClick {

    private RecyclerView rvInstructor;
    private PopularInstructorAdapter popularInstructorAdapter;
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
    }


    private void initUI()
    {
        ArrayList<String> list=new ArrayList<>();
        list.add("ghjhjhjhj");
        list.add("kjkj");
        list.add("ghjhjhjhj");
        list.add("kjkj");

        rvInstructor =findViewById(R.id.rvInstructor);
        popularInstructorAdapter= new PopularInstructorAdapter(PopularInstructorActivity.this, list,this);
        rvInstructor.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager manager = new LinearLayoutManager(PopularInstructorActivity.this);
        rvInstructor.setLayoutManager(manager);
        rvInstructor.setAdapter(popularInstructorAdapter);
    }

    @Override
    public void onGridClick(int pos) {

        Intent intent =new Intent(PopularInstructorActivity.this,InstructorProfileActivity.class);
        startActivity(intent);

    }
}
