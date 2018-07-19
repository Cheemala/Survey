package com.ags.survey.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ags.survey.R;
import com.ags.survey.networkapi.APIServices;
import com.ags.survey.networkapi.APIUtils;
import com.ags.survey.pojo.ErrorResponse;
import com.ags.survey.pojo.LoginRespone;
import com.google.gson.Gson;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity implements View.OnClickListener {

    private TextView loginSumbitTv,loginDontHaveAccount;
    private EditText loginEmailIdEdt,loginPasswordIdEdt;
    private APIServices loginService;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor editor;
    public final static String LOGIN_PRFS_NAME = "login_preferences";
    public final static String EMAIL_KEY = "email_key";
    public final static String USER_TYPE_KEY = "user_type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_page);
        loginInitialView();
        loginPreferences = getSharedPreferences(LOGIN_PRFS_NAME,MODE_PRIVATE);
    }

    private void loginInitialView()
    {
        loginSumbitTv= (TextView) findViewById(R.id.login_submit);
        loginSumbitTv.setOnClickListener(this);
        loginDontHaveAccount=(TextView)findViewById(R.id.login_dont_have_account);
        loginDontHaveAccount.setOnClickListener(this);
        loginEmailIdEdt=(EditText)findViewById(R.id.login_emailId);
        loginPasswordIdEdt=(EditText)findViewById(R.id.login_passwordId);
        loginService = APIUtils.getAPIService();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.login_submit:
                checkNdLoginUser();
                break;
            case R.id.login_dont_have_account:
                startActivity(new Intent(LoginPage.this,RegisterPage.class));
                finish();
                break;

        }

    }


    private void checkNdLoginUser() {

        String emailString = loginEmailIdEdt.getText().toString().trim();
        String passwordString = loginPasswordIdEdt.getText().toString().trim();
        if (!emailString.isEmpty()){
            if(!passwordString.isEmpty()){
                loginService.checkLogin(emailString,passwordString).enqueue(new Callback<LoginRespone>() {
                        @Override
                        public void onResponse(Call<LoginRespone> call, Response<LoginRespone> response) {

                            if (response.isSuccessful()){
                                if (response.body().getResponseCode().contentEquals("200")){
                                    Toast.makeText(LoginPage.this,""+response.body().getResponseStatusMsg(),Toast.LENGTH_SHORT).show();
                                    saveLoginPrefs(response);
                                    startActivity(new Intent(LoginPage.this,MainActivity.class).putExtra("USER_TYPE",response.body().getUserType()));
                                    finish();
                                }
                            }else {
                                String errorResponse = null;
                                try {
                                    errorResponse = new String(response.errorBody().bytes());
                                    Log.e("Error", response.raw().toString() + " : " + errorResponse);
                                    ErrorResponse xError = new Gson().fromJson(errorResponse, ErrorResponse.class);
                                    //Displaye error message as given by TronX_API.
                                    Log.e("Error", xError.getResponseCode());
                                    Log.e("Error", xError.getResponseError());
                                    Toast.makeText(getApplicationContext(), xError.getResponseError(), Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginRespone> call, Throwable t) {
                            Toast.makeText(LoginPage.this,""+t.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
            }else {
                Toast.makeText(LoginPage.this,"Enter password!",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(LoginPage.this,"Enter email!",Toast.LENGTH_SHORT).show();
        }

    }

    private void saveLoginPrefs(Response<LoginRespone> response) {

        editor = loginPreferences.edit();
        editor.putString(EMAIL_KEY,response.body().getUserEmail());
        editor.putString(USER_TYPE_KEY,response.body().getUserType());
        editor.commit();



       /* if (!loginPreferences.contains(EMAIL_KEY)){
            editor = loginPreferences.edit();
            editor.putString(EMAIL_KEY,response.body().getUserEmail());
            editor.commit();
        }

        if (!loginPreferences.contains(USER_TYPE_KEY)){
            editor = loginPreferences.edit();
            editor.putString(USER_TYPE_KEY,response.body().getUserType());
            editor.commit();
        }*/

    }

}
