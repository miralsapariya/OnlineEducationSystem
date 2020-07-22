package com.onlineeducationsyestem;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.adapter.HomeSearchAdapter;
import com.onlineeducationsyestem.interfaces.OnItemClick;

import java.util.ArrayList;

public class HomeSearchActivity extends BaseActivity implements OnItemClick {

    private RecyclerView recyclerView;
    private EditText etSearch;
    private HomeSearchAdapter homeSearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initToolbar();
        initUI();
    }
    private void initUI()
    {
        recyclerView =findViewById(R.id.recyclerView);

        ArrayList<String> list =new ArrayList<>();
        list.add("djkjf");
        list.add("hfjd");
        list.add("dkjfkjd");
        list.add("dkjd");

        homeSearchAdapter =
                new HomeSearchAdapter(HomeSearchActivity.this, list,this);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager manager = new LinearLayoutManager(HomeSearchActivity.this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(homeSearchAdapter);
    }

    @Override
    public void onGridClick(int pos) {

    }

    @SuppressLint("ClickableViewAccessibility")
    private void initToolbar()
    {
        etSearch =findViewById(R.id.etSearch);

        etSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (etSearch.getRight() - etSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        Toast.makeText(HomeSearchActivity.this, "close ", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return false;
            }
        });
    }

}
