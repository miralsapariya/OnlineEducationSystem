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
import com.onlineeducationsyestem.interfaces.NetworkListener;
import com.onlineeducationsyestem.interfaces.OnItemClick;
import com.onlineeducationsyestem.model.Home;
import com.onlineeducationsyestem.network.ApiCall;
import com.onlineeducationsyestem.network.ApiInterface;
import com.onlineeducationsyestem.network.RestApi;
import com.onlineeducationsyestem.network.ServerConstents;
import com.onlineeducationsyestem.util.AppConstant;
import com.onlineeducationsyestem.util.AppSharedPreference;
import com.onlineeducationsyestem.util.AppUtils;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;


public class HomeFragment extends BaseFragment  implements OnItemClick , NetworkListener {

    private View view;
    private ImageAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<String> list;
    private HomeAdapter homeAdapter;
    private ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view =inflater.inflate(R.layout.fragment_home, container, false);

        //initViewPager();
        iniUI();

        return  view;
    }

    private void iniUI()
    {
         viewPager = view.findViewById(R.id.view_pager);

        if (AppUtils.isInternetAvailable(activity)) {
                hintHome();
        }


        recyclerView =view.findViewById(R.id.recyclerView);

    }

    private void hintHome()
    {
        AppUtils.showDialog(activity, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        if (AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            params.put("language", AppConstant.ENG_LANG);
        }else
        {
            params.put("language", AppConstant.ARABIC_LANG);
        }
        Call<Home> call = apiInterface.getHome(params);

        ApiCall.getInstance().hitService(activity, call, this, ServerConstents.HOME);

    }

    @Override
    public void onGridClick(int pos) {

    }



    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {

        Home data=(Home) response;
        if(data.getStatus()==ServerConstents.CODE_SUCCESS)
        {
            adapter = new ImageAdapter(activity,data.getData().get(0).getBannersList());
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(0);

            //

            data.getData().remove(0);
            HomeAdapter homeAdapter =
                    new HomeAdapter(activity, data.getData(),this);

            recyclerView.setItemAnimator(new DefaultItemAnimator());
            LinearLayoutManager manager = new LinearLayoutManager(activity);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(homeAdapter);

        }

    }

    @Override
    public void onError(String response, int requestCode) {

    }

    @Override
    public void onFailure() {

    }
}

