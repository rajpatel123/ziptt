
package com.ziploan.team.collection.model.app_list;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

public class OverdueBreakup implements Serializable {

    @SerializedName("bouncing_component_overdue")
    private String mBouncingComponentOverdue;
    @SerializedName("due_date")
    private String mDueDate;
    @SerializedName("instalment_amount")
    private String mInstalmentAmount;
    @SerializedName("interest_component_overdue")
    private String mInterestComponentOverdue;
    @SerializedName("overdue_charges")
    private String mOverdueCharges;
    @SerializedName("principal_component_overdue")
    private String mPrincipalComponentOverdue;
    @SerializedName("total_emi")
    private String mTotalEmi;
    @SerializedName("penal_charges")
    private String mPenelCharges;


    public String getmPenelCharges() {
        return mPenelCharges;
    }

    public void setmPenelCharges(String mPenelCharges) {
        this.mPenelCharges = mPenelCharges;
    }

    public String getBouncingComponentOverdue() {
        return mBouncingComponentOverdue;
    }

    public void setBouncingComponentOverdue(String bouncingComponentOverdue) {
        mBouncingComponentOverdue = bouncingComponentOverdue;
    }

    public String showBouncingCharges() {
        String formattedAmount = mBouncingComponentOverdue;
        Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        if (!TextUtils.isEmpty(mBouncingComponentOverdue) && !mBouncingComponentOverdue.equalsIgnoreCase("NA")) {
            formattedAmount = format.format(Double.parseDouble(mBouncingComponentOverdue));
        }
        return formattedAmount;
    }

    public String getDueDate() {
        return mDueDate;
    }

    public void setDueDate(String dueDate) {
        mDueDate = dueDate;
    }

    public String getInstalmentAmount() {
        return mInstalmentAmount;
    }

    public void setInstalmentAmount(String instalmentAmount) {
        mInstalmentAmount = instalmentAmount;
    }

    public String showInstalmentAmount() {
        String formattedAmount = mInstalmentAmount;
        Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        if (!TextUtils.isEmpty(mInstalmentAmount) && !mInstalmentAmount.equalsIgnoreCase("NA")) {
            formattedAmount = format.format(Double.parseDouble(mInstalmentAmount));
        }
        return formattedAmount;
    }

    public String getInterestComponentOverdue() {
        return mInterestComponentOverdue;
    }

    public void setInterestComponentOverdue(String interestComponentOverdue) {
        mInterestComponentOverdue = interestComponentOverdue;
    }

    public String showInterestAmount() {
        String formattedAmount = mInterestComponentOverdue;
        Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        if (!TextUtils.isEmpty(mInterestComponentOverdue) && !mInterestComponentOverdue.equalsIgnoreCase("NA")) {
            formattedAmount = format.format(Double.parseDouble(mInterestComponentOverdue));
        }
        return formattedAmount;
    }

    public String getOverdueCharges() {
        return mOverdueCharges;
    }

    public void setOverdueCharges(String overdueCharges) {
        mOverdueCharges = overdueCharges;
    }

    public String showOverdueCharges() {
        String formattedAmount = mOverdueCharges;
        Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        if (!TextUtils.isEmpty(mOverdueCharges) && !mOverdueCharges.equalsIgnoreCase("NA")) {
            formattedAmount = format.format(Double.parseDouble(mOverdueCharges));
        }
        return formattedAmount;
    }

    public String getPrincipalComponentOverdue() {
        return mPrincipalComponentOverdue;
    }

    public void setPrincipalComponentOverdue(String principalComponentOverdue) {
        mPrincipalComponentOverdue = principalComponentOverdue;
    }

    public String showPrincipalCharges() {
        String formattedAmount = mPrincipalComponentOverdue;
        Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        if (!TextUtils.isEmpty(mPrincipalComponentOverdue) && !mPrincipalComponentOverdue.equalsIgnoreCase("NA")) {
            formattedAmount = format.format(Double.parseDouble(mPrincipalComponentOverdue));
        }
        return formattedAmount;
    }


    public String getTotalEmi() {
        return mTotalEmi;
    }



    public void setTotalEmi(String totalEmi) {
        mTotalEmi = totalEmi;
    }

    public String showtotalEmi() {
        String formattedAmount = mTotalEmi;
        Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        if (!TextUtils.isEmpty(mTotalEmi) && !mTotalEmi.equalsIgnoreCase("NA")) {
            formattedAmount = format.format(Double.parseDouble(mTotalEmi));
        }
        return formattedAmount;
    }


    public String getBreakupEmiText() {
        return "Breakup of Due Date " + mDueDate;
    }

    @Override
    public String toString() {
        return "EMI Pending Due Date : " + mDueDate;
    }

    public String showPenelCharges() {
        String formattedAmount = getmPenelCharges();
        if (!TextUtils.isEmpty(mPenelCharges) && !mPenelCharges.equalsIgnoreCase("NA")) {
            Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
            formattedAmount = format.format(Double.parseDouble(mPenelCharges));
        }
        return formattedAmount;
    }
}
