package com.ags.survey.pojo;

/**
 * Created by CheemalaCh on 3/20/2018.
 */

public class UserAnswerDump {

    private String questionNum;
    private String questionAnsNum;
    private String surveyId;
    private boolean postStatus;

    public UserAnswerDump(String questionNum,String questionAnsNum,String surveyId,boolean postStatus){
        this.questionNum = questionNum;
        this.questionAnsNum = questionAnsNum;
        this.surveyId = surveyId;
        this.postStatus = postStatus;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getQuestionAnsNum() {
        return questionAnsNum;
    }

    public void setQuestionAnsNum(String questionAnsNum) {
        this.questionAnsNum = questionAnsNum;
    }

    public String getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(String questionNum) {
        this.questionNum = questionNum;
    }

    public boolean isAnsPosted() {
        return postStatus;
    }

    public void setAnsPosted(boolean postStatus) {
        this.postStatus = postStatus;
    }
}
