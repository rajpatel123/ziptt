
package com.ziploan.team.asset_module.ews.res;

import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("feature")
    private String mFeature;
    @SerializedName("loan_application_number")
    private String mLoanApplicationNumber;
    @SerializedName("score")
    private String mScore;
    @SerializedName("value")
    private String mValue;

    public String getFeature() {
        return mFeature;
    }

    public void setFeature(String feature) {
        mFeature = feature;
    }

    public String getLoanApplicationNumber() {
        return mLoanApplicationNumber;
    }

    public void setLoanApplicationNumber(String loanApplicationNumber) {
        mLoanApplicationNumber = loanApplicationNumber;
    }

    public String getScore() {
        return mScore;
    }

    public void setScore(String score) {
        mScore = score;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }

}
