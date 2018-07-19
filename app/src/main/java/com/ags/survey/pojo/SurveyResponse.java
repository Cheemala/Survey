package com.ags.survey.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by CheemalaCh on 3/18/2018.
 */

public class SurveyResponse {
    @SerializedName("response_code")
    @Expose
    public String responseCode;

    @SerializedName("status_message")
    @Expose
    public String responseStatusMsg;

    @SerializedName("survey_data")
    @Expose
    public ArrayList<SurveyData> getSurveyQnData;

    public SurveyResponse(String responseCode,String responseStatusMsg,ArrayList<SurveyData> getSurveyQnData){
        this.responseCode = responseCode;
        this.responseStatusMsg = responseStatusMsg;
        this.getSurveyQnData = getSurveyQnData;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseStatusMsg() {
        return responseStatusMsg;
    }

    public void setResponseStatusMsg(String responseStatusMsg) {
        this.responseStatusMsg = responseStatusMsg;
    }

    public ArrayList<SurveyData> getGetSurveyQnData() {
        return getSurveyQnData;
    }

    public void setGetSurveyQnData(ArrayList<SurveyData> getSurveyQnData) {
        this.getSurveyQnData = getSurveyQnData;
    }
}
