package com.ziploan.team.asset_module.change_request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.File;

public class RepaymentBankChangeRequest implements Parcelable {
    @SerializedName("loan_request_id")
    private String loan_request_id;
    @SerializedName("request_category")
    private String request_category;
    @SerializedName("repayment_bank_name")
    private String repayment_bank_name;
    @SerializedName("repayment_bank_account_no")
    private String repayment_bank_account_no;
    @SerializedName("repayment_bank_ifsc")
    private String repayment_bank_ifsc;
    @SerializedName("repayment_bank_account_holder_name")
    private String repayment_bank_account_holder_name;
    @SerializedName("repayment_bank_branch")
    private String repayment_bank_branch;
    @SerializedName("repayment_bank_city")
    private String repayment_bank_city;
    @SerializedName("repayment_bank_account_type")
    private String repayment_bank_account_type;
    @SerializedName("proof_url")
    private String proof_url;

    public RepaymentBankChangeRequest() {

    }

    protected RepaymentBankChangeRequest(Parcel in) {
        loan_request_id = in.readString();
        request_category = in.readString();
        repayment_bank_name = in.readString();
        repayment_bank_account_no = in.readString();
        repayment_bank_ifsc = in.readString();
        repayment_bank_account_holder_name = in.readString();
        repayment_bank_branch = in.readString();
        repayment_bank_city = in.readString();
        repayment_bank_account_type = in.readString();
        proof_url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(loan_request_id);
        dest.writeString(request_category);
        dest.writeString(repayment_bank_name);
        dest.writeString(repayment_bank_account_no);
        dest.writeString(repayment_bank_ifsc);
        dest.writeString(repayment_bank_account_holder_name);
        dest.writeString(repayment_bank_branch);
        dest.writeString(repayment_bank_city);
        dest.writeString(repayment_bank_account_type);
        dest.writeString(proof_url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RepaymentBankChangeRequest> CREATOR = new Creator<RepaymentBankChangeRequest>() {
        @Override
        public RepaymentBankChangeRequest createFromParcel(Parcel in) {
            return new RepaymentBankChangeRequest(in);
        }

        @Override
        public RepaymentBankChangeRequest[] newArray(int size) {
            return new RepaymentBankChangeRequest[size];
        }
    };

    public String getProof_url() {
        return proof_url;
    }

    public void setProof_url(String proof_url) {
        this.proof_url = proof_url;
    }

    public String getLoan_request_id() {
        return loan_request_id;
    }

    public void setLoan_request_id(String loan_request_id) {
        this.loan_request_id = loan_request_id;
    }

    public String getRequest_category() {
        return request_category;
    }

    public void setRequest_category(String request_category) {
        this.request_category = request_category;
    }

    public String getRepayment_bank_name() {
        return repayment_bank_name;
    }

    public void setRepayment_bank_name(String repayment_bank_name) {
        this.repayment_bank_name = repayment_bank_name;
    }

    public String getRepayment_bank_account_no() {
        return repayment_bank_account_no;
    }

    public void setRepayment_bank_account_no(String repayment_bank_account_no) {
        this.repayment_bank_account_no = repayment_bank_account_no;
    }

    public String getRepayment_bank_ifsc() {
        return repayment_bank_ifsc;
    }

    public void setRepayment_bank_ifsc(String repayment_bank_ifsc) {
        this.repayment_bank_ifsc = repayment_bank_ifsc;
    }

    public String getRepayment_bank_account_holder_name() {
        return repayment_bank_account_holder_name;
    }

    public void setRepayment_bank_account_holder_name(String repayment_bank_account_holder_name) {
        this.repayment_bank_account_holder_name = repayment_bank_account_holder_name;
    }

    public String getRepayment_bank_branch() {
        return repayment_bank_branch;
    }

    public void setRepayment_bank_branch(String repayment_bank_branch) {
        this.repayment_bank_branch = repayment_bank_branch;
    }

    public String getRepayment_bank_city() {
        return repayment_bank_city;
    }

    public void setRepayment_bank_city(String repayment_bank_city) {
        this.repayment_bank_city = repayment_bank_city;
    }

    public String getRepayment_bank_account_type() {
        return repayment_bank_account_type;
    }

    public void setRepayment_bank_account_type(String repayment_bank_account_type) {
        this.repayment_bank_account_type = repayment_bank_account_type;
    }

    public String fetchPhotoFileName() {
        try {
            return new File(this.proof_url).getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}