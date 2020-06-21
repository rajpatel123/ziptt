package com.ziploan.team.asset_module;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.ziploan.team.webapi.EWS;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ZIploan-Nitesh on 8/23/2017.
 */

public class EWSDisplay implements Serializable, Parcelable{
    @SerializedName("triggers") ArrayList<EWS> triggers;
    @SerializedName("emi") private String emi;
    @SerializedName("emi_date") private String emi_date;
    @SerializedName("amt_overdue") private String amt_overdue;

    public EWSDisplay() {
    }

    protected EWSDisplay(Parcel in) {
        triggers = in.createTypedArrayList(EWS.CREATOR);
        emi = in.readString();
        emi_date = in.readString();
        amt_overdue = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(triggers);
        dest.writeString(emi);
        dest.writeString(emi_date);
        dest.writeString(amt_overdue);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EWSDisplay> CREATOR = new Creator<EWSDisplay>() {
        @Override
        public EWSDisplay createFromParcel(Parcel in) {
            return new EWSDisplay(in);
        }

        @Override
        public EWSDisplay[] newArray(int size) {
            return new EWSDisplay[size];
        }
    };

    public ArrayList<EWS> getTriggers() {
        return triggers;
    }

    public void setTriggers(ArrayList<EWS> triggers) {
        this.triggers = triggers;
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
