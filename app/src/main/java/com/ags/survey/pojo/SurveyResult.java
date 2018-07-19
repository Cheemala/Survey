package com.ags.survey.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by CheemalaCh on 5/6/2018.
 */

public class SurveyResult {

    @SerializedName("response_code")
    @Expose
    public String responseCode;

    @SerializedName("status_message")
    @Expose
    public String responseStatusMsg;

    @SerializedName("survey_result_data")
    @Expose
    public ArrayList<ResultRawData> resultData;



    public SurveyResult(String responseCode,String responseStatusMsg,ArrayList<ResultRawData> resultData){
        this.responseCode = responseCode;
        this.responseStatusMsg = responseStatusMsg;
        this.resultData = resultData;
    }

    public ArrayList<ResultRawData> getResultData() {
        return resultData;
    }

    public void setResultData(ArrayList<ResultRawData> resultData) {
        this.resultData = resultData;
    }

    public String getResponseStatusMsg() {
        return responseStatusMsg;
    }

    public void setResponseStatusMsg(String responseStatusMsg) {
        this.responseStatusMsg = responseStatusMsg;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

}
