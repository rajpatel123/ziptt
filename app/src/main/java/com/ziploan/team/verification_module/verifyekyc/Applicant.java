package com.ziploan.team.verification_module.verifyekyc;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZIploan-Nitesh on 2/6/2017.
 */
public class Applicant implements Parcelable {
    @SerializedName("user_name") private String first_name;
    @SerializedName("last_name") private String last_name;
    @SerializedName("verify_status") private boolean verify_status;
    @SerializedName("applicant_type") private String applicant_type;
    @SerializedName("adhaar_no") private String adhaar_no;

    public Applicant(String firstName, String lastName, String adhaar_no, boolean status, String applicant_type) {
        this.first_name = firstName;
        this.last_name = lastName;
        this.verify_status = status;
        this.adhaar_no = adhaar_no;
        this.applicant_type = applicant_type;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public boolean isVerify_status() {
        return verify_status;
    }

    public void setVerify_status(boolean verify_status) {
        this.verify_status = verify_status;
    }

    public String getAdhaar_no() {
        return adhaar_no;
    }

    public void setAdhaar_no(String adhaar_no) {
        this.adhaar_no = adhaar_no;
    }

    public String getApplicant_type() {
        return applicant_type;
    }

    public void setApplicant_type(String applicant_type) {
        this.applicant_type = applicant_type;
    }

    protected Applicant(Parcel in) {
        first_name = in.readString();
        last_name = in.readString();
        adhaar_no = in.readString();
        applicant_type = in.readString();
        verify_status = in.readByte() != 0;
    }

    public static final Creator<Applicant> CREATOR = new Creator<Applicant>() {
        @Override
        public Applicant createFromParcel(Parcel in) {
            return new Applicant(in);
        }

        @Override
        public Applicant[] newArray(int size) {
            return new Applicant[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(first_name);
        parcel.writeString(last_name);
        parcel.writeString(adhaar_no);
        parcel.writeString(applicant_type);
        parcel.writeByte((byte) (verify_status ? 1 : 0));
    }
}
