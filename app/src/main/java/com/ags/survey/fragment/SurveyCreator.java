package com.ags.survey.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.ags.survey.activity.LoginPage;
import com.ags.survey.R;
import com.ags.survey.networkapi.APIServices;
import com.ags.survey.networkapi.APIUtils;
import com.ags.survey.pojo.NewSurveyResponse;
import java.util.Calendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Created by Dictator on 2/13/2018.
 */

@SuppressLint("ValidFragment")
public class SurveyCreator extends Fragment {

    private TextInputEditText surveyIdEdt,surveyTitlEdt;
    private EditText surveyEndDate,surveyEndTime;
    RelativeLayout createBtnLayout;
    private TextView sbmitBtnTxt;
    private APIServices surveyCreatorService;
    private SharedPreferences loginPreferences;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Context context;

    public SurveyCreator(Context context){
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        surveyCreatorService = APIUtils.getAPIService();
        loginPreferences = context.getSharedPreferences(LoginPage.LOGIN_PRFS_NAME,Context.MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View surveyCreatorLayout = inflater.inflate(R.layout.survey_creator,container,false);
        surveyIdEdt = surveyCreatorLayout.findViewById(R.id.survey_id_edt);
        surveyTitlEdt = surveyCreatorLayout.findViewById(R.id.survey_titl_edt);
        createBtnLayout = (RelativeLayout) surveyCreatorLayout.findViewById(R.id.create_btn_layout);
        createBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateNdCreateSurvey();
            }
        });
        sbmitBtnTxt = (TextView) surveyCreatorLayout.findViewById(R.id.sbmit_btn_txt);
        sbmitBtnTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateNdCreateSurvey();
            }
        });
        surveyEndDate = (EditText) surveyCreatorLayout.findViewById(R.id.end_date);
        surveyEndTime = (EditText) surveyCreatorLayout.findViewById(R.id.end_time);
        surveyEndDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                    DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                    surveyEndDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();

                }
            }
        });

        surveyEndTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                surveyEndTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }

        });
        return surveyCreatorLayout;

    }

    private void validateNdCreateSurvey() {
        String surveyIdVal = surveyIdEdt.getText().toString().trim();
        String surveyTitlVal = surveyTitlEdt.getText().toString().trim();

        if (!surveyIdVal.isEmpty()){
            if (!surveyTitlVal.isEmpty()){
                if (loginPreferences.contains(LoginPage.EMAIL_KEY)) {
                    Toast.makeText(context,"mail: "+loginPreferences.getString(LoginPage.EMAIL_KEY,""),Toast.LENGTH_SHORT).show();
                    surveyCreatorService.createNewSurvey(loginPreferences.getString(LoginPage.EMAIL_KEY,""), surveyIdVal, surveyTitlVal).enqueue(new Callback<NewSurveyResponse>() {
                        @Override
                        public void onResponse(Call<NewSurveyResponse> call, Response<NewSurveyResponse> response) {

                            if (response.isSuccessful()){

                                if (response.body().getResponseCode().contentEquals("200")){
                                    Toast.makeText(context,""+response.body().getResponseStatusMsg(),Toast.LENGTH_SHORT).show();
                                    surveyIdEdt.setText("");
                                    surveyTitlEdt.setText("");
                                }else if(response.body().getResponseCode().contentEquals("400")){
                                    Toast.makeText(context,""+response.body().getResponseStatusMsg(),Toast.LENGTH_SHORT).show();
                                }else {}

                            }else {
                                Toast.makeText(context,"something went wrong!",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<NewSurveyResponse> call, Throwable t) {
                            Toast.makeText(context,"error: "+t.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(context,"Please re-login and try again!",Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(context,"Please enter Survey Title!",Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(context,"Please enter Survey ID!",Toast.LENGTH_LONG).show();
        }
    }


}
