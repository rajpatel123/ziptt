package com.ziploan.team.verification_module.borrowerdetails;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ZIploan-Nitesh on 2/9/2017.
 */
public class ZiploanQuestion implements Serializable, Parcelable{
    @SerializedName("question") private String question;
    @SerializedName("answer") private String answer;

    public ZiploanQuestion() {
    }

    public ZiploanQuestion(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }


    protected ZiploanQuestion(Parcel in) {
        question = in.readString();
        answer = in.readString();
    }

    public static final Creator<ZiploanQuestion> CREATOR = new Creator<ZiploanQuestion>() {
        @Override
        public ZiploanQuestion createFromParcel(Parcel in) {
            return new ZiploanQuestion(in);
        }

        @Override
        public ZiploanQuestion[] newArray(int size) {
            return new ZiploanQuestion[size];
        }
    };

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(question);
        parcel.writeString(answer);
    }
}
