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
import com.onlineeducationsyestem.model.BaseBean;
import com.onlineeducationsyestem.network.ApiCall;
import com.onlineeducationsyestem.network.ApiInterface;
import com.onlineeducationsyestem.network.RestApi;
import com.onlineeducationsyestem.network.ServerConstents;
import com.onlineeducationsyestem.util.AppConstant;
import com.onlineeducationsyestem.util.AppSharedPreference;
import com.onlineeducationsyestem.util.AppUtils;

import java.util.HashMap;

import retrofit2.Call;

public class SignUpActivity extends BaseActivity implements NetworkListener
{
    private EditText etName,etEmail,etPhone,etPassword;
    private LinearLayout llMain;
    private TextView tvLogin,btnSignUp;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUI();
    }

    private void initUI()
    {
        etName =findViewById(R.id.etName);
        etEmail =findViewById(R.id.etEmail);
        etPhone =findViewById(R.id.etPhone);
        etPassword =findViewById(R.id.etPassword);
        imgBack =findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        llMain =findViewById(R.id.llMain);
        tvLogin =findViewById(R.id.tvLogin);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnSignUp =findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppUtils.isInternetAvailable(SignUpActivity.this)) {
                    if (isValid()) {
                        hintRegister();
                    }
                }
            }
        });
    }

    private void hintRegister()
    {
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("name", etName.getText().toString());
        params.put("email", etEmail.getText().toString());
        params.put("password", etPassword.getText().toString());
        params.put("phone_no", etPhone.getText().toString());
        params.put("device_token", "1234");
        params.put("device_type", ServerConstents.DEVICE_TYPE);
        if (AppSharedPreference.getInstance().getString(this, AppSharedPreference.LANGUAGE_SELECTED) == null) {
            params.put("language", AppConstant.ENG_LANG);
        }else
        {
            params.put("language", AppConstant.ARABIC_LANG);
        }

        Call<BaseBean> call = apiInterface.register(params);
        ApiCall.getInstance().hitService(SignUpActivity.this, call, this, ServerConstents.LOGIN);
    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {

        Intent intent =new Intent(SignUpActivity.this, OTPActivity.class);
        intent.putExtra("phone", etPhone.getText().toString());

        startActivity(intent);
        finish();

    }

    @Override
    public void onError(String response, int requestCode) {
        Toast.makeText(SignUpActivity.this, response, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFailure() {

    }

    private boolean isValid()
    {
        boolean bool=true;
        if(TextUtils.isEmpty(etName.getText().toString()))
        {
            bool=false;
            hideKeyboard();
            Toast.makeText(SignUpActivity.this, getString(R.string.toast_name), Toast.LENGTH_SHORT).show();
           // L.showSnackbar(llLogin, getString(R.string.toast_Ic));

        }else if(TextUtils.isEmpty(etEmail.getText().toString()))
        {
            bool=false;
            hideKeyboard();
            Toast.makeText(SignUpActivity.this, getString(R.string.toast_email), Toast.LENGTH_SHORT).show();

            //L.showSnackbar(llLogin, getString(R.string.toast_pwd));
        }else if(TextUtils.isEmpty(etPhone.getText().toString()))
        { bool=false;
            hideKeyboard();
            Toast.makeText(SignUpActivity.this, getString(R.string.toast_phone), Toast.LENGTH_SHORT).show();


        }else if(TextUtils.isEmpty(etPassword.getText().toString()))
        {
            bool=false;
            hideKeyboard();
            Toast.makeText(SignUpActivity.this, getString(R.string.toast_password), Toast.LENGTH_SHORT).show();

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
