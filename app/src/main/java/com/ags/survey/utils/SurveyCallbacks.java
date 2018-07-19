package com.ags.survey.utils;

/**
 * Created by CheemalaCh on 3/20/2018.
 */

public interface SurveyCallbacks {
    public void saveAnswer(String surveyId,String questionNumFlag,String questionAnsFlag);
    public void postNxtSurveyAns();
}
