package com.ziploan.team.webapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZIploan-Nitesh on 10/16/2017.
 */

public class OverdueBreakup implements Parcelable{
    @SerializedName("due_date") private String due_date;
    @SerializedName("interest_component_overdue") private String interest_component_overdue     ;
    @SerializedName("principal_component_overdue") private String principal_component_overdue;
    @SerializedName("bouncing_component_overdue") private String bouncing_component_overdue;
    @SerializedName("total_emi") private String total_emi;
    @SerializedName("overdue_charges") private String overdue_charges;

    protected OverdueBreakup(Parcel in) {
        due_date = in.readString();
        interest_component_overdue = in.readString();
        principal_component_overdue = in.readString();
        bouncing_component_overdue = in.readString();
        total_emi = in.readString();
        overdue_charges = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(due_date);
        dest.writeString(interest_component_overdue);
        dest.writeString(principal_component_overdue);
        dest.writeString(bouncing_component_overdue);
        dest.writeString(total_emi);
        dest.writeString(overdue_charges);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OverdueBreakup> CREATOR = new Creator<OverdueBreakup>() {
        @Override
        public OverdueBreakup createFromParcel(Parcel in) {
            return new OverdueBreakup(in);
        }

        @Override
        public OverdueBreakup[] newArray(int size) {
            return new OverdueBreakup[size];
        }
    };

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getInterest_component_overdue() {
        return interest_component_overdue;
    }

    public void setInterest_component_overdue(String interest_component_overdue) {
        this.interest_component_overdue = interest_component_overdue;
    }

    public String getPrincipal_component_overdue() {
        return principal_component_overdue;
    }

    public void setPrincipal_component_overdue(String principal_component_overdue) {
        this.principal_component_overdue = principal_component_overdue;
    }

    public String getBouncing_component_overdue() {
        return bouncing_component_overdue;
    }

    public void setBouncing_component_overdue(String bouncing_component_overdue) {
        this.bouncing_component_overdue = bouncing_component_overdue;
    }

    public String getTotal_emi() {
        return total_emi;
    }

    public void setTotal_emi(String total_emi) {
        this.total_emi = total_emi;
    }

    public String getOverdue_charges() {
        return overdue_charges;
    }

    public void setOverdue_charges(String overdue_charges) {
        this.overdue_charges = overdue_charges;
    }

    public String fetchDuedate(){
        return due_date;
    }
    public String fetchTotalEMI(){
        return "₹ "+total_emi;
    }
    public String fetchOverdueCharges(){
        return "₹ "+overdue_charges;
    }
    public String fetchInterestComponentOverdue(){
        return "₹ "+interest_component_overdue;
    }
    public String fetchPrincipleComponentOverdue(){
        return "₹ "+principal_component_overdue;
    }
    public String fetchBouncingComponentOverdue(){
        return "₹ "+bouncing_component_overdue;
    }
}
