package com.ags.survey.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dictator on 3/3/2018.
 */

public class LoginRespone {

    @SerializedName("response_code")
    @Expose
    public String responseCode;

    @SerializedName("status_message")
    @Expose
    public String responseStatusMsg;

    @SerializedName("user_server_id")
    @Expose
    public String userServerId;

    @SerializedName("user_email")
    @Expose
    public String userEmail;

    @SerializedName("user_type")
    @Expose
    public String userType;

    public LoginRespone(String responseCode,String responseStatusMsg,String userServerId,String userEmail,String userType){

        this.responseCode = responseCode;
        this.responseStatusMsg = responseStatusMsg;
        this.userServerId = userServerId;
        this.userEmail = userEmail;
        this.userType = userType;

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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserServerId() {
        return userServerId;
    }

    public void setUserServerId(String userServerId) {
        this.userServerId = userServerId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
