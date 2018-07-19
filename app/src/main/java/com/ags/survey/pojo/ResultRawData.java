package com.ags.survey.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by CheemalaCh on 5/6/2018.
 */

public class ResultRawData implements Parcelable {

    @SerializedName("question_text")
    @Expose
    public String questionTxt;

    @SerializedName("option_data")
    @Expose
    public ArrayList<ResultOptionRawDta> optionDta;


    public ResultRawData(String questionTxt,ArrayList<ResultOptionRawDta> optionDta){
        this.questionTxt = questionTxt;
        this.optionDta = optionDta;
    }

    protected ResultRawData(Parcel in) {
        questionTxt = in.readString();
    }

    public static final Creator<ResultRawData> CREATOR = new Creator<ResultRawData>() {
        @Override
        public ResultRawData createFromParcel(Parcel in) {
            return new ResultRawData(in);
        }

        @Override
        public ResultRawData[] newArray(int size) {
            return new ResultRawData[size];
        }
    };

    public String getQuestionTxt() {
        return questionTxt;
    }

    public void setQuestionTxt(String questionTxt) {
        this.questionTxt = questionTxt;
    }

    public ArrayList<ResultOptionRawDta> getOptionDta() {
        return optionDta;
    }

    public void setOptionDta(ArrayList<ResultOptionRawDta> optionDta) {
        this.optionDta = optionDta;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(questionTxt);
    }
}
