package com.ags.survey.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by CheemalaCh on 3/11/2018.
 */

public class NewSurveyResponse {

    @SerializedName("response_code")
    @Expose
    public String responseCode;

    @SerializedName("status_message")
    @Expose
    public String responseStatusMsg;

    @SerializedName("response_flag")
    @Expose
    public String responseFlag;


    public NewSurveyResponse(String responseCode,String responseStatusMsg){
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

    public String getResponseFlag() {
        return responseFlag;
    }

    public void setResponseFlag(String responseFlag) {
        this.responseFlag = responseFlag;
    }

}
