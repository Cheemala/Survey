package com.ags.survey.networkapi;

import com.ags.survey.pojo.AdminSurveyRsltResponse;
import com.ags.survey.pojo.LoginRespone;
import com.ags.survey.pojo.NewSurveyResponse;
import com.ags.survey.pojo.RegistrationResponse;
import com.ags.survey.pojo.SubmitSurveyResponse;
import com.ags.survey.pojo.SurveyResponse;
import com.ags.survey.pojo.SurveyResult;
import com.ags.survey.pojo.SurveyResultReleaseResponse;
import com.ags.survey.pojo.UpdtUserSrvyStsResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Cheemala on 12/9/2017.
 */

public interface APIServices {

    @POST("registerAUser")
    @FormUrlEncoded
    Call<RegistrationResponse> registerAUser(@Field("USER_EMAIL") String userEmail, @Field("USER_PASSWORD") String userPassword,@Field("USER_TYPE") String userType);

    @POST("chkLogin")
    @FormUrlEncoded
    Call<LoginRespone> checkLogin(@Field("USER_EMAIL") String userEmail, @Field("USER_PASSWORD") String userPassword);

    @POST("createNewSurvey")
    @FormUrlEncoded
    Call<NewSurveyResponse> createNewSurvey(@Field("ADMIN_EMAIL") String userEmail, @Field("SURVEY_ID") String surveyId, @Field("SURVEY_TITLE") String surveyTitle);

    @POST("postSurveyQn")
    @FormUrlEncoded
    Call<NewSurveyResponse> postSurveyQuestion(@Field("SURVEY_ID") String surveyId, @Field("SURVEY_QUESTION") String surveyQsn, @Field("SURVEY_OPTIONS") String surveyOptions);

    @GET("getSurveyData")
    Call<SurveyResponse> getSurveyData(@Query("SURVEY_ID") String surveyId,@Query("USER_EMAIL") String userMaild);

    @POST("updtUsrSurvySts")
    @FormUrlEncoded
    Call<UpdtUserSrvyStsResponse> updtUsrSurvySts(@Field("SURVEY_ID") String surveyId, @Field("USER_EMAIL") String userMaild);

    @POST("postUserSurvey")
    @FormUrlEncoded
    Call<SubmitSurveyResponse> postUserSurvey(@Field("SURVEY_ID")String surveyId,@Field("SURVEY_QN_NUM")String surveyQnNum,@Field("SURVEY_ANS_NUM")String surveyAnsNum);

    @GET("getPostedSurveys")
    Call<AdminSurveyRsltResponse> getAdminSurveys(@Query("ADMIN_EMAIL") String adminEmail);

    @GET("getUserSurveys")
    Call<AdminSurveyRsltResponse> getUserSurveys(@Query("USER_EMAIL") String userEmail);

    @POST("updtSrvyRstSts")
    @FormUrlEncoded
    Call<SurveyResultReleaseResponse> releaseSurveyResult(@Field("ADMIN_EMAIL") String userMaild, @Field("SURVEY_ID") String surveyId);

    @GET("getSurveyResult")
    Call<SurveyResult> getSurveyResult(@Query("SURVEY_ID") String surveyId);

}
