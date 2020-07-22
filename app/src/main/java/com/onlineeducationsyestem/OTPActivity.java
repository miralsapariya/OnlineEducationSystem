package com.onlineeducationsyestem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.onlineeducationsyestem.interfaces.NetworkListener;
import com.onlineeducationsyestem.model.BaseBean;
import com.onlineeducationsyestem.model.User;
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

public class OTPActivity extends BaseActivity implements NetworkListener {

    private String phone;
    private EditText etOtp;
    private LinearLayout llMain;
    private TextView btnVerify,tvTimer,tvResend;
    private AppSharedPreference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwd);
        initUI();
    }

    private void initUI()
    {
        preference = AppSharedPreference.getInstance();
        etOtp =findViewById(R.id.etOtp);
        llMain =findViewById(R.id.llMain);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        // get data via the key
        phone  = extras.getString("phone");
        btnVerify=findViewById(R.id.btnVerify);
        tvTimer =findViewById(R.id.tvTimer);
        countDownTimer();
        tvResend =findViewById(R.id.tvResend);
        tvResend.setEnabled(false);
        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.isInternetAvailable(OTPActivity.this)) {
                    if (isValid()) {
                        hintResend();
                    }
                }
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppUtils.isInternetAvailable(OTPActivity.this)) {
                    if (isValid()) {
                        hintOtp();
                    }
                }
            }
        });
    }

    private void hintResend()
    {
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("phone_no", phone);
        if (AppSharedPreference.getInstance().getString(this, AppSharedPreference.LANGUAGE_SELECTED) == null) {
            params.put("language", AppConstant.ENG_LANG);
        }else
        {
            params.put("language", AppConstant.ARABIC_LANG);
        }

        Call<BaseBean> call = apiInterface.resend(params);
        ApiCall.getInstance().hitService(OTPActivity.this, call, this, ServerConstents.RESEND);

    }
    private void hintOtp()
    {
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("otp", etOtp.getText().toString());
        params.put("phone_no", phone);
        if (AppSharedPreference.getInstance().getString(this, AppSharedPreference.LANGUAGE_SELECTED) == null) {
            params.put("language", AppConstant.ENG_LANG);
        }else
        {
            params.put("language", AppConstant.ARABIC_LANG);
        }

        Call<User> call = apiInterface.otp(params);
        ApiCall.getInstance().hitService(OTPActivity.this, call, this, ServerConstents.LOGIN);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {

        if(requestCode == ServerConstents.RESEND)
        {
            BaseBean data=(BaseBean) response;
            if(data.getStatus()==ServerConstents.CODE_SUCCESS)
            {
              /*  Intent intent = new Intent(OTPActivity.this,MainActivity.class);
                startActivity(intent);
                finish();*/
                countDownTimer();
                tvResend.setEnabled(false);
            }
        }else
        {
            User data=(User) response;
            if(data.getStatus()==ServerConstents.CODE_SUCCESS)
            {
                ArrayList<User.Datum> res= data.getData();

                preference.putString(OTPActivity.this, AppSharedPreference.USERID, res.get(0).getUserId()+"");
                preference.putString(OTPActivity.this, AppSharedPreference.NAME, res.get(0).getName()+"");
                preference.putString(OTPActivity.this, AppSharedPreference.EMAIL, res.get(0).getEmail());

                preference.putString(OTPActivity.this, AppSharedPreference.FIRST_NAME, res.get(0).getFirstName()+"");
                preference.putString(OTPActivity.this, AppSharedPreference.LAST_NAME, res.get(0).getLastName()+"");
                preference.putString(OTPActivity.this, AppSharedPreference.PROFILE_PIC, res.get(0).getProfilePicture());
                preference.putString(OTPActivity.this,AppSharedPreference.PHONE, res.get(0).getPhoneNo());

                Intent i = new Intent(OTPActivity.this,
                        MainActivity.class);
                startActivity(i);
                finish();
            }
        }

    }

    @Override
    public void onError(String response, int requestCode) {

        Toast.makeText(OTPActivity.this, response, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFailure() {

    }
    private void countDownTimer() {
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                tvTimer.setText("Time Remaining (" + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds) + ")");
            }

            public void onFinish() {
                // mTextField.setText("done!");
                tvTimer.setText("Time Remaining (00:00)");
                tvResend.setEnabled(true);
            }

        }.start();
    }
    private boolean isValid() {
        boolean bool = true;
        if (TextUtils.isEmpty(etOtp.getText().toString())) {
            bool = false;
            hideKeyboard();
            Toast.makeText(OTPActivity.this, getString(R.string.toast_otp), Toast.LENGTH_SHORT).show();
            // L.showSnackbar(llLogin, getString(R.string.toast_Ic));

        }
        return bool;
    }
    private void hideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(llMain.getWindowToken(), 0);
    }
}