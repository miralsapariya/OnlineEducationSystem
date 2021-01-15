package com.onlineeducationsyestem.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.MainActivity;
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
import com.paytabs.paytabs_sdk.payment.ui.activities.PayTabActivity;
import com.paytabs.paytabs_sdk.utils.PaymentParams;

import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;

import static android.app.Activity.RESULT_OK;


public class CartFragment extends BaseFragment implements NetworkListener, OnItemClick, DeleteItemInCart , ApplyPromoCode, CheckOutInCart {

    private View view;
    private CartListAdapter cartListAdapter;
    private RecyclerView recyclerView;
    private TextView tvNoRecord;
    CartList data;
    private String cartId="",amount="";
    private LinearLayout llMain;
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
        llMain =view.findViewById(R.id.llMain);
        recyclerView=view.findViewById(R.id.recyclerView);
        if (AppUtils.isInternetAvailable(activity)) {
            getCartList();
        }else {
            AppUtils.showAlertDialog(activity,activity.getString(R.string.no_internet),activity.getString(R.string.alter_net));
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
        }else if(requestCode == ServerConstents.CHECKOUT) {
            BaseBean data = (BaseBean) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                Toast.makeText(activity, data.getMessage(), Toast.LENGTH_SHORT).show();
                getCartList();
                ViewDialog alert = new ViewDialog();
                alert.showDialog(activity);
            }
        }else {
            BaseBean data = (BaseBean) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                getCartList();
            }
        }
    }

    public  class ViewDialog {

        public void showDialog(final Activity activity){

           // Toast.makeText(activity, "....."+AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED), Toast.LENGTH_SHORT).show();
            if (AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED)!= null &&AppSharedPreference.getInstance().getString(activity, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ARABIC_LANG)) {
                String languageToLoad = "ar"; // your language
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                activity.getBaseContext().getResources().updateConfiguration(config, activity.getBaseContext().getResources().getDisplayMetrics());

            }
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_thankyou_payment);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            Button btnApply =  dialog.findViewById(R.id.btnApply);
            btnApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                }
            });

            Button btnCancel =  dialog.findViewById(R.id.btnCancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(activity, MainActivity.class);

                    intent.putExtra("from", "thankyou");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    activity.finish();
                    dialog.cancel();
                }
            });

            dialog.show();
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
    public void doCheckout(String cartId, String courseAmount,String courseName) {


        Log.d("AMOUNT PAY:: ", courseAmount+"");
        this.cartId=cartId;
        this.amount=courseAmount;
        Intent in = new Intent(activity, PayTabActivity.class);
        in.putExtra(PaymentParams.MERCHANT_EMAIL, "harshida.patoliya@beelinesoftwares.com");
        in.putExtra(PaymentParams.SECRET_KEY,"n2EqYIEcn144WCWcHsK4ERv756CJP46d7KD5tihQkmUgYRsGjdLY4g59mITcNZqUK3Zdlh3GDXWCPpi8C36GkcPGUISIIiCsmCGI");//Add your Secret Key Here
        in.putExtra(PaymentParams.LANGUAGE,PaymentParams.ENGLISH);
        in.putExtra(PaymentParams.TRANSACTION_TITLE, "Test Paytabs android library");
        in.putExtra(PaymentParams.AMOUNT, Double.parseDouble(courseAmount));

        in.putExtra(PaymentParams.CURRENCY_CODE, "USD");
        in.putExtra(PaymentParams.CUSTOMER_PHONE_NUMBER, "009733");
        in.putExtra(PaymentParams.CUSTOMER_EMAIL, "customer-email@example.com");
        in.putExtra(PaymentParams.ORDER_ID, "123456");
        in.putExtra(PaymentParams.PRODUCT_NAME, "Product 1, Product 2");
        //Billing Address
        in.putExtra(PaymentParams.ADDRESS_BILLING, "Flat 1,Building 123, Road 2345");
        in.putExtra(PaymentParams.CITY_BILLING, "Manama");
        in.putExtra(PaymentParams.STATE_BILLING, "Manama");
        in.putExtra(PaymentParams.COUNTRY_BILLING, "BHR");
        in.putExtra(PaymentParams.POSTAL_CODE_BILLING, "00973"); //Put Country Phone code if Postal code not available '00973'

        //Payment Page Style
        in.putExtra(PaymentParams.PAY_BUTTON_COLOR, "#e98074");

        //Tokenization
        in.putExtra(PaymentParams.IS_TOKENIZATION, true);
        startActivityForResult(in, PaymentParams.PAYMENT_REQUEST_CODE);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("===fraggg result ::: ", "==============");
        if (resultCode == RESULT_OK && requestCode == PaymentParams.PAYMENT_REQUEST_CODE) {
            Log.e("Tag", data.getStringExtra(PaymentParams.RESPONSE_CODE));
            Log.e("Tag", data.getStringExtra(PaymentParams.TRANSACTION_ID));

            hideKeyboard();
            callApiDoCheckOut();
            if (data.hasExtra(PaymentParams.TOKEN) && !data.getStringExtra(PaymentParams.TOKEN).isEmpty()) {
                Log.e("Tag", data.getStringExtra(PaymentParams.TOKEN));
                Log.e("Tag", data.getStringExtra(PaymentParams.CUSTOMER_EMAIL));
                Log.e("Tag", data.getStringExtra(PaymentParams.CUSTOMER_PASSWORD));
            }
        }
    }

    private void hideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager)
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(llMain.getWindowToken(), 0);
    }
    private void callApiDoCheckOut()
    {
        String lang="";
        AppUtils.showDialog(activity, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("cart_id", cartId);
        params.put("course_amount", amount);
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
