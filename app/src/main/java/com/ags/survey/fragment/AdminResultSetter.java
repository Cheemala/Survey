package com.ags.survey.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ags.survey.activity.LoginPage;
import com.ags.survey.R;
import com.ags.survey.adapters.AdminSurveyAdapter;
import com.ags.survey.networkapi.APIServices;
import com.ags.survey.networkapi.APIUtils;
import com.ags.survey.pojo.AdminSurveyData;
import com.ags.survey.pojo.AdminSurveyRsltResponse;
import com.ags.survey.pojo.ErrorResponse;
import com.ags.survey.pojo.SurveyResultReleaseResponse;
import com.ags.survey.utils.EqualSpacingItemDecoration;
import com.ags.survey.utils.SurveyResultCallbacks;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dictator on 2/13/2018.
 */

public class AdminResultSetter extends Fragment {

    private RecyclerView surveyCyclerVw;
    private AdminSurveyAdapter adminSurveyAdapter;
    private SharedPreferences loginPreferences;
    private SurveyResultCallbacks srvyRsltCallbacks;
    private APIServices apiServices;
    private Context context;

    public AdminResultSetter(Context context){
        this.context = context;
        loginPreferences = context.getSharedPreferences(LoginPage.LOGIN_PRFS_NAME,Context.MODE_PRIVATE);
        apiServices = APIUtils.getAPIService();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View adminRsltStrLyout = inflater.inflate(R.layout.admin_result_setter,container,false);
        surveyCyclerVw = adminRsltStrLyout.findViewById(R.id.result_recycler_view);
        return adminRsltStrLyout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        validateNdGetSurvey();
    }

    private void validateNdGetSurvey() {
        apiServices.getAdminSurveys(loginPreferences.getString(LoginPage.EMAIL_KEY,"")).enqueue(new Callback<AdminSurveyRsltResponse>() {
            @Override
            public void onResponse(Call<AdminSurveyRsltResponse> call, Response<AdminSurveyRsltResponse> response) {
                if (response.isSuccessful()){
                    if(response.body().getResponseCode().contentEquals("200")){
                        Toast.makeText(context,""+response.body().getResponseStatusMsg(),Toast.LENGTH_SHORT).show();
                        setDataToRecyclerVw(response.body().getAdminSurveyData());
                    }else {
                        Toast.makeText(context,"something went wrong!",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(context, xError.getResponseError(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AdminSurveyRsltResponse> call, Throwable t) {

            }
        });
    }

    private void setDataToRecyclerVw(ArrayList<AdminSurveyData> adminSurveyData) {
        if (adminSurveyData.size() > 0){
            Toast.makeText(context,"Data found"+adminSurveyData.size(),Toast.LENGTH_SHORT).show();
            surveyCyclerVw.setHasFixedSize(true);
            surveyCyclerVw.setLayoutManager(new LinearLayoutManager(context));
            surveyCyclerVw.setItemAnimator(new DefaultItemAnimator());
            surveyCyclerVw.addItemDecoration(new EqualSpacingItemDecoration(20, EqualSpacingItemDecoration.VERTICAL));
            adminSurveyAdapter = new AdminSurveyAdapter(context, adminSurveyData, new SurveyResultCallbacks() {
                @Override
                public void changeSurveySts(String surveyId) {
                    if (surveyId != null){
                        releaseSurveyResult(surveyId);
                    }
                }

                @Override
                public void getSurveyResult(String surveyId) {

                }
            });
            surveyCyclerVw.setAdapter(adminSurveyAdapter);
        }else {
            Toast.makeText(context,"Data not found!",Toast.LENGTH_SHORT).show();
        }

    }

    private void releaseSurveyResult(String surveyId) {
        apiServices.releaseSurveyResult(loginPreferences.getString(LoginPage.EMAIL_KEY,""),surveyId).enqueue(new Callback<SurveyResultReleaseResponse>() {
            @Override
            public void onResponse(Call<SurveyResultReleaseResponse> call, Response<SurveyResultReleaseResponse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context,""+response.body().getResponseStatusMsg(),Toast.LENGTH_SHORT).show();
                    validateNdGetSurvey();
                }else{
                    String errorResponse = null;
                    try {
                        errorResponse = new String(response.errorBody().bytes());
                        Log.e("Error", response.raw().toString() + " : " + errorResponse);
                        ErrorResponse xError = new Gson().fromJson(errorResponse, ErrorResponse.class);
                        //Displaye error message as given by TronX_API.
                        Log.e("Error", xError.getResponseCode());
                        Log.e("Error", xError.getResponseError());
                        Toast.makeText(context, xError.getResponseError(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SurveyResultReleaseResponse> call, Throwable t) {

            }
        });
    }
}
