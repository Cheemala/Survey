package com.ags.survey.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by CheemalaCh on 3/25/2018.
 */

public class AdminSurveyData {

    @Expose
    @SerializedName("survey_id")
    String surveyId;

    @Expose
    @SerializedName("survey_title")
    String surveyTitle;

    @Expose
    @SerializedName("survey_status")
    String surveyStatus;

    public AdminSurveyData(String surveyId,String surveyTitle,String surveyStatus){
        this.surveyId = surveyId;
        this.surveyTitle = surveyTitle;
        this.surveyStatus = surveyStatus;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getSurveyTitle() {
        return surveyTitle;
    }

    public void setSurveyTitle(String surveyTitle) {
        this.surveyTitle = surveyTitle;
    }

    public String getSurveyStatus() {
        return surveyStatus;
    }

    public void setSurveyStatus(String surveyStatus) {
        this.surveyStatus = surveyStatus;
    }

}
