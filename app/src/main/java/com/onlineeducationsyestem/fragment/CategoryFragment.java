package com.onlineeducationsyestem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.R;
import com.onlineeducationsyestem.SubCategoryActivity;
import com.onlineeducationsyestem.adapter.CategoryAdapter;
import com.onlineeducationsyestem.adapter.GridTopCategoryAdapter;
import com.onlineeducationsyestem.interfaces.OnItemClick;
import com.onlineeducationsyestem.interfaces.OnSubItemClick;

import java.util.ArrayList;


public class CategoryFragment extends BaseFragment implements OnItemClick {

    private View view;
    private RecyclerView recyclerView,rvSubCat;
    private ArrayList<String> list;

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
        startActivity(intent);
    }

    private void init()
    {
        list =new ArrayList<>();
        list.add("jkjh");
        list.add("sfsf");
        list.add("fdfd");
        list.add("jkjh");
        list.add("sfsf");
        list.add("fdfd");

        recyclerView =view.findViewById(R.id.recyclerView);
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(activity, numberOfColumns));
        GridTopCategoryAdapter adapter = new GridTopCategoryAdapter(activity, list, this);
        recyclerView.setAdapter(adapter);

        rvSubCat =view.findViewById(R.id.rvSubCat);

        CategoryAdapter homeAdapter =
                new CategoryAdapter(activity, list, new OnSubItemClick() {
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
