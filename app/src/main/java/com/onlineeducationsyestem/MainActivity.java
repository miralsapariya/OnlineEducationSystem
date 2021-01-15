package com.onlineeducationsyestem;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.onlineeducationsyestem.fragment.CartFragment;
import com.onlineeducationsyestem.fragment.CategoryFragment;
import com.onlineeducationsyestem.fragment.HomeFragment;
import com.onlineeducationsyestem.fragment.MyCoursesFragment;
import com.onlineeducationsyestem.fragment.UserProfileFragment;
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
        toolbar_title = findViewById(R.id.toolbar_title);
        initBottomNavigationBar();

        Bundle b=getIntent().getExtras();
        if( b!= null && b.containsKey("from")){
            gotoMyCourses();
        }else {
            loadFragment(new HomeFragment());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(AppConstant.fromCourseDetail)
        {
            AppConstant.fromCourseDetail=false;
            gotoCart();
        }
    }

    private void setLanguage() {
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
                // "MA" For Morocco to use 0123... number not in arbic
                Locale locale = new Locale(languageToLoad,"MA");
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

    }


    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
        }
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("===== onactivity result ::: ", "==============");
       *//* Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        fragment.onActivityResult(requestCode, resultCode, data);*//*
    }*/

    private void initBottomNavigationBar() {
        nav_view = findViewById(R.id.nav_view);
        imgSearch = findViewById(R.id.imgSearch);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HomeSearchActivity.class);
                ;
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
                    case R.id.page_3:

                        imgSearch.setVisibility(View.GONE);
                        toolbar_title.setText(getString(R.string.my_courses));
                        if (AppSharedPreference.getInstance().getString(MainActivity.this, AppSharedPreference.USERID) == null) {
                           // AppUtils.loginAlert(MainActivity.this);
                            //set eng lang
                            String languageToLoad = "en"; // your language
                            Locale locale = new Locale(languageToLoad);
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());


                            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            loadFragment(new MyCoursesFragment());
                        }

                        break;
                    case R.id.page_4:
                        gotoCart();
                        break;
                    case R.id.page_5:
                        imgSearch.setVisibility(View.GONE);
                        toolbar_title.setText(getString(R.string.account));
                        loadFragment(new UserProfileFragment());
                        break;

                }
                return true;
            }
        });
    }

    private void gotoCart()
    {
        nav_view.getMenu().findItem(R.id.page_4).setChecked(true);
        imgSearch.setVisibility(View.GONE);
        toolbar_title.setText(getString(R.string.cart));
        if (AppSharedPreference.getInstance().getString(MainActivity.this, AppSharedPreference.USERID) == null) {
            // AppUtils.loginAlert(MainActivity.this);

            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            loadFragment(new CartFragment());
        }
    }
    public void gotoCategory() {

        nav_view.getMenu().findItem(R.id.page_2).setChecked(true);

        imgSearch.setVisibility(View.GONE);
        toolbar_title.setText(getString(R.string.category));
        loadFragment(new CategoryFragment());
    }

    public void gotoMyCourses()
    {
        nav_view.getMenu().findItem(R.id.page_3).setChecked(true);
        toolbar_title.setText(getString(R.string.my_courses));
        imgSearch.setVisibility(View.GONE);
        loadFragment(new MyCoursesFragment());
    }

}
