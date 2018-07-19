package com.ags.survey.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.ags.survey.utils.SurveyCallbacks;
import com.ags.survey.adapters.ResultAdapter;
import com.ags.survey.adapters.UserSurveyAdapter;
import com.ags.survey.networkapi.APIServices;
import com.ags.survey.networkapi.APIUtils;
import com.ags.survey.pojo.AdminSurveyData;
import com.ags.survey.pojo.AdminSurveyRsltResponse;
import com.ags.survey.pojo.ErrorResponse;
import com.ags.survey.pojo.ResultRawData;
import com.ags.survey.pojo.SurveyResult;
import com.ags.survey.utils.EqualSpacingItemDecoration;
import com.ags.survey.utils.SurveyResultCallbacks;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CheemalaCh on 3/26/2018.
 */

public class UserResultSetter extends Fragment {
    private RecyclerView surveyCyclerVw;
    private UserSurveyAdapter userSurveyAdapter;
    private SharedPreferences loginPreferences;
    private SurveyCallbacks surveyCallbacks;
    private APIServices apiServices;
    private Context context;

    public UserResultSetter(Context context){
        this.context = context;
        loginPreferences = context.getSharedPreferences(LoginPage.LOGIN_PRFS_NAME,Context.MODE_PRIVATE);
        apiServices = APIUtils.getAPIService();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        surveyCallbacks = new SurveyCallbacks() {
            @Override
            public void saveAnswer(String surveyId,String questionNumFlag, String questionAnsFlag) {
            }

            @Override
            public void postNxtSurveyAns() {
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View userRsltStrLyout = inflater.inflate(R.layout.user_result_setter,container,false);
        surveyCyclerVw = userRsltStrLyout.findViewById(R.id.result_recycler_view);
        return userRsltStrLyout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        validateNdGetSurvey();
    }

    private void validateNdGetSurvey() {
        apiServices.getUserSurveys(loginPreferences.getString(LoginPage.EMAIL_KEY,"")).enqueue(new Callback<AdminSurveyRsltResponse>() {
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

    private void setDataToRecyclerVw(ArrayList<AdminSurveyData> userSurveyData) {
        if (userSurveyData.size() > 0){
            Toast.makeText(context,"Data found"+userSurveyData.size(),Toast.LENGTH_SHORT).show();
            surveyCyclerVw.setHasFixedSize(true);
            surveyCyclerVw.setLayoutManager(new LinearLayoutManager(context));
            surveyCyclerVw.setItemAnimator(new DefaultItemAnimator());
            surveyCyclerVw.addItemDecoration(new EqualSpacingItemDecoration(20, EqualSpacingItemDecoration.VERTICAL));
            userSurveyAdapter = new UserSurveyAdapter(context, userSurveyData, new SurveyResultCallbacks() {
                @Override
                public void changeSurveySts(String surveyId) {

                }

                @Override
                public void getSurveyResult(String surveyId) {
                    if (surveyId != null){
                        apiServices.getSurveyResult(surveyId).enqueue(new Callback<SurveyResult>() {
                            @Override
                            public void onResponse(Call<SurveyResult> call, Response<SurveyResult> response) {
                                if (response.isSuccessful()){
                                    SurveyResult surveyResult = response.body();
                                    if (surveyResult.getResultData().size() > 0){
                                        //Toast.makeText(context,""+surveyResult.getResultData().get(0).getOptionDta().get(0).getOptionTxt(),Toast.LENGTH_SHORT).show();
                                        navigateToShowResult(surveyResult.getResultData());
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
                            public void onFailure(Call<SurveyResult> call, Throwable t) {
                                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
            surveyCyclerVw.setAdapter(userSurveyAdapter);
        }else {
            Toast.makeText(context,"Data not found!",Toast.LENGTH_SHORT).show();
        }

    }

    private void navigateToShowResult(ArrayList<ResultRawData> resultData) {

        LayoutInflater li = LayoutInflater.from(context);
        final View prompt = li.inflate(R.layout.activity_show_result, null);
        RecyclerView resultRcylerVw = prompt.findViewById(R.id.result_data_recycler_view);
        ResultAdapter rsltAdpter = new ResultAdapter(context,resultData);
        resultRcylerVw.setHasFixedSize(true);
        resultRcylerVw.setLayoutManager(new LinearLayoutManager(context));
        resultRcylerVw.setItemAnimator(new DefaultItemAnimator());
        resultRcylerVw.addItemDecoration(new EqualSpacingItemDecoration(20, EqualSpacingItemDecoration.VERTICAL));
        resultRcylerVw.setAdapter(rsltAdpter);

        final AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);

        alertDialogBuilder.setView(prompt);
        alertDialogBuilder.setTitle("Result");
        // positive button  --right side
        alertDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialogBuilder.show();
    }
}
