package com.ziploan.team.webapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ZIploan-Nitesh on 8/23/2017.
 */

public class EWS implements Serializable, Parcelable{
    @SerializedName("trigger_msg") private String trigger_msg;
    @SerializedName("trigger_name") private String trigger_name;
    @SerializedName("trigger_time") private String trigger_time;
    @SerializedName("emi") private String emi;
    @SerializedName("emi_date") private String emi_date;
    @SerializedName("amt_overdue") private String amt_overdue;

    protected EWS(Parcel in) {
        trigger_msg = in.readString();
        trigger_name = in.readString();
        trigger_time = in.readString();
        emi = in.readString();
        emi_date = in.readString();
        amt_overdue = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(trigger_msg);
        dest.writeString(trigger_name);
        dest.writeString(trigger_time);
        dest.writeString(emi);
        dest.writeString(emi_date);
        dest.writeString(amt_overdue);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EWS> CREATOR = new Creator<EWS>() {
        @Override
        public EWS createFromParcel(Parcel in) {
            return new EWS(in);
        }

        @Override
        public EWS[] newArray(int size) {
            return new EWS[size];
        }
    };

    public String getTrigger_msg() {
        return trigger_msg;
    }

    public void setTrigger_msg(String trigger_msg) {
        this.trigger_msg = trigger_msg;
    }

    public String getTrigger_name() {
        return trigger_name;
    }

    public void setTrigger_name(String trigger_name) {
        this.trigger_name = trigger_name;
    }

    public String getTrigger_time() {
        return trigger_time;
    }

    public void setTrigger_time(String trigger_time) {
        this.trigger_time = trigger_time;
    }

    public String getEmi() {
        return emi;
    }

    public void setEmi(String emi) {
        this.emi = emi;
    }

    public String getEmi_date() {
        return emi_date;
    }

    public void setEmi_date(String emi_date) {
        this.emi_date = emi_date;
    }

    public String getAmt_overdue() {
        return amt_overdue;
    }

    public void setAmt_overdue(String amt_overdue) {
        this.amt_overdue = amt_overdue;
    }
}
