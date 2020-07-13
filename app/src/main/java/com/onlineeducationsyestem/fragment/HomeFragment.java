package com.onlineeducationsyestem.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.onlineeducationsyestem.R;
import com.onlineeducationsyestem.adapter.HomeAdapter;
import com.onlineeducationsyestem.adapter.ImageAdapter;
import com.onlineeducationsyestem.interfaces.OnItemClick;

import java.util.ArrayList;


public class HomeFragment extends BaseFragment  implements OnItemClick {

    private View view;
    private ImageAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<String> list;
    private HomeAdapter homeAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view =inflater.inflate(R.layout.fragment_home, container, false);

        initViewPager();
        iniUI();

        return  view;
    }

    private void iniUI()
    {
        list =new ArrayList<>();
        list.add("sdfsf");
        list.add("sdfsf");
        list.add("sdfsf");
        list.add("sdfsf");

        recyclerView =view.findViewById(R.id.recyclerView);

        HomeAdapter homeAdapter =
                new HomeAdapter(activity, list,this);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(homeAdapter);

    }

    @Override
    public void onGridClick(int pos) {

    }

    private void initViewPager()
    {
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        adapter = new ImageAdapter(activity);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(adapter.getCount()-1);
    }
}
