package com.ziploan.team.verification_module.borrowerdetails;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class BankInfoModel implements Parcelable {

    @SerializedName("bank_name") private String name;
    @SerializedName("loan_amount") private String no_of_active_loans;
    @SerializedName("current_emi_amount") private String current_emi_amount;

    public BankInfoModel(String bank_name, String no_of_active_loans, String current_emi_amount){
        this.current_emi_amount = current_emi_amount;
        this.name = bank_name;
        this.no_of_active_loans = no_of_active_loans;
    }

    protected BankInfoModel(Parcel in) {
        name = in.readString();
        no_of_active_loans = in.readString();
        current_emi_amount = in.readString();
    }

    public static final Creator<BankInfoModel> CREATOR = new Creator<BankInfoModel>() {
        @Override
        public BankInfoModel createFromParcel(Parcel in) {
            return new BankInfoModel(in);
        }

        @Override
        public BankInfoModel[] newArray(int size) {
            return new BankInfoModel[size];
        }
    };

    public String getCurrent_emi_amount() {
        return current_emi_amount;
    }

    public String getName() {
        return name;
    }

    public String getNo_of_active_loans() {
        return no_of_active_loans;
    }

    public void setCurrent_emi_amount(String current_emi_amount) {
        this.current_emi_amount = current_emi_amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNo_of_active_loans(String no_of_active_loans) {
        this.no_of_active_loans = no_of_active_loans;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(no_of_active_loans);
        parcel.writeString(current_emi_amount);
    }
}
