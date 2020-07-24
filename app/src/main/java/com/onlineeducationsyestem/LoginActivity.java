package com.onlineeducationsyestem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.onlineeducationsyestem.interfaces.NetworkListener;
import com.onlineeducationsyestem.model.User;
import com.onlineeducationsyestem.network.ApiCall;
import com.onlineeducationsyestem.network.ApiInterface;
import com.onlineeducationsyestem.network.RestApi;
import com.onlineeducationsyestem.network.ServerConstents;
import com.onlineeducationsyestem.util.AppSharedPreference;
import com.onlineeducationsyestem.util.AppUtils;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;

public class LoginActivity extends BaseActivity implements NetworkListener {

    private EditText etEmail,etPassword;
    private TextView btnLogin,tvSignUP,tvForgotPwd;
    private LinearLayout llMain;
    private AppSharedPreference preference;
    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
    }

    private void initUI()
    {
        preference = AppSharedPreference.getInstance();
        etEmail =findViewById(R.id.etEmail);
        etPassword =findViewById(R.id.etPassword);
        llMain =findViewById(R.id.llMain);
        imgBack =findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnLogin =findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.isInternetAvailable(LoginActivity.this)) {
                    if (isValid()) {
                        hintLogin();
                    }
                }
            }
        });
        tvSignUP =findViewById(R.id.tvSignUP);
        tvSignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvForgotPwd =findViewById(R.id.tvForgotPwd);
        tvForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ForgotPwdActivity.class);
                startActivity(intent);

            }
        });
    }

    private void hintLogin()
    {
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("email", etEmail.getText().toString());
        params.put("password", etPassword.getText().toString());
        params.put("device_token", "1234");
        params.put("device_type", ServerConstents.DEVICE_TYPE);
        Call<User> call = apiInterface.login(params);

        ApiCall.getInstance().hitService(LoginActivity.this, call, this, ServerConstents.LOGIN);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        User data=(User) response;
        if(data.getStatus()==ServerConstents.CODE_SUCCESS)
        {
            ArrayList<User.Datum> res= data.getData();

            preference.putString(LoginActivity.this, AppSharedPreference.USERID, res.get(0).getUserId()+"");
            preference.putString(LoginActivity.this, AppSharedPreference.NAME, res.get(0).getName()+"");
            preference.putString(LoginActivity.this, AppSharedPreference.EMAIL, res.get(0).getEmail());

            preference.putString(LoginActivity.this, AppSharedPreference.FIRST_NAME, res.get(0).getFirstName()+"");
            preference.putString(LoginActivity.this, AppSharedPreference.LAST_NAME, res.get(0).getLastName()+"");
            preference.putString(LoginActivity.this, AppSharedPreference.PROFILE_PIC, res.get(0).getProfilePicture());
            preference.putString(LoginActivity.this,AppSharedPreference.PHONE, res.get(0).getPhoneNo());
            preference.putString(LoginActivity.this, AppSharedPreference.ACCESS_TOKEN, "Bearer"+" "+res.get(0).getToken());

            Intent i = new Intent(LoginActivity.this,
                    MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onError(String response, int requestCode) {

        Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure() {

    }

    private boolean isValid()
    {
        boolean bool=true;
         if(TextUtils.isEmpty(etEmail.getText().toString()))
        {
            bool=false;
            hideKeyboard();
            Toast.makeText(LoginActivity.this, getString(R.string.toast_email), Toast.LENGTH_SHORT).show();

            //L.showSnackbar(llLogin, getString(R.string.toast_pwd));
        }else if(TextUtils.isEmpty(etPassword.getText().toString()))
        {
            bool=false;
            hideKeyboard();
            Toast.makeText(LoginActivity.this, getString(R.string.toast_password), Toast.LENGTH_SHORT).show();

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
