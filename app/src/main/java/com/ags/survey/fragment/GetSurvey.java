package com.ags.survey.fragment;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ags.survey.activity.LoginPage;
import com.ags.survey.R;
import com.ags.survey.adapters.SurveyAdapter;
import com.ags.survey.utils.SurveyCallbacks;
import com.ags.survey.networkapi.APIServices;
import com.ags.survey.networkapi.APIUtils;
import com.ags.survey.pojo.ErrorResponse;
import com.ags.survey.pojo.SubmitSurveyResponse;
import com.ags.survey.pojo.SurveyData;
import com.ags.survey.pojo.SurveyResponse;
import com.ags.survey.pojo.UpdtUserSrvyStsResponse;
import com.ags.survey.pojo.UserAnswerDump;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CheemalaCh on 3/14/2018.
 */

public class GetSurvey extends Fragment implements View.OnClickListener{

    private Context context;
    private ImageButton getSurveyBtn;
    private Button postSurveyBtn;
    private EditText getSurveyEdt;
    private APIServices apiServices;
    private RecyclerView surveyCyclerVw;
    private SurveyCallbacks surveyCallbacks;
    private SurveyAdapter recylrAdpater;
    private SharedPreferences appPreferences;
    private RelativeLayout getSurveyDta,submitSurveyDta;
    private ArrayList<SurveyData> surveyQnData;
    private ArrayList<UserAnswerDump> userAnsDump = new ArrayList<>();

    public GetSurvey(Context context){
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiServices = APIUtils.getAPIService();
        appPreferences = context.getSharedPreferences(LoginPage.LOGIN_PRFS_NAME,Context.MODE_PRIVATE);
        surveyCallbacks = new SurveyCallbacks() {
            @Override
            public void saveAnswer(String surveyId,String questionNumFlag, String questionAnsFlag) {
                Log.d("tst_fired_","sId: "+surveyId+" qNum: "+questionNumFlag+" qAns: "+questionAnsFlag);
                userAnsDump.add(new UserAnswerDump(questionNumFlag,questionAnsFlag,surveyId,false));
            }

            @Override
            public void postNxtSurveyAns() {
                validateNdPostSurvey();
            }
        };

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View getSurveyLayout = inflater.inflate(R.layout.get_survey_layout,container,false);
        surveyCyclerVw = getSurveyLayout.findViewById(R.id.survey_recycler_view);
        getSurveyEdt = getSurveyLayout.findViewById(R.id.get_survey_edt);
        getSurveyDta = getSurveyLayout.findViewById(R.id.get_survey_layout);
        submitSurveyDta = getSurveyLayout.findViewById(R.id.submit_survey_layout);
        getSurveyBtn = getSurveyLayout.findViewById(R.id.get_survey_btn);
        postSurveyBtn = getSurveyLayout.findViewById(R.id.post_survey_btn);
        getSurveyBtn.setOnClickListener(this);
        postSurveyBtn.setOnClickListener(this);
        return getSurveyLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_survey_btn:
                validateNdGetSurvey();
                break;
            case R.id.post_survey_btn:
                validateNdPostSurvey();
                break;
        }
    }

    private void validateNdPostSurvey() {
        if(userAnsDump.size() > 0){
            for (int i = 0;i < userAnsDump.size(); i++){
                if((i+1) == userAnsDump.size()){
                    if (!userAnsDump.get(i).isAnsPosted()){
                        userAnsDump.get(i).setAnsPosted(true);
                        postSurveyAnswerDta(userAnsDump.get(i).getSurveyId(),userAnsDump.get(i).getQuestionNum(),userAnsDump.get(i).getQuestionAnsNum());
                        submitSurveyDta.setVisibility(View.GONE);
                        getSurveyDta.setVisibility(View.VISIBLE);
                        updateUsrSrvyStatus(userAnsDump.get(i).getSurveyId());
                        break;
                    }
                }else {
                    if (!userAnsDump.get(i).isAnsPosted()){
                        userAnsDump.get(i).setAnsPosted(true);
                        postSurveyAnswerDta(userAnsDump.get(i).getSurveyId(),userAnsDump.get(i).getQuestionNum(),userAnsDump.get(i).getQuestionAnsNum());
                        break;
                    }
                }
            }
        }else {
            Log.d("user_ans_","data_null");
        }
    }

    private void updateUsrSrvyStatus(String surveyId) {
        Log.d("survy_id: ",""+surveyId);
        apiServices.updtUsrSurvySts(surveyId,appPreferences.getString(LoginPage.EMAIL_KEY,"")).enqueue(new Callback<UpdtUserSrvyStsResponse>() {
            @Override
            public void onResponse(Call<UpdtUserSrvyStsResponse> call, Response<UpdtUserSrvyStsResponse> response) {
                if (response.isSuccessful()){
                    if(response.body().getResponseCode().contentEquals("200")){
                        Toast.makeText(context,""+response.body().getResponseStatusMsg(),Toast.LENGTH_SHORT).show();
                        clearSurveyOnScreen();
                        userAnsDump.clear();
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
            public void onFailure(Call<UpdtUserSrvyStsResponse> call, Throwable t) {
                Toast.makeText(context,""+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postSurveyAnswerDta(String surveyId, String questionNum, String questionAnsNum) {
        apiServices.postUserSurvey(surveyId,questionNum,questionAnsNum).enqueue(new Callback<SubmitSurveyResponse>() {
            @Override
            public void onResponse(Call<SubmitSurveyResponse> call, Response<SubmitSurveyResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getResponseCode().contentEquals("200")){
                        Toast.makeText(context,response.body().getResponseStatusMsg(),Toast.LENGTH_SHORT).show();
                        surveyCallbacks.postNxtSurveyAns();
                    }else {
                        Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<SubmitSurveyResponse> call, Throwable t) {
                Toast.makeText(context,""+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void validateNdGetSurvey() {

        String surveyId = getSurveyEdt.getText().toString().trim();
        if(!surveyId.isEmpty()){
            apiServices.getSurveyData(surveyId,appPreferences.getString(LoginPage.EMAIL_KEY,"")).enqueue(new Callback<SurveyResponse>() {
                @Override
                public void onResponse(Call<SurveyResponse> call, Response<SurveyResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body().getResponseCode().contentEquals("200")){
                            Toast.makeText(context,response.body().getResponseStatusMsg()+" data_count: "+response.body().getGetSurveyQnData().size(),Toast.LENGTH_SHORT).show();
                            surveyQnData = response.body().getGetSurveyQnData();
                            setUpDataToRecyclerVw();
                            submitSurveyDta.setVisibility(View.VISIBLE);
                            getSurveyDta.setVisibility(View.GONE);
                        }else {
                            Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<SurveyResponse> call, Throwable t) {
                    Toast.makeText(context,""+t.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(context,"Please provide Survey Id",Toast.LENGTH_SHORT).show();
        }

    }

    private void setUpDataToRecyclerVw() {
        if (surveyQnData.size() > 0){
            surveyCyclerVw = ((Activity)context).findViewById(R.id.survey_recycler_view);
            surveyCyclerVw.setHasFixedSize(true);
            surveyCyclerVw.setLayoutManager(new LinearLayoutManager(context));
            surveyCyclerVw.setItemAnimator(new DefaultItemAnimator());
            recylrAdpater = new SurveyAdapter(context,surveyQnData, surveyCallbacks);
            surveyCyclerVw.setAdapter(recylrAdpater);
        }else {
            Toast.makeText(context,"Data not found!",Toast.LENGTH_SHORT).show();
        }
    }

    public void clearSurveyOnScreen(){
        surveyQnData.clear();
        recylrAdpater.notifyDataSetChanged();
    }

}
