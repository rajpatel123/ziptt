package com.ziploan.team.verification_module.borrowerdetails;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.ziploan.team.verification_module.borrowerdetails.questions.An;

public class ZiploanNewQuestion implements Parcelable {
    @SerializedName("question") private String question;
    @SerializedName("answer") private An answer;


    protected ZiploanNewQuestion(Parcel in) {
        question = in.readString();
        answer = in.readParcelable(An.class.getClassLoader());
    }

    public ZiploanNewQuestion(String que, An an){
        question = que;
        answer = an;
    }

    public static final Creator<ZiploanNewQuestion> CREATOR = new Creator<ZiploanNewQuestion>() {
        @Override
        public ZiploanNewQuestion createFromParcel(Parcel in) {
            return new ZiploanNewQuestion(in);
        }

        @Override
        public ZiploanNewQuestion[] newArray(int size) {
            return new ZiploanNewQuestion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getQuestion() {
        return question;
    }

    public An getAnswer() {
        return answer;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(question);
        parcel.writeParcelable(answer, i);
    }
}
