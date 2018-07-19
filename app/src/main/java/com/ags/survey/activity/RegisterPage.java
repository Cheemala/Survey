package com.ags.survey.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ags.survey.R;
import com.ags.survey.networkapi.APIServices;
import com.ags.survey.networkapi.APIUtils;
import com.ags.survey.pojo.ErrorResponse;
import com.ags.survey.pojo.RegistrationResponse;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPage extends AppCompatActivity implements View.OnClickListener{

    private TextView registerSumbitTv,registerHaveAccount;
    private EditText emailIdEdt,passwordIdEdt;
    private RadioButton registerAdmin,registerUser;
    private RadioGroup registerGroup;
    private String typeOfUser=null;
    private APIServices registerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
       registerInitialView();
    }

    private void registerInitialView() {

        registerSumbitTv=(TextView)findViewById(R.id.register_btn_tv);
        registerSumbitTv.setOnClickListener(this);
        registerHaveAccount=(TextView)findViewById(R.id.redirect_to_login_tv);
        registerHaveAccount.setOnClickListener(this);
        emailIdEdt=(EditText)findViewById(R.id.email_id_edt);
        passwordIdEdt=(EditText)findViewById(R.id.password_id_edt);
        registerGroup=(RadioGroup)findViewById(R.id.register_group);
        registerAdmin=(RadioButton) findViewById(R.id.register_admin);
        registerUser=(RadioButton)findViewById(R.id.register_user);
        registerService = APIUtils.getAPIService();
        registerGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.register_admin){
                    typeOfUser = "admin";
                }else if (checkedId == R.id.register_user){
                    typeOfUser = "user";
                }else {}

            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.register_btn_tv:
                checkNdRegisterUser();
                break;
            case R.id.redirect_to_login_tv:
                startActivity(new Intent(this,LoginPage.class));
                finish();
                break;

        }

    }

    private void checkNdRegisterUser() {
        String emailString = emailIdEdt.getText().toString().trim();
        String passwordString = passwordIdEdt.getText().toString().trim();
        if (!emailString.isEmpty()){
            if(!passwordString.isEmpty()){
                if (typeOfUser != null){

                    registerService.registerAUser(emailString,passwordString,typeOfUser).enqueue(new Callback<RegistrationResponse>() {

                        @Override
                        public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                            if (response.isSuccessful()){
                                if (response.body().getResponseCode().contentEquals("200")){
                                    Toast.makeText(RegisterPage.this,""+response.body().getResponseStatusMsg(),Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterPage.this,LoginPage.class));
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
                        public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                            Toast.makeText(RegisterPage.this,"Server error!"+t.getMessage(),Toast.LENGTH_SHORT).show();
                            Log.d("error: ",""+t.getMessage());
                        }
                    });
                }else {
                    Toast.makeText(RegisterPage.this,"Select type of user!",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(RegisterPage.this,"Enter password!",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(RegisterPage.this,"Enter email!",Toast.LENGTH_SHORT).show();
        }
    }
}
