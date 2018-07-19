package com.ags.survey.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by CheemalaCh on 3/20/2018.
 */

public class SubmitSurveyResponse {

    @SerializedName("response_code")
    @Expose
    public String responseCode;

    @SerializedName("status_message")
    @Expose
    public String responseStatusMsg;


    public SubmitSurveyResponse(String  responseCode,String responseStatusMsg){
        this.responseCode = responseCode;
        this.responseStatusMsg = responseStatusMsg;
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
}
