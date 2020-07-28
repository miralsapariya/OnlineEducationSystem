package com.onlineeducationsyestem;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.onlineeducationsyestem.interfaces.NetworkListener;
import com.onlineeducationsyestem.model.BaseBean;
import com.onlineeducationsyestem.network.ApiCall;
import com.onlineeducationsyestem.network.ApiInterface;
import com.onlineeducationsyestem.network.RestApi;
import com.onlineeducationsyestem.network.ServerConstents;
import com.onlineeducationsyestem.util.AppSharedPreference;
import com.onlineeducationsyestem.util.AppUtils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;

public class ChangePwdActivity extends BaseActivity implements NetworkListener {

    private EditText etOldPwd,etNewPwd,etConfirmPwd;
    private TextView btnContinue;
    private LinearLayout llMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

        initUI();
    }

    private void changePwd()
    {
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("current_password", etOldPwd.getText().toString());
        params.put("new_password", etNewPwd.getText().toString());
        params.put("confirm_password", etConfirmPwd.getText().toString());
        Call<BaseBean> call = apiInterface.changePwd(
                AppSharedPreference.getInstance().
                        getString(ChangePwdActivity.this, AppSharedPreference.ACCESS_TOKEN),
                ServerConstents.HEADER_ACCEPT,
                params);

        ApiCall.getInstance().hitService(ChangePwdActivity.this, call, this, ServerConstents.CHANGE_PWD);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {

        BaseBean data=(BaseBean) response;
        if(data.getStatus()==ServerConstents.CODE_SUCCESS)
        {
            Toast.makeText(ChangePwdActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onError(String response, int requestCode) {
        Toast.makeText(ChangePwdActivity.this, response, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFailure() {

    }

    private boolean isValid()
    {
        boolean bool=true;
        if(TextUtils.isEmpty(etOldPwd.getText().toString()))
        {
            bool=false;
            hideKeyboard();
            Toast.makeText(ChangePwdActivity.this, getString(R.string.toast_old_pwd), Toast.LENGTH_SHORT).show();

            //L.showSnackbar(llLogin, getString(R.string.toast_pwd));
        }else if(TextUtils.isEmpty(etNewPwd.getText().toString()))
        {
            bool=false;
            hideKeyboard();
            Toast.makeText(ChangePwdActivity.this, getString(R.string.toast_new_pwd), Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(etConfirmPwd.getText().toString()))
        {
            bool=false;
            hideKeyboard();
            Toast.makeText(ChangePwdActivity.this, getString(R.string.toast_confirm_pwd), Toast.LENGTH_SHORT).show();

        }else if(!etNewPwd.getText().toString().equals(etConfirmPwd.getText().toString()))
        {
            bool=false;
            Toast.makeText(ChangePwdActivity.this, getString(R.string.toast_pwd_retype_pwd_same), Toast.LENGTH_SHORT).show();
        }else if(!isValidPassword(etNewPwd.getText().toString())) {
            bool=false;
            hideKeyboard();
            Toast.makeText(ChangePwdActivity.this, getString(R.string.toast_pwd_match), Toast.LENGTH_SHORT).show();

        }
        return bool;
    }
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        //(?=.*\d)
        // final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";
        //final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z#@$!%*?&]{6,}$";
        final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z#@$!%*?&0-9]{6,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    private void hideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(llMain.getWindowToken(), 0);
    }

    private void initUI()
    {
        llMain =findViewById(R.id.llMain);
        etOldPwd =findViewById(R.id.etOldPwd);
        etNewPwd =findViewById(R.id.etNewPwd);
        etConfirmPwd =findViewById(R.id.etConfirmPwd);

        btnContinue =findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.isInternetAvailable(ChangePwdActivity.this)) {
                    if (isValid()) {
                        changePwd();
                    }
                }
            }
        });
    }
}
