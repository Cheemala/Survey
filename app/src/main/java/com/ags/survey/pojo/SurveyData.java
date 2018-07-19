package com.ags.survey.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by CheemalaCh on 3/18/2018.
 */


public class SurveyData {

    @SerializedName("survey_id")
    @Expose
    public String surveyId;
    @SerializedName("survey_title")
    @Expose
    public String surveyTitle;
    @SerializedName("survey_question")
    @Expose
    public String surveyQuestion;
    @SerializedName("survey_option_string")
    @Expose
    public String surveyOptions;

    public SurveyData(String surveyId,String surveyTitle,String surveyQuestion,String surveyOptions){
        this.surveyId =  surveyId;
        this.surveyTitle =  surveyTitle;
        this.surveyQuestion =  surveyQuestion;
        this.surveyOptions =  surveyOptions;
    }


    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getSurveyOptions() {
        return surveyOptions;
    }

    public void setSurveyQuestion(String surveyQuestion) {
        this.surveyQuestion = surveyQuestion;
    }

    public String getSurveyTitle() {
        return surveyTitle;
    }

    public void setSurveyTitle(String surveyTitle) {
        this.surveyTitle = surveyTitle;
    }

    public String getSurveyQuestion() {
        return surveyQuestion;
    }

    public void setSurveyOptions(String surveyOptions) {
        this.surveyOptions = surveyOptions;
    }
}







