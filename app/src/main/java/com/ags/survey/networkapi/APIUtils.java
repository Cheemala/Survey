package com.ags.survey.networkapi;

/**
 * Created by Cheemala on 12/9/2017.
 */

public class APIUtils{

    private APIUtils() {}

    public static final String BASE_URL = "http://192.168.43.3:8080/Survey/rest/surveyservices/";

    public static APIServices getAPIService()
    {
        return RetrofitClient.getClient(BASE_URL).create(APIServices.class);
    }

}
