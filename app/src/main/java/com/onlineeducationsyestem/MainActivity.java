package com.onlineeducationsyestem;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.onlineeducationsyestem.fragment.CategoryFragment;
import com.onlineeducationsyestem.fragment.HomeFragment;
import com.onlineeducationsyestem.fragment.MyCoursesFragment;
import com.onlineeducationsyestem.fragment.UserProfileFragment;
import com.onlineeducationsyestem.fragment.WhishListFragment;
import com.onlineeducationsyestem.util.AppConstant;
import com.onlineeducationsyestem.util.AppSharedPreference;

import java.util.Locale;

public class MainActivity extends BaseActivity {

    public BottomNavigationView nav_view;
    private TextView toolbar_title;
    private ImageView imgSearch;
    private Configuration config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLanguage();
        setContentView(R.layout.activity_main);

        toolbar_title =findViewById(R.id.toolbar_title);


        initBottomNavigationBar();
        loadFragment(new HomeFragment());

    }

    private void setLanguage()
    {
        if (AppSharedPreference.getInstance().getString(this, AppSharedPreference.LANGUAGE_SELECTED) != null) {

            if (AppSharedPreference.getInstance().getString(this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
                String languageToLoad = "en"; // your language
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());


            } else {

                String languageToLoad = "ar"; // your language
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

            }
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Log.d("in  main activty :: ", "configration chage :: ");
        // your code here, you can use newConfig.locale if you need to check the language
        // or just re-set all the labels to desired string resource
    }


    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
        }
    }

    private void initBottomNavigationBar()
    {
        nav_view = findViewById(R.id.nav_view);
        imgSearch= findViewById(R.id.imgSearch);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new Intent(MainActivity.this, HomeSearchActivity.class);;
                startActivity(intent);

            }
        });

        nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_1:
                        imgSearch.setVisibility(View.VISIBLE);
                        toolbar_title.setText(getString(R.string.home));
                        loadFragment(new HomeFragment());
                        break;
                    case R.id.page_2:
                        imgSearch.setVisibility(View.GONE);
                        toolbar_title.setText(getString(R.string.category));
                        loadFragment(new CategoryFragment());
                        break;
                    case R.id.page_3 :

                        imgSearch.setVisibility(View.GONE);
                        toolbar_title.setText(getString(R.string.my_courses));
                        loadFragment(new MyCoursesFragment());
                        break;
                    case R.id.page_4:
                        imgSearch.setVisibility(View.GONE);
                        toolbar_title.setText(getString(R.string.whishlist_));
                        loadFragment(new WhishListFragment());
                        break;
                    case R.id.page_5:
                        imgSearch.setVisibility(View.GONE);
                        toolbar_title.setText(getString(R.string.account_profile));
                        loadFragment(new UserProfileFragment());
                        break;

                }
                return true;
            }
        });
    }


}
