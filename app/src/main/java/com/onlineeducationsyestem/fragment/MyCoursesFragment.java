package com.onlineeducationsyestem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.R;
import com.onlineeducationsyestem.UserCourseDetailActivity;
import com.onlineeducationsyestem.adapter.MyCoursesAdapter;
import com.onlineeducationsyestem.interfaces.OnItemClick;

import java.util.ArrayList;


public class MyCoursesFragment extends BaseFragment implements OnItemClick {

        private View view;
        private RecyclerView rvMyCourses;
        private MyCoursesAdapter myCoursesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =inflater.inflate(R.layout.fragment_my_courses, container, false);

        initUI();
        return view;
    }

    private void initUI()
    {

        ArrayList<String> list =new ArrayList<>();
        list.add("vfdf");
        list.add("dfdf");

        rvMyCourses =view.findViewById(R.id.rvMyCourses);
        myCoursesAdapter =new MyCoursesAdapter(activity,list,this);
        rvMyCourses.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        rvMyCourses.setLayoutManager(manager);
        rvMyCourses.setAdapter(myCoursesAdapter);
    }

    @Override
    public void onGridClick(int pos) {

        Intent intent =new Intent(activity, UserCourseDetailActivity.class);
        startActivity(intent);

    }
}
