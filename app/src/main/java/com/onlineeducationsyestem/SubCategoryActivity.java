package com.onlineeducationsyestem;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.adapter.SubCategoryAdapter;
import com.onlineeducationsyestem.interfaces.OnItemClick;

import java.util.ArrayList;

public class SubCategoryActivity extends AppCompatActivity implements OnItemClick {

    private RecyclerView recyclerViewSubCat;
    private SubCategoryAdapter subCategoryAdapter;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        initToolbar();
        initUI();

    }

    private void initUI()
    {

        ArrayList<String> list =new ArrayList<>();
        list.add("sdsd");
        list.add("sdsd");
        list.add("sdsd");

        recyclerViewSubCat =findViewById(R.id.recyclerViewSubCat);
        subCategoryAdapter =
                new SubCategoryAdapter(SubCategoryActivity.this, list,this);

        recyclerViewSubCat.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager manager = new LinearLayoutManager(SubCategoryActivity.this);
        recyclerViewSubCat.setLayoutManager(manager);
        recyclerViewSubCat.setAdapter(subCategoryAdapter);

        imgBack =findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onGridClick(int pos) {

    }

    private void initToolbar()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
