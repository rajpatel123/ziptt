
package com.ziploan.team.verification_module.borrowerdetails.questions;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Val implements Parcelable {

    @SerializedName("sub_ans")
    @Expose
    private List<String> subAns = null;
    @SerializedName("sub_ques")
    @Expose
    private String subQues;

    public Val(){}

    protected Val(Parcel in) {
        subAns = in.createStringArrayList();
        subQues = in.readString();
    }

    public static final Creator<Val> CREATOR = new Creator<Val>() {
        @Override
        public Val createFromParcel(Parcel in) {
            return new Val(in);
        }

        @Override
        public Val[] newArray(int size) {
            return new Val[size];
        }
    };

    public List<String> getSubAns() {
        return subAns;
    }

    public void setSubAns(List<String> subAns) {
        this.subAns = subAns;
    }

    public String getSubQues() {
        return subQues;
    }

    public void setSubQues(String subQues) {
        this.subQues = subQues;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(subAns);
        parcel.writeString(subQues);
    }
}
