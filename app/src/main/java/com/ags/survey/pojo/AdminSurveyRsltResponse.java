package com.ags.survey.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by CheemalaCh on 3/25/2018.
 */

public class AdminSurveyRsltResponse {

    @SerializedName("response_code")
    @Expose
    public String responseCode;

    @SerializedName("status_message")
    @Expose
    public String responseStatusMsg;

    @SerializedName("survey_data")
    @Expose
    public ArrayList<AdminSurveyData> adminSurveyData;

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

    public ArrayList<AdminSurveyData> getAdminSurveyData() {
        return adminSurveyData;
    }

    public void setAdminSurveyData(ArrayList<AdminSurveyData> adminSurveyData) {
        this.adminSurveyData = adminSurveyData;
    }

}
