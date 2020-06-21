package com.ziploan.team.webapi;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ZIploan-Nitesh on 8/21/2017.
 */

public class Loan implements Serializable, Parcelable {
    @SerializedName("last_visit_date")
    private String last_visit_date;
    @SerializedName("ews_count")
    private HashMap<String, ArrayList<EWS>> ews_count;
    @SerializedName("portfolio_amount")
    private String portfolio_amount;
    @SerializedName("top_up_eligibility")
    private String top_up_eligibility;
    @SerializedName("emi")
    private String emi;
    @SerializedName("deliquency_bucket")
    private String deliquency_bucket;
    @SerializedName("foreclosure_amount")
    private String foreclosure_amount;
    @SerializedName("business_address")
    private String business_address;
    @SerializedName("loan_amount")
    private String loan_amount;
    @SerializedName("amount_overdue")
    private String amount_overdue;
    @SerializedName("loan_application_number")
    private String loan_application_number;
    @SerializedName("applicant_name")
    private String applicant_name;
    @SerializedName("mobile_number")
    private String mobile_number;
    @SerializedName("residence_address")
    private String residence_address;
    @SerializedName("business_name")
    private String business_name;
    @SerializedName("identifier")
    private String identifier;
    @SerializedName("sourcing_type")
    private String sourcing_type;
    @SerializedName("sourcing_channel")
    private String sourcing_channel;
    @SerializedName("months_remaining")
    private String months_remaining;
    @SerializedName("app_uninstallation_reason")
    private String app_uninstallation_reason;
    @SerializedName("app_installation_status")
    private String app_installation_status;
    @SerializedName("loan_tenor")
    private String loan_tenor;
    @SerializedName("ews_sum")
    private int ews_sum;
    @SerializedName("date_of_disbursement")
    private String date_of_disbursement;
    @SerializedName("overdue_breakup")
    private ArrayList<OverdueBreakup> overdue_breakup;
    @SerializedName("last_visited_coordinates")
    private ArrayList<String> last_visited_coordinates;
    @SerializedName("applicant_type")
    private String mApplicantType;

    public Loan() {

    }

    protected Loan(Parcel in) {
        last_visit_date = in.readString();
        ews_count = (HashMap<String, ArrayList<EWS>>) in.readSerializable();
        portfolio_amount = in.readString();
        top_up_eligibility = in.readString();
        emi = in.readString();
        deliquency_bucket = in.readString();
        foreclosure_amount = in.readString();
        business_address = in.readString();
        loan_amount = in.readString();
        amount_overdue = in.readString();
        loan_application_number = in.readString();
        applicant_name = in.readString();
        mobile_number = in.readString();
        residence_address = in.readString();
        business_name = in.readString();
        identifier = in.readString();
        sourcing_type = in.readString();
        sourcing_channel = in.readString();
        months_remaining = in.readString();
        app_uninstallation_reason = in.readString();
        app_installation_status = in.readString();
        loan_tenor = in.readString();
        ews_sum = in.readInt();
        date_of_disbursement = in.readString();
        overdue_breakup = in.createTypedArrayList(OverdueBreakup.CREATOR);
        last_visited_coordinates = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(last_visit_date);
        dest.writeSerializable(ews_count);
        dest.writeString(portfolio_amount);
        dest.writeString(top_up_eligibility);
        dest.writeString(emi);
        dest.writeString(deliquency_bucket);
        dest.writeString(foreclosure_amount);
        dest.writeString(business_address);
        dest.writeString(loan_amount);
        dest.writeString(amount_overdue);
        dest.writeString(loan_application_number);
        dest.writeString(applicant_name);
        dest.writeString(mobile_number);
        dest.writeString(residence_address);
        dest.writeString(business_name);
        dest.writeString(identifier);
        dest.writeString(sourcing_type);
        dest.writeString(sourcing_channel);
        dest.writeString(months_remaining);
        dest.writeString(app_uninstallation_reason);
        dest.writeString(app_installation_status);
        dest.writeString(loan_tenor);
        dest.writeInt(ews_sum);
        dest.writeString(date_of_disbursement);
        dest.writeTypedList(overdue_breakup);
        dest.writeStringList(last_visited_coordinates);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Loan> CREATOR = new Creator<Loan>() {
        @Override
        public Loan createFromParcel(Parcel in) {
            return new Loan(in);
        }

        @Override
        public Loan[] newArray(int size) {
            return new Loan[size];
        }
    };

    public String getMonths_remaining() {
        return months_remaining;
    }

    public String fetchMonthRemaining() {
        return this.getMonths_remaining() + "/" + this.getLoan_tenor();
    }

    public void setMonths_remaining(String months_remaining) {
        this.months_remaining = months_remaining;
    }

    public String getApp_uninstallation_reason() {
        return app_uninstallation_reason;
    }

    public void setApp_uninstallation_reason(String app_uninstallation_reason) {
        this.app_uninstallation_reason = app_uninstallation_reason;
    }

    public String getInstallationStatus() {
        return "NA";
    }

    public String getLoanAmountCustom() {
        if (!TextUtils.isEmpty(this.loan_amount))
            return "₹ " + this.loan_amount;
        else
            return "NA";
    }

    public String getPrincipleAmountCustom() {
        if (!TextUtils.isEmpty(this.portfolio_amount))
            return "₹ " + this.portfolio_amount;
        else
            return "NA";
    }

    public String getEmiAmountCustom() {
        if (!TextUtils.isEmpty(this.emi))
            return "₹ " + this.emi;
        else
            return "NA";
    }

    public String getForeClosureAmountCustom() {
        if (!TextUtils.isEmpty(this.foreclosure_amount))
            return "₹ " + this.foreclosure_amount;
        else
            return "NA";
    }

    public String getOverdueAmountCustom() {
        if (!TextUtils.isEmpty(this.amount_overdue))
            return "₹ " + this.amount_overdue;
        else
            return "NA";
    }

    public String dpdMessage() {
        return "DPD : " + deliquency_bucket;
    }

    public String ewsMessage() {
        return "EWS : " + this.getEws_sum();
    }

    public String sourcing() {
        return sourcing_channel + " - " + sourcing_type;
    }

    public String getLast_visit_date() {
        return last_visit_date;
    }

    public void setLast_visit_date(String last_visit_date) {
        this.last_visit_date = last_visit_date;
    }

    public HashMap<String, ArrayList<EWS>> getEws_count() {
        return ews_count;
    }

    public void setEws_count(HashMap<String, ArrayList<EWS>> ews_count) {
        this.ews_count = ews_count;
    }

    public String getPortfolio_amount() {
        return portfolio_amount;
    }

    public void setPortfolio_amount(String portfolio_amount) {
        this.portfolio_amount = portfolio_amount;
    }

    public String isTop_up_eligibility() {
        return top_up_eligibility;
    }

    public boolean top_up_eligibility() {
        if (top_up_eligibility.equalsIgnoreCase("yes")) {
            return true;
        }
        return false;
    }

    public boolean isInstalled() {
        return this.app_installation_status.equalsIgnoreCase("1");
    }

    public void setTop_up_eligibility(String top_up_eligibility) {
        this.top_up_eligibility = top_up_eligibility;
    }

    public String getEmi() {
        return emi;
    }

    public void setEmi(String emi) {
        this.emi = emi;
    }

    public String getDeliquency_bucket() {
        return deliquency_bucket;
    }

    public void setDeliquency_bucket(String deliquency_bucket) {
        this.deliquency_bucket = deliquency_bucket;
    }

    public String getForeclosure_amount() {
        return foreclosure_amount;
    }

    public void setForeclosure_amount(String foreclosure_amount) {
        this.foreclosure_amount = foreclosure_amount;
    }

    public String getBusiness_address() {
        return business_address;
    }

    public void setBusiness_address(String business_address) {
        this.business_address = business_address;
    }

    public String getLoan_amount() {
        return loan_amount;
    }

    public void setLoan_amount(String loan_amount) {
        this.loan_amount = loan_amount;
    }

    public String getAmount_overdue() {
        return amount_overdue;
    }

    public void setAmount_overdue(String amount_overdue) {
        this.amount_overdue = amount_overdue;
    }

    public String getLoan_application_number() {
        return loan_application_number;
    }

    public void setLoan_application_number(String loan_application_number) {
        this.loan_application_number = loan_application_number;
    }

    public String getApplicant_name() {
        return applicant_name;
    }

    public void setApplicant_name(String applicant_name) {
        this.applicant_name = applicant_name;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getResidence_address() {
        return residence_address;
    }

    public void setResidence_address(String residence_address) {
        this.residence_address = residence_address;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getSourcing_type() {
        return sourcing_type;
    }

    public void setSourcing_type(String sourcing_type) {
        this.sourcing_type = sourcing_type;
    }

    public String getSourcing_channel() {
        return sourcing_channel;
    }

    public void setSourcing_channel(String sourcing_channel) {
        this.sourcing_channel = sourcing_channel;
    }

    public String getLoan_tenor() {
        return loan_tenor;
    }

    public void setLoan_tenor(String loan_tenor) {
        this.loan_tenor = loan_tenor;
    }

    public int getEws_sum() {
        return ews_sum;
    }

    public void setEws_sum(int ews_sum) {
        this.ews_sum = ews_sum;
    }

    public String getDate_of_disbursement() {
        return date_of_disbursement;
    }

    public void setDate_of_disbursement(String date_of_disbursement) {
        this.date_of_disbursement = date_of_disbursement;
    }

    public ArrayList<OverdueBreakup> getOverdue_breakup() {
        return overdue_breakup;
    }

    public void setOverdue_breakup(ArrayList<OverdueBreakup> overdue_breakup) {
        this.overdue_breakup = overdue_breakup;
    }

    public String getApp_installation_status() {
        return app_installation_status;
    }

    public ArrayList<String> getLast_visited_coordinates() {
        return last_visited_coordinates;
    }

    public void setLast_visited_coordinates(ArrayList<String> last_visited_coordinates) {
        this.last_visited_coordinates = last_visited_coordinates;
    }

    public int fetchAppInstallStatusInt() {
        try {
            return Integer.parseInt(app_installation_status);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void setApp_installation_status(String app_installation_status) {
        this.app_installation_status = app_installation_status;
    }

    public String fetchInstallationStatus() {
        if(!TextUtils.isEmpty(app_installation_status)) {
            switch (this.app_installation_status) {
                case "-1":
                    return "Not Installed";
                case "0":
                    return "UnInstalled";
                case "1":
                    return "Installed";
            }
            return "Not Installed";
        } else {
            return "";
        }
    }

    public String getmApplicantType() {
        return mApplicantType;
    }

    public void setmApplicantType(String mApplicantType) {
        this.mApplicantType = mApplicantType;
    }
}
