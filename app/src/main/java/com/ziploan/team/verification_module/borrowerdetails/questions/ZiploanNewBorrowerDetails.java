package com.ziploan.team.verification_module.borrowerdetails.questions;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.ziploan.team.verification_module.borrowerdetails.BankInfoModel;
import com.ziploan.team.verification_module.borrowerdetails.ZiploanNewQuestion;
import com.ziploan.team.verification_module.borrowerdetails.ZiploanSiteInfo;
import com.ziploan.team.webapi.EkycDetail;

import java.util.ArrayList;
import java.util.List;

public class ZiploanNewBorrowerDetails implements Parcelable {
    @SerializedName("loan_request_id") private String loan_request_id;
    @SerializedName("is_final") private boolean is_final;
    @SerializedName("lat") private String lat;
    @SerializedName("lng") private String lng;
    @SerializedName("site_info") private ZiploanSiteInfo site_info;
    @SerializedName("questions") private ArrayList<ZiploanNewQuestion> questions;
    @SerializedName("ekyc_details") private ArrayList<EkycDetail> ekyc_details;
    private List<BankInfoModel> loan_details;


    public ZiploanNewBorrowerDetails() {

    }


    protected ZiploanNewBorrowerDetails(Parcel in) {
        loan_request_id = in.readString();
        is_final = in.readByte() != 0;
        lat = in.readString();
        lng = in.readString();
        site_info = in.readParcelable(ZiploanSiteInfo.class.getClassLoader());
        questions = in.createTypedArrayList(ZiploanNewQuestion.CREATOR);
        loan_details = in.createTypedArrayList(BankInfoModel.CREATOR);
    }

    public static final Creator<ZiploanNewBorrowerDetails> CREATOR = new Creator<ZiploanNewBorrowerDetails>() {
        @Override
        public ZiploanNewBorrowerDetails createFromParcel(Parcel in) {
            return new ZiploanNewBorrowerDetails(in);
        }

        @Override
        public ZiploanNewBorrowerDetails[] newArray(int size) {
            return new ZiploanNewBorrowerDetails[size];
        }
    };

    public ZiploanSiteInfo getSite_info() {
        return site_info;
    }

    public void setSite_info(ZiploanSiteInfo site_info) {
        this.site_info = site_info;
    }

    public ArrayList<ZiploanNewQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<ZiploanNewQuestion> questions) {
        this.questions = questions;
    }

    public ArrayList<EkycDetail> getEkyc_details() {
        return ekyc_details;
    }

    public void setEkyc_details(ArrayList<EkycDetail> ekyc_details) {
        this.ekyc_details = ekyc_details;
    }

    public String getLoan_request_id() {
        return loan_request_id;
    }

    public void setLoan_request_id(String loan_request_id) {
        this.loan_request_id = loan_request_id;
    }

    public boolean is_final() {
        return is_final;
    }

    public void setIs_final(boolean is_final) {
        this.is_final = is_final;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(loan_request_id);
        parcel.writeByte((byte) (is_final ? 1 : 0));
        parcel.writeString(lat);
        parcel.writeString(lng);
        parcel.writeParcelable(site_info, i);
        parcel.writeTypedList(questions);
        parcel.writeTypedList(ekyc_details);
        parcel.writeTypedList(loan_details);
    }

    public List<BankInfoModel> getLoan_details() {
        return loan_details;
    }

    public void setLoan_details(List<BankInfoModel> loan_details) {
        this.loan_details = loan_details;
    }
}
