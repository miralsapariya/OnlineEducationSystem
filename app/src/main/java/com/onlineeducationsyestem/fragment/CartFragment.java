package com.onlineeducationsyestem.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.R;
import com.onlineeducationsyestem.adapter.CartListAdapter;
import com.onlineeducationsyestem.interfaces.ApplyPromoCode;
import com.onlineeducationsyestem.interfaces.CheckOutInCart;
import com.onlineeducationsyestem.interfaces.DeleteItemInCart;
import com.onlineeducationsyestem.interfaces.NetworkListener;
import com.onlineeducationsyestem.interfaces.OnItemClick;
import com.onlineeducationsyestem.model.BaseBean;
import com.onlineeducationsyestem.model.CartList;
import com.onlineeducationsyestem.network.ApiCall;
import com.onlineeducationsyestem.network.ApiInterface;
import com.onlineeducationsyestem.network.RestApi;
import com.onlineeducationsyestem.network.ServerConstents;
import com.onlineeducationsyestem.util.AppConstant;
import com.onlineeducationsyestem.util.AppSharedPreference;
import com.onlineeducationsyestem.util.AppUtils;

import java.util.HashMap;

import retrofit2.Call;


public class CartFragment extends BaseFragment implements NetworkListener, OnItemClick, DeleteItemInCart , ApplyPromoCode, CheckOutInCart {

    private View view;
    private CartListAdapter cartListAdapter;
    private RecyclerView recyclerView;
    private TextView tvNoRecord;
    CartList data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_cart, container, false);
        init();
        return  view;
    }

    private void init()
    {
        tvNoRecord =view.findViewById(R.id.tvNoRecord);
        recyclerView=view.findViewById(R.id.recyclerView);
        if (AppUtils.isInternetAvailable(activity)) {
            getCartList();
        }
    }
    private void getCartList()
    {
        String lang="";
        AppUtils.showDialog(activity, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        if (AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<CartList> call = apiInterface.getCartList(lang,AppSharedPreference.getInstance().
                getString(activity, AppSharedPreference.ACCESS_TOKEN));
        ApiCall.getInstance().hitService(activity, call, this, ServerConstents.CART);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if(requestCode == ServerConstents.CART) {
            data= (CartList) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {

                tvNoRecord.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                cartListAdapter= new CartListAdapter(activity, data.getData().get(0).getList(),this,this,this,this);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                LinearLayoutManager manager = new LinearLayoutManager(activity);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(cartListAdapter);
            }
        }else if(requestCode == ServerConstents.PROMO)
        {
            BaseBean data = (BaseBean) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                Toast.makeText(activity, data.getMessage(), Toast.LENGTH_SHORT).show();
                getCartList();
            }
        }else if(requestCode == ServerConstents.CHECKOUT)
        {
            BaseBean data = (BaseBean) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                Toast.makeText(activity, data.getMessage(), Toast.LENGTH_SHORT).show();
                getCartList();
            }
        }
        else
         {
            BaseBean data = (BaseBean) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                getCartList();
            }
        }
    }


    @Override
    public void onError(String response, int requestCode, int errorCode) {
        if(requestCode == ServerConstents.CART) {
            tvNoRecord.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else
        {
            Toast.makeText(activity, response, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onGridClick(int pos) {

    }

    @Override
    public void promoApply(int pos,String promocode) {
        String lang="";
        AppUtils.showDialog(activity, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("cart_id", data.getData().get(0).getList().get(pos).getCartid());
        params.put("promocode", promocode);
        if (AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<BaseBean> call = apiInterface.applyPromo(lang,AppSharedPreference.getInstance().
                getString(activity, AppSharedPreference.ACCESS_TOKEN),params);
        ApiCall.getInstance().hitService(activity, call, this, ServerConstents.PROMO);

    }

    @Override
    public void deleteCart(int pos) {
        String lang="";
        AppUtils.showDialog(activity, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("cart_id", data.getData().get(0).getList().get(pos).getCartid());
        if (AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<BaseBean> call = apiInterface.deleteFromCart(lang,AppSharedPreference.getInstance().
                getString(activity, AppSharedPreference.ACCESS_TOKEN),params);
        ApiCall.getInstance().hitService(activity, call, this, ServerConstents.DELETE);

    }

    @Override
    public void doCheckout(String cartId, String courseAmount) {
        String lang="";
        AppUtils.showDialog(activity, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("cart_id", cartId);
        params.put("course_amount", courseAmount);
        if (AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }
        Call<BaseBean> call = apiInterface.doCheckOut(lang,AppSharedPreference.getInstance().
                getString(activity, AppSharedPreference.ACCESS_TOKEN),params);
        ApiCall.getInstance().hitService(activity, call, this, ServerConstents.CHECKOUT);

    }
}
