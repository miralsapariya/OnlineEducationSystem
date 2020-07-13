package com.onlineeducationsyestem;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.adapter.WhishListAdapter;
import com.onlineeducationsyestem.interfaces.OnItemClick;

import java.util.ArrayList;

public class WhishListActivity extends AppCompatActivity implements OnItemClick {

    private RecyclerView rvWhishList;
    private WhishListAdapter whishListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whish_list);

        initToolBar();
        initUI();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        rvWhishList =findViewById(R.id.rvWhishList);
        whishListAdapter= new WhishListAdapter(WhishListActivity.this, list,this);
        rvWhishList.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager manager = new LinearLayoutManager(WhishListActivity.this);
        rvWhishList.setLayoutManager(manager);
        rvWhishList.setAdapter(whishListAdapter);
    }

    @Override
    public void onGridClick(int pos)
    {


    }
}
