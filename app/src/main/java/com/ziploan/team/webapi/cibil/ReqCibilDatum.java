
package com.ziploan.team.webapi.cibil;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReqCibilDatum {

    @SerializedName("cibil_live_data")
    @Expose
    private List<CibilLiveDatum> cibilLiveData = null;
    @SerializedName("primary_filter_value")
    @Expose
    private List<String> primaryFilterValue = null;
    @SerializedName("cibil_score")
    @Expose
    private Integer cibilScore;
    @SerializedName("years_of_credit_history")
    @Expose
    private Integer yearsOfCreditHistory;
    @SerializedName("emi_data")
    @Expose
    private List<EmiDatum> emiData = null;
    @SerializedName("primary_filter_status")
    @Expose
    private String primaryFilterStatus;
    @SerializedName("applicant_type")
    @Expose
    private Integer applicantType;

    public List<CibilLiveDatum> getCibilLiveData() {
        return cibilLiveData;
    }

    public void setCibilLiveData(List<CibilLiveDatum> cibilLiveData) {
        this.cibilLiveData = cibilLiveData;
    }

    public List<String> getPrimaryFilterValue() {
        return primaryFilterValue;
    }

    public void setPrimaryFilterValue(List<String> primaryFilterValue) {
        this.primaryFilterValue = primaryFilterValue;
    }

    public Integer getCibilScore() {
        return cibilScore;
    }

    public void setCibilScore(Integer cibilScore) {
        this.cibilScore = cibilScore;
    }

    public Integer getYearsOfCreditHistory() {
        return yearsOfCreditHistory;
    }

    public void setYearsOfCreditHistory(Integer yearsOfCreditHistory) {
        this.yearsOfCreditHistory = yearsOfCreditHistory;
    }

    public List<EmiDatum> getEmiData() {
        return emiData;
    }

    public void setEmiData(List<EmiDatum> emiData) {
        this.emiData = emiData;
    }

    public String getPrimaryFilterStatus() {
        return primaryFilterStatus;
    }

    public void setPrimaryFilterStatus(String primaryFilterStatus) {
        this.primaryFilterStatus = primaryFilterStatus;
    }

    public Integer getApplicantType() {
        return applicantType;
    }

    public void setApplicantType(Integer applicantType) {
        this.applicantType = applicantType;
    }

}
