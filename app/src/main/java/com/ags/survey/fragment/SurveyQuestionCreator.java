package com.ags.survey.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ags.survey.R;
import com.ags.survey.networkapi.APIServices;
import com.ags.survey.networkapi.APIUtils;
import com.ags.survey.pojo.NewSurveyResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dictator on 2/13/2018.
 */

public class SurveyQuestionCreator extends Fragment {

    private LinearLayout optionLayout;
    private ImageButton addOptionBtn;
    private Button addQuestionBtn;
    private EditText surveyIdEdt,questionTxtEdt;
    private LayoutInflater inflater;
    private StringBuilder optionBuilder;
    private APIServices surveyCreatorService;
    private Context context;


    public SurveyQuestionCreator(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public SurveyQuestionCreator(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        surveyCreatorService = APIUtils.getAPIService();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View questionCreatorView = inflater.inflate(R.layout.survey_question_creator,container,false);
        surveyIdEdt = (EditText) questionCreatorView.findViewById(R.id.survey_id_edt);
        questionTxtEdt = (EditText) questionCreatorView.findViewById(R.id.question_txt_edt);
        optionLayout = questionCreatorView.findViewById(R.id.option_layout);
        addOptionBtn = questionCreatorView.findViewById(R.id.add_option_btn);
        addQuestionBtn = (Button) questionCreatorView.findViewById(R.id.add_question_btn);
        addQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateNdAddQuestion();
            }
        });
        addOptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View newOptionLayout = inflater.inflate(R.layout.options_layout,null);
                optionLayout.addView(newOptionLayout);
            }
        });
        return questionCreatorView;
    }

    private void validateNdAddQuestion() {
        String surveyIdVal = surveyIdEdt.getText().toString().trim();
        String questionTxtVal = questionTxtEdt.getText().toString().trim();

        if(!surveyIdVal.isEmpty()){
            if (!questionTxtVal.isEmpty()){
                if(optionLayout.getChildCount() > 0){
                    optionBuilder = new StringBuilder();
                    for (int i = 0; i < optionLayout.getChildCount(); i++){
                        LinearLayout optionEdtLyout = (LinearLayout) optionLayout.getChildAt(i);
                        EditText optionEdt = (EditText) optionEdtLyout.findViewById(R.id.option_edt);
                        if (optionLayout.getChildCount() != (i+1)){
                            optionBuilder.append(optionEdt.getText().toString().trim()+";");
                        }else {
                            optionBuilder.append(optionEdt.getText().toString().trim());
                        }
                    }
                    postQuestionToServer(surveyIdVal,questionTxtVal,optionBuilder.toString());
                    Log.d("option_string_: ",optionBuilder.toString());
                }else {
                    Toast.makeText(context,"Please provide options!",Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(context,"Please provide Question!",Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(context,"Please provide SURVEY ID!",Toast.LENGTH_LONG).show();
        }

    }

    private void postQuestionToServer(String surveyIdVal, String questionTxtVal, String optionDumpString) {
        surveyCreatorService.postSurveyQuestion(surveyIdVal,questionTxtVal,optionDumpString).enqueue(new Callback<NewSurveyResponse>() {
            @Override
            public void onResponse(Call<NewSurveyResponse> call, Response<NewSurveyResponse> response) {
                if (response.isSuccessful()){

                    if (response.body().getResponseCode().contentEquals("200")){
                        Toast.makeText(context,""+response.body().getResponseStatusMsg(),Toast.LENGTH_SHORT).show();
                        surveyIdEdt.setText("");
                        questionTxtEdt.setText("");
                        optionLayout.removeAllViews();
                    }else if(response.body().getResponseCode().contentEquals("400")){
                        Toast.makeText(context,""+response.body().getResponseStatusMsg(),Toast.LENGTH_SHORT).show();
                    }else {}

                }else {
                    Toast.makeText(context,"something went wrong!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewSurveyResponse> call, Throwable t) {
                Toast.makeText(context,""+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


}
