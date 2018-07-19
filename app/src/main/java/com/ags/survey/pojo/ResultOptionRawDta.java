package com.ags.survey.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by CheemalaCh on 5/6/2018.
 */

public class ResultOptionRawDta implements Parcelable{

    @SerializedName("option_text")
    @Expose
    public String optionTxt;

    @SerializedName("option_percent")
    @Expose
    public String optionPercentage;

    public ResultOptionRawDta(String optionTxt,String optionPercentage){
        this.optionTxt = optionTxt;
        this.optionPercentage = optionPercentage;
    }


    protected ResultOptionRawDta(Parcel in) {
        optionTxt = in.readString();
        optionPercentage = in.readString();
    }

    public static final Creator<ResultOptionRawDta> CREATOR = new Creator<ResultOptionRawDta>() {
        @Override
        public ResultOptionRawDta createFromParcel(Parcel in) {
            return new ResultOptionRawDta(in);
        }

        @Override
        public ResultOptionRawDta[] newArray(int size) {
            return new ResultOptionRawDta[size];
        }
    };

    public String getOptionTxt() {
        return optionTxt;
    }

    public void setOptionTxt(String optionTxt) {
        this.optionTxt = optionTxt;
    }

    public String getOptionPercentage() {
        return optionPercentage;
    }

    public void setOptionPercentage(String optionPercentage) {
        this.optionPercentage = optionPercentage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(optionTxt);
        dest.writeString(optionPercentage);
    }
}
