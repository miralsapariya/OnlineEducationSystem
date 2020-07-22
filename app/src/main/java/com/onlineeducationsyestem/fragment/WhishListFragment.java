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
import com.onlineeducationsyestem.SubCategoryActivity;
import com.onlineeducationsyestem.adapter.WhishListAdapter;
import com.onlineeducationsyestem.interfaces.OnItemClick;

import java.util.ArrayList;


public class WhishListFragment extends BaseFragment implements OnItemClick {

    private View view;
    private RecyclerView recyclerView;
    private ArrayList<String> list;
    private WhishListAdapter whishListAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_whish_list, container, false);

        init();

        return  view;
    }

    @Override
    public void onGridClick(int pos) {

        Intent intent = new Intent(activity, SubCategoryActivity.class);
        startActivity(intent);

    }


    private void init()
    {

        recyclerView =view.findViewById(R.id.recyclerView);
        ArrayList<String> list=new ArrayList<>();
        list.add("ghjhjhjhj");
        list.add("kjkj");
        list.add("ghjhjhjhj");
        list.add("kjkj");

        whishListAdapter= new WhishListAdapter(activity, list,this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(whishListAdapter);


    }


}
