
package com.ziploan.team.collection.model.app_list;

import android.text.TextUtils;

import java.io.Serializable;
import java.text.Format;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import com.google.gson.annotations.SerializedName;
import com.ziploan.team.collection.model.app_list.co_applicant.CoApplicantData;

public class Result implements Serializable {

    @SerializedName("amount_overdue")
    private String mAmountOverdue;
    @SerializedName("app_installation_status")
    private String mAppInstallationStatus;
    @SerializedName("applicant_name")
    private String mApplicantName;
    @SerializedName("business_state")
    private String mBusiness_state;
    @SerializedName("business_pincode")
    private String mBusiness_pincode;
    @SerializedName("business_city")
    private String mBusiness_city;
    @SerializedName("business_address")
    private String mBusinessAddress;
    @SerializedName("business_name")
    private String mBusinessName;
    @SerializedName("date_of_disbursement")
    private String mDateOfDisbursement;
    @SerializedName("deliquency_bucket")
    private String mDeliquencyBucket;
    @SerializedName("emi")
    private String mEmi;
    @SerializedName("ews_sum")
    private String mEwsSum;
    @SerializedName("foreclosure_amount")
    private String mForeclosureAmount;
    @SerializedName("identifier")
    private String mIdentifier;
    @SerializedName("last_visit_date")
    private String mLastVisitDate;
    @SerializedName("last_visited_coordinates")
    private List<String> mLastVisitedCoordinates;
    @SerializedName("loan_amount")
    private String mLoanAmount;
    @SerializedName("loan_application_number")
    private String mLoanApplicationNumber;
    @SerializedName("loan_tenor")
    private String mLoanTenor;
    @SerializedName("mobile_number")
    private String mMobileNumber;
    @SerializedName("months_remaining")
    private String mMonthsRemaining;
    @SerializedName("overdue_breakup")
    private List<OverdueBreakup> mOverdueBreakup;
    @SerializedName("portfolio_amount")
    private String mPortfolioAmount;
    @SerializedName("residence_address")
    private String mResidenceAddress;
    @SerializedName("sourcing_channel")
    private String mSourcingChannel;
    @SerializedName("sourcing_type")
    private String mSourcingType;
    @SerializedName("top_up_eligibility")
    private String mTopUpEligibility;
    @SerializedName("references")
    private List<Reference> mReferences;
    @SerializedName("all_applicant_data")
    private List<CoApplicantData> mCoApplicants;

    public List<Reference> getReferences() {
        return mReferences;
    }

    public List<CoApplicantData> getCoApplicants() {
        return mCoApplicants;
    }

    public void setCoApplicants(List<CoApplicantData> mCoApplicants) {
        this.mCoApplicants = mCoApplicants;
    }

    public void setReferences(List<Reference> references) {
        mReferences = references;
    }
    public String getAmountOverdue() {
        return mAmountOverdue;
    }

    public void setAmountOverdue(String amountOverdue) {
        mAmountOverdue = amountOverdue;
    }


    public String getAppInstallationStatus() {
        return mAppInstallationStatus;
    }

    public void setAppInstallationStatus(String appInstallationStatus) {
        mAppInstallationStatus = appInstallationStatus;
    }

    public String getApplicantName() {
        return mApplicantName;
    }

    public void setApplicantName(String applicantName) {
        mApplicantName = applicantName;
    }

    public String getBusinessAddress() {
        return mBusinessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        mBusinessAddress = businessAddress;
    }

    public String getBusinessName() {
        return mBusinessName;
    }

    public void setBusinessName(String businessName) {
        mBusinessName = businessName;
    }

    public String getDateOfDisbursement() {
        return mDateOfDisbursement;
    }

    public void setDateOfDisbursement(String dateOfDisbursement) {
        mDateOfDisbursement = dateOfDisbursement;
    }

    public String getDeliquencyBucket() {
        return mDeliquencyBucket;
    }

    public void setDeliquencyBucket(String deliquencyBucket) {
        mDeliquencyBucket = deliquencyBucket;
    }

    public String getEmi() {
        return mEmi;
    }

    public void setEmi(String emi) {
        mEmi = emi;
    }

    public String showemiAmount() {
        String formattedAmount = mEmi;
        Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        if (!TextUtils.isEmpty(mEmi) && !mEmi.equalsIgnoreCase("NA")) {
            formattedAmount = format.format(Double.parseDouble(mEmi));
        }
        return formattedAmount;
    }

//    public EwsCount getEwsCount() {
//        return mEwsCount;
//    }
//
//    public void setEwsCount(EwsCount ewsCount) {
//        mEwsCount = ewsCount;
//    }

    public String getEwsSum() {
        return mEwsSum;
    }

    public void setEwsSum(String ewsSum) {
        mEwsSum = ewsSum;
    }

    public String getForeclosureAmount() {
        return mForeclosureAmount;
    }

    public String showForeclosureAmount() {
        String formattedAmount = mForeclosureAmount;
        Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        if (!TextUtils.isEmpty(mForeclosureAmount) && !mForeclosureAmount.equalsIgnoreCase("NA")) {
            formattedAmount = format.format(Double.parseDouble(mForeclosureAmount));
        }
        return formattedAmount;
    }

    public void setForeclosureAmount(String foreclosureAmount) {
        mForeclosureAmount = foreclosureAmount;
    }

    public String getIdentifier() {
        return mIdentifier;
    }

    public void setIdentifier(String identifier) {
        mIdentifier = identifier;
    }

    public String getLastVisitDate() {
        return  mLastVisitDate;
    }

    public String showLastVistDate(){
        return "Last Visit : " + mLastVisitDate;
    }

    public void setLastVisitDate(String lastVisitDate) {
        mLastVisitDate = lastVisitDate;
    }

    public List<String> getLastVisitedCoordinates() {
        return mLastVisitedCoordinates;
    }

    public void setLastVisitedCoordinates(List<String> lastVisitedCoordinates) {
        mLastVisitedCoordinates = lastVisitedCoordinates;
    }

    public String getLoanAmount() {
        return mLoanAmount;
    }

    public String showLoanAmount() {
        String formattedAmount;
        Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        if (!TextUtils.isEmpty(mAmountOverdue) && !mAmountOverdue.equalsIgnoreCase("NA")) {
            formattedAmount = "Overdue : " + format.format(Double.parseDouble(mAmountOverdue));
        } else {
            formattedAmount = "Overdue : Not Available";
        }
        return formattedAmount;
    }

    public boolean getBreakupButtonisibility() {
        return mOverdueBreakup != null && mOverdueBreakup.size() > 0;
    }

    public String showActualLoanAmount() {
        String formattedAmount = mLoanAmount;
        Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        if (!TextUtils.isEmpty(mLoanAmount) && !mLoanAmount.equalsIgnoreCase("NA")) {
            formattedAmount = format.format(Double.parseDouble(mLoanAmount));
        }
        return formattedAmount;
    }

    public void setLoanAmount(String loanAmount) {
        mLoanAmount = loanAmount;
    }

    public String getLoanApplicationNumber() {
        return mLoanApplicationNumber;
    }

    public void setLoanApplicationNumber(String loanApplicationNumber) {
        mLoanApplicationNumber = loanApplicationNumber;
    }

    public String getLoanTenor() {
        return mLoanTenor;
    }

    public void setLoanTenor(String loanTenor) {
        mLoanTenor = loanTenor;
    }

    public String getMobileNumber() {
        return mMobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        mMobileNumber = mobileNumber;
    }

    public String getMonthsRemaining() {
        return mMonthsRemaining;
    }

    public void setMonthsRemaining(String monthsRemaining) {
        mMonthsRemaining = monthsRemaining;
    }

    public List<OverdueBreakup> getOverdueBreakup() {
        return mOverdueBreakup;
    }

    public void setOverdueBreakup(List<OverdueBreakup> overdueBreakup) {
        mOverdueBreakup = overdueBreakup;
    }

    public String getPortfolioAmount() {
        return mPortfolioAmount;
    }

    public void setPortfolioAmount(String portfolioAmount) {
        mPortfolioAmount = portfolioAmount;
    }

    public String showPortfolioAmount() {
        String formattedAmount = mPortfolioAmount;
        Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        if (!TextUtils.isEmpty(mPortfolioAmount) && !mPortfolioAmount.equalsIgnoreCase("NA")) {
            formattedAmount = format.format(Double.parseDouble(mPortfolioAmount));
        }
        return formattedAmount;
    }

    public String getResidenceAddress() {
        return mResidenceAddress;
    }

    public void setResidenceAddress(String residenceAddress) {
        mResidenceAddress = residenceAddress;
    }

    public String getSourcingChannel() {
        return mSourcingChannel;
    }

    public void setSourcingChannel(String sourcingChannel) {
        mSourcingChannel = sourcingChannel;
    }

    public String getSourcingType() {
        return mSourcingType;
    }

    public void setSourcingType(String sourcingType) {
        mSourcingType = sourcingType;
    }

    public String getTopUpEligibility() {
        return mTopUpEligibility;
    }

    public void setTopUpEligibility(String topUpEligibility) {
        mTopUpEligibility = topUpEligibility;
    }

    public String getmBusiness_state() {
        return mBusiness_state;
    }

    public void setmBusiness_state(String mBusiness_state) {
        this.mBusiness_state = mBusiness_state;
    }

    public String getmBusiness_pincode() {
        return mBusiness_pincode;
    }

    public void setmBusiness_pincode(String mBusiness_pincode) {
        this.mBusiness_pincode = mBusiness_pincode;
    }

    public String getmBusiness_city() {
        return mBusiness_city;
    }

    public String getCityState(){
        return mBusiness_city + ", " + mBusiness_state + "-" + mBusiness_pincode;
    }

    public void setmBusiness_city(String mBusiness_city) {
        this.mBusiness_city = mBusiness_city;
    }

}
