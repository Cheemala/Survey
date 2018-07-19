package com.ags.survey.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by CheemalaCh on 3/14/2018.
 */

public class ErrorResponse {

    @Expose
    @SerializedName("response_code")
    public String response_code;

    @Expose
    @SerializedName("status_message")
    public String responseErrorMsg;

    public String getResponseCode() {
        return response_code;
    }

    public String getResponseError() {
        return responseErrorMsg;
    }
}
